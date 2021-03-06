package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.config.Constants;
import com.sgkhmjaes.jdias.domain.*;
import com.sgkhmjaes.jdias.domain.enumeration.PostType;
import com.sgkhmjaes.jdias.repository.*;
import com.sgkhmjaes.jdias.repository.search.ReshareSearchRepository;
import com.sgkhmjaes.jdias.repository.search.StatusMessageSearchRepository;
import com.sgkhmjaes.jdias.service.*;
import com.sgkhmjaes.jdias.repository.search.PostSearchRepository;
import com.sgkhmjaes.jdias.service.dto.PostDTO;
import com.sgkhmjaes.jdias.service.dto.StatusMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Post.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final PostSearchRepository postSearchRepository;

    private final StatusMessageRepository statusMessageRepository;
    private final StatusMessageSearchRepository statusMessageSearchRepository;
    private final ReshareRepository reshareRepository;
    private final ReshareSearchRepository reshareSearchRepository;
    private final UserRepository userRepository;
    private final PollAnswerService pollAnswerService;
    private final PollService pollService;
    private final LocationService locationService;
    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final AspectRepository aspectRepository;
    private final AspectVisiblityService aspectVisiblityService;
    private final StorageService storageService;

    public PostServiceImpl(PostRepository postRepository, PostSearchRepository postSearchRepository, StatusMessageRepository statusMessageRepository, StatusMessageSearchRepository statusMessageSearchRepository, ReshareRepository reshareRepository, ReshareSearchRepository reshareSearchRepository, UserRepository userRepository, PollAnswerService pollAnswerService, PollService pollService, LocationService locationService, PhotoRepository photoRepository, UserService userService, AspectRepository aspectRepository, AspectVisiblityService aspectVisiblityService, StorageService storageService) {
        this.postRepository = postRepository;
        this.postSearchRepository = postSearchRepository;
        this.statusMessageRepository = statusMessageRepository;
        this.statusMessageSearchRepository = statusMessageSearchRepository;
        this.reshareRepository = reshareRepository;
        this.reshareSearchRepository = reshareSearchRepository;
        this.userRepository = userRepository;
        this.pollAnswerService = pollAnswerService;
        this.pollService = pollService;
        this.locationService = locationService;
        this.photoRepository = photoRepository;
        this.userService = userService;
        this.aspectRepository = aspectRepository;
        this.aspectVisiblityService = aspectVisiblityService;
        this.storageService = storageService;
    }



    /**
     * Save a post.
     *
     * @param post the entity to save
     * @return the persisted entity
     */
    @Override
    public Post save(Post post) {
        log.debug("Request to save Post : {}", post);
        Post result = postRepository.save(post);
        postSearchRepository.save(result);
        return result;
    }

    /**
     * Save a statusMessage.
     *
     * @param statusMessage the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusMessage save(StatusMessage statusMessage) {
        log.debug("Request to save StatusMessage : {}", statusMessage);
        if (statusMessage.getId() == null) {
            Person person = userService.getCurrentPerson();//personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
            Post post = save(new Post(person.getDiasporaId(), UUID.randomUUID().toString(),
                LocalDate.now(), true, PostType.STATUSMESSAGE, null, null, person));
            statusMessage.setId(post.getId());
            statusMessage.addPost(post);
            StatusMessage result = statusMessageRepository.save(statusMessage);
            statusMessageSearchRepository.save(result);
            Reshare reshare = new Reshare(post.getId(), post.getAuthor(), post.getGuid());
            reshare.addPost(post);
            reshare = save(reshare);
            post.setStatusMessage(result);
            post.setReshare(reshare);
            save(post);
            return result;
        } else {
            StatusMessage result = statusMessageRepository.save(statusMessage);
            statusMessageSearchRepository.save(result);
            return result;
        }
    }

    /**
     * Save a statusMessage.
     *
     * @param statusMessageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusMessage save(StatusMessageDTO statusMessageDTO) {
        StatusMessage statusMessage = statusMessageDTO.getStatusMessage();
        if(statusMessageDTO.getPollQuestion() != null) {
            Poll poll = pollService.save(new Poll(statusMessageDTO.getPollQuestion()));
            for (String s : statusMessageDTO.getPollAnswers()) {
                PollAnswer pollAnswer = pollAnswerService.save(new PollAnswer(s, poll));
                poll.addPollanswers(pollAnswer);
            }
            poll = pollService.save(poll);
            statusMessage.setPoll(poll);
        }
        if(statusMessageDTO.getLocationAddress() != null){
            String [] coords = statusMessageDTO.getLocationCoords().split(", ");
            statusMessage.setLocation(locationService.save(new Location(statusMessageDTO.getLocationAddress(),Float.parseFloat(coords[0]),Float.parseFloat(coords[1]))));
        }
        statusMessage = save(statusMessage);
        if(statusMessageDTO.getAspectIds() != null){
            //Set<AspectVisiblity> aspectVisiblities = new HashSet<>();
            Post post = findOnePost(statusMessage.getId());
            for (Long id: statusMessageDTO.getAspectIds()) {
                AspectVisiblity aspectVisiblity = new AspectVisiblity();
                Aspect aspect = aspectRepository.findOne(id);
                aspectVisiblity.setAspect(aspect);
                aspectVisiblity.setPost(post);
                aspectVisiblity.setPostType(post.getPostType());
                aspectVisiblityService.save(aspectVisiblity);
               // aspectVisiblities.add(aspectVisiblity);
            }
            //post.setAspectVisiblities(aspectVisiblities);
        }
        if(statusMessageDTO.getPhotos() != null){
            for(Long id: statusMessageDTO.getPhotos()) {
                statusMessage.addPhotos(photoRepository.findOne(id));
            }
        }
        return save(statusMessage);
    }

    /**
     * Save a reshare.
     *
     * @param reshare the entity to save
     * @return the persisted entity
     */
    @Override
    public Reshare save(Reshare reshare) {
        log.debug("Request to save Reshare : {}", reshare);
        Reshare result = reshareRepository.save(reshare);
        reshareSearchRepository.save(result);
        return result;
    }

    @Override
    public Reshare saveReshare(PostDTO postDTO) {
        Post post = postRepository.findOne(postDTO.getId());
        Reshare saveReshare = saveReshare(post);
        reshareSearchRepository.save(saveReshare);
        return saveReshare(post);
    }

    @Override
    public Reshare saveReshare(Post parrentPost) {
        Person person = userService.getCurrentPerson();
        Reshare reshare = reshareRepository.findOne(parrentPost.getReshare().getId());
        StatusMessage statusMessage = statusMessageRepository.findOne(parrentPost.getStatusMessage().getId());
        Set<Post> posts = reshare.getPosts();
        boolean isHasRepost = false;
        for (Post p : posts) {
            if (p.getAuthor().equals(person.getDiasporaId())) {
                isHasRepost = true;
                break;
            }
        }
        if (parrentPost.getAuthor().equals(person.getDiasporaId())||isHasRepost) {
            return parrentPost.getReshare();
        } else {
            Post post = save(new Post(person.getDiasporaId(), UUID.randomUUID().toString(),
                LocalDate.now(), true, PostType.RESHARE, statusMessage, reshare, person));
            reshare = parrentPost.getReshare();
            reshare.addPost(post);
            statusMessage.addPost(post);
            save(statusMessage);
            return save(reshare);
        }
    }

    /**
     * Get all the posts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllPost() {
        log.debug("Request to get all Posts");
        return postRepository.findAll();
    }

    /**
     * Get all the statusMessages.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StatusMessage> findAllStatusMessage() {
        log.debug("Request to get all StatusMessages");
        return statusMessageRepository.findAll();
    }

    /**
     * Get all the reshares.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reshare> findAllReshare() {
        log.debug("Request to get all Reshares");
        return reshareRepository.findAll();
    }

    /**
     * Get one post by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Post findOnePost(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findOne(id);
    }

    /**
     * Get one statusMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StatusMessage findOneStatusMessage(Long id) {
        log.debug("Request to get StatusMessage : {}", id);
        return statusMessageRepository.findOne(id);
    }

    /**
     * Get one reshare by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Reshare findOneReshare(Long id) {
        log.debug("Request to get Reshare : {}", id);
        return reshareRepository.findOne(id);
    }

    /**
     * Delete the post by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.delete(id);
        postSearchRepository.delete(id);
    }

    /**
     * Delete the statusMessage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void deleteStatusMessage(Long id) {
        log.debug("Request to delete StatusMessage : {}", id);
        Post post = findOnePost(id);
        Person person = userService.getCurrentPerson();
        if (person.getDiasporaId().equals(post.getAuthor())) {
            StatusMessage statusMessage = findOneStatusMessage(id);
            for (Photo photo: statusMessage.getPhotos()){
                storageService.deleteImage(photo.getRemotePhotoName());
                storageService.deleteImage(Constants.SMALL_PREFIX + photo.getRemotePhotoName());
                storageService.deleteImage(Constants.MEDIUM_PREFIX + photo.getRemotePhotoName());
                storageService.deleteImage(Constants.LARGE_PREFIX + photo.getRemotePhotoName());
                storageService.deleteImage(Constants.SCALED_FULL_PREFIX + photo.getRemotePhotoName());
            }
            deleteReshare(id);
            statusMessageRepository.delete(id);
            statusMessageSearchRepository.delete(id);
        }
    }

    /**
     * Delete the reshare by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void deleteReshare(Long id) {
        log.debug("Request to delete Reshare : {}", id);
        Post post = findOnePost(id);
        Person person = userService.getCurrentPerson();
        if (person.getDiasporaId().equals(post.getAuthor())) {
            reshareRepository.delete(id);
            reshareSearchRepository.delete(id);
        }
    }

    @Override
    public void deletePost(Long id) {
        Post post = findOnePost(id);
        Person person = userService.getCurrentPerson();
        StatusMessage statusMessage = findOneStatusMessage(post.getStatusMessage().getId());
        if (person.getDiasporaId().equals(post.getAuthor())) {
            if (post.getId().equals(statusMessage.getId())) {
                deleteStatusMessage(id);
            } else {
                statusMessage.removePost(post);
                Reshare reshare = findOneReshare(statusMessage.getId());
                reshare.removePost(post);
                save(reshare);
                save(statusMessage);
                delete(id);
            }
        }
    }

    /**
     * Search for the post corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Post> searchPost(String query) {
        log.debug("Request to search Posts for query {}", query);
        return StreamSupport
            .stream(postSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search for the statusMessage corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StatusMessage> searchStatusMessage(String query) {
        log.debug("Request to search StatusMessages for query {}", query);
        return StreamSupport
            .stream(statusMessageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search for the reshare corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reshare> searchReshare(String query) {
        log.debug("Request to search Reshares for query {}", query);
        return StreamSupport
            .stream(reshareSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteSetPosts(Set<Post> postSet) {
        postRepository.delete(postSet);
        postSearchRepository.delete(postSet);
    }
}
