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

    @Column(name = "rootauthor")
    private String rootauthor;

    @Column(name = "rootguid")
    private String rootguid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRootauthor() {
        return rootauthor;
    }

    public Reshare rootauthor(String rootauthor) {
        this.rootauthor = rootauthor;
        return this;
    }

    public void setRootauthor(String rootauthor) {
        this.rootauthor = rootauthor;
    }

    public String getRootguid() {
        return rootguid;
    }

    public Reshare rootguid(String rootguid) {
        this.rootguid = rootguid;
        return this;
    }

    public void setRootguid(String rootguid) {
        this.rootguid = rootguid;
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
        return "Reshare{" +
            "id=" + getId() +
            ", rootauthor='" + getRootauthor() + "'" +
            ", rootguid='" + getRootguid() + "'" +
            "}";
    }
}
