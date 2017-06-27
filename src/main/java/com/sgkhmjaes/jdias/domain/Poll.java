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
import java.util.UUID;

/**
 * A Poll.
 */
@Entity
@Table(name = "poll")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "poll")
public class Poll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "question")
    private String question;

    @OneToMany(mappedBy = "poll")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PollAnswer> pollanswers = new HashSet<>();

    @OneToMany(mappedBy = "poll")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PollParticipation> pollparticipants = new HashSet<>();

    public Poll(){}

    public Poll(String question, Set<PollAnswer> pollanswers) {
        this.guid = UUID.randomUUID().toString();
        this.question = question;
        this.pollanswers = pollanswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public Poll guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getQuestion() {
        return question;
    }

    public Poll question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<PollAnswer> getPollanswers() {
        return pollanswers;
    }

    public Poll pollanswers(Set<PollAnswer> pollAnswers) {
        this.pollanswers = pollAnswers;
        return this;
    }

    public Poll addPollanswers(PollAnswer pollAnswer) {
        this.pollanswers.add(pollAnswer);
        pollAnswer.setPoll(this);
        return this;
    }

    public Poll removePollanswers(PollAnswer pollAnswer) {
        this.pollanswers.remove(pollAnswer);
        pollAnswer.setPoll(null);
        return this;
    }

    public void setPollanswers(Set<PollAnswer> pollAnswers) {
        this.pollanswers = pollAnswers;
    }

    public Set<PollParticipation> getPollparticipants() {
        return pollparticipants;
    }

    public Poll pollparticipants(Set<PollParticipation> pollParticipations) {
        this.pollparticipants = pollParticipations;
        return this;
    }

    public Poll addPollparticipants(PollParticipation pollParticipation) {
        this.pollparticipants.add(pollParticipation);
        pollParticipation.setPoll(this);
        return this;
    }

    public Poll removePollparticipants(PollParticipation pollParticipation) {
        this.pollparticipants.remove(pollParticipation);
        pollParticipation.setPoll(null);
        return this;
    }

    public void setPollparticipants(Set<PollParticipation> pollParticipations) {
        this.pollparticipants = pollParticipations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Poll poll = (Poll) o;
        if (poll.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poll.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Poll{"
                + "id=" + getId()
                + ", guid='" + getGuid() + "'"
                + ", question='" + getQuestion() + "'"
                + "}";
    }
}
