package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.Reshare;
import com.sgkhmjaes.jdias.service.ReshareService;
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
 * REST controller for managing Reshare.
 */
@RestController
@RequestMapping("/api")
public class ReshareResource {

    private final Logger log = LoggerFactory.getLogger(ReshareResource.class);

    private static final String ENTITY_NAME = "reshare";

    private final ReshareService reshareService;

    public ReshareResource(ReshareService reshareService) {
        this.reshareService = reshareService;
    }

    /**
     * POST /reshares : Create a new reshare.
     *
     * @param parrentPost the reshare to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new reshare, or with status 400 (Bad Request) if the reshare has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reshares")
    @Timed
    public ResponseEntity<Reshare> createReshare(@RequestBody Post parrentPost) throws URISyntaxException {
        log.debug("REST request to save Reshare : {}", parrentPost);
        if (parrentPost.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reshare cannot already have an ID")).body(null);
        }
        Reshare result = reshareService.save(parrentPost);
        return ResponseEntity.created(new URI("/api/reshares/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /reshares : Updates an existing reshare.
     *
     * @param post the reshare to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * reshare, or with status 400 (Bad Request) if the reshare is not valid, or
     * with status 500 (Internal Server Error) if the reshare couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reshares")
    @Timed
    public ResponseEntity<Reshare> updateReshare(@RequestBody Post post) throws URISyntaxException {
        log.debug("REST request to update Reshare : {}", post);
        if (post.getId() == null) {
            return createReshare(post);
        }
        Reshare result = reshareService.save(post);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, post.getId().toString()))
                .body(result);
    }

    /**
     * GET /reshares : get all the reshares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reshares
     * in body
     */
    @GetMapping("/reshares")
    @Timed
    public List<Reshare> getAllReshares() {
        log.debug("REST request to get all Reshares");
        return reshareService.findAll();
    }

    /**
     * GET /reshares/:id : get the "id" reshare.
     *
     * @param id the id of the reshare to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * reshare, or with status 404 (Not Found)
     */
    @GetMapping("/reshares/{id}")
    @Timed
    public ResponseEntity<Reshare> getReshare(@PathVariable Long id) {
        log.debug("REST request to get Reshare : {}", id);
        Reshare reshare = reshareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reshare));
    }

    /**
     * DELETE /reshares/:id : delete the "id" reshare.
     *
     * @param id the id of the reshare to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reshares/{id}")
    @Timed
    public ResponseEntity<Void> deleteReshare(@PathVariable Long id) {
        log.debug("REST request to delete Reshare : {}", id);
        reshareService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH /_search/reshares?query=:query : search for the reshare
     * corresponding to the query.
     *
     * @param query the query of the reshare search
     * @return the result of the search
     */
    @GetMapping("/_search/reshares")
    @Timed
    public List<Reshare> searchReshares(@RequestParam String query) {
        log.debug("REST request to search Reshares for query {}", query);
        return reshareService.search(query);
    }

}
