package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.sgkhmjaes.jdias.domain.enumeration.PostType;

/**
 * A AspectVisiblity.
 */
@Entity
@Table(name = "aspect_visiblity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "aspectvisiblity")
public class AspectVisiblity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @ManyToOne
    private Aspect aspect;

    @ManyToOne
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PostType getPostType() {
        return postType;
    }

    public AspectVisiblity postType(PostType postType) {
        this.postType = postType;
        return this;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public Aspect getAspect() {
        return aspect;
    }

    public AspectVisiblity aspect(Aspect aspect) {
        this.aspect = aspect;
        return this;
    }

    public void setAspect(Aspect aspect) {
        this.aspect = aspect;
    }

    public Post getPost() {
        return post;
    }

    public AspectVisiblity post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AspectVisiblity aspectVisiblity = (AspectVisiblity) o;
        if (aspectVisiblity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aspectVisiblity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AspectVisiblity{" +
            "id=" + getId() +
            ", postType='" + getPostType() + "'" +
            "}";
    }
}
