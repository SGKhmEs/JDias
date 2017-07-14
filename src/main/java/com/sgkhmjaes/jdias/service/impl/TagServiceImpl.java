package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.HashTag;
import com.sgkhmjaes.jdias.domain.Person;
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
import com.sgkhmjaes.jdias.service.TagFollowingService;
import com.sgkhmjaes.jdias.service.TaggingService;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.StringTokenizer;
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
    private final TaggingService taggingService;
    private final TagFollowingService tagFollowingService;
    
    private String newPost;

    public TagServiceImpl(TagRepository tagRepository, TagSearchRepository tagSearchRepository, 
            HashTagRepository hashTagRepository, HashTagSearchRepository hashTagSearchRepository,
            TaggingService taggingService, TagFollowingService tagFollowingService) {
        this.tagRepository = tagRepository;
        this.tagSearchRepository = tagSearchRepository;
        this.hashTagRepository = hashTagRepository;
        this.hashTagSearchRepository = hashTagSearchRepository;
        this.taggingService = taggingService;
        this.tagFollowingService = tagFollowingService;
    }

    /**
     * Save a tag.
     *
     * @param statusMessage
     * @param tagContextSet
     * @return the persisted entity
     */
        
    @Override
    public String saveAllTagsFromStatusMessages (StatusMessage statusMessage, Set<String> tagContextSet){
        //Set<String> tagContextSet = searchingTags(statusMessage.getText());
        //Set <Tag> tags = new HashSet <>();
        for (String tagContext : tagContextSet) {
            Long tagsHash = getHashCode(tagContext);
            Tag searchTagByContext = searchTagByContext(tagContext, tagsHash);
            if (searchTagByContext == null) save(new Tag(tagContext), statusMessage, tagsHash);
            else {
                searchTagByContext.setUpdatedAt(ZonedDateTime.now());
                save(searchTagByContext, statusMessage, tagsHash);
            }
        }
        return newPost;
    }
    
    private Tag save(Tag tag, StatusMessage statusMessage, Long tagsHash) {
        
        HashTag hashTag = new HashTag();
        hashTag.setId(tagsHash);
        hashTagRepository.saveAndFlush(hashTag);
        tag.setHashTag(hashTag);
        
        Tag tagResult = tagRepository.saveAndFlush(tag);
        tagSearchRepository.save(tagResult);
        hashTag.addTag(tagResult);
        HashTag hashTagResult = hashTagRepository.save(hashTag);
        hashTagSearchRepository.save(hashTagResult);
        
        for (Post post : statusMessage.getPosts()) {
            taggingService.save(new Tagging(tagResult, post));
            tagFollowingService.save(new TagFollowing(tagResult, post.getPerson()));
        }
        
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
    
    @Override
    public Set <Post> findPostsByTag (String tagContext){
        Tag searchTagByContext = searchTagByContext(tagContext, getHashCode(tagContext));
        HashSet <Post> posts = new HashSet <> ();
        if(searchTagByContext != null){
            Set<Tagging> taggings = searchTagByContext.getTaggings();
            for (Tagging tagging : taggings) posts.add(tagging.getPost());
        }
        return posts;
    }
    
    @Override
    public Set <Person> findPersonByTag (String tagContext){
        Tag searchTagByContext = searchTagByContext(tagContext, getHashCode(tagContext));
        HashSet <Person> persons = new HashSet <> ();
        if(searchTagByContext != null){
            Set<TagFollowing> tagFollowings = searchTagByContext.getTagFollowings();
            for (TagFollowing tagFollowing : tagFollowings) persons.add(tagFollowing.getPerson());
        }
        return persons;
    }

    /**
     * Search for the tag corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    
        /**
     *  Get all the tags.
     *
     *  @return the list of entities
     */
    /*
    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        log.debug("Request to get all Tags");
        return tagRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tag> search(String query) {
        log.debug("Request to search Tags for query {}", query);
        
        // for testing
        for (Post post : findPostsByTag(query)) {
            System.out.println("***Post: "+ post.getStatusMessage().getText());
        }
        for (Person person : findPersonByTag(query)) {
            System.out.println("***Person: "+ person);
        }
        System.out.println("***Tag: "+searchTagByContext(query, getHashCode(query)));
        
        return StreamSupport
                .stream(tagSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
    */
    private Long getHashCode (String tagContext) {
        int tagsHash = tagContext.hashCode();
        if (tagsHash < 0) return 2147483648L+tagsHash*-1;
        else return Long.valueOf(tagsHash);
    }
    
    private Set<String> searchingTags(String post) {
        Set<String> tags = new HashSet<>();
        if (post == null || post.isEmpty()) return tags;
        int tagsCount = 0;
        StringTokenizer st = new StringTokenizer(" " + post, "#");
        while (st.hasMoreTokens()) {
            tagsCount++;
            if (tagsCount > 1) {
                StringTokenizer getOneWord = new StringTokenizer(st.nextToken());
                if (getOneWord.hasMoreTokens()) {
                    char[] tagInCharArray = getOneWord.nextToken().toLowerCase().toCharArray();
                    for (int i = tagInCharArray.length - 1; i >= 0; i--) {
                        if (Character.isLetterOrDigit(tagInCharArray[i])) {
                            if (tagInCharArray.length == i + 1) tags.add(new String(tagInCharArray));
                            else tags.add(new String(Arrays.copyOfRange(tagInCharArray, 0, i + 1)));
                            break;
            }}}}
            else st.nextToken();
        }
        //tags.remove("");
        Set<String> tagsToLowerCase = new HashSet<>();
        for (String tag : tags) {
            String tagToLowerCase = tag.toLowerCase();
            post = post.replace("#"+tag, "<a href=/api/tags/posts/"+tagToLowerCase+">#"+tag+"</a>");
            tagsToLowerCase.add(tagToLowerCase);
        }
        newPost = post;
        System.out.println("--------NewPost--------" + newPost);
        return tagsToLowerCase;
        //return tags;
    }
    
}