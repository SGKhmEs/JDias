package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.HashTag;
import com.sgkhmjaes.jdias.service.TagService;
import com.sgkhmjaes.jdias.domain.Tag;
import com.sgkhmjaes.jdias.repository.HashTagRepository;
import com.sgkhmjaes.jdias.repository.TagRepository;
import com.sgkhmjaes.jdias.repository.search.HashTagSearchRepository;
import com.sgkhmjaes.jdias.repository.search.TagSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tag.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService{

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);
    private final TagRepository tagRepository;
    private final TagSearchRepository tagSearchRepository;
    private final HashTagRepository hashTagRepository;
    private final HashTagSearchRepository hashTagSearchRepository;

    public TagServiceImpl(TagRepository tagRepository, TagSearchRepository tagSearchRepository, 
            HashTagRepository hashTagRepository, HashTagSearchRepository hashTagSearchRepository) {
        this.tagRepository = tagRepository;
        this.tagSearchRepository = tagSearchRepository;
        this.hashTagRepository = hashTagRepository;
        this.hashTagSearchRepository = hashTagSearchRepository;
    }

    /**
     * Save a tag.
     *
     * @param tag the entity to save
     * @return the persisted entity
     */
    @Override
    public Tag save(Tag tag) {
        log.debug("Request to save Tag : {}", tag);
        Tag tagResult;
        Long tagHashCode = Long.valueOf(tag.hashCode());
        HashTag hashTag = hashTagRepository.getOne(tagHashCode);
        if (hashTag != null) {
            for (Tag tags : hashTag.getTags()) {
                if (tags.equals(tag)) return tag;
            }
        }
        else hashTag = new HashTag();
        
        tagResult = tagRepository.save(tag);
        tagSearchRepository.save(tagResult);
        hashTag.setId(tagHashCode);
        hashTag.addTags(tagResult);
        HashTag hashTagResult = hashTagRepository.save(hashTag);
        hashTagSearchRepository.save(hashTagResult);
        return tagResult;
    }

    /**
     *  Get all the tags.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        log.debug("Request to get all Tags");
        return tagRepository.findAll();
    }

    /**
     *  Get one tag by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Tag findOne(Long id) {
        log.debug("Request to get Tag : {}", id);
        return tagRepository.findOne(id);
    }

    /**
     *  Delete the  tag by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.delete(id);
        tagSearchRepository.delete(id);
    }

    /**
     * Search for the tag corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tag> search(String query) {
        log.debug("Request to search Tags for query {}", query);
        return StreamSupport
            .stream(tagSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
