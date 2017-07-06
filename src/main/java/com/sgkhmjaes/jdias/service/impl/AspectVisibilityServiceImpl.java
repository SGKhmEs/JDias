package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.AspectVisibilityService;
import com.sgkhmjaes.jdias.domain.AspectVisibility;
import com.sgkhmjaes.jdias.repository.AspectVisibilityRepository;
import com.sgkhmjaes.jdias.repository.search.AspectVisibilitySearchRepository;
import com.sgkhmjaes.jdias.service.dto.AspectVisibilityDTO;
import com.sgkhmjaes.jdias.service.mapper.AspectVisibilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AspectVisibility.
 */
@Service
@Transactional
public class AspectVisibilityServiceImpl implements AspectVisibilityService{

    private final Logger log = LoggerFactory.getLogger(AspectVisibilityServiceImpl.class);

    private final AspectVisibilityRepository aspectVisibilityRepository;

    private final AspectVisibilityMapper aspectVisibilityMapper;

    private final AspectVisibilitySearchRepository aspectVisibilitySearchRepository;

    public AspectVisibilityServiceImpl(AspectVisibilityRepository aspectVisibilityRepository, AspectVisibilityMapper aspectVisibilityMapper, AspectVisibilitySearchRepository aspectVisibilitySearchRepository) {
        this.aspectVisibilityRepository = aspectVisibilityRepository;
        this.aspectVisibilityMapper = aspectVisibilityMapper;
        this.aspectVisibilitySearchRepository = aspectVisibilitySearchRepository;
    }

    /**
     * Save a aspectVisibility.
     *
     * @param aspectVisibilityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AspectVisibilityDTO save(AspectVisibilityDTO aspectVisibilityDTO) {
        log.debug("Request to save AspectVisibility : {}", aspectVisibilityDTO);
        AspectVisibility aspectVisibility = aspectVisibilityMapper.toEntity(aspectVisibilityDTO);
        aspectVisibility = aspectVisibilityRepository.save(aspectVisibility);
        AspectVisibilityDTO result = aspectVisibilityMapper.toDto(aspectVisibility);
        aspectVisibilitySearchRepository.save(aspectVisibility);
        return result;
    }

    /**
     *  Get all the aspectVisibilities.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AspectVisibilityDTO> findAll() {
        log.debug("Request to get all AspectVisibilities");
        return aspectVisibilityRepository.findAll().stream()
            .map(aspectVisibilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one aspectVisibility by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AspectVisibilityDTO findOne(Long id) {
        log.debug("Request to get AspectVisibility : {}", id);
        AspectVisibility aspectVisibility = aspectVisibilityRepository.findOne(id);
        return aspectVisibilityMapper.toDto(aspectVisibility);
    }

    /**
     *  Delete the  aspectVisibility by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AspectVisibility : {}", id);
        aspectVisibilityRepository.delete(id);
        aspectVisibilitySearchRepository.delete(id);
    }

    /**
     * Search for the aspectVisibility corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AspectVisibilityDTO> search(String query) {
        log.debug("Request to search AspectVisibilities for query {}", query);
        return StreamSupport
            .stream(aspectVisibilitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(aspectVisibilityMapper::toDto)
            .collect(Collectors.toList());
    }
}
