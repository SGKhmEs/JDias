package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.PollParticipation;

import com.sgkhmjaes.jdias.repository.PollParticipationRepository;
import com.sgkhmjaes.jdias.repository.search.PollParticipationSearchRepository;
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
 * REST controller for managing PollParticipation.
 */
@RestController
@RequestMapping("/api")
public class PollParticipationResource {

    private final Logger log = LoggerFactory.getLogger(PollParticipationResource.class);

    private static final String ENTITY_NAME = "pollParticipation";
        
    private final PollParticipationRepository pollParticipationRepository;

    private final PollParticipationSearchRepository pollParticipationSearchRepository;

    public PollParticipationResource(PollParticipationRepository pollParticipationRepository, PollParticipationSearchRepository pollParticipationSearchRepository) {
        this.pollParticipationRepository = pollParticipationRepository;
        this.pollParticipationSearchRepository = pollParticipationSearchRepository;
    }

    /**
     * POST  /poll-participations : Create a new pollParticipation.
     *
     * @param pollParticipation the pollParticipation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pollParticipation, or with status 400 (Bad Request) if the pollParticipation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/poll-participations")
    @Timed
    public ResponseEntity<PollParticipation> createPollParticipation(@RequestBody PollParticipation pollParticipation) throws URISyntaxException {
        log.debug("REST request to save PollParticipation : {}", pollParticipation);
        if (pollParticipation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pollParticipation cannot already have an ID")).body(null);
        }
        PollParticipation result = pollParticipationRepository.save(pollParticipation);
        pollParticipationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/poll-participations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /poll-participations : Updates an existing pollParticipation.
     *
     * @param pollParticipation the pollParticipation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pollParticipation,
     * or with status 400 (Bad Request) if the pollParticipation is not valid,
     * or with status 500 (Internal Server Error) if the pollParticipation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/poll-participations")
    @Timed
    public ResponseEntity<PollParticipation> updatePollParticipation(@RequestBody PollParticipation pollParticipation) throws URISyntaxException {
        log.debug("REST request to update PollParticipation : {}", pollParticipation);
        if (pollParticipation.getId() == null) {
            return createPollParticipation(pollParticipation);
        }
        PollParticipation result = pollParticipationRepository.save(pollParticipation);
        pollParticipationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pollParticipation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /poll-participations : get all the pollParticipations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pollParticipations in body
     */
    @GetMapping("/poll-participations")
    @Timed
    public List<PollParticipation> getAllPollParticipations() {
        log.debug("REST request to get all PollParticipations");
        List<PollParticipation> pollParticipations = pollParticipationRepository.findAll();
        return pollParticipations;
    }

    /**
     * GET  /poll-participations/:id : get the "id" pollParticipation.
     *
     * @param id the id of the pollParticipation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pollParticipation, or with status 404 (Not Found)
     */
    @GetMapping("/poll-participations/{id}")
    @Timed
    public ResponseEntity<PollParticipation> getPollParticipation(@PathVariable Long id) {
        log.debug("REST request to get PollParticipation : {}", id);
        PollParticipation pollParticipation = pollParticipationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pollParticipation));
    }

    /**
     * DELETE  /poll-participations/:id : delete the "id" pollParticipation.
     *
     * @param id the id of the pollParticipation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/poll-participations/{id}")
    @Timed
    public ResponseEntity<Void> deletePollParticipation(@PathVariable Long id) {
        log.debug("REST request to delete PollParticipation : {}", id);
        pollParticipationRepository.delete(id);
        pollParticipationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/poll-participations?query=:query : search for the pollParticipation corresponding
     * to the query.
     *
     * @param query the query of the pollParticipation search 
     * @return the result of the search
     */
    @GetMapping("/_search/poll-participations")
    @Timed
    public List<PollParticipation> searchPollParticipations(@RequestParam String query) {
        log.debug("REST request to search PollParticipations for query {}", query);
        return StreamSupport
            .stream(pollParticipationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
