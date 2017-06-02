package com.sgkhmjaes.jdias.service.dto;

/**
 * Created by inna on 02.06.17.
 */
public class AvatarDTO {
    private String small;
    private String large;
    private String medium;

    public AvatarDTO(){}

    public AvatarDTO(String small, String large, String medium) {
        this.small = small;
        this.large = large;
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
