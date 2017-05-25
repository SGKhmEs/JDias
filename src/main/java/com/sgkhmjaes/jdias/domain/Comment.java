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

    @Column(name = "parentguid")
    private String parentguid;

    @Column(name = "text")
    private String text;

    @Column(name = "createdat")
    private LocalDate createdat;

    @Column(name = "authorsignature")
    private String authorsignature;

    @Column(name = "parentauthorsignature")
    private String parentauthorsignature;

    @Column(name = "threadparentguid")
    private String threadparentguid;

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

    public String getParentguid() {
        return parentguid;
    }

    public Comment parentguid(String parentguid) {
        this.parentguid = parentguid;
        return this;
    }

    public void setParentguid(String parentguid) {
        this.parentguid = parentguid;
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

    public LocalDate getCreatedat() {
        return createdat;
    }

    public Comment createdat(LocalDate createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public String getAuthorsignature() {
        return authorsignature;
    }

    public Comment authorsignature(String authorsignature) {
        this.authorsignature = authorsignature;
        return this;
    }

    public void setAuthorsignature(String authorsignature) {
        this.authorsignature = authorsignature;
    }

    public String getParentauthorsignature() {
        return parentauthorsignature;
    }

    public Comment parentauthorsignature(String parentauthorsignature) {
        this.parentauthorsignature = parentauthorsignature;
        return this;
    }

    public void setParentauthorsignature(String parentauthorsignature) {
        this.parentauthorsignature = parentauthorsignature;
    }

    public String getThreadparentguid() {
        return threadparentguid;
    }

    public Comment threadparentguid(String threadparentguid) {
        this.threadparentguid = threadparentguid;
        return this;
    }

    public void setThreadparentguid(String threadparentguid) {
        this.threadparentguid = threadparentguid;
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
            ", parentguid='" + getParentguid() + "'" +
            ", text='" + getText() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", authorsignature='" + getAuthorsignature() + "'" +
            ", parentauthorsignature='" + getParentauthorsignature() + "'" +
            ", threadparentguid='" + getThreadparentguid() + "'" +
            "}";
    }
}
