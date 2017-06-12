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

    @Column(name = "target_guid")
    private String targetGuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    private Type targetType;

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

    public String getTargetGuid() {
        return targetGuid;
    }

    public Retraction targetGuid(String targetGuid) {
        this.targetGuid = targetGuid;
        return this;
    }

    public void setTargetGuid(String targetGuid) {
        this.targetGuid = targetGuid;
    }

    public Type getTargetType() {
        return targetType;
    }

    public Retraction targetType(Type targetType) {
        this.targetType = targetType;
        return this;
    }

    public void setTargetType(Type targetType) {
        this.targetType = targetType;
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
        return "Retraction{"
                + "id=" + getId()
                + ", author='" + getAuthor() + "'"
                + ", targetGuid='" + getTargetGuid() + "'"
                + ", targetType='" + getTargetType() + "'"
                + "}";
    }
}
