package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.domain.Photo;
import com.sgkhmjaes.jdias.service.PhotoService;
import com.sgkhmjaes.jdias.service.StorageService;
import com.sgkhmjaes.jdias.storage.StorageFileNotFoundException;
import com.sgkhmjaes.jdias.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final StorageService storageService;

    private final Logger log = LoggerFactory.getLogger(FileUploadController.class);
    @Inject
    private PhotoService photoService;

    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public ResponseEntity<Photo[]> handleFileUpload(@RequestParam("file") MultipartFile[] file, RedirectAttributes redirectAttributes) throws URISyntaxException, IOException {
        Photo[] photos = new Photo[file.length];
        for (int i = 0; i<file.length; i++) {
            Path path = storageService.store(file[i]);
            redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file[i].getOriginalFilename() + "!");

            photos[i] = photoService.save(path.toFile());
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("PHOTOS", "UPLOADED"))
            .body(photos);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
