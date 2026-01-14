package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.AuditLog;
import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.UserRepository;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceUnitTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuditLogService auditLogService;
    @InjectMocks
    private ProjectService projectService;

    @Test
    void getAllProjectsPositiveTest() {
        List<Project> mockProjects = new ArrayList<>();

        Project project1 = new Project();
        project1.setProjectId(UUID.randomUUID());
        project1.setName("Project 1");

        Project project2 = new Project();
        project2.setProjectId(UUID.randomUUID());
        project2.setName("Project 2");

        mockProjects.add(project1);
        mockProjects.add(project2);

        when(projectRepository.findAll()).thenReturn(mockProjects);

        List<Project> result = projectService.getAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getName());
        assertEquals("Project 2", result.get(1).getName());
    }

    @Test
    void getAllProjectsEmptyTest() {
        when(projectRepository.findAll()).thenReturn(new ArrayList<>());

        List<Project> result = projectService.getAllProjects();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getProjectByIdPositiveTest() {
        Project mockProject = new Project();
        mockProject.setProjectId(UUID.randomUUID());
        mockProject.setName("Project 1");

        when(projectRepository.findById(mockProject.getProjectId())).thenReturn(Optional.of(mockProject));

        Optional<Project> result = projectService.getProjectById(mockProject.getProjectId());

        assertNotNull(result);
        assertEquals(mockProject.getProjectId(), result.get().getProjectId());
        assertEquals(mockProject.getName(), result.get().getName());
    }

    @Test
    void getProjectByIdNegativeTest() {
        UUID projectId = UUID.randomUUID();
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Optional<Project> result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void createProjectPositiveTest() {
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setRole(User.UserRole.ADMIN);

        Project mockProject = new Project();
        mockProject.setProjectId(UUID.randomUUID());
        mockProject.setName("Project 1");
        mockProject.setOwner(admin);

        when(userRepository.findUserByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        when(projectRepository.save(mockProject)).thenReturn(mockProject);

        Project result = projectService.createProject(mockProject);

        assertNotNull(result);
        assertEquals(mockProject.getProjectId(), result.getProjectId());
        assertEquals(mockProject.getName(), result.getName());
        assertEquals(admin, result.getOwner());
    }

    @Test
    void createProjectNegativeTest_NotAdmin() {
        User developer = new User();
        developer.setEmail("dev@example.com");
        developer.setRole(User.UserRole.DEVELOPER);

        Project mockProject = new Project();
        mockProject.setProjectId(UUID.randomUUID());
        mockProject.setName("Project 1");
        mockProject.setOwner(developer);

        when(userRepository.findUserByEmail(developer.getEmail())).thenReturn(Optional.of(developer));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            projectService.createProject(mockProject);
        });

        assertEquals("Only ADMIN users can create projects", exception.getMessage());
    }

    @Test
    void updateProjectPositiveTest() {
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setRole(User.UserRole.ADMIN);

        Project mockProject = new Project();
        mockProject.setProjectId(UUID.randomUUID());
        mockProject.setName("Project 1");
        mockProject.setOwner(admin);

        when(userRepository.findUserByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        when(projectRepository.existsById(mockProject.getProjectId())).thenReturn(true);
        when(projectRepository.save(mockProject)).thenReturn(mockProject);

        Project result = projectService.updateProject(mockProject);

        assertNotNull(result);
        assertEquals(mockProject.getProjectId(), result.getProjectId());
        assertEquals("Project 1", result.getName());
    }

    @Test
    void updateProjectNegativeTest_OwnerNotFound() {
        User admin = new User();
        admin.setEmail("notfound@taskboard.com");

        Project mockProject = new Project();
        mockProject.setProjectId(UUID.randomUUID());
        mockProject.setOwner(admin);

        when(projectRepository.existsById(mockProject.getProjectId())).thenReturn(true);
        when(userRepository.findUserByEmail(admin.getEmail())).thenReturn(Optional.empty());

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            projectService.updateProject(mockProject);
        });

        assertEquals("Owner not found", exception.getMessage());
    }

    @Test
    void updateProjectNegativeTest_ProjectNotFound() {
        User admin = new User();
        admin.setEmail("notfound@taskboard.com");

        Project mockProject = new Project();
        mockProject.setProjectId(UUID.randomUUID());
        mockProject.setOwner(admin);

        when(projectRepository.existsById(mockProject.getProjectId())).thenReturn(false);

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            projectService.updateProject(mockProject);
        });

        assertEquals("Project not found", exception.getMessage());
    }

    @Test
    void deleteProjectPositiveTest() {
        User admin = new User();
        admin.setEmail("admin@taskboard.com");
        admin.setRole(User.UserRole.ADMIN);

        Project mockProject = new Project();
        UUID projectId = UUID.randomUUID();
        mockProject.setProjectId(projectId);
        mockProject.setOwner(admin);
        mockProject.setName("Mock Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockProject));

        projectService.deleteProject(projectId);

        verify(auditLogService).logProjectAction(projectId.toString(),
                AuditLog.ActionType.DELETE,
                admin.getEmail(),
                "Deleted project: " + mockProject.getName());
    }

    @Test
    void deleteProjectNegativeTest_ProjectNotFound() {
        UUID projectId = UUID.randomUUID();

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            projectService.deleteProject(projectId);
        });

        assertEquals("Project not found", exception.getMessage());
    }
}
