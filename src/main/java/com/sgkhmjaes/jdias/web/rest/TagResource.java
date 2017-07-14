package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.service.TagService;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

/**
 * REST controller for managing Tag.
 */
@RestController
@RequestMapping("/api")
public class TagResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);
    private final TagService tagService;

    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }
    
    @PostMapping("/tags/posts")
    @Timed
    public Set <Post> searchPostByTag(@RequestBody String tag) throws URISyntaxException {
        log.debug("REST request to search post by tag : {}", tag);
        if (tag == null || tag.isEmpty())return null;
        else return tagService.findPostsByTag(tag);
    }
    
    @PostMapping("/tags/persons")
    @Timed
    public Set <Person> searchPersonByTag(@RequestBody String tag) throws URISyntaxException {
        log.debug("REST request to search post by tag : {}", tag);
        if (tag == null || tag.isEmpty())return null;
        else return tagService.findPersonByTag(tag);
    }
    
    /**
     * PUT  /tags : Updates an existing tag.
     *
     * @param tag the tag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tag,
     * or with status 400 (Bad Request) if the tag is not valid,
     * or with status 500 (Internal Server Error) if the tag couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*
    @PutMapping("/tags")
    @Timed
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag) throws URISyntaxException {
        log.debug("REST request to update Tag : {}", tag);
        if (tag.getId() == null) {
            return createTag(tag);
        }
        Tag result = tagRepository.save(tag);
        tagSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tag.getId().toString()))
            .body(result);
    }
*/
    /**
     * GET  /tags : get all the tags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tags in body
     */
    /*
    @GetMapping("/tags")
    @Timed
    public List<Tag> getAllTags() {
        log.debug("REST request to get all Tags");
        return tagService.findAll();
    }
*/
    /**
     * GET  /tags/:id : get the "id" tag.
     *
     * @param id the id of the tag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tag, or with status 404 (Not Found)
     */
    /*
    @GetMapping("/tags/{id}")
    @Timed
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        log.debug("REST request to get Tag : {}", id);
        Tag tag = tagRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tag));
    }
*/
    /**
     * DELETE  /tags/:id : delete the "id" tag.
     *
     * @param id the id of the tag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    /*
    @DeleteMapping("/tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        log.debug("REST request to delete Tag : {}", id);
        tagRepository.delete(id);
        tagSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
*/
    /**
     * SEARCH  /_search/tags?query=:query : search for the tag corresponding
     * to the query.
     *
     * @param query the query of the tag search
     * @return the result of the search
     */
    /*
    @GetMapping("/_search/tags")
    @Timed
    public List<Tag> searchTags(@RequestParam String query) {
        log.debug("REST request to search Tags for query {}", query);
        return tagService.search(query);
    }
*/
}
