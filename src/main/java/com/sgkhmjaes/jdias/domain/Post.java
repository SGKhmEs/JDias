package com.sgkhmjaes.jdias.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sgkhmjaes.jdias.domain.enumeration.PostType;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "guid")
    private String guid;

    @Column(name = "createdat")
    private LocalDate createdat;

    @Column(name = "pub")
    private Boolean pub;

    @Column(name = "providerdisplayname")
    private String providerdisplayname;

    @Enumerated(EnumType.STRING)
    @Column(name = "posttype")
    private PostType posttype;

    @OneToOne
    @JoinColumn(unique = true)
    private StatusMessage statusMessage;

    @OneToOne
    @JoinColumn(unique = true)
    private Reshare reshare;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

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

    public Post author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public Post guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDate getCreatedat() {
        return createdat;
    }

    public Post createdat(LocalDate createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public Boolean isPub() {
        return pub;
    }

    public Post pub(Boolean pub) {
        this.pub = pub;
        return this;
    }

    public void setPub(Boolean pub) {
        this.pub = pub;
    }

    public String getProviderdisplayname() {
        return providerdisplayname;
    }

    public Post providerdisplayname(String providerdisplayname) {
        this.providerdisplayname = providerdisplayname;
        return this;
    }

    public void setProviderdisplayname(String providerdisplayname) {
        this.providerdisplayname = providerdisplayname;
    }

    public PostType getPosttype() {
        return posttype;
    }

    public Post posttype(PostType posttype) {
        this.posttype = posttype;
        return this;
    }

    public void setPosttype(PostType posttype) {
        this.posttype = posttype;
    }

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    public Post statusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Reshare getReshare() {
        return reshare;
    }

    public Post reshare(Reshare reshare) {
        this.reshare = reshare;
        return this;
    }

    public void setReshare(Reshare reshare) {
        this.reshare = reshare;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Post comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Post addComments(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public Post removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Person getPerson() {
        return person;
    }

    public Post person(Person person) {
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
        Post post = (Post) o;
        if (post.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + getGuid() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", pub='" + isPub() + "'" +
            ", providerdisplayname='" + getProviderdisplayname() + "'" +
            ", posttype='" + getPosttype() + "'" +
            "}";
    }
}
