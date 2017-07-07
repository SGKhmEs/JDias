package com.sgkhmjaes.jdias.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HashTag.
 */
@Entity
@Table(name = "hash_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hashtag")
public class HashTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "hashTag")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public HashTag tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public HashTag addTags(Tag tag) {
        this.tags.add(tag);
        tag.setHashTag(this);
        return this;
    }

    public HashTag removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.setHashTag(null);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashTag hashTag = (HashTag) o;
        if (hashTag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hashTag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HashTag{" +
            "id=" + getId() +
            "}";
    }
}
