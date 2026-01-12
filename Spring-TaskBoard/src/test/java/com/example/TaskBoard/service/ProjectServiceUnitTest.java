package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.UserRepository;
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
}
