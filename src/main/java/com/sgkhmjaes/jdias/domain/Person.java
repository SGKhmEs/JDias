package com.sgkhmjaes.jdias.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "diaspora_id")
    private String diasporaId;

    @Column(name = "serialized_public_key")
    private String serializedPublicKey;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "closed_account")
    private Boolean closedAccount;

    @Column(name = "fetch_status")
    private Integer fetchStatus;

    @Column(name = "pod_id")
    private Integer podId;

    @ManyToOne
    private Conversation conversation;

    @OneToOne
    @JoinColumn(unique = true)
    private Profile profile;

    @OneToOne
    @JoinColumn(unique = true)
    private AccountDeletion accountdeletion;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Participation> participations = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EventParticipation> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public Person guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDiasporaId() {
        return diasporaId;
    }

    public Person diasporaId(String diasporaId) {
        this.diasporaId = diasporaId;
        return this;
    }

    public void setDiasporaId(String diasporaId) {
        this.diasporaId = diasporaId;
    }

    public String getSerializedPublicKey() {
        return serializedPublicKey;
    }

    public Person serializedPublicKey(String serializedPublicKey) {
        this.serializedPublicKey = serializedPublicKey;
        return this;
    }

    public void setSerializedPublicKey(String serializedPublicKey) {
        this.serializedPublicKey = serializedPublicKey;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Person createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Person updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean isClosedAccount() {
        return closedAccount;
    }

    public Person closedAccount(Boolean closedAccount) {
        this.closedAccount = closedAccount;
        return this;
    }

    public void setClosedAccount(Boolean closedAccount) {
        this.closedAccount = closedAccount;
    }

    public Integer getFetchStatus() {
        return fetchStatus;
    }

    public Person fetchStatus(Integer fetchStatus) {
        this.fetchStatus = fetchStatus;
        return this;
    }

    public void setFetchStatus(Integer fetchStatus) {
        this.fetchStatus = fetchStatus;
    }

    public Integer getPodId() {
        return podId;
    }

    public Person podId(Integer podId) {
        this.podId = podId;
        return this;
    }

    public void setPodId(Integer podId) {
        this.podId = podId;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Person conversation(Conversation conversation) {
        this.conversation = conversation;
        return this;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Profile getProfile() {
        return profile;
    }

    public Person profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public AccountDeletion getAccountdeletion() {
        return accountdeletion;
    }

    public Person accountdeletion(AccountDeletion accountDeletion) {
        this.accountdeletion = accountDeletion;
        return this;
    }

    public void setAccountdeletion(AccountDeletion accountDeletion) {
        this.accountdeletion = accountDeletion;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Person contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Person addContacts(Contact contact) {
        this.contacts.add(contact);
        contact.setPerson(this);
        return this;
    }

    public Person removeContacts(Contact contact) {
        this.contacts.remove(contact);
        contact.setPerson(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Person posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Person addPosts(Post post) {
        this.posts.add(post);
        post.setPerson(this);
        return this;
    }

    public Person removePosts(Post post) {
        this.posts.remove(post);
        post.setPerson(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Person photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Person addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setPerson(this);
        return this;
    }

    public Person removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setPerson(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Person comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Person addComments(Comment comment) {
        this.comments.add(comment);
        comment.setPerson(this);
        return this;
    }

    public Person removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setPerson(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Participation> getParticipations() {
        return participations;
    }

    public Person participations(Set<Participation> participations) {
        this.participations = participations;
        return this;
    }

    public Person addParticipations(Participation participation) {
        this.participations.add(participation);
        participation.setPerson(this);
        return this;
    }

    public Person removeParticipations(Participation participation) {
        this.participations.remove(participation);
        participation.setPerson(null);
        return this;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }

    public Set<EventParticipation> getEvents() {
        return events;
    }

    public Person events(Set<EventParticipation> eventParticipations) {
        this.events = eventParticipations;
        return this;
    }

    public Person addEvents(EventParticipation eventParticipation) {
        this.events.add(eventParticipation);
        eventParticipation.setPerson(this);
        return this;
    }

    public Person removeEvents(EventParticipation eventParticipation) {
        this.events.remove(eventParticipation);
        eventParticipation.setPerson(null);
        return this;
    }

    public void setEvents(Set<EventParticipation> eventParticipations) {
        this.events = eventParticipations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", diasporaId='" + getDiasporaId() + "'" +
            ", serializedPublicKey='" + getSerializedPublicKey() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", closedAccount='" + isClosedAccount() + "'" +
            ", fetchStatus='" + getFetchStatus() + "'" +
            ", podId='" + getPodId() + "'" +
            "}";
    }
}
