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

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "guid")
    private String guid;

    @Column(name = "summary")
    private String summary;

    @Column(name = "jhi_start")
    private LocalDate start;

    @Column(name = "jhi_end")
    private LocalDate end;

    @Column(name = "all_day")
    private Boolean allDay;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EventParticipation> eventPatricipations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public Event author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public Event guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSummary() {
        return summary;
    }

    public Event summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDate getStart() {
        return start;
    }

    public Event start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Event end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Boolean isAllDay() {
        return allDay;
    }

    public Event allDay(Boolean allDay) {
        this.allDay = allDay;
        return this;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public String getTimezone() {
        return timezone;
    }

    public Event timezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<EventParticipation> getEventPatricipations() {
        return eventPatricipations;
    }

    public Event eventPatricipations(Set<EventParticipation> eventParticipations) {
        this.eventPatricipations = eventParticipations;
        return this;
    }

    public Event addEventPatricipations(EventParticipation eventParticipation) {
        this.eventPatricipations.add(eventParticipation);
        eventParticipation.setEvent(this);
        return this;
    }

    public Event removeEventPatricipations(EventParticipation eventParticipation) {
        this.eventPatricipations.remove(eventParticipation);
        eventParticipation.setEvent(null);
        return this;
    }

    public void setEventPatricipations(Set<EventParticipation> eventParticipations) {
        this.eventPatricipations = eventParticipations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + getGuid() + "'" +
            ", summary='" + getSummary() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", allDay='" + isAllDay() + "'" +
            ", timezone='" + getTimezone() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
