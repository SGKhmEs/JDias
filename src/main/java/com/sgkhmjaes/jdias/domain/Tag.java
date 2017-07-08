package com.sgkhmjaes.jdias.domain;

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

    @Column(name = "tag_context")
    private String tagContext;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tag_tagpost",
               joinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tagposts_id", referencedColumnName="id"))
    private Set<Post> tagposts = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tag_tagperson",
               joinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tagpeople_id", referencedColumnName="id"))
    private Set<Person> tagpeople = new HashSet<>();

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

    public Set<Post> getTagposts() {
        return tagposts;
    }

    public Tag tagposts(Set<Post> Posts) {
        this.tagposts = Posts;
        return this;
    }

    public Tag addTagpost(Post Post) {
        this.tagposts.add(Post);
        Post.getPosttags().add(this);
        return this;
    }

    public Tag removeTagpost(Post Post) {
        this.tagposts.remove(Post);
        Post.getPosttags().remove(this);
        return this;
    }

    public void setTagposts(Set<Post> Posts) {
        this.tagposts = Posts;
    }

    public Set<Person> getTagpeople() {
        return tagpeople;
    }

    public Tag tagpeople(Set<Person> People) {
        this.tagpeople = People;
        return this;
    }

    public Tag addTagperson(Person Person) {
        this.tagpeople.add(Person);
        Person.getPersontags().add(this);
        return this;
    }

    public Tag removeTagperson(Person Person) {
        this.tagpeople.remove(Person);
        Person.getPersontags().remove(this);
        return this;
    }

    public void setTagpeople(Set<Person> People) {
        this.tagpeople = People;
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
            "}";
    }
}
