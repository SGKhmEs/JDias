package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.PollAnswer;
import com.sgkhmjaes.jdias.service.PollAnswerService;
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
 * REST controller for managing PollAnswer.
 */
@RestController
@RequestMapping("/api")
public class PollAnswerResource {

    private final Logger log = LoggerFactory.getLogger(PollAnswerResource.class);

    private static final String ENTITY_NAME = "pollAnswer";

    private final PollAnswerService pollAnswerService;

    public PollAnswerResource(PollAnswerService pollAnswerService) {
        this.pollAnswerService = pollAnswerService;
    }

    /**
     * POST  /poll-answers : Create a new pollAnswer.
     *
     * @param pollAnswer the pollAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pollAnswer, or with status 400 (Bad Request) if the pollAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/poll-answers")
    @Timed
    public ResponseEntity<PollAnswer> createPollAnswer(@RequestBody PollAnswer pollAnswer) throws URISyntaxException {
        log.debug("REST request to save PollAnswer : {}", pollAnswer);
        if (pollAnswer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pollAnswer cannot already have an ID")).body(null);
        }
        PollAnswer result = pollAnswerService.save(pollAnswer);
        return ResponseEntity.created(new URI("/api/poll-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /poll-answers : Updates an existing pollAnswer.
     *
     * @param pollAnswer the pollAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pollAnswer,
     * or with status 400 (Bad Request) if the pollAnswer is not valid,
     * or with status 500 (Internal Server Error) if the pollAnswer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/poll-answers")
    @Timed
    public ResponseEntity<PollAnswer> updatePollAnswer(@RequestBody PollAnswer pollAnswer) throws URISyntaxException {
        log.debug("REST request to update PollAnswer : {}", pollAnswer);
        if (pollAnswer.getId() == null) {
            return createPollAnswer(pollAnswer);
        }
        PollAnswer result = pollAnswerService.save(pollAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pollAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /poll-answers : get all the pollAnswers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pollAnswers in body
     */
    @GetMapping("/poll-answers")
    @Timed
    public List<PollAnswer> getAllPollAnswers() {
        log.debug("REST request to get all PollAnswers");
        return pollAnswerService.findAll();
    }

    /**
     * GET  /poll-answers/:id : get the "id" pollAnswer.
     *
     * @param id the id of the pollAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pollAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/poll-answers/{id}")
    @Timed
    public ResponseEntity<PollAnswer> getPollAnswer(@PathVariable Long id) {
        log.debug("REST request to get PollAnswer : {}", id);
        PollAnswer pollAnswer = pollAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pollAnswer));
    }

    /**
     * DELETE  /poll-answers/:id : delete the "id" pollAnswer.
     *
     * @param id the id of the pollAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/poll-answers/{id}")
    @Timed
    public ResponseEntity<Void> deletePollAnswer(@PathVariable Long id) {
        log.debug("REST request to delete PollAnswer : {}", id);
        pollAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/poll-answers?query=:query : search for the pollAnswer corresponding
     * to the query.
     *
     * @param query the query of the pollAnswer search
     * @return the result of the search
     */
    @GetMapping("/_search/poll-answers")
    @Timed
    public List<PollAnswer> searchPollAnswers(@RequestParam String query) {
        log.debug("REST request to search PollAnswers for query {}", query);
        return pollAnswerService.search(query);
    }

}
