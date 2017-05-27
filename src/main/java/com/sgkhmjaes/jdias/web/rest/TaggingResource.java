package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.Tagging;

import com.sgkhmjaes.jdias.repository.TaggingRepository;
import com.sgkhmjaes.jdias.repository.search.TaggingSearchRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Tagging.
 */
@RestController
@RequestMapping("/api")
public class TaggingResource {

    private final Logger log = LoggerFactory.getLogger(TaggingResource.class);

    private static final String ENTITY_NAME = "tagging";
        
    private final TaggingRepository taggingRepository;

    private final TaggingSearchRepository taggingSearchRepository;

    public TaggingResource(TaggingRepository taggingRepository, TaggingSearchRepository taggingSearchRepository) {
        this.taggingRepository = taggingRepository;
        this.taggingSearchRepository = taggingSearchRepository;
    }

    /**
     * POST  /taggings : Create a new tagging.
     *
     * @param tagging the tagging to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tagging, or with status 400 (Bad Request) if the tagging has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/taggings")
    @Timed
    public ResponseEntity<Tagging> createTagging(@RequestBody Tagging tagging) throws URISyntaxException {
        log.debug("REST request to save Tagging : {}", tagging);
        if (tagging.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tagging cannot already have an ID")).body(null);
        }
        Tagging result = taggingRepository.save(tagging);
        taggingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/taggings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taggings : Updates an existing tagging.
     *
     * @param tagging the tagging to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tagging,
     * or with status 400 (Bad Request) if the tagging is not valid,
     * or with status 500 (Internal Server Error) if the tagging couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/taggings")
    @Timed
    public ResponseEntity<Tagging> updateTagging(@RequestBody Tagging tagging) throws URISyntaxException {
        log.debug("REST request to update Tagging : {}", tagging);
        if (tagging.getId() == null) {
            return createTagging(tagging);
        }
        Tagging result = taggingRepository.save(tagging);
        taggingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tagging.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taggings : get all the taggings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taggings in body
     */
    @GetMapping("/taggings")
    @Timed
    public List<Tagging> getAllTaggings() {
        log.debug("REST request to get all Taggings");
        List<Tagging> taggings = taggingRepository.findAll();
        return taggings;
    }

    /**
     * GET  /taggings/:id : get the "id" tagging.
     *
     * @param id the id of the tagging to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tagging, or with status 404 (Not Found)
     */
    @GetMapping("/taggings/{id}")
    @Timed
    public ResponseEntity<Tagging> getTagging(@PathVariable Long id) {
        log.debug("REST request to get Tagging : {}", id);
        Tagging tagging = taggingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tagging));
    }

    /**
     * DELETE  /taggings/:id : delete the "id" tagging.
     *
     * @param id the id of the tagging to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/taggings/{id}")
    @Timed
    public ResponseEntity<Void> deleteTagging(@PathVariable Long id) {
        log.debug("REST request to delete Tagging : {}", id);
        taggingRepository.delete(id);
        taggingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/taggings?query=:query : search for the tagging corresponding
     * to the query.
     *
     * @param query the query of the tagging search 
     * @return the result of the search
     */
    @GetMapping("/_search/taggings")
    @Timed
    public List<Tagging> searchTaggings(@RequestParam String query) {
        log.debug("REST request to search Taggings for query {}", query);
        return StreamSupport
            .stream(taggingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
