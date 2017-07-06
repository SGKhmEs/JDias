package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.Like;
import com.sgkhmjaes.jdias.service.LikeService;
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
 * REST controller for managing Like.
 */
@RestController
@RequestMapping("/api")
public class LikeResource {

    private final Logger log = LoggerFactory.getLogger(LikeResource.class);

    private static final String ENTITY_NAME = "like";

    private final LikeService likeService;

    public LikeResource(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * POST  /likes : Create a new like.
     *
     * @param like the like to create
     * @return the ResponseEntity with status 201 (Created) and with body the new like, or with status 400 (Bad Request) if the like has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/likes")
    @Timed
    public ResponseEntity<Like> createLike(@RequestBody Like like) throws URISyntaxException {
        log.debug("REST request to save Like : {}", like);
        if (like.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new like cannot already have an ID")).body(null);
        }
        Like result = likeService.save(like);
        return ResponseEntity.created(new URI("/api/likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /likes : Updates an existing like.
     *
     * @param like the like to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated like,
     * or with status 400 (Bad Request) if the like is not valid,
     * or with status 500 (Internal Server Error) if the like couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/likes")
    @Timed
    public ResponseEntity<Like> updateLike(@RequestBody Like like) throws URISyntaxException {
        log.debug("REST request to update Like : {}", like);
        if (like.getId() == null) {
            return createLike(like);
        }
        Like result = likeService.save(like);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, like.getId().toString()))
            .body(result);
    }

    /**
     * GET  /likes : get all the likes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of likes in body
     */
    @GetMapping("/likes")
    @Timed
    public List<Like> getAllLikes() {
        log.debug("REST request to get all Likes");
        return likeService.findAll();
    }

    /**
     * GET  /likes/:id : get the "id" like.
     *
     * @param id the id of the like to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the like, or with status 404 (Not Found)
     */
    @GetMapping("/likes/{id}")
    @Timed
    public ResponseEntity<Like> getLike(@PathVariable Long id) {
        log.debug("REST request to get Like : {}", id);
        Like like = likeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(like));
    }

    /**
     * DELETE  /likes/:id : delete the "id" like.
     *
     * @param id the id of the like to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/likes/{id}")
    @Timed
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        log.debug("REST request to delete Like : {}", id);
        likeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/likes?query=:query : search for the like corresponding
     * to the query.
     *
     * @param query the query of the like search
     * @return the result of the search
     */
    @GetMapping("/_search/likes")
    @Timed
    public List<Like> searchLikes(@RequestParam String query) {
        log.debug("REST request to search Likes for query {}", query);
        return likeService.search(query);
    }

}
