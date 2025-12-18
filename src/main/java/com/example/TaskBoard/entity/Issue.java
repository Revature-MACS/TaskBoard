package com.example.TaskBoard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "issue")
public class Issue {

    @Id
    @Column(nullable = false)
    private UUID issueID;

    @Column(nullable = false)
    private String  title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private int priority;

    //Subject to change
    @Column(nullable = false)
    private Long timeCreatedAtEpoch;

    //Subject to change
    @Column
    private Long timeUpdatedAtEpoch;
}
