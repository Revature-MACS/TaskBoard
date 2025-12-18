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

    //Subject to change (0 - Closed, 1 - Open)
    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private int priority;

    //Subject to change
    @Column(nullable = false)
    private String dateTime;
}
