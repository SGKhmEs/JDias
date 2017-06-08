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
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "following")
    private Boolean following;

    @Column(name = "sharing")
    private Boolean sharing;

    @OneToMany(mappedBy = "contact")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AspectMembership> aspectMemberships = new HashSet<>();

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

    public Contact author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRecipient() {
        return recipient;
    }

    public Contact recipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Boolean isFollowing() {
        return following;
    }

    public Contact following(Boolean following) {
        this.following = following;
        return this;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public Boolean isSharing() {
        return sharing;
    }

    public Contact sharing(Boolean sharing) {
        this.sharing = sharing;
        return this;
    }

    public void setSharing(Boolean sharing) {
        this.sharing = sharing;
    }

    public Set<AspectMembership> getAspectMemberships() {
        return aspectMemberships;
    }

    public Contact aspectMemberships(Set<AspectMembership> aspectMemberships) {
        this.aspectMemberships = aspectMemberships;
        return this;
    }

    public Contact addAspectMemberships(AspectMembership aspectMembership) {
        this.aspectMemberships.add(aspectMembership);
        aspectMembership.setContact(this);
        return this;
    }

    public Contact removeAspectMemberships(AspectMembership aspectMembership) {
        this.aspectMemberships.remove(aspectMembership);
        aspectMembership.setContact(null);
        return this;
    }

    public void setAspectMemberships(Set<AspectMembership> aspectMemberships) {
        this.aspectMemberships = aspectMemberships;
    }

    public Person getPerson() {
        return person;
    }

    public Contact person(Person person) {
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
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", recipient='" + getRecipient() + "'" +
            ", following='" + isFollowing() + "'" +
            ", sharing='" + isSharing() + "'" +
            "}";
    }
}
