package com.example.TaskBoard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Data
@Table(name = "comments")
public class Comments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    private Long timeCreatedAtEpoch;

    @JsonIgnore
    @Column(name = "updated_at")
    private Long timeUpdatedAtEpoch;

    @PrePersist
    protected void onCreate() {
        timeCreatedAtEpoch = System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        timeUpdatedAtEpoch = System.currentTimeMillis();
    }

    @ManyToOne
    @JoinColumn(name = "creator_name", nullable = true, referencedColumnName = "email")
    private User user;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = true, referencedColumnName = "issue_id")
    private Issue issue;

    public Comments(Long commentId, String comment, Long timeCreatedAtEpoch, Long timeUpdatedAtEpoch, User user, Issue issue) {
        this.commentId = commentId;
        this.comment = comment;
        this.timeCreatedAtEpoch = timeCreatedAtEpoch;
        this.timeUpdatedAtEpoch = timeUpdatedAtEpoch;
        this.user = user;
        this.issue = issue;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", date=" + timeUpdatedAtEpoch +
                ", time=" + timeUpdatedAtEpoch +
                ", user=" + user +
                ", issue=" + issue +
                '}';
    }
}
