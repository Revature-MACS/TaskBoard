package com.example.TaskBoard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "creation_date", nullable = false)
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "creation_time", nullable = false)
//    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "creator_name", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = true)
    private Issue issue;

    public Comments(Long commentId, String comment, LocalDate date, LocalTime time, User user, Issue issue) {
        this.commentId = commentId;
        this.comment = comment;
        this.date = date;
        this.time = time;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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
                ", date=" + date +
                ", time=" + time +
                ", user=" + user +
                ", issue=" + issue +
                '}';
    }
}
