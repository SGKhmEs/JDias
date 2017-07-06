package com.sgkhmjaes.jdias.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Aspectvisibility entity.
 */
public class AspectvisibilityDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AspectvisibilityDTO aspectvisibilityDTO = (AspectvisibilityDTO) o;
        if(aspectvisibilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aspectvisibilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AspectvisibilityDTO{" +
            "id=" + getId() +
            "}";
    }
}
