package com.sgkhmjaes.jdias.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sgkhmjaes.jdias.security.RSAKeysGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.UUID;

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
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    //@SequenceGenerator(name = "sequenceGenerator")
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

    @JsonIgnore
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

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Message> messages = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @OrderBy(value="updatedAt DESC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_conversation",
               joinColumns = @JoinColumn(name="people_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="conversations_id", referencedColumnName="id"))
    private List<Conversation> conversations = new ArrayList<>();

    @OneToOne
    @JoinColumn(unique = true)
    private UserAccount userAccount;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aspect> aspects = new HashSet<>();

    public Person (){}

    public Person (Long id, String serializedPublicKey, String login){
        this.id = id;
        this.serializedPublicKey = RSAKeysGenerator.getRsaPublicKey(serializedPublicKey);
        this.closedAccount = false;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.guid = UUID.nameUUIDFromBytes(login.getBytes()).toString();
    }

    public Set<Aspect> getAspects() {
        return aspects;
    }

    public void setAspects(Set<Aspect> aspects) {
        this.aspects = aspects;
    }

    public Person addAspect(Aspect aspect) {
        this.aspects.add(aspect);
        aspect.setPerson(this);
        return this;
    }

    public Person removeAspect(Aspect aspect) {
        this.aspects.remove(aspect);
        aspect.setPerson(null);
        return this;
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

    public List<Message> getMessages() {
        return messages;
    }

    public Person messages(List<Message> messages) {
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

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public Person conversations(List<Conversation> conversations) {
        this.conversations = conversations;
        return this;
    }

    public Person addConversation(Conversation conversation) {
        this.conversations.add(conversation);
        conversation.getParticipants().add(this);
        return this;
    }

    public boolean addUniqueConversation(Conversation conversation) {
        if (!this.conversations.contains(conversation)) {
            this.conversations.add(conversation);
            conversation.getParticipants().add(this);
            return true;
        }
        else return false;
    }

    public Person removeConversation(Conversation conversation) {
        this.conversations.remove(conversation);
        conversation.getParticipants().remove(this);
        return this;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Person userAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

//    public Set<Aspect> getAspects() {
//        return aspects;
//    }
//
//    public void setAspects(Set<Aspect> aspects) {
//        this.aspects = aspects;
//    }
//
//    public Person addAspect(Aspect aspect) {
//        this.aspects.add(aspect);
//        aspect.setPerson(this);
//        return this;
//    }
//
//    public Person removeAspect(Aspect aspect) {
//        this.aspects.remove(aspect);
//        aspect.setPerson(null);
//        return this;
//    }

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
