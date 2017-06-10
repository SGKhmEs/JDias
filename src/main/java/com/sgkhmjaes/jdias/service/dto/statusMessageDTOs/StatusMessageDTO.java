package com.sgkhmjaes.jdias.service.dto.statusMessageDTOs;

import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 * Created by inna on 10.06.17.
 */
public class StatusMessageDTO implements AutoMapping {
    private String aspect_ids;
    private String location_coords;
    private String[] poll_answers;
    private String poll_question;
    private StatusMessageBodyDTO status_message;

    public StatusMessageDTO(){}

    public StatusMessageDTO(String aspect_ids, String location_coords, String[] poll_answers, String poll_question, StatusMessageBodyDTO status_message) {
        this.aspect_ids = aspect_ids;
        this.location_coords = location_coords;
        this.poll_answers = poll_answers;
        this.poll_question = poll_question;
        this.status_message = status_message;
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

    public StatusMessageBodyDTO getStatus_message() {
        return status_message;
    }

    public void setStatus_message(StatusMessageBodyDTO status_message) {
        this.status_message = status_message;
    }
}
