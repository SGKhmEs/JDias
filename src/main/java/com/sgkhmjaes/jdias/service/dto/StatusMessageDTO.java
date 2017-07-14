package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

import java.util.Set;

/**
 * Created by inna on 10.06.17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusMessageDTO implements AutoMapping {

    @JsonProperty("status_message")
    private StatusMessage statusMessage;
    @JsonProperty("aspect_ids")
    private Set<Long> aspectIds;
    @JsonProperty("photos")
    private Set<Long> photos;
    @JsonProperty("location_address")
    private String locationAddress;
    @JsonProperty("location_coords")
    private String locationCoords;
    @JsonProperty("poll_question")
    private String pollQuestion;
    @JsonProperty("poll_answers")
    private Set<String> pollAnswers;

    public StatusMessageDTO(){}

    @JsonProperty("status_message")
    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    @JsonProperty("status_message")
    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    @JsonProperty("aspect_ids")
    public Set<Long> getAspectIds() {
        return aspectIds;
    }

    @JsonProperty("aspect_ids")
    public void setAspectIds(Set<Long> aspectIds) {
        this.aspectIds = aspectIds;
    }

    @JsonProperty("photos")
    public Set<Long> getPhotos() {
        return photos;
    }

    @JsonProperty("photos")
    public void setPhotos(Set<Long> photos) {
        this.photos = photos;
    }

    @JsonProperty("location_address")
    public String getLocationAddress() {
        return locationAddress;
    }

    @JsonProperty("location_address")
    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    @JsonProperty("location_coords")
    public String getLocationCoords() {
        return locationCoords;
    }

    @JsonProperty("location_coords")
    public void setLocationCoords(String locationCoords) {
        this.locationCoords = locationCoords;
    }

    @JsonProperty("poll_question")
    public String getPollQuestion() {
        return pollQuestion;
    }

    @JsonProperty("poll_question")
    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    @JsonProperty("poll_answers")
    public Set<String> getPollAnswers() {
        return pollAnswers;
    }

    @JsonProperty("poll_answers")
    public void setPollAnswers(Set<String> pollAnswers) {
        this.pollAnswers = pollAnswers;
    }
    /*
    private String[] aspect_ids;
    private String location_coords;
    private String[] poll_answers;
    private String poll_question;
    private StatusMessage status_message;
    private String[] photos;

    public StatusMessageDTO(){}

    public StatusMessageDTO(String[] aspect_ids, String location_coords, String[] poll_answers, String poll_question, StatusMessage statusMessage, String[] phpotos) {
        this.aspect_ids = aspect_ids;
        this.location_coords = location_coords;
        this.poll_answers = poll_answers;
        this.poll_question = poll_question;
        this.status_message = statusMessage;
        this.photos = phpotos;
    }

    public String[] getAspect_ids() {
        return aspect_ids;
    }

    public void setAspect_ids(String[] aspect_ids) {
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

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public StatusMessage getStatusMessage() {
        return status_message;
    }

    public void setStatusMessage(StatusMessage status_message) {
        this.status_message = status_message;
    }

*/
    
}
