package com.sgkhmjaes.jdias.service.dto.aspectDTOs;

import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 * Created by inna on 10.06.17.
 */
public class AspectListDTO implements AutoMapping {
    private long id;
    private String name;
    private int order;

    public AspectListDTO(){}

    public AspectListDTO(long id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
