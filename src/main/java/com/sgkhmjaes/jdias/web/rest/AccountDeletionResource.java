package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.domain.AccountDeletion;
import com.sgkhmjaes.jdias.service.AccountDeletionService;
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
 * REST controller for managing AccountDeletion.
 */
@RestController
@RequestMapping("/api")
public class AccountDeletionResource {

    private final Logger log = LoggerFactory.getLogger(AccountDeletionResource.class);

    private static final String ENTITY_NAME = "accountDeletion";

    private final AccountDeletionService accountDeletionService;

    public AccountDeletionResource(AccountDeletionService accountDeletionService) {
        this.accountDeletionService = accountDeletionService;
    }

    /**
     * POST /account-deletions : Create a new accountDeletion.
     *
     * @param accountDeletion the accountDeletion to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new accountDeletion, or with status 400 (Bad Request) if the
     * accountDeletion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-deletions")
    @Timed
    public ResponseEntity<AccountDeletion> createAccountDeletion(@RequestBody AccountDeletion accountDeletion) throws URISyntaxException {
        log.debug("REST request to save AccountDeletion : {}", accountDeletion);
        if (accountDeletion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new accountDeletion cannot already have an ID")).body(null);
        }
        AccountDeletion result = accountDeletionService.save(accountDeletion);
        return ResponseEntity.created(new URI("/api/account-deletions/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /account-deletions : Updates an existing accountDeletion.
     *
     * @param accountDeletion the accountDeletion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * accountDeletion, or with status 400 (Bad Request) if the accountDeletion
     * is not valid, or with status 500 (Internal Server Error) if the
     * accountDeletion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-deletions")
    @Timed
    public ResponseEntity<AccountDeletion> updateAccountDeletion(@RequestBody AccountDeletion accountDeletion) throws URISyntaxException {
        log.debug("REST request to update AccountDeletion : {}", accountDeletion);
        if (accountDeletion.getId() == null) {
            return createAccountDeletion(accountDeletion);
        }
        AccountDeletion result = accountDeletionService.save(accountDeletion);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountDeletion.getId().toString()))
                .body(result);
    }

    /**
     * GET /account-deletions : get all the accountDeletions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * accountDeletions in body
     */
    @GetMapping("/account-deletions")
    @Timed
    public List<AccountDeletion> getAllAccountDeletions() {
        log.debug("REST request to get all AccountDeletions");
        return accountDeletionService.findAll();
    }

    /**
     * GET /account-deletions/:id : get the "id" accountDeletion.
     *
     * @param id the id of the accountDeletion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * accountDeletion, or with status 404 (Not Found)
     */
    @GetMapping("/account-deletions/{id}")
    @Timed
    public ResponseEntity<AccountDeletion> getAccountDeletion(@PathVariable Long id) {
        log.debug("REST request to get AccountDeletion : {}", id);
        AccountDeletion accountDeletion = accountDeletionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDeletion));
    }

    /**
     * DELETE /account-deletions/:id : delete the "id" accountDeletion.
     *
     * @param id the id of the accountDeletion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-deletions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccountDeletion(@PathVariable Long id) {
        log.debug("REST request to delete AccountDeletion : {}", id);
        accountDeletionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH /_search/account-deletions?query=:query : search for the
     * accountDeletion corresponding to the query.
     *
     * @param query the query of the accountDeletion search
     * @return the result of the search
     */
    @GetMapping("/_search/account-deletions")
    @Timed
    public List<AccountDeletion> searchAccountDeletions(@RequestParam String query) {
        log.debug("REST request to search AccountDeletions for query {}", query);
        return accountDeletionService.search(query);
    }

}
