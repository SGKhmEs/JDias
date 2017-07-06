package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.service.AspectVisibilityService;
import com.sgkhmjaes.jdias.web.rest.util.HeaderUtil;
import com.sgkhmjaes.jdias.service.dto.AspectVisibilityDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AspectVisibility.
 */
@RestController
@RequestMapping("/api")
public class AspectVisibilityResource {

    private final Logger log = LoggerFactory.getLogger(AspectVisibilityResource.class);

    private static final String ENTITY_NAME = "aspectVisibility";

    private final AspectVisibilityService aspectVisibilityService;

    public AspectVisibilityResource(AspectVisibilityService aspectVisibilityService) {
        this.aspectVisibilityService = aspectVisibilityService;
    }

    /**
     * POST  /aspect-visibilities : Create a new aspectVisibility.
     *
     * @param aspectVisibilityDTO the aspectVisibilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aspectVisibilityDTO, or with status 400 (Bad Request) if the aspectVisibility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aspect-visibilities")
    @Timed
    public ResponseEntity<AspectVisibilityDTO> createAspectVisibility(@RequestBody AspectVisibilityDTO aspectVisibilityDTO) throws URISyntaxException {
        log.debug("REST request to save AspectVisibility : {}", aspectVisibilityDTO);
        if (aspectVisibilityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aspectVisibility cannot already have an ID")).body(null);
        }
        AspectVisibilityDTO result = aspectVisibilityService.save(aspectVisibilityDTO);
        return ResponseEntity.created(new URI("/api/aspect-visibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aspect-visibilities : Updates an existing aspectVisibility.
     *
     * @param aspectVisibilityDTO the aspectVisibilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aspectVisibilityDTO,
     * or with status 400 (Bad Request) if the aspectVisibilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the aspectVisibilityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aspect-visibilities")
    @Timed
    public ResponseEntity<AspectVisibilityDTO> updateAspectVisibility(@RequestBody AspectVisibilityDTO aspectVisibilityDTO) throws URISyntaxException {
        log.debug("REST request to update AspectVisibility : {}", aspectVisibilityDTO);
        if (aspectVisibilityDTO.getId() == null) {
            return createAspectVisibility(aspectVisibilityDTO);
        }
        AspectVisibilityDTO result = aspectVisibilityService.save(aspectVisibilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aspectVisibilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aspect-visibilities : get all the aspectVisibilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aspectVisibilities in body
     */
    @GetMapping("/aspect-visibilities")
    @Timed
    public List<AspectVisibilityDTO> getAllAspectVisibilities() {
        log.debug("REST request to get all AspectVisibilities");
        return aspectVisibilityService.findAll();
    }

    /**
     * GET  /aspect-visibilities/:id : get the "id" aspectVisibility.
     *
     * @param id the id of the aspectVisibilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aspectVisibilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/aspect-visibilities/{id}")
    @Timed
    public ResponseEntity<AspectVisibilityDTO> getAspectVisibility(@PathVariable Long id) {
        log.debug("REST request to get AspectVisibility : {}", id);
        AspectVisibilityDTO aspectVisibilityDTO = aspectVisibilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aspectVisibilityDTO));
    }

    /**
     * DELETE  /aspect-visibilities/:id : delete the "id" aspectVisibility.
     *
     * @param id the id of the aspectVisibilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aspect-visibilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAspectVisibility(@PathVariable Long id) {
        log.debug("REST request to delete AspectVisibility : {}", id);
        aspectVisibilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/aspect-visibilities?query=:query : search for the aspectVisibility corresponding
     * to the query.
     *
     * @param query the query of the aspectVisibility search
     * @return the result of the search
     */
    @GetMapping("/_search/aspect-visibilities")
    @Timed
    public List<AspectVisibilityDTO> searchAspectVisibilities(@RequestParam String query) {
        log.debug("REST request to search AspectVisibilities for query {}", query);
        return aspectVisibilityService.search(query);
    }

}
