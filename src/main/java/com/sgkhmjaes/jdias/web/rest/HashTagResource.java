package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.HashTag;
import com.sgkhmjaes.jdias.service.HashTagService;
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
 * REST controller for managing HashTag.
 */
@RestController
@RequestMapping("/api")
public class HashTagResource {

    private final Logger log = LoggerFactory.getLogger(HashTagResource.class);

    private static final String ENTITY_NAME = "hashTag";

    private final HashTagService hashTagService;

    public HashTagResource(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }

    /**
     * POST  /hash-tags : Create a new hashTag.
     *
     * @param hashTag the hashTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hashTag, or with status 400 (Bad Request) if the hashTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hash-tags")
    @Timed
    public ResponseEntity<HashTag> createHashTag(@RequestBody HashTag hashTag) throws URISyntaxException {
        log.debug("REST request to save HashTag : {}", hashTag);
        if (hashTag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hashTag cannot already have an ID")).body(null);
        }
        HashTag result = hashTagService.save(hashTag);
        return ResponseEntity.created(new URI("/api/hash-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hash-tags : Updates an existing hashTag.
     *
     * @param hashTag the hashTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hashTag,
     * or with status 400 (Bad Request) if the hashTag is not valid,
     * or with status 500 (Internal Server Error) if the hashTag couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hash-tags")
    @Timed
    public ResponseEntity<HashTag> updateHashTag(@RequestBody HashTag hashTag) throws URISyntaxException {
        log.debug("REST request to update HashTag : {}", hashTag);
        if (hashTag.getId() == null) {
            return createHashTag(hashTag);
        }
        HashTag result = hashTagService.save(hashTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hashTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hash-tags : get all the hashTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hashTags in body
     */
    @GetMapping("/hash-tags")
    @Timed
    public List<HashTag> getAllHashTags() {
        log.debug("REST request to get all HashTags");
        return hashTagService.findAll();
    }

    /**
     * GET  /hash-tags/:id : get the "id" hashTag.
     *
     * @param id the id of the hashTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hashTag, or with status 404 (Not Found)
     */
    @GetMapping("/hash-tags/{id}")
    @Timed
    public ResponseEntity<HashTag> getHashTag(@PathVariable Long id) {
        log.debug("REST request to get HashTag : {}", id);
        HashTag hashTag = hashTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hashTag));
    }

    /**
     * DELETE  /hash-tags/:id : delete the "id" hashTag.
     *
     * @param id the id of the hashTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hash-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteHashTag(@PathVariable Long id) {
        log.debug("REST request to delete HashTag : {}", id);
        hashTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hash-tags?query=:query : search for the hashTag corresponding
     * to the query.
     *
     * @param query the query of the hashTag search
     * @return the result of the search
     */
    @GetMapping("/_search/hash-tags")
    @Timed
    public List<HashTag> searchHashTags(@RequestParam String query) {
        log.debug("REST request to search HashTags for query {}", query);
        return hashTagService.search(query);
    }

}
