package com.example.TaskBoard.repository;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.ProjectUser;
import com.example.TaskBoard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    List<ProjectUser> findProjectUserByUser(User user);
    List<ProjectUser> findProjectUserByProject(Project project);
    Optional<ProjectUser> findProjectUserByUserAndProject(User user, Project project);
    void deleteByUserAndProject(User user, Project project);
}
