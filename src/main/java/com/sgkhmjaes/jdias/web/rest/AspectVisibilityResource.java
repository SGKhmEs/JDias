package com.sgkhmjaes.jdias.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sgkhmjaes.jdias.service.AspectvisibilityService;
import com.sgkhmjaes.jdias.web.rest.util.HeaderUtil;
import com.sgkhmjaes.jdias.service.dto.AspectvisibilityDTO;
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
 * REST controller for managing Aspectvisibility.
 */
@RestController
@RequestMapping("/api")
public class AspectvisibilityResource {

    private final Logger log = LoggerFactory.getLogger(AspectvisibilityResource.class);

    private static final String ENTITY_NAME = "aspectvisibility";

    private final AspectvisibilityService aspectvisibilityService;

    public AspectvisibilityResource(AspectvisibilityService aspectvisibilityService) {
        this.aspectvisibilityService = aspectvisibilityService;
    }

    /**
     * POST  /aspectvisibilities : Create a new aspectvisibility.
     *
     * @param aspectvisibilityDTO the aspectvisibilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aspectvisibilityDTO, or with status 400 (Bad Request) if the aspectvisibility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aspectvisibilities")
    @Timed
    public ResponseEntity<AspectvisibilityDTO> createAspectvisibility(@RequestBody AspectvisibilityDTO aspectvisibilityDTO) throws URISyntaxException {
        log.debug("REST request to save Aspectvisibility : {}", aspectvisibilityDTO);
        if (aspectvisibilityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aspectvisibility cannot already have an ID")).body(null);
        }
        AspectvisibilityDTO result = aspectvisibilityService.save(aspectvisibilityDTO);
        return ResponseEntity.created(new URI("/api/aspectvisibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aspectvisibilities : Updates an existing aspectvisibility.
     *
     * @param aspectvisibilityDTO the aspectvisibilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aspectvisibilityDTO,
     * or with status 400 (Bad Request) if the aspectvisibilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the aspectvisibilityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aspectvisibilities")
    @Timed
    public ResponseEntity<AspectvisibilityDTO> updateAspectvisibility(@RequestBody AspectvisibilityDTO aspectvisibilityDTO) throws URISyntaxException {
        log.debug("REST request to update Aspectvisibility : {}", aspectvisibilityDTO);
        if (aspectvisibilityDTO.getId() == null) {
            return createAspectvisibility(aspectvisibilityDTO);
        }
        AspectvisibilityDTO result = aspectvisibilityService.save(aspectvisibilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aspectvisibilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aspectvisibilities : get all the aspectvisibilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aspectvisibilities in body
     */
    @GetMapping("/aspectvisibilities")
    @Timed
    public List<AspectvisibilityDTO> getAllAspectvisibilities() {
        log.debug("REST request to get all Aspectvisibilities");
        return aspectvisibilityService.findAll();
    }

    /**
     * GET  /aspectvisibilities/:id : get the "id" aspectvisibility.
     *
     * @param id the id of the aspectvisibilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aspectvisibilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/aspectvisibilities/{id}")
    @Timed
    public ResponseEntity<AspectvisibilityDTO> getAspectvisibility(@PathVariable Long id) {
        log.debug("REST request to get Aspectvisibility : {}", id);
        AspectvisibilityDTO aspectvisibilityDTO = aspectvisibilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aspectvisibilityDTO));
    }

    /**
     * DELETE  /aspectvisibilities/:id : delete the "id" aspectvisibility.
     *
     * @param id the id of the aspectvisibilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aspectvisibilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAspectvisibility(@PathVariable Long id) {
        log.debug("REST request to delete Aspectvisibility : {}", id);
        aspectvisibilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/aspectvisibilities?query=:query : search for the aspectvisibility corresponding
     * to the query.
     *
     * @param query the query of the aspectvisibility search
     * @return the result of the search
     */
    @GetMapping("/_search/aspectvisibilities")
    @Timed
    public List<AspectvisibilityDTO> searchAspectvisibilities(@RequestParam String query) {
        log.debug("REST request to search Aspectvisibilities for query {}", query);
        return aspectvisibilityService.search(query);
    }

}
