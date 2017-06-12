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
 * A Conversation.
 */
@Entity
@Table(name = "conversation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "conversation")
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "guid")
    private String guid;

    @Column(name = "subject")
    private String subject;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "message")
    private String message;

    @OneToMany(mappedBy = "conversation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Person> participants = new HashSet<>();

    @OneToMany(mappedBy = "conversation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();

    @ManyToOne
    private UserAccount userAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public Conversation author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public Conversation guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSubject() {
        return subject;
    }

    public Conversation subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Conversation createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public Conversation message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<Person> getParticipants() {
        return participants;
    }

    public Conversation participants(Set<Person> people) {
        this.participants = people;
        return this;
    }

    public Conversation addParticipants(Person person) {
        this.participants.add(person);
        person.setConversation(this);
        return this;
    }

    public Conversation removeParticipants(Person person) {
        this.participants.remove(person);
        person.setConversation(null);
        return this;
    }

    public void setParticipants(Set<Person> people) {
        this.participants = people;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Conversation messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Conversation addMessages(Message message) {
        this.messages.add(message);
        message.setConversation(this);
        return this;
    }

    public Conversation removeMessages(Message message) {
        this.messages.remove(message);
        message.setConversation(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Conversation userAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Conversation conversation = (Conversation) o;
        if (conversation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conversation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Conversation{"
                + "id=" + getId()
                + ", author='" + getAuthor() + "'"
                + ", guid='" + getGuid() + "'"
                + ", subject='" + getSubject() + "'"
                + ", createdAt='" + getCreatedAt() + "'"
                + ", message='" + getMessage() + "'"
                + "}";
    }
}
