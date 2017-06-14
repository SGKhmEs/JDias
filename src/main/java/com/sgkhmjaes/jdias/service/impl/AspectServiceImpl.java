package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.UserAccount;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.AspectService;
import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.repository.AspectRepository;
import com.sgkhmjaes.jdias.repository.search.AspectSearchRepository;
import com.sgkhmjaes.jdias.service.dto.aspectDTOs.AspectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Aspect.
 */
@Service
@Transactional
public class AspectServiceImpl implements AspectService{

    private final Logger log = LoggerFactory.getLogger(AspectServiceImpl.class);

    private final AspectRepository aspectRepository;

    private final AspectSearchRepository aspectSearchRepository;

    @Inject
    private UserRepository userRepository;

    public AspectServiceImpl(AspectRepository aspectRepository, AspectSearchRepository aspectSearchRepository) {
        this.aspectRepository = aspectRepository;
        this.aspectSearchRepository = aspectSearchRepository;
    }

    /**
     * Save a aspect.
     *
     * @param aspect the entity to save
     * @return the persisted entity
     */
    @Override
    public Aspect save(Aspect aspect) {
        log.debug("Request to save Aspect : {}", aspect);
        Aspect result = aspectRepository.save(aspect);
        aspectSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the aspects.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Aspect> findAll() {
        log.debug("Request to get all Aspects");
        return aspectRepository.findAll();
    }

    /**
     *  Get all the aspects by user id.
     *
     *  @return the list of entities by user id
     */
    @Override
    public List<Aspect> findAllByUserId() {
        log.debug("Request to get all Aspects by user id");
        Long userId = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId();
        return aspectRepository.findAllByAspectMemberships(userId);
    }

    /**
     *  Get one aspect by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Aspect findOne(Long id) {
        log.debug("Request to get Aspect : {}", id);
        return aspectRepository.findOne(id);
    }

    /**
     *  Delete the  aspect by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aspect : {}", id);
        aspectRepository.delete(id);
        aspectSearchRepository.delete(id);
    }

    /**
     * Search for the aspect corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Aspect> search(String query) {
        log.debug("Request to search Aspects for query {}", query);
        return StreamSupport
            .stream(aspectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
