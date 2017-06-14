package com.sgkhmjaes.jdias.service.dto;

/**
 * Created by inna on 10.06.17.
 */
public class IdentificatorDTO {
    private long id;

    public IdentificatorDTO(){}

    public IdentificatorDTO(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
