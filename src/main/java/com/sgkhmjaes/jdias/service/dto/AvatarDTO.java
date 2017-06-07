package com.sgkhmjaes.jdias.service.dto;

/**
 * Created by inna on 02.06.17.
 */
public class AvatarDTO implements AutoMapping{
    private String profileImageUrlSmall;
    private String profileImageUrl;
    private String profileImageUrlMedium;

    public AvatarDTO(){}

    public AvatarDTO(String profileImageUrlSmall, String profileImageUrl, String profileImageUrlMedium) {
        this.profileImageUrlSmall = profileImageUrlSmall;
        this.profileImageUrl = profileImageUrl;
        this.profileImageUrlMedium = profileImageUrlMedium;
    }

    public String getProfileImageUrlSmall() {
        return profileImageUrlSmall;
    }

    public void setProfileImageUrlSmall(String profileImageUrlSmall) {
        this.profileImageUrlSmall = profileImageUrlSmall;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrlMedium() {
        return profileImageUrlMedium;
    }

    public void setProfileImageUrlMedium(String profileImageUrlMedium) {
        this.profileImageUrlMedium = profileImageUrlMedium;
    }


    
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("AvatarDTO: {").append("small=").append(profileImageUrlSmall).
            append(", large=").append(profileImageUrl).append(", medium=").append(profileImageUrlMedium).append("}");
    return sb.toString();
}
    
}
