package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.AspectvisibilityService;
import com.sgkhmjaes.jdias.domain.Aspectvisibility;
import com.sgkhmjaes.jdias.repository.AspectvisibilityRepository;
import com.sgkhmjaes.jdias.repository.search.AspectvisibilitySearchRepository;
import com.sgkhmjaes.jdias.service.dto.AspectvisibilityDTO;
import com.sgkhmjaes.jdias.service.mapper.AspectvisibilityMapper;
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
 * Service Implementation for managing Aspectvisibility.
 */
@Service
@Transactional
public class AspectvisibilityServiceImpl implements AspectvisibilityService{

    private final Logger log = LoggerFactory.getLogger(AspectvisibilityServiceImpl.class);

    private final AspectvisibilityRepository aspectvisibilityRepository;

    private final AspectvisibilityMapper aspectvisibilityMapper;

    private final AspectvisibilitySearchRepository aspectvisibilitySearchRepository;

    public AspectvisibilityServiceImpl(AspectvisibilityRepository aspectvisibilityRepository, AspectvisibilityMapper aspectvisibilityMapper, AspectvisibilitySearchRepository aspectvisibilitySearchRepository) {
        this.aspectvisibilityRepository = aspectvisibilityRepository;
        this.aspectvisibilityMapper = aspectvisibilityMapper;
        this.aspectvisibilitySearchRepository = aspectvisibilitySearchRepository;
    }

    /**
     * Save a aspectvisibility.
     *
     * @param aspectvisibilityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AspectvisibilityDTO save(AspectvisibilityDTO aspectvisibilityDTO) {
        log.debug("Request to save Aspectvisibility : {}", aspectvisibilityDTO);
        Aspectvisibility aspectvisibility = aspectvisibilityMapper.toEntity(aspectvisibilityDTO);
        aspectvisibility = aspectvisibilityRepository.save(aspectvisibility);
        AspectvisibilityDTO result = aspectvisibilityMapper.toDto(aspectvisibility);
        aspectvisibilitySearchRepository.save(aspectvisibility);
        return result;
    }

    /**
     *  Get all the aspectvisibilities.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AspectvisibilityDTO> findAll() {
        log.debug("Request to get all Aspectvisibilities");
        return aspectvisibilityRepository.findAll().stream()
            .map(aspectvisibilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one aspectvisibility by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AspectvisibilityDTO findOne(Long id) {
        log.debug("Request to get Aspectvisibility : {}", id);
        Aspectvisibility aspectvisibility = aspectvisibilityRepository.findOne(id);
        return aspectvisibilityMapper.toDto(aspectvisibility);
    }

    /**
     *  Delete the  aspectvisibility by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aspectvisibility : {}", id);
        aspectvisibilityRepository.delete(id);
        aspectvisibilitySearchRepository.delete(id);
    }

    /**
     * Search for the aspectvisibility corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AspectvisibilityDTO> search(String query) {
        log.debug("Request to search Aspectvisibilities for query {}", query);
        return StreamSupport
            .stream(aspectvisibilitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(aspectvisibilityMapper::toDto)
            .collect(Collectors.toList());
    }
}
