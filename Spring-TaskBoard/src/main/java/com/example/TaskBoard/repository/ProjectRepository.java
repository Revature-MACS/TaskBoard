package com.example.TaskBoard.repository;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    // Find all projects owned by a specific user (Admin)
    List<Project> findByOwner_Email(String ownerEmail);
}