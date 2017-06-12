package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.sgkhmjaes.jdias.domain.enumeration.EventStatus;

/**
 * A EventParticipation.
 */
@Entity
@Table(name = "event_participation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eventparticipation")
public class EventParticipation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "guid")
    private String guid;

    @Column(name = "parent_guid")
    private String parentGuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EventStatus status;

    @Column(name = "author_signature")
    private String authorSignature;

    @Column(name = "parent_author_signature")
    private String parentAuthorSignature;

    @ManyToOne
    private Event event;

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

    public EventParticipation author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public EventParticipation guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public EventParticipation parentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
        return this;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public EventStatus getStatus() {
        return status;
    }

    public EventParticipation status(EventStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getAuthorSignature() {
        return authorSignature;
    }

    public EventParticipation authorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
        return this;
    }

    public void setAuthorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
    }

    public String getParentAuthorSignature() {
        return parentAuthorSignature;
    }

    public EventParticipation parentAuthorSignature(String parentAuthorSignature) {
        this.parentAuthorSignature = parentAuthorSignature;
        return this;
    }

    public void setParentAuthorSignature(String parentAuthorSignature) {
        this.parentAuthorSignature = parentAuthorSignature;
    }

    public Event getEvent() {
        return event;
    }

    public EventParticipation event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Person getPerson() {
        return person;
    }

    public EventParticipation person(Person person) {
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
        EventParticipation eventParticipation = (EventParticipation) o;
        if (eventParticipation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventParticipation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventParticipation{"
                + "id=" + getId()
                + ", author='" + getAuthor() + "'"
                + ", guid='" + getGuid() + "'"
                + ", parentGuid='" + getParentGuid() + "'"
                + ", status='" + getStatus() + "'"
                + ", authorSignature='" + getAuthorSignature() + "'"
                + ", parentAuthorSignature='" + getParentAuthorSignature() + "'"
                + "}";
    }
}
