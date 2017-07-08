
package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "answer",
    "poll_id",
    "guid",
    "vote_count"
})
public class  PollAnswerDTO implements AutoMapping {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("answer")
    private String answer;
    @JsonProperty("poll_id")
    private Long pollId;
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("answer")
    public String getAnswer() {
        return answer;
    }

    @JsonProperty("answer")
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @JsonProperty("poll_id")
    public Long getPollId() {
        return pollId;
    }

    @JsonProperty("poll_id")
    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonProperty("vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    @JsonProperty("vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(answer).append(pollId).append(guid).append(voteCount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PollAnswerDTO) == false) {
            return false;
        }
        PollAnswerDTO rhs = ((PollAnswerDTO) other);
        return new EqualsBuilder().append(id, rhs.id).append(answer, rhs.answer).append(pollId, rhs.pollId).append(guid, rhs.guid).append(voteCount, rhs.voteCount).isEquals();
    }

}
