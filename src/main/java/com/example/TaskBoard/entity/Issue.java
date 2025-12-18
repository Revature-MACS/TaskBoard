package com.example.TaskBoard.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @Column(name = "issue_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID issueID;

    @Column(nullable = false)
    private String  title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int status = 0;

    @Column(nullable = false)
    private int priority;

    //Subject to change
    @Column(nullable = false)
    private Long timeCreatedAtEpoch;

    //Subject to change
    @Column
    private Long timeUpdatedAtEpoch;
}
