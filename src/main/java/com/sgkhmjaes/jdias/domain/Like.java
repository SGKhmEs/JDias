package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.sgkhmjaes.jdias.domain.enumeration.Type;

/**
 * A Like.
 */
@Entity
@Table(name = "jhi_like")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "like")
public class Like implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_type")
    private Type parentType;

    @Column(name = "positive")
    private Boolean positive;

    @Column(name = "author_signature")
    private String authorSignature;

    @Column(name = "parent_author_signature")
    private String parentAuthorSignature;

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

    public Like author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public Like guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public Like parentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
        return this;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public Type getParentType() {
        return parentType;
    }

    public Like parentType(Type parentType) {
        this.parentType = parentType;
        return this;
    }

    public void setParentType(Type parentType) {
        this.parentType = parentType;
    }

    public Boolean isPositive() {
        return positive;
    }

    public Like positive(Boolean positive) {
        this.positive = positive;
        return this;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    public String getAuthorSignature() {
        return authorSignature;
    }

    public Like authorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
        return this;
    }

    public void setAuthorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
    }

    public String getParentAuthorSignature() {
        return parentAuthorSignature;
    }

    public Like parentAuthorSignature(String parentAuthorSignature) {
        this.parentAuthorSignature = parentAuthorSignature;
        return this;
    }

    public void setParentAuthorSignature(String parentAuthorSignature) {
        this.parentAuthorSignature = parentAuthorSignature;
    }

    public Post getPost() {
        return post;
    }

    public Like post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Person getPerson() {
        return person;
    }

    public Like person(Person person) {
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
        Like like = (Like) o;
        if (like.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), like.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Like{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + getGuid() + "'" +
            ", parentGuid='" + getParentGuid() + "'" +
            ", parentType='" + getParentType() + "'" +
            ", positive='" + isPositive() + "'" +
            ", authorSignature='" + getAuthorSignature() + "'" +
            ", parentAuthorSignature='" + getParentAuthorSignature() + "'" +
            "}";
    }
}
