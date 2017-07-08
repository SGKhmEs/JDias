package com.sgkhmjaes.jdias.domain;

import com.sgkhmjaes.jdias.service.dto.PhotoSizesDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

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
    private String guid;

    @Column(name = "created_at")
    private LocalDate createdAt;

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

    @Column(name = "thumb_small")
    private String thumb_small;

    @Column(name = "thumb_medium")
    private String thumb_medium;

    @Column(name = "thumb_large")
    private String thumb_large;

    @Column(name = "scaled_full")
    private String scaled_full;

    @ManyToOne
    private StatusMessage statusMessage;

    @ManyToOne
    private Person person;

    public Photo(){}

    public Photo(String author, String fileName, String remotePhotoPath, String remotePhotoName, Integer height, Integer width, String text, PhotoSizesDTO photoSizesDTO, Person person) {
        this.author = author;
        this.guid = UUID.nameUUIDFromBytes(fileName.getBytes()).toString();
        this.createdAt = LocalDate.now();
        this.remotePhotoPath = remotePhotoPath;
        this.remotePhotoName = remotePhotoName;
        this.height = height;
        this.width = width;
        this.text = text;
        this.thumb_small = photoSizesDTO.getThumbSmall();
        this.thumb_medium = photoSizesDTO.getThumbMedium();
        this.thumb_large = photoSizesDTO.getThumbLarge();
        this.scaled_full = photoSizesDTO.getScaledFull();
        this.person = person;
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

    public Photo author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public Photo guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Photo createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
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

    public String getThumb_small() {
        return thumb_small;
    }

    public Photo thumb_small(String thumb_small) {
        this.thumb_small = thumb_small;
        return this;
    }

    public void setThumb_small(String thumb_small) {
        this.thumb_small = thumb_small;
    }

    public String getThumb_medium() {
        return thumb_medium;
    }

    public Photo thumb_medium(String thumb_medium) {
        this.thumb_medium = thumb_medium;
        return this;
    }

    public void setThumb_medium(String thumb_medium) {
        this.thumb_medium = thumb_medium;
    }

    public String getThumb_large() {
        return thumb_large;
    }

    public Photo thumb_large(String thumb_large) {
        this.thumb_large = thumb_large;
        return this;
    }

    public void setThumb_large(String thumb_large) {
        this.thumb_large = thumb_large;
    }

    public String getScaled_full() {
        return scaled_full;
    }

    public Photo scaled_full(String scaled_full) {
        this.scaled_full = scaled_full;
        return this;
    }

    public void setScaled_full(String scaled_full) {
        this.scaled_full = scaled_full;
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
            ", guid='" + getGuid() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", remotePhotoPath='" + getRemotePhotoPath() + "'" +
            ", remotePhotoName='" + getRemotePhotoName() + "'" +
            ", height='" + getHeight() + "'" +
            ", width='" + getWidth() + "'" +
            ", text='" + getText() + "'" +
            ", statusMessageGuid='" + getStatusMessageGuid() + "'" +
            ", thumb_small='" + getThumb_small() + "'" +
            ", thumb_medium='" + getThumb_medium() + "'" +
            ", thumb_large='" + getThumb_large() + "'" +
            ", scaled_full='" + getScaled_full() + "'" +
            "}";
    }
}
