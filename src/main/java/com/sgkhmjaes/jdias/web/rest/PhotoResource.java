package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.Photo;
import com.sgkhmjaes.jdias.service.PhotoService;
import com.sgkhmjaes.jdias.web.rest.util.HeaderUtil;
import com.sgkhmjaes.jdias.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Photo.
 */
@RestController
@RequestMapping("/api")
public class PhotoResource {

    private final Logger log = LoggerFactory.getLogger(PhotoResource.class);

    private static final String ENTITY_NAME = "photo";
        
    private final PhotoService photoService;

    public PhotoResource(PhotoService photoService) {
        this.photoService = photoService;
    }

    /**
     * POST  /photos : Create a new photo.
     *
     * @param photo the photo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photo, or with status 400 (Bad Request) if the photo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/photos")
    @Timed
    public ResponseEntity<Photo> createPhoto(@RequestBody Photo photo) throws URISyntaxException {
        log.debug("REST request to save Photo : {}", photo);
        if (photo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new photo cannot already have an ID")).body(null);
        }
        Photo result = photoService.save(photo);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /photos : Updates an existing photo.
     *
     * @param photo the photo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated photo,
     * or with status 400 (Bad Request) if the photo is not valid,
     * or with status 500 (Internal Server Error) if the photo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/photos")
    @Timed
    public ResponseEntity<Photo> updatePhoto(@RequestBody Photo photo) throws URISyntaxException {
        log.debug("REST request to update Photo : {}", photo);
        if (photo.getId() == null) {
            return createPhoto(photo);
        }
        Photo result = photoService.save(photo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, photo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /photos : get all the photos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     */
    @GetMapping("/photos")
    @Timed
    public ResponseEntity<List<Photo>> getAllPhotos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Photos");
        Page<Photo> page = photoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/photos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /photos/:id : get the "id" photo.
     *
     * @param id the id of the photo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the photo, or with status 404 (Not Found)
     */
    @GetMapping("/photos/{id}")
    @Timed
    public ResponseEntity<Photo> getPhoto(@PathVariable Long id) {
        log.debug("REST request to get Photo : {}", id);
        Photo photo = photoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(photo));
    }

    /**
     * DELETE  /photos/:id : delete the "id" photo.
     *
     * @param id the id of the photo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/photos/{id}")
    @Timed
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        log.debug("REST request to delete Photo : {}", id);
        photoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/photos?query=:query : search for the photo corresponding
     * to the query.
     *
     * @param query the query of the photo search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/photos")
    @Timed
    public ResponseEntity<List<Photo>> searchPhotos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Photos for query {}", query);
        Page<Photo> page = photoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/photos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
