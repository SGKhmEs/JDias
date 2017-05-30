package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.service.ConversationService;
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
 * REST controller for managing Conversation.
 */
@RestController
@RequestMapping("/api")
public class ConversationResource {

    private final Logger log = LoggerFactory.getLogger(ConversationResource.class);

    private static final String ENTITY_NAME = "conversation";

    private final ConversationService conversationService;

    public ConversationResource(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    /**
     * POST  /conversations : Create a new conversation.
     *
     * @param conversation the conversation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conversation, or with status 400 (Bad Request) if the conversation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conversations")
    @Timed
    public ResponseEntity<Conversation> createConversation(@RequestBody Conversation conversation) throws URISyntaxException {
        log.debug("REST request to save Conversation : {}", conversation);
        if (conversation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new conversation cannot already have an ID")).body(null);
        }
        Conversation result = conversationService.save(conversation);
        return ResponseEntity.created(new URI("/api/conversations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conversations : Updates an existing conversation.
     *
     * @param conversation the conversation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conversation,
     * or with status 400 (Bad Request) if the conversation is not valid,
     * or with status 500 (Internal Server Error) if the conversation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conversations")
    @Timed
    public ResponseEntity<Conversation> updateConversation(@RequestBody Conversation conversation) throws URISyntaxException {
        log.debug("REST request to update Conversation : {}", conversation);
        if (conversation.getId() == null) {
            return createConversation(conversation);
        }
        Conversation result = conversationService.save(conversation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conversation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conversations : get all the conversations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conversations in body
     */
    @GetMapping("/conversations")
    @Timed
    public ResponseEntity<List<Conversation>> getAllConversations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Conversations");
        Page<Conversation> page = conversationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/conversations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /conversations/:id : get the "id" conversation.
     *
     * @param id the id of the conversation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conversation, or with status 404 (Not Found)
     */
    @GetMapping("/conversations/{id}")
    @Timed
    public ResponseEntity<Conversation> getConversation(@PathVariable Long id) {
        log.debug("REST request to get Conversation : {}", id);
        Conversation conversation = conversationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conversation));
    }

    /**
     * DELETE  /conversations/:id : delete the "id" conversation.
     *
     * @param id the id of the conversation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conversations/{id}")
    @Timed
    public ResponseEntity<Void> deleteConversation(@PathVariable Long id) {
        log.debug("REST request to delete Conversation : {}", id);
        conversationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/conversations?query=:query : search for the conversation corresponding
     * to the query.
     *
     * @param query the query of the conversation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/conversations")
    @Timed
    public ResponseEntity<List<Conversation>> searchConversations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Conversations for query {}", query);
        Page<Conversation> page = conversationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/conversations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
