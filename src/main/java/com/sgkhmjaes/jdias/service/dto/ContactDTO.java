package com.sgkhmjaes.jdias.service.dto;

/**
 * Created by inna on 7/8/17.
 */
public class ContactDTO {

    private Long id;

    private String author;

    private String recipient;

    private Boolean following;

    private Boolean sharing;

    private Long ownId;

    private Long personId;

    private Long aspectId;

    public ContactDTO(){}
    public ContactDTO(Long id, String author, String recipient, Boolean following, Boolean sharing, Long ownId, Long personId, Long aspectId) {
        this.id = id;
        this.author = author;
        this.recipient = recipient;
        this.following = following;
        this.sharing = sharing;
        this.ownId = ownId;
        this.personId = personId;
        this.aspectId = aspectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public Boolean getSharing() {
        return sharing;
    }

    public void setSharing(Boolean sharing) {
        this.sharing = sharing;
    }

    public Long getOwnId() {
        return ownId;
    }

    public void setOwnId(Long ownId) {
        this.ownId = ownId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getAspectId() {
        return aspectId;
    }

    public void setAspectId(Long aspectId) {
        this.aspectId = aspectId;
    }
}

