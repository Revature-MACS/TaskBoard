package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.AuditLog;
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
    private final AuditLogService auditLogService;

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
        if (project.getOwner() == null || project.getOwner().getEmail() == null) {
            throw new RuntimeException("Project owner and email are required");
        }

        User owner = userRepository.findUserByEmail(project.getOwner().getEmail())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // Validate that owner is ADMIN
        if (owner.getRole() != User.UserRole.ADMIN) {
            throw new RuntimeException("Only ADMIN users can create projects");
        }

        project.setOwner(owner);
        Project savedProject = projectRepository.save(project);

        // Audit log
        auditLogService.logProjectAction(
                savedProject.getProjectId().toString(),
                AuditLog.ActionType.CREATE,
                owner.getEmail(),
                "Created project: " + savedProject.getName());

        return savedProject;
    }

    // PUT /projects/{id} - Update project (Admin only)
    public Project updateProject(Project project) {
        if (project.getProjectId() == null) {
            throw new RuntimeException("Project ID is required for update");
        }

        if (!projectRepository.existsById(project.getProjectId())) {
            throw new RuntimeException("Project not found");
        }

        if (project.getOwner() == null || project.getOwner().getEmail() == null) {
            throw new RuntimeException("Project owner and email are required");
        }

        User owner = userRepository.findUserByEmail(project.getOwner().getEmail())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // Validate that owner is ADMIN
        if (owner.getRole() != User.UserRole.ADMIN) {
            throw new RuntimeException("Only ADMIN users can update projects");
        }

        project.setOwner(owner);
        Project savedProject = projectRepository.save(project);

        // Audit log
        auditLogService.logProjectAction(
                savedProject.getProjectId().toString(),
                AuditLog.ActionType.UPDATE,
                owner.getEmail(),
                "Updated project: " + savedProject.getName());

        return savedProject;
    }

    // DELETE /projects/{id} - Delete project (Admin only)
    public void deleteProject(UUID projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new RuntimeException("Project not found");
        }
        String projectName = project.map(Project::getName).orElse("Unknown");
        String ownerEmail = project.map(p -> p.getOwner().getEmail()).orElse("Unknown");

        projectRepository.deleteById(projectId);

        // Audit log
        auditLogService.logProjectAction(
                projectId.toString(),
                AuditLog.ActionType.DELETE,
                ownerEmail,
                "Deleted project: " + projectName);
    }

    public List<Project> getProjectsByOwnerEmail(String ownerEmail) {
        return projectRepository.findByOwner_Email(ownerEmail);
    }
}
