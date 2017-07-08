package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.AspectVisiblity;
import com.sgkhmjaes.jdias.service.AspectVisiblityService;
import com.sgkhmjaes.jdias.web.rest.util.HeaderUtil;
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
 * REST controller for managing AspectVisiblity.
 */
@RestController
@RequestMapping("/api")
public class AspectVisiblityResource {

    private final Logger log = LoggerFactory.getLogger(AspectVisiblityResource.class);

    private static final String ENTITY_NAME = "aspectVisiblity";

    private final AspectVisiblityService aspectVisiblityService;

    public AspectVisiblityResource(AspectVisiblityService aspectVisiblityService) {
        this.aspectVisiblityService = aspectVisiblityService;
    }

    /**
     * POST  /aspect-visiblities : Create a new aspectVisiblity.
     *
     * @param aspectVisiblity the aspectVisiblity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aspectVisiblity, or with status 400 (Bad Request) if the aspectVisiblity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aspect-visiblities")
    @Timed
    public ResponseEntity<AspectVisiblity> createAspectVisiblity(@RequestBody AspectVisiblity aspectVisiblity) throws URISyntaxException {
        log.debug("REST request to save AspectVisiblity : {}", aspectVisiblity);
        if (aspectVisiblity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aspectVisiblity cannot already have an ID")).body(null);
        }
        AspectVisiblity result = aspectVisiblityService.save(aspectVisiblity);
        return ResponseEntity.created(new URI("/api/aspect-visiblities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aspect-visiblities : Updates an existing aspectVisiblity.
     *
     * @param aspectVisiblity the aspectVisiblity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aspectVisiblity,
     * or with status 400 (Bad Request) if the aspectVisiblity is not valid,
     * or with status 500 (Internal Server Error) if the aspectVisiblity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aspect-visiblities")
    @Timed
    public ResponseEntity<AspectVisiblity> updateAspectVisiblity(@RequestBody AspectVisiblity aspectVisiblity) throws URISyntaxException {
        log.debug("REST request to update AspectVisiblity : {}", aspectVisiblity);
        if (aspectVisiblity.getId() == null) {
            return createAspectVisiblity(aspectVisiblity);
        }
        AspectVisiblity result = aspectVisiblityService.save(aspectVisiblity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aspectVisiblity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aspect-visiblities : get all the aspectVisiblities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aspectVisiblities in body
     */
    @GetMapping("/aspect-visiblities")
    @Timed
    public List<AspectVisiblity> getAllAspectVisiblities() {
        log.debug("REST request to get all AspectVisiblities");
        return aspectVisiblityService.findAll();
    }

    /**
     * GET  /aspect-visiblities/:id : get the "id" aspectVisiblity.
     *
     * @param id the id of the aspectVisiblity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aspectVisiblity, or with status 404 (Not Found)
     */
    @GetMapping("/aspect-visiblities/{id}")
    @Timed
    public ResponseEntity<AspectVisiblity> getAspectVisiblity(@PathVariable Long id) {
        log.debug("REST request to get AspectVisiblity : {}", id);
        AspectVisiblity aspectVisiblity = aspectVisiblityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aspectVisiblity));
    }

    /**
     * DELETE  /aspect-visiblities/:id : delete the "id" aspectVisiblity.
     *
     * @param id the id of the aspectVisiblity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aspect-visiblities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAspectVisiblity(@PathVariable Long id) {
        log.debug("REST request to delete AspectVisiblity : {}", id);
        aspectVisiblityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/aspect-visiblities?query=:query : search for the aspectVisiblity corresponding
     * to the query.
     *
     * @param query the query of the aspectVisiblity search
     * @return the result of the search
     */
    @GetMapping("/_search/aspect-visiblities")
    @Timed
    public List<AspectVisiblity> searchAspectVisiblities(@RequestParam String query) {
        log.debug("REST request to search AspectVisiblities for query {}", query);
        return aspectVisiblityService.search(query);
    }

}
