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
 * A PollAnswer.
 */
@Entity
@Table(name = "poll_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pollanswer")
public class PollAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "answer")
    private String answer;

    @ManyToOne
    private Poll poll;

    @OneToMany(mappedBy = "pollAnswer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PollParticipation> pollanswers1S = new HashSet<>();

    public PollAnswer(String answer, Poll poll) {
        this.guid = UUID.randomUUID().toString();
        this.poll = poll;
        this.answer = answer;
    }

    public PollAnswer() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public PollAnswer guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAnswer() {
        return answer;
    }

    public PollAnswer answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Poll getPoll() {
        return poll;
    }

    public PollAnswer poll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Set<PollParticipation> getPollanswers1S() {
        return pollanswers1S;
    }

    public PollAnswer pollanswers1S(Set<PollParticipation> pollParticipations) {
        this.pollanswers1S = pollParticipations;
        return this;
    }

    public PollAnswer addPollanswers1(PollParticipation pollParticipation) {
        this.pollanswers1S.add(pollParticipation);
        pollParticipation.setPollAnswer(this);
        return this;
    }

    public PollAnswer removePollanswers1(PollParticipation pollParticipation) {
        this.pollanswers1S.remove(pollParticipation);
        pollParticipation.setPollAnswer(null);
        return this;
    }

    public void setPollanswers1S(Set<PollParticipation> pollParticipations) {
        this.pollanswers1S = pollParticipations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PollAnswer pollAnswer = (PollAnswer) o;
        if (pollAnswer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pollAnswer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PollAnswer{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
