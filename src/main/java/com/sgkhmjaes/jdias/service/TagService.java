package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Tag;
import java.util.List;

/**
 * Service Interface for managing Tag.
 */
public interface TagService {
    private final Logger log = LoggerFactory.getLogger(TagService.class);
	private final TagRepository tagRepository;
	private final TagSearchRepository tagSearchRepository;
	
	public TagService(TagRepository tagRepository, TagSearchRepository tagSearchRepository) {
        this.tagRepository = tagRepository;
        this.tagSearchRepository = tagSearchRepository;
    }
	
	/*Create new Tag*/
	public Tag createTag(String name) {

        Tag newTag = new Tag();
        newTag.setName(name);
        tagRepository.save(newTag);
        userSearchRepository.save(newTag);
        log.debug("Created Information for User: {}", newTag);
        return newTag;
    }
	
	/*Update Tag by Id*/
	public void updateTag(Long id, String name) {
	
		tagRepository.findOneWithEagerRelationships(id).ifPresent(tag -> {
            tag.setName(name);
            tagSearchRepository.save(tag);
            log.debug("Changed Information for User: {}", tag);
        });
    }
	
	/*Delete Tag*/
	public void deleteTag(Long id) {
		tagRepository.findOneWithEagerRelationships(id).ifPresent(tag -> {
            tagRepository.delete(tag);
            tagSearchRepository.delete(tag);
            log.debug("Deleted Tag: {}", tag);
        });
    }
	/*Get all Tags*/
	@Transactional(readOnly = true)
    public Page<Tag> getAllTags(Pageable pageable) {
        return userRepository.findAllTags().map(Tag::new);
    }
	
	@Transactional(readOnly = true)
    public Optional<Tag> getTagsByPost(Long postId) {
        return tagRepository.findAllByPost(postId);
    }
	
   
    List<Tag> search(String query){};

}
