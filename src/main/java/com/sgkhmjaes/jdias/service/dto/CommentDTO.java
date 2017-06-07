package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class CommentDTO implements AutoMapping{
    @JsonProperty("guid")
    private String commentGuid;
    @JsonProperty("id")
    private Long commentId;
    private AuthorDTO author;
    @JsonProperty("text")
    private String commentText;
    @JsonProperty("createdAt")
    private LocalDate commentCreatedAt;

    public CommentDTO() {}

    public String getCommentGuid() {
        return commentGuid;
    }

    public void setCommentGuid(String commentGuid) {
        this.commentGuid = commentGuid;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDate getCommentCreatedAt() {
        return commentCreatedAt;
    }

    public void setCommentCreatedAt(LocalDate commentCreatedAt) {
        this.commentCreatedAt = commentCreatedAt;
    }



    
    @Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append("Comment guid: ").append(commentGuid).append("\r\n").
append("Comment id: ").append(commentId).append("\r\n").
append("Comment author: ").append(author).append("\r\n").
append("Comment text: ").append(commentText).append("\r\n").
append("Comment created at: ").append(commentCreatedAt).append("\r\n");
return sb.toString();
}
    
}
