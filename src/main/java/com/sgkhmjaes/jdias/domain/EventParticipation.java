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

    @Column(name = "parentguid")
    private String parentguid;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EventStatus status;

    @Column(name = "authorsignature")
    private String authorsignature;

    @Column(name = "parentauthorsignature")
    private String parentauthorsignature;

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

    public String getParentguid() {
        return parentguid;
    }

    public EventParticipation parentguid(String parentguid) {
        this.parentguid = parentguid;
        return this;
    }

    public void setParentguid(String parentguid) {
        this.parentguid = parentguid;
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

    public String getAuthorsignature() {
        return authorsignature;
    }

    public EventParticipation authorsignature(String authorsignature) {
        this.authorsignature = authorsignature;
        return this;
    }

    public void setAuthorsignature(String authorsignature) {
        this.authorsignature = authorsignature;
    }

    public String getParentauthorsignature() {
        return parentauthorsignature;
    }

    public EventParticipation parentauthorsignature(String parentauthorsignature) {
        this.parentauthorsignature = parentauthorsignature;
        return this;
    }

    public void setParentauthorsignature(String parentauthorsignature) {
        this.parentauthorsignature = parentauthorsignature;
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
        return "EventParticipation{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + getGuid() + "'" +
            ", parentguid='" + getParentguid() + "'" +
            ", status='" + getStatus() + "'" +
            ", authorsignature='" + getAuthorsignature() + "'" +
            ", parentauthorsignature='" + getParentauthorsignature() + "'" +
            "}";
    }
}
