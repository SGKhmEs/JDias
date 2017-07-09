package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
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
    private String author; // diaspora* ID 	The diaspora* ID of the sender of the contact.

    @Column(name = "recipient")
    private String recipient; // diaspora* ID 	The diaspora* ID of the recipient.

    @Column(name = "following")
    private Boolean following; // Boolean 	true if the author is following the recipient.

    @Column(name = "sharing")
    private Boolean sharing; // Boolean 	true if the author is sharing with the recipient.

    @Column(name = "own_id")
    private Long ownId; // The ID of the recipient.

    @ManyToOne
    private Person person;

    @ManyToOne
    private Aspect aspect;

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

    public Long getOwnId() {
        return ownId;
    }

    public Contact ownId(Long ownId) {
        this.ownId = ownId;
        return this;
    }

    public void setOwnId(Long ownId) {
        this.ownId = ownId;
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

    public Aspect getAspect() {
        return aspect;
    }

    public Contact aspect(Aspect Aspect) {
        this.aspect = Aspect;
        return this;
    }

    public void setAspect(Aspect Aspect) {
        this.aspect = Aspect;
    }

    public Boolean getFollowing() {
        return following;
    }

    public Boolean getSharing() {
        return sharing;
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
        if (contact.getRecipient() == null || getRecipient() == null) {
            return false;
        }
        return Objects.equals(getRecipient(), contact.getRecipient());
        /*
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());*/
    }
    
    @Override
    public int hashCode() {
        //return Objects.hashCode(getId());
        return Objects.hashCode(getRecipient());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", recipient='" + getRecipient() + "'" +
            ", following='" + isFollowing() + "'" +
            ", sharing='" + isSharing() + "'" +
            ", ownId='" + getOwnId() + "'" +
                ", person='" + getPerson() + "'" +
                ", aspect='" + getAspect() + "'" +
            "}";
    }
}
