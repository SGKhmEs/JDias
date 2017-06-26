package com.sgkhmjaes.jdias.service.dto;

import java.util.List;

public class InteractionDTO {

    private int likes_count;
    private int reshares_count;
    private int comments_count;
    private List<LikeDTO> likes;
    private List<String> reshares;
    private List<CommentDTO> comments;

    public InteractionDTO() {
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getReshares_count() {
        return reshares_count;
    }

    public void setReshares_count(int reshares_count) {
        this.reshares_count = reshares_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public List<LikeDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }

    public List<String> getReshares() {
        return reshares;
    }

    public void setReshares(List<String> reshares) {
        this.reshares = reshares;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Likes_count: ").append(likes_count).append("\r\n").
                append("Reshares_count: ").append(reshares_count).append("\r\n").
                append("Comments_count: ").append(comments_count).append("\r\n").
                append("Likes: ").append(likes).append("\r\n").
                append("Reshares: ").append(reshares).append("\r\n").
                append("Comments: ").append(comments).append("\r\n");
        return sb.toString();
    }

}
