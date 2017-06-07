package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.domain.Location;
import com.sgkhmjaes.jdias.domain.Photo;
import com.sgkhmjaes.jdias.domain.Poll;
import com.sgkhmjaes.jdias.domain.enumeration.PostType;
import java.time.LocalDate;
import java.util.List;

public class PostDTO implements AutoMapping{
    @JsonProperty("text")
    private String statusMessageText;
    @JsonProperty("id")
    private Long postId;
    @JsonProperty("guid")
    private String postGuid;
    private String open_graph_cache;
    private String title;
    private String provider_display_name;
    @JsonProperty("nsfw")
    private boolean postNsfw;
    private String o_embed_cache;
    private String participation;
    private boolean already_participated_in_poll;
    @JsonProperty("created_at")
    private LocalDate postCreatedAt;
    @JsonProperty("public")
    private boolean postPub;
    private LocalDate interacted_at;
    
    @JsonProperty("root")
    private PostDTO postDTO; // root
    @JsonProperty("author")
    private AuthorDTO author; // author
    private Poll poll;    
    private Location location;
    @JsonProperty("post_type")
    private PostType postPostType;
    @JsonProperty("mentioned_people")
    private List<String> testDTOMentioned_people;
    
    private List<Photo> photos;
    private InteractionDTO interactions;

    public PostDTO(){}
    
    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }

    public String getStatusMessageText() {
        return statusMessageText;
    }

    public void setStatusMessageText(String statusMessageText) {
        this.statusMessageText = statusMessageText;
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

    public LocalDate getPostCreatedAt() {
        return postCreatedAt;
    }

    public void setPostCreatedAt(LocalDate postCreatedAt) {
        this.postCreatedAt = postCreatedAt;
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

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public PostType getTestDTOPostType() {
        return postPostType;
    }

    public void setTestDTOPostType(PostType testDTOPostType) {
        this.postPostType = testDTOPostType;
    }

    public List<String> getTestDTOMentioned_people() {
        return testDTOMentioned_people;
    }

    public void setTestDTOMentioned_people(List<String> testDTOMentioned_people) {
        this.testDTOMentioned_people = testDTOMentioned_people;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public InteractionDTO getInteractions() {
        return interactions;
    }

    public void setInteractions(InteractionDTO interactions) {
        this.interactions = interactions;
    }
    
    


    @Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append("Post DTO: { Status message text: ").append(statusMessageText).append("\r\n").
append("Post id: ").append(postId).append("\r\n").
append("Post guid: ").append(postGuid).append("\r\n").
append("Open_graph_cache: ").append(open_graph_cache).append("\r\n").
append("Title: ").append(title).append("\r\n").
append("Provider_display_name: ").append(provider_display_name).append("\r\n").
append("Post nsfw: ").append(postNsfw).append("\r\n").
append("O_embed_cache: ").append(o_embed_cache).append("\r\n").
append("Participation: ").append(participation).append("\r\n").
append("Already_participated_in_poll: ").append(already_participated_in_poll).append("\r\n").
append("Post created_at: ").append(postCreatedAt).append("\r\n").
append("Post pub: ").append(postPub).append("\r\n").
append("Interacted_at: ").append(interacted_at).append("\r\n").
append("Author: ").append(author).append("\r\n").
append("Poll: ").append(poll).append("\r\n").
append("Post type: ").append(postPostType).append("\r\n").
append("Location: ").append(location).append("\r\n").
append("Photos: ").append(photos).append("\r\n").
append("Mentioned_people: ").append(testDTOMentioned_people).append("\r\n").
append("Interactions: ").append(interactions).append("\r\n").
        append("Post DTO: {").append(postDTO).append("}");
return sb.toString();
}

}
