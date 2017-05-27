package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.Aspect;

import com.sgkhmjaes.jdias.repository.AspectRepository;
import com.sgkhmjaes.jdias.repository.search.AspectSearchRepository;
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
 * REST controller for managing Aspect.
 */
@RestController
@RequestMapping("/api")
public class AspectResource {

    private final Logger log = LoggerFactory.getLogger(AspectResource.class);

    private static final String ENTITY_NAME = "aspect";
        
    private final AspectRepository aspectRepository;

    private final AspectSearchRepository aspectSearchRepository;

    public AspectResource(AspectRepository aspectRepository, AspectSearchRepository aspectSearchRepository) {
        this.aspectRepository = aspectRepository;
        this.aspectSearchRepository = aspectSearchRepository;
    }

    /**
     * POST  /aspects : Create a new aspect.
     *
     * @param aspect the aspect to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aspect, or with status 400 (Bad Request) if the aspect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aspects")
    @Timed
    public ResponseEntity<Aspect> createAspect(@RequestBody Aspect aspect) throws URISyntaxException {
        log.debug("REST request to save Aspect : {}", aspect);
        if (aspect.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aspect cannot already have an ID")).body(null);
        }
        Aspect result = aspectRepository.save(aspect);
        aspectSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aspects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aspects : Updates an existing aspect.
     *
     * @param aspect the aspect to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aspect,
     * or with status 400 (Bad Request) if the aspect is not valid,
     * or with status 500 (Internal Server Error) if the aspect couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aspects")
    @Timed
    public ResponseEntity<Aspect> updateAspect(@RequestBody Aspect aspect) throws URISyntaxException {
        log.debug("REST request to update Aspect : {}", aspect);
        if (aspect.getId() == null) {
            return createAspect(aspect);
        }
        Aspect result = aspectRepository.save(aspect);
        aspectSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aspect.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aspects : get all the aspects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aspects in body
     */
    @GetMapping("/aspects")
    @Timed
    public List<Aspect> getAllAspects() {
        log.debug("REST request to get all Aspects");
        List<Aspect> aspects = aspectRepository.findAll();
        return aspects;
    }

    /**
     * GET  /aspects/:id : get the "id" aspect.
     *
     * @param id the id of the aspect to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aspect, or with status 404 (Not Found)
     */
    @GetMapping("/aspects/{id}")
    @Timed
    public ResponseEntity<Aspect> getAspect(@PathVariable Long id) {
        log.debug("REST request to get Aspect : {}", id);
        Aspect aspect = aspectRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aspect));
    }

    /**
     * DELETE  /aspects/:id : delete the "id" aspect.
     *
     * @param id the id of the aspect to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aspects/{id}")
    @Timed
    public ResponseEntity<Void> deleteAspect(@PathVariable Long id) {
        log.debug("REST request to delete Aspect : {}", id);
        aspectRepository.delete(id);
        aspectSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/aspects?query=:query : search for the aspect corresponding
     * to the query.
     *
     * @param query the query of the aspect search 
     * @return the result of the search
     */
    @GetMapping("/_search/aspects")
    @Timed
    public List<Aspect> searchAspects(@RequestParam String query) {
        log.debug("REST request to search Aspects for query {}", query);
        return StreamSupport
            .stream(aspectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
