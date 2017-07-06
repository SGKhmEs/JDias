package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.TagFollowing;
import com.sgkhmjaes.jdias.service.TagFollowingService;
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
 * REST controller for managing TagFollowing.
 */
@RestController
@RequestMapping("/api")
public class TagFollowingResource {

    private final Logger log = LoggerFactory.getLogger(TagFollowingResource.class);

    private static final String ENTITY_NAME = "tagFollowing";

    private final TagFollowingService tagFollowingService;

    public TagFollowingResource(TagFollowingService tagFollowingService) {
        this.tagFollowingService = tagFollowingService;
    }

    /**
     * POST  /tag-followings : Create a new tagFollowing.
     *
     * @param tagFollowing the tagFollowing to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tagFollowing, or with status 400 (Bad Request) if the tagFollowing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tag-followings")
    @Timed
    public ResponseEntity<TagFollowing> createTagFollowing(@RequestBody TagFollowing tagFollowing) throws URISyntaxException {
        log.debug("REST request to save TagFollowing : {}", tagFollowing);
        if (tagFollowing.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tagFollowing cannot already have an ID")).body(null);
        }
        TagFollowing result = tagFollowingService.save(tagFollowing);
        return ResponseEntity.created(new URI("/api/tag-followings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tag-followings : Updates an existing tagFollowing.
     *
     * @param tagFollowing the tagFollowing to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tagFollowing,
     * or with status 400 (Bad Request) if the tagFollowing is not valid,
     * or with status 500 (Internal Server Error) if the tagFollowing couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tag-followings")
    @Timed
    public ResponseEntity<TagFollowing> updateTagFollowing(@RequestBody TagFollowing tagFollowing) throws URISyntaxException {
        log.debug("REST request to update TagFollowing : {}", tagFollowing);
        if (tagFollowing.getId() == null) {
            return createTagFollowing(tagFollowing);
        }
        TagFollowing result = tagFollowingService.save(tagFollowing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tagFollowing.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tag-followings : get all the tagFollowings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tagFollowings in body
     */
    @GetMapping("/tag-followings")
    @Timed
    public List<TagFollowing> getAllTagFollowings() {
        log.debug("REST request to get all TagFollowings");
        return tagFollowingService.findAll();
    }

    /**
     * GET  /tag-followings/:id : get the "id" tagFollowing.
     *
     * @param id the id of the tagFollowing to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tagFollowing, or with status 404 (Not Found)
     */
    @GetMapping("/tag-followings/{id}")
    @Timed
    public ResponseEntity<TagFollowing> getTagFollowing(@PathVariable Long id) {
        log.debug("REST request to get TagFollowing : {}", id);
        TagFollowing tagFollowing = tagFollowingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tagFollowing));
    }

    /**
     * DELETE  /tag-followings/:id : delete the "id" tagFollowing.
     *
     * @param id the id of the tagFollowing to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tag-followings/{id}")
    @Timed
    public ResponseEntity<Void> deleteTagFollowing(@PathVariable Long id) {
        log.debug("REST request to delete TagFollowing : {}", id);
        tagFollowingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tag-followings?query=:query : search for the tagFollowing corresponding
     * to the query.
     *
     * @param query the query of the tagFollowing search
     * @return the result of the search
     */
    @GetMapping("/_search/tag-followings")
    @Timed
    public List<TagFollowing> searchTagFollowings(@RequestParam String query) {
        log.debug("REST request to search TagFollowings for query {}", query);
        return tagFollowingService.search(query);
    }

}
