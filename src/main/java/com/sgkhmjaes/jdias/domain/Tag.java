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

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TagFollowing> tagFollowings = new HashSet<>();

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tagging> taggings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Post getPost() {
        return post;
    }

    public Tag post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
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
            ", name='" + getName() + "'" +
            "}";
    }
}
