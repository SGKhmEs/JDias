package com.sgkhmjaes.jdias.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tag_context")
    private String tagContext;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tagging> taggings = new HashSet<>();

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TagFollowing> tagFollowings = new HashSet<>();

    @ManyToOne
    private HashTag hashTag;
    
    public Tag () {}
    
    public Tag (String tagContext) {
        this.tagContext = tagContext;
        this.createdAt = LocalDate.now();
        this.updatedAt = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagContext() {
        return tagContext;
    }

    public Tag tagContext(String tagContext) {
        this.tagContext = tagContext;
        return this;
    }

    public void setTagContext(String tagContext) {
        this.tagContext = tagContext;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Tag createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Tag updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Tagging> getTaggings() {
        return taggings;
    }

    public Tag taggings(Set<Tagging> taggings) {
        this.taggings = taggings;
        return this;
    }

    public Tag addTaggings(Tagging tagging) {
        this.taggings.add(tagging);
        tagging.setTag(this);
        return this;
    }

    public Tag removeTaggings(Tagging tagging) {
        this.taggings.remove(tagging);
        tagging.setTag(null);
        return this;
    }

    public void setTaggings(Set<Tagging> taggings) {
        this.taggings = taggings;
    }

    public Set<TagFollowing> getTagFollowings() {
        return tagFollowings;
    }

    public Tag tagFollowings(Set<TagFollowing> tagFollowings) {
        this.tagFollowings = tagFollowings;
        return this;
    }

    public Tag addTagFollowings(TagFollowing tagFollowing) {
        this.tagFollowings.add(tagFollowing);
        tagFollowing.setTag(this);
        return this;
    }

    public Tag removeTagFollowings(TagFollowing tagFollowing) {
        this.tagFollowings.remove(tagFollowing);
        tagFollowing.setTag(null);
        return this;
    }

    public void setTagFollowings(Set<TagFollowing> tagFollowings) {
        this.tagFollowings = tagFollowings;
    }

    public HashTag getHashTag() {
        return hashTag;
    }

    public Tag hashTag(HashTag hashTag) {
        this.hashTag = hashTag;
        return this;
    }

    public void setHashTag(HashTag hashTag) {
        this.hashTag = hashTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        if (tag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", tagContext='" + getTagContext() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
