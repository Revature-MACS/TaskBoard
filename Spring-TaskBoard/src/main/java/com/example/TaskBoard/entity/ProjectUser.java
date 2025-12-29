package com.example.TaskBoard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projectusers")
public class ProjectUser {


    @ManyToMany
    @JoinColumn(name = "FK_user_id", referencedColumnName = "user_id")
    private UUID userID;

    @ManyToMany
    @JoinColumn(name = "FK_project_id", referencedColumnName = "project_id")
    private UUID projectID;
}
