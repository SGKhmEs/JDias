package com.sgkhmjaes.jdias.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sgkhmjaes.jdias.domain.enumeration.PostType;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "guid")
    private String guid;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "pub")
    private Boolean pub;

    @Column(name = "provider_display_name")
    private String providerDisplayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AspectVisiblity> aspectVisiblities = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Like> likes = new HashSet<>();

    @ManyToOne
    private Person person;

    @ManyToOne
    private Reshare reshare;

    @ManyToOne
    private StatusMessage statusMessage;
    
    @OneToMany(mappedBy = "post")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tagging> taggings = new HashSet<>();
    
    public Post() {}

    public Post(String author, String guid, LocalDate createdAt, Boolean pub, PostType postType, StatusMessage statusMessage, Reshare reshare, Person person) {
        this.author = author;
        this.guid = guid;
        this.createdAt = createdAt;
        this.pub = pub;
        this.postType = postType;
        this.statusMessage = statusMessage;
        this.reshare = reshare;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public Post author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public Post guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Post createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean isPub() {
        return pub;
    }

    public Post pub(Boolean pub) {
        this.pub = pub;
        return this;
    }

    public void setPub(Boolean pub) {
        this.pub = pub;
    }

    public String getProviderDisplayName() {
        return providerDisplayName;
    }

    public Post providerDisplayName(String providerDisplayName) {
        this.providerDisplayName = providerDisplayName;
        return this;
    }

    public void setProviderDisplayName(String providerDisplayName) {
        this.providerDisplayName = providerDisplayName;
    }

    public PostType getPostType() {
        return postType;
    }

    public Post postType(PostType postType) {
        this.postType = postType;
        return this;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public Set<AspectVisiblity> getAspectVisiblities() {
        return aspectVisiblities;
    }

    public Post aspectVisiblities(Set<AspectVisiblity> aspectVisiblities) {
        this.aspectVisiblities = aspectVisiblities;
        return this;
    }

    public Post addAspectVisiblities(AspectVisiblity aspectVisiblity) {
        this.aspectVisiblities.add(aspectVisiblity);
        aspectVisiblity.setPost(this);
        return this;
    }

    public Post removeAspectVisiblities(AspectVisiblity aspectVisiblity) {
        this.aspectVisiblities.remove(aspectVisiblity);
        aspectVisiblity.setPost(null);
        return this;
    }

    public void setAspectVisiblities(Set<AspectVisiblity> aspectVisiblities) {
        this.aspectVisiblities = aspectVisiblities;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Post comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Post addComments(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public Post removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public Post likes(Set<Like> likes) {
        this.likes = likes;
        return this;
    }

    public Post addLikes(Like like) {
        this.likes.add(like);
        like.setPost(this);
        return this;
    }

    public Post removeLikes(Like like) {
        this.likes.remove(like);
        like.setPost(null);
        return this;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Person getPerson() {
        return person;
    }

    public Post person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Reshare getReshare() {
        return reshare;
    }

    public Post reshare(Reshare reshare) {
        this.reshare = reshare;
        return this;
    }

    public void setReshare(Reshare reshare) {
        this.reshare = reshare;
    }

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    public Post statusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Set<Tagging> getTaggings() {
        return taggings;
    }

    public Post taggings(Set<Tagging> taggings) {
        this.taggings = taggings;
        return this;
    }

    public Post addTagging(Tagging tagging) {
        this.taggings.add(tagging);
        tagging.setPost(this);
        return this;
    }

    public Post removeTagging(Tagging tagging) {
        this.taggings.remove(tagging);
        tagging.setPost(null);
        return this;
    }

    public void setTaggings(Set<Tagging> taggings) {
        this.taggings = taggings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        if (post.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + getGuid() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", pub='" + isPub() + "'" +
            ", providerDisplayName='" + getProviderDisplayName() + "'" +
            ", postType='" + getPostType() + "'" +
            "}";
    }
}
