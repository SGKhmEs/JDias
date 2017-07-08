
package com.sgkhmjaes.jdias.service.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "poll_id",
    "post_id",
    "question",
    "poll_answers",
    "participation_count"
})
public class PollDTO implements AutoMapping {

    @JsonProperty("poll_id")
    private Long id;
    @JsonProperty("post_id")
    private Long postId;
    @JsonProperty("question")
    private String question;
    @JsonProperty("poll_answers")
    private Set<PollAnswerDTO> pollAnswerDTOS;
    @JsonProperty("participation_count")
    private Integer participationCount;

    @JsonProperty("poll_id")
    public Long getId() {
        return id;
    }

    @JsonProperty("poll_id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("post_id")
    public Long getPostId() {
        return postId;
    }

    @JsonProperty("post_id")
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @JsonProperty("question")
    public String getQuestion() {
        return question;
    }

    @JsonProperty("question")
    public void setQuestion(String question) {
        this.question = question;
    }

    @JsonProperty("poll_answers")
    public Set<PollAnswerDTO> getPollAnswerDTOS() {
        return pollAnswerDTOS;
    }

    @JsonProperty("poll_answers")
    public void setPollAnswerDTOS(Set<PollAnswerDTO> pollAnswers) {
        this.pollAnswerDTOS = pollAnswers;
    }

    @JsonProperty("participation_count")
    public Integer getParticipationCount() {
        return participationCount;
    }

    @JsonProperty("participation_count")
    public void setParticipationCount(Integer participationCount) {
        this.participationCount = participationCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(postId).append(question).append(pollAnswerDTOS).append(participationCount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PollDTO) == false) {
            return false;
        }
        PollDTO rhs = ((PollDTO) other);
        return new EqualsBuilder().append(id, rhs.id).append(postId, rhs.postId).append(question, rhs.question).append(pollAnswerDTOS, rhs.pollAnswerDTOS).append(participationCount, rhs.participationCount).isEquals();
    }

}
