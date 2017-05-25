package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Photo.
 */
public interface PhotoService {

    /**
     * Save a photo.
     *
     * @param photo the entity to save
     * @return the persisted entity
     */
    Photo save(Photo photo);

    /**
     *  Get all the photos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Photo> findAll(Pageable pageable);

    /**
     *  Get the "id" photo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Photo findOne(Long id);

    /**
     *  Delete the "id" photo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the photo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Photo> search(String query, Pageable pageable);
}
