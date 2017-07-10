package com.sgkhmjaes.jdias.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StatusMessage.
 */
@Entity
@Table(name = "status_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "statusmessage")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @JsonProperty("text")
    @Column(name = "text")
    private String text;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(unique = true)
    private Location location;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(unique = true)
    private Poll poll;

    @OneToMany(mappedBy = "statusMessage", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "statusMessage", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Photo> photos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public StatusMessage text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Location getLocation() {
        return location;
    }

    public StatusMessage location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Poll getPoll() {
        return poll;
    }

    public StatusMessage poll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public StatusMessage posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public StatusMessage addPost(Post post) {
        this.posts.add(post);
        post.setStatusMessage(this);
        return this;
    }

    public StatusMessage removePost(Post post) {
        this.posts.remove(post);
        post.setStatusMessage(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public StatusMessage photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public StatusMessage addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setStatusMessage(this);
        return this;
    }

    public StatusMessage removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setStatusMessage(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StatusMessage statusMessage = (StatusMessage) o;
        if (statusMessage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statusMessage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatusMessage{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            "}";
    }
}
