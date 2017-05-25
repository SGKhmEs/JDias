package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.EventParticipation;
import com.sgkhmjaes.jdias.service.EventParticipationService;
import com.sgkhmjaes.jdias.web.rest.util.HeaderUtil;
import com.sgkhmjaes.jdias.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
     * POST  /event-participations : Create a new eventParticipation.
     *
     * @param eventParticipation the eventParticipation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventParticipation, or with status 400 (Bad Request) if the eventParticipation has already an ID
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
     * PUT  /event-participations : Updates an existing eventParticipation.
     *
     * @param eventParticipation the eventParticipation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventParticipation,
     * or with status 400 (Bad Request) if the eventParticipation is not valid,
     * or with status 500 (Internal Server Error) if the eventParticipation couldnt be updated
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
     * GET  /event-participations : get all the eventParticipations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eventParticipations in body
     */
    @GetMapping("/event-participations")
    @Timed
    public ResponseEntity<List<EventParticipation>> getAllEventParticipations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EventParticipations");
        Page<EventParticipation> page = eventParticipationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/event-participations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /event-participations/:id : get the "id" eventParticipation.
     *
     * @param id the id of the eventParticipation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventParticipation, or with status 404 (Not Found)
     */
    @GetMapping("/event-participations/{id}")
    @Timed
    public ResponseEntity<EventParticipation> getEventParticipation(@PathVariable Long id) {
        log.debug("REST request to get EventParticipation : {}", id);
        EventParticipation eventParticipation = eventParticipationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eventParticipation));
    }

    /**
     * DELETE  /event-participations/:id : delete the "id" eventParticipation.
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
     * SEARCH  /_search/event-participations?query=:query : search for the eventParticipation corresponding
     * to the query.
     *
     * @param query the query of the eventParticipation search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/event-participations")
    @Timed
    public ResponseEntity<List<EventParticipation>> searchEventParticipations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of EventParticipations for query {}", query);
        Page<EventParticipation> page = eventParticipationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/event-participations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
