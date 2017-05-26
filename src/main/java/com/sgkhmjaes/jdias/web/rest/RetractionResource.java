package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.Retraction;

import com.sgkhmjaes.jdias.repository.RetractionRepository;
import com.sgkhmjaes.jdias.repository.search.RetractionSearchRepository;
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
 * REST controller for managing Retraction.
 */
@RestController
@RequestMapping("/api")
public class RetractionResource {

    private final Logger log = LoggerFactory.getLogger(RetractionResource.class);

    private static final String ENTITY_NAME = "retraction";
        
    private final RetractionRepository retractionRepository;

    private final RetractionSearchRepository retractionSearchRepository;

    public RetractionResource(RetractionRepository retractionRepository, RetractionSearchRepository retractionSearchRepository) {
        this.retractionRepository = retractionRepository;
        this.retractionSearchRepository = retractionSearchRepository;
    }

    /**
     * POST  /retractions : Create a new retraction.
     *
     * @param retraction the retraction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new retraction, or with status 400 (Bad Request) if the retraction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/retractions")
    @Timed
    public ResponseEntity<Retraction> createRetraction(@RequestBody Retraction retraction) throws URISyntaxException {
        log.debug("REST request to save Retraction : {}", retraction);
        if (retraction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new retraction cannot already have an ID")).body(null);
        }
        Retraction result = retractionRepository.save(retraction);
        retractionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/retractions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /retractions : Updates an existing retraction.
     *
     * @param retraction the retraction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated retraction,
     * or with status 400 (Bad Request) if the retraction is not valid,
     * or with status 500 (Internal Server Error) if the retraction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/retractions")
    @Timed
    public ResponseEntity<Retraction> updateRetraction(@RequestBody Retraction retraction) throws URISyntaxException {
        log.debug("REST request to update Retraction : {}", retraction);
        if (retraction.getId() == null) {
            return createRetraction(retraction);
        }
        Retraction result = retractionRepository.save(retraction);
        retractionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, retraction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /retractions : get all the retractions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of retractions in body
     */
    @GetMapping("/retractions")
    @Timed
    public List<Retraction> getAllRetractions() {
        log.debug("REST request to get all Retractions");
        List<Retraction> retractions = retractionRepository.findAll();
        return retractions;
    }

    /**
     * GET  /retractions/:id : get the "id" retraction.
     *
     * @param id the id of the retraction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the retraction, or with status 404 (Not Found)
     */
    @GetMapping("/retractions/{id}")
    @Timed
    public ResponseEntity<Retraction> getRetraction(@PathVariable Long id) {
        log.debug("REST request to get Retraction : {}", id);
        Retraction retraction = retractionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(retraction));
    }

    /**
     * DELETE  /retractions/:id : delete the "id" retraction.
     *
     * @param id the id of the retraction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/retractions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRetraction(@PathVariable Long id) {
        log.debug("REST request to delete Retraction : {}", id);
        retractionRepository.delete(id);
        retractionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/retractions?query=:query : search for the retraction corresponding
     * to the query.
     *
     * @param query the query of the retraction search 
     * @return the result of the search
     */
    @GetMapping("/_search/retractions")
    @Timed
    public List<Retraction> searchRetractions(@RequestParam String query) {
        log.debug("REST request to search Retractions for query {}", query);
        return StreamSupport
            .stream(retractionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
