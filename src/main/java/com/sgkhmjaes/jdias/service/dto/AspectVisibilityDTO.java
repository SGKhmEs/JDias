package com.sgkhmjaes.jdias.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AspectVisibility entity.
 */
public class AspectVisibilityDTO implements Serializable {

    private Long id;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private Long aspectId;

    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getAspectId() {
        return aspectId;
    }

    public void setAspectId(Long AspectId) {
        this.aspectId = AspectId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long PostId) {
        this.postId = PostId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AspectVisibilityDTO aspectVisibilityDTO = (AspectVisibilityDTO) o;
        if(aspectVisibilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aspectVisibilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AspectVisibilityDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
