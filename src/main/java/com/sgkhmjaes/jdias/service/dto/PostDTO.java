package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.domain.Location;
import com.sgkhmjaes.jdias.domain.Photo;
import com.sgkhmjaes.jdias.domain.Poll;
import com.sgkhmjaes.jdias.domain.enumeration.PostType;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.time.LocalDate;
import java.util.List;

public class PostDTO implements AutoMapping {
//public class PostDTO extends AutoMapping1{

    @JsonProperty("text")
    private String text;
    @JsonProperty("id")
    private long id;
    @JsonProperty("guid")
    private String guid;
    private String open_graph_cache;
    private String title;
    private String provider_display_name;
    @JsonProperty("nsfw")
    private boolean nsfw;
    private String o_embed_cache;
    private String participation;
    private boolean already_participated_in_poll;
    @JsonProperty("created_at")
    private LocalDate createdAt;// post
    private boolean pub;
    private LocalDate interacted_at;

    @JsonProperty("root")
    private PostDTO postDTO; // root
    @JsonProperty("author")
    private AuthorDTO authorDTO; // author
    private Poll poll;
    private Location location;

    @JsonProperty("post_type")
    private PostType postType;
    @JsonProperty("mentioned_people")
    private List<String> mentioned_people;

    private List<Photo> photos;
    private InteractionDTO interactions;

    public PostDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
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

    public boolean getAlready_participated_in_poll() {
        return already_participated_in_poll;
    }

    public void setAlready_participated_in_poll(boolean already_participated_in_poll) {
        this.already_participated_in_poll = already_participated_in_poll;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getPub() {
        return pub;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }

    public LocalDate getInteracted_at() {
        return interacted_at;
    }

    public void setInteracted_at(LocalDate interacted_at) {
        this.interacted_at = interacted_at;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
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

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public List<String> getMentioned_people() {
        return mentioned_people;
    }

    public void setMentioned_people(List<String> mentioned_people) {
        this.mentioned_people = mentioned_people;
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
        sb.append("Post DTO: { Status message text: ").append(text).append("\r\n").
                append("Post id: ").append(id).append("\r\n").
                append("Post guid: ").append(guid).append("\r\n").
                append("Open_graph_cache: ").append(open_graph_cache).append("\r\n").
                append("Title: ").append(title).append("\r\n").
                append("Provider_display_name: ").append(provider_display_name).append("\r\n").
                append("Post nsfw: ").append(nsfw).append("\r\n").
                append("O_embed_cache: ").append(o_embed_cache).append("\r\n").
                append("Participation: ").append(participation).append("\r\n").
                append("Already_participated_in_poll: ").append(already_participated_in_poll).append("\r\n").
                append("Post created_at: ").append(createdAt).append("\r\n").
                append("Post pub: ").append(pub).append("\r\n").
                append("Interacted_at: ").append(interacted_at).append("\r\n").
                append("AuthorDTO: ").append(authorDTO).append("\r\n").
                append("Poll: ").append(poll).append("\r\n").
                append("Post type: ").append(postType).append("\r\n").
                append("Location: ").append(location).append("\r\n").
                append("Photos: ").append(photos).append("\r\n").
                append("Mentioned_people: ").append(mentioned_people).append("\r\n").
                append("Interactions: ").append(interactions).append("\r\n").
                append("Post DTO: {").append(postDTO).append("}");
        return sb.toString();
    }

}
