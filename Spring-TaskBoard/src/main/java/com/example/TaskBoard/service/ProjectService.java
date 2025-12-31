package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // GET /projects - List all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // GET /projects/{id} - Get project details
    public Optional<Project> getProjectById(UUID projectId) {
        return projectRepository.findById(projectId);
    }

    // POST /projects - Create project (Admin only)
    public Project createProject(Project project) {
        User owner = userRepository.findUserByEmail(project.getOwner().getEmail())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        project.setOwner(owner);
        return projectRepository.save(project);
    }

    // PUT /projects/{id} - Update project (Admin only)
    public Project updateProject(Project project) {
        User owner = userRepository.findUserByEmail(project.getOwner().getEmail())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        project.setOwner(owner);
        return projectRepository.save(project);
    }

    // DELETE /projects/{id} - Delete project (Admin only)
    public void deleteProject(UUID projectId) {
        projectRepository.deleteById(projectId);
    }

    public List<Project> getProjectsByOwnerEmail(String ownerEmail) {
        return projectRepository.findByOwner_Email(ownerEmail);
    }
}