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

    @Column(name = "parentguid")
    private String parentguid;

    @Enumerated(EnumType.STRING)
    @Column(name = "parenttype")
    private Type parenttype;

    @Column(name = "positive")
    private Boolean positive;

    @Column(name = "authorsignature")
    private String authorsignature;

    @Column(name = "parentauthorsignature")
    private String parentauthorsignature;

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

    public String getParentguid() {
        return parentguid;
    }

    public Like parentguid(String parentguid) {
        this.parentguid = parentguid;
        return this;
    }

    public void setParentguid(String parentguid) {
        this.parentguid = parentguid;
    }

    public Type getParenttype() {
        return parenttype;
    }

    public Like parenttype(Type parenttype) {
        this.parenttype = parenttype;
        return this;
    }

    public void setParenttype(Type parenttype) {
        this.parenttype = parenttype;
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

    public String getAuthorsignature() {
        return authorsignature;
    }

    public Like authorsignature(String authorsignature) {
        this.authorsignature = authorsignature;
        return this;
    }

    public void setAuthorsignature(String authorsignature) {
        this.authorsignature = authorsignature;
    }

    public String getParentauthorsignature() {
        return parentauthorsignature;
    }

    public Like parentauthorsignature(String parentauthorsignature) {
        this.parentauthorsignature = parentauthorsignature;
        return this;
    }

    public void setParentauthorsignature(String parentauthorsignature) {
        this.parentauthorsignature = parentauthorsignature;
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
            ", parentguid='" + getParentguid() + "'" +
            ", parenttype='" + getParenttype() + "'" +
            ", positive='" + isPositive() + "'" +
            ", authorsignature='" + getAuthorsignature() + "'" +
            ", parentauthorsignature='" + getParentauthorsignature() + "'" +
            "}";
    }
}
