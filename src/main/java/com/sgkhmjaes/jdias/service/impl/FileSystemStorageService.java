package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.StorageService;
import com.sgkhmjaes.jdias.service.util.ImageConverter;
import com.sgkhmjaes.jdias.storage.StorageException;
import com.sgkhmjaes.jdias.storage.StorageFileNotFoundException;
import com.sgkhmjaes.jdias.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public File store(MultipartFile file) {
        Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        Path userPath = Paths.get(rootLocation + "/" + person.getGuid());
        if (!userPath.toFile().exists() && !userPath.toFile().isDirectory()) {
            userPath.toFile().mkdir();
        }
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), userPath.resolve(file.getOriginalFilename()));
            return load(file.getOriginalFilename()).toFile();

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        Path userPath = Paths.get(rootLocation + "/" + person.getGuid());
        try {
            return Files.walk(userPath, 1)
                    .filter(path -> !path.equals(userPath))
                    .map(path -> userPath.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        Path userPath = Paths.get(rootLocation + "/" + person.getGuid());
        return userPath.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        Path userPath = Paths.get(rootLocation + "/" + person.getGuid());
        FileSystemUtils.deleteRecursively(userPath.toFile());
    }

    @Override
    public void deleteImage(String filename) {
        Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        Path userPath = Paths.get(rootLocation + "/" + person.getGuid());
        FileSystemUtils.deleteRecursively(userPath.resolve(filename).toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
