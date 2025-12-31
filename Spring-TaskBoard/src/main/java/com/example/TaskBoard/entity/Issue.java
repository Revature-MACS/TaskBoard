package com.example.TaskBoard.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issue {

    public enum IssueStatus {OPEN, IN_PROGRESS, RESOLVED, CLOSED};
    public enum IssuePriority {LOW, MEDIUM, HIGH};
    public enum IssueSeverity {LOW, MEDIUM, HIGH};

    @Id
    @Column(name = "issue_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID issueId;

    @Column(nullable = false)
    private String  title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated
    private IssueStatus status = IssueStatus.OPEN;

    @Column(nullable = false)
    private IssuePriority priority;

    @Column(nullable = false)
    private IssueSeverity severity;

    /*
    @Column()
    private List<Comment> comments;
     */

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date timeCreatedAtEpoch;

    @Column(name = "updated_at")
    private Date timeUpdatedAtEpoch;

    @PrePersist
    protected void onCreate() {
        timeCreatedAtEpoch = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        timeUpdatedAtEpoch = new Date();
    }
}
