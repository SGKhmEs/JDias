package com.sgkhmjaes.jdias.service.dto.statusMessageDTOs;

import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 * Created by inna on 10.06.17.
 */
public class StatusMessageDTO implements AutoMapping {
    private String aspect_ids;
    private String location_coords;
    private String[] poll_answers;
    private String poll_question;
    private StatusMessage statusMessage;

    public StatusMessageDTO(){}

    public StatusMessageDTO(String aspect_ids, String location_coords, String[] poll_answers, String poll_question, StatusMessage statusMessage) {
        this.aspect_ids = aspect_ids;
        this.location_coords = location_coords;
        this.poll_answers = poll_answers;
        this.poll_question = poll_question;
        this.statusMessage = statusMessage;
    }

    public String getAspect_ids() {
        return aspect_ids;
    }

    public void setAspect_ids(String aspect_ids) {
        this.aspect_ids = aspect_ids;
    }

    public String getLocation_coords() {
        return location_coords;
    }

    public void setLocation_coords(String location_coords) {
        this.location_coords = location_coords;
    }

    public String[] getPoll_answers() {
        return poll_answers;
    }

    public void setPoll_answers(String[] poll_answers) {
        this.poll_answers = poll_answers;
    }

    public String getPoll_question() {
        return poll_question;
    }

    public void setPoll_question(String poll_question) {
        this.poll_question = poll_question;
    }

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }


}
