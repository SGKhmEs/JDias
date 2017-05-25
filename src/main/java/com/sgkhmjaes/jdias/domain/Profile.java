package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "imageurl")
    private String imageurl;

    @Column(name = "imageurlsmall")
    private String imageurlsmall;

    @Column(name = "imageurlmedium")
    private String imageurlmedium;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender")
    private String gender;

    @Column(name = "bio")
    private String bio;

    @Column(name = "location")
    private String location;

    @Column(name = "searchable")
    private Boolean searchable;

    @Column(name = "nsfw")
    private Boolean nsfw;

    @Column(name = "tagstring")
    private String tagstring;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public Profile author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFirstname() {
        return firstname;
    }

    public Profile firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Profile lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public Profile imageurl(String imageurl) {
        this.imageurl = imageurl;
        return this;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurlsmall() {
        return imageurlsmall;
    }

    public Profile imageurlsmall(String imageurlsmall) {
        this.imageurlsmall = imageurlsmall;
        return this;
    }

    public void setImageurlsmall(String imageurlsmall) {
        this.imageurlsmall = imageurlsmall;
    }

    public String getImageurlmedium() {
        return imageurlmedium;
    }

    public Profile imageurlmedium(String imageurlmedium) {
        this.imageurlmedium = imageurlmedium;
        return this;
    }

    public void setImageurlmedium(String imageurlmedium) {
        this.imageurlmedium = imageurlmedium;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Profile birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public Profile gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public Profile bio(String bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public Profile location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isSearchable() {
        return searchable;
    }

    public Profile searchable(Boolean searchable) {
        this.searchable = searchable;
        return this;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean isNsfw() {
        return nsfw;
    }

    public Profile nsfw(Boolean nsfw) {
        this.nsfw = nsfw;
        return this;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    public String getTagstring() {
        return tagstring;
    }

    public Profile tagstring(String tagstring) {
        this.tagstring = tagstring;
        return this;
    }

    public void setTagstring(String tagstring) {
        this.tagstring = tagstring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", imageurl='" + getImageurl() + "'" +
            ", imageurlsmall='" + getImageurlsmall() + "'" +
            ", imageurlmedium='" + getImageurlmedium() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", gender='" + getGender() + "'" +
            ", bio='" + getBio() + "'" +
            ", location='" + getLocation() + "'" +
            ", searchable='" + isSearchable() + "'" +
            ", nsfw='" + isNsfw() + "'" +
            ", tagstring='" + getTagstring() + "'" +
            "}";
    }
}
