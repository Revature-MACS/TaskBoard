package com.example.TaskBoard.repository;

import com.example.TaskBoard.entity.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, ProjectUser> {

    Optional<List<ProjectUser>> findProjectUserByUserID(UUID userID);
    Optional<List<ProjectUser>> findProjectUserByProjectID(UUID projectID);
}
