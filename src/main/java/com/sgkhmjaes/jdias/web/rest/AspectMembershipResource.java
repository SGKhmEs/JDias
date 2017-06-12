package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.AspectMembership;
import com.sgkhmjaes.jdias.service.AspectMembershipService;
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
 * REST controller for managing AspectMembership.
 */
@RestController
@RequestMapping("/api")
public class AspectMembershipResource {

    private final Logger log = LoggerFactory.getLogger(AspectMembershipResource.class);

    private static final String ENTITY_NAME = "aspectMembership";

    private final AspectMembershipService aspectMembershipService;

    public AspectMembershipResource(AspectMembershipService aspectMembershipService) {
        this.aspectMembershipService = aspectMembershipService;
    }

    /**
     * POST /aspect-memberships : Create a new aspectMembership.
     *
     * @param aspectMembership the aspectMembership to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new aspectMembership, or with status 400 (Bad Request) if the
     * aspectMembership has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aspect-memberships")
    @Timed
    public ResponseEntity<AspectMembership> createAspectMembership(@RequestBody AspectMembership aspectMembership) throws URISyntaxException {
        log.debug("REST request to save AspectMembership : {}", aspectMembership);
        if (aspectMembership.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aspectMembership cannot already have an ID")).body(null);
        }
        AspectMembership result = aspectMembershipService.save(aspectMembership);
        return ResponseEntity.created(new URI("/api/aspect-memberships/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /aspect-memberships : Updates an existing aspectMembership.
     *
     * @param aspectMembership the aspectMembership to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * aspectMembership, or with status 400 (Bad Request) if the
     * aspectMembership is not valid, or with status 500 (Internal Server Error)
     * if the aspectMembership couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aspect-memberships")
    @Timed
    public ResponseEntity<AspectMembership> updateAspectMembership(@RequestBody AspectMembership aspectMembership) throws URISyntaxException {
        log.debug("REST request to update AspectMembership : {}", aspectMembership);
        if (aspectMembership.getId() == null) {
            return createAspectMembership(aspectMembership);
        }
        AspectMembership result = aspectMembershipService.save(aspectMembership);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aspectMembership.getId().toString()))
                .body(result);
    }

    /**
     * GET /aspect-memberships : get all the aspectMemberships.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * aspectMemberships in body
     */
    @GetMapping("/aspect-memberships")
    @Timed
    public List<AspectMembership> getAllAspectMemberships() {
        log.debug("REST request to get all AspectMemberships");
        return aspectMembershipService.findAll();
    }

    /**
     * GET /aspect-memberships/:id : get the "id" aspectMembership.
     *
     * @param id the id of the aspectMembership to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * aspectMembership, or with status 404 (Not Found)
     */
    @GetMapping("/aspect-memberships/{id}")
    @Timed
    public ResponseEntity<AspectMembership> getAspectMembership(@PathVariable Long id) {
        log.debug("REST request to get AspectMembership : {}", id);
        AspectMembership aspectMembership = aspectMembershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aspectMembership));
    }

    /**
     * DELETE /aspect-memberships/:id : delete the "id" aspectMembership.
     *
     * @param id the id of the aspectMembership to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aspect-memberships/{id}")
    @Timed
    public ResponseEntity<Void> deleteAspectMembership(@PathVariable Long id) {
        log.debug("REST request to delete AspectMembership : {}", id);
        aspectMembershipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH /_search/aspect-memberships?query=:query : search for the
     * aspectMembership corresponding to the query.
     *
     * @param query the query of the aspectMembership search
     * @return the result of the search
     */
    @GetMapping("/_search/aspect-memberships")
    @Timed
    public List<AspectMembership> searchAspectMemberships(@RequestParam String query) {
        log.debug("REST request to search AspectMemberships for query {}", query);
        return aspectMembershipService.search(query);
    }

}
