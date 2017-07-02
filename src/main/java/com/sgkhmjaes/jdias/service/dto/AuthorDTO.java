package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 * Created by inna on 02.06.17.
 */
public class AuthorDTO implements AutoMapping {

    @JsonProperty("diaspora_id")
    private String diasporaId;
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("avatar")
    private AvatarDTO avatarDTO;

    public AuthorDTO() {
    }

    public String getDiasporaId() {
        return diasporaId;
    }

    public void setDiasporaId(String diasporaId) {
        this.diasporaId = diasporaId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AvatarDTO getAvatarDTO() {
        return avatarDTO;
    }

    public void setAvatarDTO(AvatarDTO avatarDTO) {
        this.avatarDTO = avatarDTO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuthorDTO: {").append("person diaspora id=").append(diasporaId).
                append("person guid=").append(guid).append(", person id=").append(id).
                append(", ").append(avatarDTO).append("}");
        return sb.toString();
    }

}
