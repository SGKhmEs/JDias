package com.sgkhmjaes.jdias.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Reshare.
 */
@Entity
@Table(name = "reshare")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reshare")
public class Reshare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "root_author")
    private String rootAuthor;

    @Column(name = "root_guid")
    private String rootGuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRootAuthor() {
        return rootAuthor;
    }

    public Reshare rootAuthor(String rootAuthor) {
        this.rootAuthor = rootAuthor;
        return this;
    }

    public void setRootAuthor(String rootAuthor) {
        this.rootAuthor = rootAuthor;
    }

    public String getRootGuid() {
        return rootGuid;
    }

    public Reshare rootGuid(String rootGuid) {
        this.rootGuid = rootGuid;
        return this;
    }

    public void setRootGuid(String rootGuid) {
        this.rootGuid = rootGuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reshare reshare = (Reshare) o;
        if (reshare.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reshare.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reshare{"
                + "id=" + getId()
                + ", rootAuthor='" + getRootAuthor() + "'"
                + ", rootGuid='" + getRootGuid() + "'"
                + "}";
    }
}
