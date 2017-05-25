package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.StatusMessageService;
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
 * REST controller for managing StatusMessage.
 */
@RestController
@RequestMapping("/api")
public class StatusMessageResource {

    private final Logger log = LoggerFactory.getLogger(StatusMessageResource.class);

    private static final String ENTITY_NAME = "statusMessage";
        
    private final StatusMessageService statusMessageService;

    public StatusMessageResource(StatusMessageService statusMessageService) {
        this.statusMessageService = statusMessageService;
    }

    /**
     * POST  /status-messages : Create a new statusMessage.
     *
     * @param statusMessage the statusMessage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statusMessage, or with status 400 (Bad Request) if the statusMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/status-messages")
    @Timed
    public ResponseEntity<StatusMessage> createStatusMessage(@RequestBody StatusMessage statusMessage) throws URISyntaxException {
        log.debug("REST request to save StatusMessage : {}", statusMessage);
        if (statusMessage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new statusMessage cannot already have an ID")).body(null);
        }
        StatusMessage result = statusMessageService.save(statusMessage);
        return ResponseEntity.created(new URI("/api/status-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status-messages : Updates an existing statusMessage.
     *
     * @param statusMessage the statusMessage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statusMessage,
     * or with status 400 (Bad Request) if the statusMessage is not valid,
     * or with status 500 (Internal Server Error) if the statusMessage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/status-messages")
    @Timed
    public ResponseEntity<StatusMessage> updateStatusMessage(@RequestBody StatusMessage statusMessage) throws URISyntaxException {
        log.debug("REST request to update StatusMessage : {}", statusMessage);
        if (statusMessage.getId() == null) {
            return createStatusMessage(statusMessage);
        }
        StatusMessage result = statusMessageService.save(statusMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statusMessage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status-messages : get all the statusMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statusMessages in body
     */
    @GetMapping("/status-messages")
    @Timed
    public ResponseEntity<List<StatusMessage>> getAllStatusMessages(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of StatusMessages");
        Page<StatusMessage> page = statusMessageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /status-messages/:id : get the "id" statusMessage.
     *
     * @param id the id of the statusMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statusMessage, or with status 404 (Not Found)
     */
    @GetMapping("/status-messages/{id}")
    @Timed
    public ResponseEntity<StatusMessage> getStatusMessage(@PathVariable Long id) {
        log.debug("REST request to get StatusMessage : {}", id);
        StatusMessage statusMessage = statusMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statusMessage));
    }

    /**
     * DELETE  /status-messages/:id : delete the "id" statusMessage.
     *
     * @param id the id of the statusMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/status-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatusMessage(@PathVariable Long id) {
        log.debug("REST request to delete StatusMessage : {}", id);
        statusMessageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/status-messages?query=:query : search for the statusMessage corresponding
     * to the query.
     *
     * @param query the query of the statusMessage search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/status-messages")
    @Timed
    public ResponseEntity<List<StatusMessage>> searchStatusMessages(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of StatusMessages for query {}", query);
        Page<StatusMessage> page = statusMessageService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/status-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
