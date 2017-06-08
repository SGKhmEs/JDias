package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.sgkhmjaes.jdias.domain.enumeration.Type;

/**
 * A Participation.
 */
@Entity
@Table(name = "participation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "participation")
public class Participation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "guid")
    private String guid;

    @Column(name = "parent_guid")
    private String parentGuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_type")
    private Type parentType;

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

    public Participation author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public Participation guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public Participation parentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
        return this;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public Type getParentType() {
        return parentType;
    }

    public Participation parentType(Type parentType) {
        this.parentType = parentType;
        return this;
    }

    public void setParentType(Type parentType) {
        this.parentType = parentType;
    }

    public Person getPerson() {
        return person;
    }

    public Participation person(Person person) {
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
        Participation participation = (Participation) o;
        if (participation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), participation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Participation{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", guid='" + getGuid() + "'" +
            ", parentGuid='" + getParentGuid() + "'" +
            ", parentType='" + getParentType() + "'" +
            "}";
    }
}
