package com.sgkhmjaes.jdias.service.dto;

import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 * Created by inna on 02.06.17.
 */
public class AvatarDTO implements AutoMapping {

    private String imageUrlSmall;
    private String imageUrl;
    private String imageUrlMedium;

    public AvatarDTO() {
    }

    public String getImageUrlSmall() {
        return imageUrlSmall;
    }

    public void setImageUrlSmall(String imageUrlSmall) {
        this.imageUrlSmall = imageUrlSmall;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlMedium() {
        return imageUrlMedium;
    }

    public void setImageUrlMedium(String imageUrlMedium) {
        this.imageUrlMedium = imageUrlMedium;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AvatarDTO: {").append("small=").append(imageUrlSmall).
                append(", large=").append(imageUrl).append(", medium=").append(imageUrlMedium).append("}");
        return sb.toString();
    }

}
