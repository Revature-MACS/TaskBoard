package com.example.TaskBoard.controller;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // GET /projects - List all projects
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // GET /projects/{id} - Get project details
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /projects/owner/{ownerEmail}
    @GetMapping("/owner/{ownerEmail}")
    public List<Project> getProjectsByOwner(@PathVariable String ownerEmail) {
        return projectService.getProjectsByOwnerEmail(ownerEmail);
    }

    // POST /projects - Create project (Admin only - security added later)
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    // PUT /projects/{id} - Update project (Admin only - security added later)
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable UUID id, @RequestBody Project project) {
        project.setProjectId(id);
        Project updatedProject = projectService.updateProject(project);
        return ResponseEntity.ok(updatedProject);
    }

    // DELETE /projects/{id} - Delete project (Admin only - security added later)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}