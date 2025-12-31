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


    @Id
    @Column(name = "project_user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID projectUserID;

    @ManyToOne
    @JoinColumn(name = "FK_user_id", referencedColumnName = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "FK_project_id", referencedColumnName = "project_id")
    private Project project;

    public ProjectUser(User user, Project project){
        this.user = user;
        this.project = project;
    }

}
