package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "photo")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "guid")
    private Boolean guid;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "remote_photo_path")
    private String remotePhotoPath;

    @Column(name = "remote_photo_name")
    private String remotePhotoName;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "text")
    private String text;

    @Column(name = "status_message_guid")
    private String statusMessageGuid;

    @ManyToOne
    private StatusMessage statusMessage;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public Photo author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean isGuid() {
        return guid;
    }

    public Photo guid(Boolean guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(Boolean guid) {
        this.guid = guid;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Photo createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRemotePhotoPath() {
        return remotePhotoPath;
    }

    public Photo remotePhotoPath(String remotePhotoPath) {
        this.remotePhotoPath = remotePhotoPath;
        return this;
    }

    public void setRemotePhotoPath(String remotePhotoPath) {
        this.remotePhotoPath = remotePhotoPath;
    }

    public String getRemotePhotoName() {
        return remotePhotoName;
    }

    public Photo remotePhotoName(String remotePhotoName) {
        this.remotePhotoName = remotePhotoName;
        return this;
    }

    public void setRemotePhotoName(String remotePhotoName) {
        this.remotePhotoName = remotePhotoName;
    }

    public Integer getHeight() {
        return height;
    }

    public Photo height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Photo width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getText() {
        return text;
    }

    public Photo text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatusMessageGuid() {
        return statusMessageGuid;
    }

    public Photo statusMessageGuid(String statusMessageGuid) {
        this.statusMessageGuid = statusMessageGuid;
        return this;
    }

    public void setStatusMessageGuid(String statusMessageGuid) {
        this.statusMessageGuid = statusMessageGuid;
    }

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    public Photo statusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Person getPerson() {
        return person;
    }

    public Photo person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        if (photo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), photo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + isGuid() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", remotePhotoPath='" + getRemotePhotoPath() + "'" +
            ", remotePhotoName='" + getRemotePhotoName() + "'" +
            ", height='" + getHeight() + "'" +
            ", width='" + getWidth() + "'" +
            ", text='" + getText() + "'" +
            ", statusMessageGuid='" + getStatusMessageGuid() + "'" +
            "}";
    }
}
