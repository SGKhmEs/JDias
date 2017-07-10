package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.HashTag;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.TagService;
import com.sgkhmjaes.jdias.domain.Tag;
import com.sgkhmjaes.jdias.domain.TagFollowing;
import com.sgkhmjaes.jdias.domain.Tagging;
import com.sgkhmjaes.jdias.repository.HashTagRepository;
import com.sgkhmjaes.jdias.repository.TagRepository;
import com.sgkhmjaes.jdias.repository.search.HashTagSearchRepository;
import com.sgkhmjaes.jdias.repository.search.TagSearchRepository;
import com.sgkhmjaes.jdias.service.util.SearchTags;
import java.util.ArrayList;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
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
    
    /*
    @Override
    public Tag save(Tag tag) {
        log.debug("Request to save Tag : {}", tag);
        return save(tag, null, Long.valueOf(tag.getTagContext().hashCode()));
    }
    */
    //for testing
    @Override
    public Tag save(Tag tag) {
        log.debug("Request to save Tag : {}", tag);
        
        //created post and statusMessage for testing
        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setText(tag.getTagContext());
        Post post = new Post();
        post.setStatusMessage(statusMessage);
        Set<Tag> saveAllPostsTags = saveAllPostsTags(post);
        
        //collection created for testing
        ArrayList<Tag> arrayList = new ArrayList <> (saveAllPostsTags);
        if (arrayList.isEmpty()) {
            return null;
        }
        else return arrayList.get(0);
    }
    
    public Set <Tag> saveAllPostsTags (Post post){
        String postText = post.getStatusMessage().getText();
        Set<String> tagContextSet = new SearchTags ().searchingTags(postText);
        Set <Tag> tags = new HashSet <>();
        for (String tagContext : tagContextSet) {
            Long tagsHash = getHashCode(tagContext);
            Tag searchTagByContext = searchTagByContext(tagContext, tagsHash);
            //tag with such hashcode dont exsist
            if (searchTagByContext == null) {
                //comment for testin 
                //Tag tag = save(tag, post, tagsHash);
                Tag tagResult = save(new Tag(tagContext), null, tagsHash);
                tags.add(tagResult);
            }
            //tag with such hashcode already exsist
            else{
                //comment for testin 
                //Tag tag = save(searchTagByContext, post, tagsHash);
                Tag tag = save(searchTagByContext, null, tagsHash);
                tags.add(tag);
            }
        }
        return tags;
    }
    
    private Tag save(Tag tag, Post post, Long tagsHash) {
        //if (post != null) tag.addPost(post);   
        
        Tagging tagging = new Tagging(tag, post);
        TagFollowing tagFollowing = new TagFollowing();
        
        HashTag hashTag = new HashTag();
        hashTag.setId(tagsHash);
        hashTagRepository.saveAndFlush(hashTag);
        tag.setHashTag(hashTag);
        
        Tag tagResult = tagRepository.saveAndFlush(tag);
        tagSearchRepository.save(tagResult);
        hashTag.addTag(tagResult);
        HashTag hashTagResult = hashTagRepository.save(hashTag);
        hashTagSearchRepository.save(hashTagResult);
        

        
        return tagResult;
    }
    
    private Tag searchTagByContext (String tagContext, Long tagsHash) {
        HashTag findTag = hashTagRepository.findOne(tagsHash);
        if (findTag != null) {
            for (Tag tag : findTag.getTags()) {
                if (tag.getTagContext().equals(tagContext)) return tag;
            }
        }
        return null;
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
    
    public Set <Post> findPostsByTag (String tagContext){
        Tag searchTagByContext = searchTagByContext(tagContext, getHashCode(tagContext));
        //if(searchTagByContext == null)
            return new HashSet <> ();
        //else return searchTagByContext.getPosts();
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
        Tag searchTagByContext = searchTagByContext(query, getHashCode(query));
        //for testing
        List <Tag> tags = new ArrayList<>();
        tags.add(searchTagByContext);
        return tags;
        /*
        return StreamSupport
            .stream(tagSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());*/
    }
    
    private Long getHashCode (String tagContext) {
        int tagsHash = tagContext.hashCode();
        if (tagsHash < 0) return 2147483648L+tagsHash*-1;
        else return Long.valueOf(tagsHash);
    }
}