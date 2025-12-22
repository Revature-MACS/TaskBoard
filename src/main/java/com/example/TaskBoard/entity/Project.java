package com.example.TaskBoard.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Column(name = "project_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID projectId;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "created_at", nullable = false, updatable = false)
    private long timeCreatedAtEpoch;

    @Column(name = "updated_at")
    private long timeUpdatedAtEpoch;

    @PrePersist
    protected void onCreate() {
        timeCreatedAtEpoch = System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        timeUpdatedAtEpoch = System.currentTimeMillis();
    }
}