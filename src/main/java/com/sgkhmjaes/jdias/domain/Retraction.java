package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.sgkhmjaes.jdias.domain.enumeration.Type;

/**
 * A Retraction.
 */
@Entity
@Table(name = "retraction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "retraction")
public class Retraction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "targetguid")
    private String targetguid;

    @Enumerated(EnumType.STRING)
    @Column(name = "targettype")
    private Type targettype;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public Retraction author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTargetguid() {
        return targetguid;
    }

    public Retraction targetguid(String targetguid) {
        this.targetguid = targetguid;
        return this;
    }

    public void setTargetguid(String targetguid) {
        this.targetguid = targetguid;
    }

    public Type getTargettype() {
        return targettype;
    }

    public Retraction targettype(Type targettype) {
        this.targettype = targettype;
        return this;
    }

    public void setTargettype(Type targettype) {
        this.targettype = targettype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Retraction retraction = (Retraction) o;
        if (retraction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), retraction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Retraction{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", targetguid='" + getTargetguid() + "'" +
            ", targettype='" + getTargettype() + "'" +
            "}";
    }
}
