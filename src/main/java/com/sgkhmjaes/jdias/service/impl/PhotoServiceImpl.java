package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.PhotoService;
import com.sgkhmjaes.jdias.domain.Photo;
import com.sgkhmjaes.jdias.repository.PhotoRepository;
import com.sgkhmjaes.jdias.repository.search.PhotoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Photo.
 */
@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);

    private final PhotoRepository photoRepository;

    private final PhotoSearchRepository photoSearchRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoSearchRepository photoSearchRepository) {
        this.photoRepository = photoRepository;
        this.photoSearchRepository = photoSearchRepository;
    }

    /**
     * Save a photo.
     *
     * @param photo the entity to save
     * @return the persisted entity
     */
    @Override
    public Photo save(Photo photo) {
        log.debug("Request to save Photo : {}", photo);
        Photo result = photoRepository.save(photo);
        photoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the photos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Photo> findAll() {
        log.debug("Request to get all Photos");
        return photoRepository.findAll();
    }

    /**
     * Get one photo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Photo findOne(Long id) {
        log.debug("Request to get Photo : {}", id);
        return photoRepository.findOne(id);
    }

    /**
     * Delete the photo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.delete(id);
        photoSearchRepository.delete(id);
    }

    /**
     * Search for the photo corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Photo> search(String query) {
        log.debug("Request to search Photos for query {}", query);
        return StreamSupport
                .stream(photoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
