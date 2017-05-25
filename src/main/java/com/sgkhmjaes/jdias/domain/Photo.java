package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    @Column(name = "createdat")
    private LocalDate createdat;

    @Column(name = "remotephotopath")
    private String remotephotopath;

    @Column(name = "remotephotoname")
    private String remotephotoname;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "text")
    private String text;

    @Column(name = "statusmessageguid")
    private String statusmessageguid;

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

    public LocalDate getCreatedat() {
        return createdat;
    }

    public Photo createdat(LocalDate createdat) {
        this.createdat = createdat;
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public String getRemotephotopath() {
        return remotephotopath;
    }

    public Photo remotephotopath(String remotephotopath) {
        this.remotephotopath = remotephotopath;
        return this;
    }

    public void setRemotephotopath(String remotephotopath) {
        this.remotephotopath = remotephotopath;
    }

    public String getRemotephotoname() {
        return remotephotoname;
    }

    public Photo remotephotoname(String remotephotoname) {
        this.remotephotoname = remotephotoname;
        return this;
    }

    public void setRemotephotoname(String remotephotoname) {
        this.remotephotoname = remotephotoname;
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

    public String getStatusmessageguid() {
        return statusmessageguid;
    }

    public Photo statusmessageguid(String statusmessageguid) {
        this.statusmessageguid = statusmessageguid;
        return this;
    }

    public void setStatusmessageguid(String statusmessageguid) {
        this.statusmessageguid = statusmessageguid;
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
            ", createdat='" + getCreatedat() + "'" +
            ", remotephotopath='" + getRemotephotopath() + "'" +
            ", remotephotoname='" + getRemotephotoname() + "'" +
            ", height='" + getHeight() + "'" +
            ", width='" + getWidth() + "'" +
            ", text='" + getText() + "'" +
            ", statusmessageguid='" + getStatusmessageguid() + "'" +
            "}";
    }
}
