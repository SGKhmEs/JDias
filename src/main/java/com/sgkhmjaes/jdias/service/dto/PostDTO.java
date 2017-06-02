package com.sgkhmjaes.jdias.service.dto;

import com.sgkhmjaes.jdias.domain.Location;
import com.sgkhmjaes.jdias.domain.Photo;
import com.sgkhmjaes.jdias.domain.Poll;
import com.sgkhmjaes.jdias.domain.enumeration.PostType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by inna on 02.06.17.
 */

public class PostDTO {
    private String statusMessageText;
    private Location location;
    private PostDTO root;
    private List<Photo> photos;
    private long postId;
    private String postGuid;
    private AuthorDTO author;
    private String open_graph_cache;
    private String title;
    private List<String> mentioned_people;
    private Poll poll;
    private String provider_display_name;
    private boolean postNsfw;
    private String o_embed_cache;
    private String participation;
    private boolean already_participated_in_poll;
    private LocalDate postCreated_at;
    private PostType postPostType;
    private boolean postPub;
    private LocalDate interacted_at;
    private List<InteractionDTO> interactions;

    public PostDTO(){}

    public String getStatusMessageText() {
        return statusMessageText;
    }

    public void setStatusMessageText(String statusMessageText) {
        this.statusMessageText = statusMessageText;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public PostDTO getRoot() {
        return root;
    }

    public void setRoot(PostDTO root) {
        this.root = root;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostGuid() {
        return postGuid;
    }

    public void setPostGuid(String postGuid) {
        this.postGuid = postGuid;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public String getOpen_graph_cache() {
        return open_graph_cache;
    }

    public void setOpen_graph_cache(String open_graph_cache) {
        this.open_graph_cache = open_graph_cache;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMentioned_people() {
        return mentioned_people;
    }

    public void setMentioned_people(List<String> mentioned_people) {
        this.mentioned_people = mentioned_people;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getProvider_display_name() {
        return provider_display_name;
    }

    public void setProvider_display_name(String provider_display_name) {
        this.provider_display_name = provider_display_name;
    }

    public boolean isPostNsfw() {
        return postNsfw;
    }

    public void setPostNsfw(boolean postNsfw) {
        this.postNsfw = postNsfw;
    }

    public String getO_embed_cache() {
        return o_embed_cache;
    }

    public void setO_embed_cache(String o_embed_cache) {
        this.o_embed_cache = o_embed_cache;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }

    public boolean isAlready_participated_in_poll() {
        return already_participated_in_poll;
    }

    public void setAlready_participated_in_poll(boolean already_participated_in_poll) {
        this.already_participated_in_poll = already_participated_in_poll;
    }

    public LocalDate getPostCreated_at() {
        return postCreated_at;
    }

    public void setPostCreated_at(LocalDate postCreated_at) {
        this.postCreated_at = postCreated_at;
    }

    public PostType getPostPostType() {
        return postPostType;
    }

    public void setPostPostType(PostType postPostType) {
        this.postPostType = postPostType;
    }

    public boolean isPostPub() {
        return postPub;
    }

    public void setPostPub(boolean postPub) {
        this.postPub = postPub;
    }

    public LocalDate getInteracted_at() {
        return interacted_at;
    }

    public void setInteracted_at(LocalDate interacted_at) {
        this.interacted_at = interacted_at;
    }

    public List<InteractionDTO> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<InteractionDTO> interactions) {
        this.interactions = interactions;
    }

    
}
