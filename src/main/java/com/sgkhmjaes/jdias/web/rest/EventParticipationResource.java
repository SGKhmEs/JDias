package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.EventParticipation;
import com.sgkhmjaes.jdias.service.EventParticipationService;
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
 * REST controller for managing EventParticipation.
 */
@RestController
@RequestMapping("/api")
public class EventParticipationResource {

    private final Logger log = LoggerFactory.getLogger(EventParticipationResource.class);

    private static final String ENTITY_NAME = "eventParticipation";

    private final EventParticipationService eventParticipationService;

    public EventParticipationResource(EventParticipationService eventParticipationService) {
        this.eventParticipationService = eventParticipationService;
    }

    /**
     * POST /event-participations : Create a new eventParticipation.
     *
     * @param eventParticipation the eventParticipation to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new eventParticipation, or with status 400 (Bad Request) if the
     * eventParticipation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/event-participations")
    @Timed
    public ResponseEntity<EventParticipation> createEventParticipation(@RequestBody EventParticipation eventParticipation) throws URISyntaxException {
        log.debug("REST request to save EventParticipation : {}", eventParticipation);
        if (eventParticipation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eventParticipation cannot already have an ID")).body(null);
        }
        EventParticipation result = eventParticipationService.save(eventParticipation);
        return ResponseEntity.created(new URI("/api/event-participations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /event-participations : Updates an existing eventParticipation.
     *
     * @param eventParticipation the eventParticipation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * eventParticipation, or with status 400 (Bad Request) if the
     * eventParticipation is not valid, or with status 500 (Internal Server
     * Error) if the eventParticipation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/event-participations")
    @Timed
    public ResponseEntity<EventParticipation> updateEventParticipation(@RequestBody EventParticipation eventParticipation) throws URISyntaxException {
        log.debug("REST request to update EventParticipation : {}", eventParticipation);
        if (eventParticipation.getId() == null) {
            return createEventParticipation(eventParticipation);
        }
        EventParticipation result = eventParticipationService.save(eventParticipation);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventParticipation.getId().toString()))
                .body(result);
    }

    /**
     * GET /event-participations : get all the eventParticipations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * eventParticipations in body
     */
    @GetMapping("/event-participations")
    @Timed
    public List<EventParticipation> getAllEventParticipations() {
        log.debug("REST request to get all EventParticipations");
        return eventParticipationService.findAll();
    }

    /**
     * GET /event-participations/:id : get the "id" eventParticipation.
     *
     * @param id the id of the eventParticipation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * eventParticipation, or with status 404 (Not Found)
     */
    @GetMapping("/event-participations/{id}")
    @Timed
    public ResponseEntity<EventParticipation> getEventParticipation(@PathVariable Long id) {
        log.debug("REST request to get EventParticipation : {}", id);
        EventParticipation eventParticipation = eventParticipationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eventParticipation));
    }

    /**
     * DELETE /event-participations/:id : delete the "id" eventParticipation.
     *
     * @param id the id of the eventParticipation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/event-participations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEventParticipation(@PathVariable Long id) {
        log.debug("REST request to delete EventParticipation : {}", id);
        eventParticipationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH /_search/event-participations?query=:query : search for the
     * eventParticipation corresponding to the query.
     *
     * @param query the query of the eventParticipation search
     * @return the result of the search
     */
    @GetMapping("/_search/event-participations")
    @Timed
    public List<EventParticipation> searchEventParticipations(@RequestParam String query) {
        log.debug("REST request to search EventParticipations for query {}", query);
        return eventParticipationService.search(query);
    }

}
