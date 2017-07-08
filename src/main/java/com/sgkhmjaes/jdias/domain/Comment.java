package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "guid")
    private String guid;

    @Column(name = "parent_guid")
    private String parentGuid;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "author_signature")
    private String authorSignature;

    @Column(name = "parent_author_signature")
    private String parentAuthorSignature;

    @Column(name = "thread_parent_guid")
    private String threadParentGuid;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public Comment author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public Comment guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public Comment parentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
        return this;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getText() {
        return text;
    }

    public Comment text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Comment createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorSignature() {
        return authorSignature;
    }

    public Comment authorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
        return this;
    }

    public void setAuthorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
    }

    public String getParentAuthorSignature() {
        return parentAuthorSignature;
    }

    public Comment parentAuthorSignature(String parentAuthorSignature) {
        this.parentAuthorSignature = parentAuthorSignature;
        return this;
    }

    public void setParentAuthorSignature(String parentAuthorSignature) {
        this.parentAuthorSignature = parentAuthorSignature;
    }

    public String getThreadParentGuid() {
        return threadParentGuid;
    }

    public Comment threadParentGuid(String threadParentGuid) {
        this.threadParentGuid = threadParentGuid;
        return this;
    }

    public void setThreadParentGuid(String threadParentGuid) {
        this.threadParentGuid = threadParentGuid;
    }

    public Post getPost() {
        return post;
    }

    public Comment post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Person getPerson() {
        return person;
    }

    public Comment person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + getGuid() + "'" +
            ", parentGuid='" + getParentGuid() + "'" +
            ", text='" + getText() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", authorSignature='" + getAuthorSignature() + "'" +
            ", parentAuthorSignature='" + getParentAuthorSignature() + "'" +
            ", threadParentGuid='" + getThreadParentGuid() + "'" +
            "}";
    }
}
