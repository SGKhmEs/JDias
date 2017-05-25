package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PollParticipation.
 */
@Entity
@Table(name = "poll_participation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pollparticipation")
public class PollParticipation implements Serializable {

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

    @Column(name = "pollanswerguid")
    private String pollanswerguid;

    @Column(name = "authorsignature")
    private String authorsignature;

    @Column(name = "parentauthorsignature")
    private String parentauthorsignature;

    @ManyToOne
    private Poll poll;

    @ManyToOne
    private PollAnswer pollAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public PollParticipation author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public PollParticipation guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentguid() {
        return parentguid;
    }

    public PollParticipation parentguid(String parentguid) {
        this.parentguid = parentguid;
        return this;
    }

    public void setParentguid(String parentguid) {
        this.parentguid = parentguid;
    }

    public String getPollanswerguid() {
        return pollanswerguid;
    }

    public PollParticipation pollanswerguid(String pollanswerguid) {
        this.pollanswerguid = pollanswerguid;
        return this;
    }

    public void setPollanswerguid(String pollanswerguid) {
        this.pollanswerguid = pollanswerguid;
    }

    public String getAuthorsignature() {
        return authorsignature;
    }

    public PollParticipation authorsignature(String authorsignature) {
        this.authorsignature = authorsignature;
        return this;
    }

    public void setAuthorsignature(String authorsignature) {
        this.authorsignature = authorsignature;
    }

    public String getParentauthorsignature() {
        return parentauthorsignature;
    }

    public PollParticipation parentauthorsignature(String parentauthorsignature) {
        this.parentauthorsignature = parentauthorsignature;
        return this;
    }

    public void setParentauthorsignature(String parentauthorsignature) {
        this.parentauthorsignature = parentauthorsignature;
    }

    public Poll getPoll() {
        return poll;
    }

    public PollParticipation poll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public PollAnswer getPollAnswer() {
        return pollAnswer;
    }

    public PollParticipation pollAnswer(PollAnswer pollAnswer) {
        this.pollAnswer = pollAnswer;
        return this;
    }

    public void setPollAnswer(PollAnswer pollAnswer) {
        this.pollAnswer = pollAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PollParticipation pollParticipation = (PollParticipation) o;
        if (pollParticipation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pollParticipation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PollParticipation{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + getGuid() + "'" +
            ", parentguid='" + getParentguid() + "'" +
            ", pollanswerguid='" + getPollanswerguid() + "'" +
            ", authorsignature='" + getAuthorsignature() + "'" +
            ", parentauthorsignature='" + getParentauthorsignature() + "'" +
            "}";
    }
}
