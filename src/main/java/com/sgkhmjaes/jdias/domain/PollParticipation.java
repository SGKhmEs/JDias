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

    @Column(name = "parent_guid")
    private String parentGuid;

    @Column(name = "poll_answer_guid")
    private String pollAnswerGuid;

    @Column(name = "author_signature")
    private String authorSignature;

    @Column(name = "parent_author_signature")
    private String parentAuthorSignature;

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

    public String getParentGuid() {
        return parentGuid;
    }

    public PollParticipation parentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
        return this;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getPollAnswerGuid() {
        return pollAnswerGuid;
    }

    public PollParticipation pollAnswerGuid(String pollAnswerGuid) {
        this.pollAnswerGuid = pollAnswerGuid;
        return this;
    }

    public void setPollAnswerGuid(String pollAnswerGuid) {
        this.pollAnswerGuid = pollAnswerGuid;
    }

    public String getAuthorSignature() {
        return authorSignature;
    }

    public PollParticipation authorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
        return this;
    }

    public void setAuthorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
    }

    public String getParentAuthorSignature() {
        return parentAuthorSignature;
    }

    public PollParticipation parentAuthorSignature(String parentAuthorSignature) {
        this.parentAuthorSignature = parentAuthorSignature;
        return this;
    }

    public void setParentAuthorSignature(String parentAuthorSignature) {
        this.parentAuthorSignature = parentAuthorSignature;
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
            ", parentGuid='" + getParentGuid() + "'" +
            ", pollAnswerGuid='" + getPollAnswerGuid() + "'" +
            ", authorSignature='" + getAuthorSignature() + "'" +
            ", parentAuthorSignature='" + getParentAuthorSignature() + "'" +
            "}";
    }
}
