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
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "closed_account")
    private Boolean closedAccount;

    @Column(name = "fetch_status")
    private Integer fetchStatus;

    @Column(name = "pod_id")
    private Integer podId;

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

    @ManyToMany(mappedBy = "tagpeople")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tag> persontags = new HashSet<>();

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

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_conversation",
               joinColumns = @JoinColumn(name="people_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="conversations_id", referencedColumnName="id"))
    private Set<Conversation> conversations = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private UserAccount userAccount;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aspect> aspects = new HashSet<>();

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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Person createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public Person updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
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

    public Profile getProfile() {
        return profile;
    }

    public Person profile(Profile Profile) {
        this.profile = Profile;
        return this;
    }

    public void setProfile(Profile Profile) {
        this.profile = Profile;
    }

    public AccountDeletion getAccountdeletion() {
        return accountdeletion;
    }

    public Person accountdeletion(AccountDeletion AccountDeletion) {
        this.accountdeletion = AccountDeletion;
        return this;
    }

    public void setAccountdeletion(AccountDeletion AccountDeletion) {
        this.accountdeletion = AccountDeletion;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Person contacts(Set<Contact> Contacts) {
        this.contacts = Contacts;
        return this;
    }

    public Person addContacts(Contact Contact) {
        this.contacts.add(Contact);
        Contact.setPerson(this);
        return this;
    }

    public Person removeContacts(Contact Contact) {
        this.contacts.remove(Contact);
        Contact.setPerson(null);
        return this;
    }

    public void setContacts(Set<Contact> Contacts) {
        this.contacts = Contacts;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Person posts(Set<Post> Posts) {
        this.posts = Posts;
        return this;
    }

    public Person addPosts(Post Post) {
        this.posts.add(Post);
        Post.setPerson(this);
        return this;
    }

    public Person removePosts(Post Post) {
        this.posts.remove(Post);
        Post.setPerson(null);
        return this;
    }

    public void setPosts(Set<Post> Posts) {
        this.posts = Posts;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Person photos(Set<Photo> Photos) {
        this.photos = Photos;
        return this;
    }

    public Person addPhotos(Photo Photo) {
        this.photos.add(Photo);
        Photo.setPerson(this);
        return this;
    }

    public Person removePhotos(Photo Photo) {
        this.photos.remove(Photo);
        Photo.setPerson(null);
        return this;
    }

    public void setPhotos(Set<Photo> Photos) {
        this.photos = Photos;
    }

    public Set<Tag> getPersontags() {
        return persontags;
    }

    public Person persontags(Set<Tag> Tags) {
        this.persontags = Tags;
        return this;
    }

    public Person addPersontag(Tag Tag) {
        this.persontags.add(Tag);
        Tag.getTagpeople().add(this);
        return this;
    }

    public Person removePersontag(Tag Tag) {
        this.persontags.remove(Tag);
        Tag.getTagpeople().remove(this);
        return this;
    }

    public void setPersontags(Set<Tag> Tags) {
        this.persontags = Tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Person comments(Set<Comment> Comments) {
        this.comments = Comments;
        return this;
    }

    public Person addComments(Comment Comment) {
        this.comments.add(Comment);
        Comment.setPerson(this);
        return this;
    }

    public Person removeComments(Comment Comment) {
        this.comments.remove(Comment);
        Comment.setPerson(null);
        return this;
    }

    public void setComments(Set<Comment> Comments) {
        this.comments = Comments;
    }

    public Set<Participation> getParticipations() {
        return participations;
    }

    public Person participations(Set<Participation> Participations) {
        this.participations = Participations;
        return this;
    }

    public Person addParticipations(Participation Participation) {
        this.participations.add(Participation);
        Participation.setPerson(this);
        return this;
    }

    public Person removeParticipations(Participation Participation) {
        this.participations.remove(Participation);
        Participation.setPerson(null);
        return this;
    }

    public void setParticipations(Set<Participation> Participations) {
        this.participations = Participations;
    }

    public Set<EventParticipation> getEvents() {
        return events;
    }

    public Person events(Set<EventParticipation> EventParticipations) {
        this.events = EventParticipations;
        return this;
    }

    public Person addEvents(EventParticipation EventParticipation) {
        this.events.add(EventParticipation);
        EventParticipation.setPerson(this);
        return this;
    }

    public Person removeEvents(EventParticipation EventParticipation) {
        this.events.remove(EventParticipation);
        EventParticipation.setPerson(null);
        return this;
    }

    public void setEvents(Set<EventParticipation> EventParticipations) {
        this.events = EventParticipations;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Person messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Person addMessage(Message message) {
        this.messages.add(message);
        message.setPerson(this);
        return this;
    }

    public Person removeMessage(Message message) {
        this.messages.remove(message);
        message.setPerson(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public Person conversations(Set<Conversation> Conversations) {
        this.conversations = Conversations;
        return this;
    }

    public Person addConversation(Conversation Conversation) {
        this.conversations.add(Conversation);
        Conversation.getParticipants().add(this);
        return this;
    }

    public Person removeConversation(Conversation Conversation) {
        this.conversations.remove(Conversation);
        Conversation.getParticipants().remove(this);
        return this;
    }

    public void setConversations(Set<Conversation> Conversations) {
        this.conversations = Conversations;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Person userAccount(UserAccount UserAccount) {
        this.userAccount = UserAccount;
        return this;
    }

    public void setUserAccount(UserAccount UserAccount) {
        this.userAccount = UserAccount;
    }

    public Set<Aspect> getAspects() {
        return aspects;
    }

    public Person aspects(Set<Aspect> Aspects) {
        this.aspects = Aspects;
        return this;
    }

    public Person addAspect(Aspect Aspect) {
        this.aspects.add(Aspect);
        Aspect.setPerson(this);
        return this;
    }

    public Person removeAspect(Aspect Aspect) {
        this.aspects.remove(Aspect);
        Aspect.setPerson(null);
        return this;
    }

    public void setAspects(Set<Aspect> Aspects) {
        this.aspects = Aspects;
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
