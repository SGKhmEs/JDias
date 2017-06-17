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
 * A Reshare.
 */
@Entity
@Table(name = "reshare")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reshare")
public class Reshare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "root_author")
    private String rootAuthor;

    @Column(name = "root_guid")
    private String rootGuid;

    @OneToMany(mappedBy = "reshare")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    public Reshare(){}

    public Reshare(Long id, String rootAuthor, String rootGuid) {
        this.id = id;
        this.rootAuthor = rootAuthor;
        this.rootGuid = rootGuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRootAuthor() {
        return rootAuthor;
    }

    public Reshare rootAuthor(String rootAuthor) {
        this.rootAuthor = rootAuthor;
        return this;
    }

    public void setRootAuthor(String rootAuthor) {
        this.rootAuthor = rootAuthor;
    }

    public String getRootGuid() {
        return rootGuid;
    }

    public Reshare rootGuid(String rootGuid) {
        this.rootGuid = rootGuid;
        return this;
    }

    public void setRootGuid(String rootGuid) {
        this.rootGuid = rootGuid;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Reshare posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Reshare addPost(Post post) {
        this.posts.add(post);
        post.setReshare(this);
        return this;
    }

    public Reshare removePost(Post post) {
        this.posts.remove(post);
        post.setReshare(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reshare reshare = (Reshare) o;
        if (reshare.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reshare.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reshare{" +
            "id=" + getId() +
            ", rootAuthor='" + getRootAuthor() + "'" +
            ", rootGuid='" + getRootGuid() + "'" +
            "}";
    }
}
