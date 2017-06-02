package com.sgkhmjaes.jdias.service.dto;

import java.time.LocalDate;

/**
 * Created by inna on 02.06.17.
 */
public class CommentDTO {
    private String commentGuid;
    private Long commentId;
    private AuthorDTO commentAuthor;
    private String commentText;
    private LocalDate commentCreatedAt;

    public CommentDTO() {    }

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

    public AuthorDTO getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(AuthorDTO commentAuthor) {
        this.commentAuthor = commentAuthor;
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

   
}
