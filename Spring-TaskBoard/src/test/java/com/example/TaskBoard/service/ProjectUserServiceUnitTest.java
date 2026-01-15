package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.ProjectUser;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.ProjectUserRepository;
import com.example.TaskBoard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectUserServiceUnitTest {

    @Mock
    private ProjectUserRepository projectUserRepo;

    @Mock
    private ProjectRepository projectRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private ProjectUserService projectUserService;

    @Test
    void getAllUserProjectAssignmentsTest() {
        List<ProjectUser> mockList = new ArrayList<>();
        when(projectUserRepo.findAll()).thenReturn(mockList);

        List<ProjectUser> result = projectUserService.getAllUserProjectAssignments();
        assertNotNull(result);
        assertEquals(mockList, result);
    }

    @Test
    void getAllUserProjects_PositiveTest() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        user.setRole(User.UserRole.DEVELOPER);

        Project project = new Project();
        project.setProjectId(UUID.randomUUID());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        List<ProjectUser> projectUserList = List.of(projectUser);

        when(projectUserRepo.findProjectUserByUser(user)).thenReturn(projectUserList);
        when(projectRepo.findById(project.getProjectId())).thenReturn(Optional.of(project));

        List<Project> result = projectUserService.getAllUserProjects(user);

        assertEquals(1, result.size());
        assertEquals(project, result.get(0));
    }

    @Test
    void getAllProjectAssignedUsers_PositiveTest() {
        Project project = new Project();
        project.setProjectId(UUID.randomUUID());

        User user = new User();
        user.setUserID(UUID.randomUUID());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setProject(project);
        projectUser.setUser(user);

        List<ProjectUser> projectUserList = List.of(projectUser);

        when(projectUserRepo.findProjectUserByProject(project)).thenReturn(projectUserList);
        when(userRepo.findById(user.getUserID())).thenReturn(Optional.of(user));

        List<User> result = projectUserService.getAllProjectAssignedUsers(project);

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    void assignUserToProject_PositiveTest() throws SQLException {
        User user = new User();
        user.setUserID(UUID.randomUUID());

        Project project = new Project();
        project.setProjectId(UUID.randomUUID());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        when(userRepo.findById(user.getUserID())).thenReturn(Optional.of(user));
        when(projectRepo.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(projectUserRepo.findProjectUserByUserAndProject(user, project)).thenReturn(Optional.empty());
        when(projectUserRepo.save(projectUser)).thenReturn(projectUser);

        ProjectUser result = projectUserService.assignUserToProject(projectUser);

        assertNotNull(result);
        assertEquals(projectUser, result);
    }

    @Test
    void assignUserToProject_UserNotFound() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        Project project = new Project();
        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        when(userRepo.findById(user.getUserID())).thenReturn(Optional.empty());

        assertThrows(SQLException.class, () -> projectUserService.assignUserToProject(projectUser));
    }

    @Test
    void assignUserToProject_ProjectNotFound() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        Project project = new Project();
        project.setProjectId(UUID.randomUUID());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        when(userRepo.findById(user.getUserID())).thenReturn(Optional.of(user));
        when(projectRepo.findById(project.getProjectId())).thenReturn(Optional.empty());

        assertThrows(SQLException.class, () -> projectUserService.assignUserToProject(projectUser));
    }

    @Test
    void assignUserToProject_AlreadyAssigned() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        Project project = new Project();
        project.setProjectId(UUID.randomUUID());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        when(userRepo.findById(user.getUserID())).thenReturn(Optional.of(user));
        when(projectRepo.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(projectUserRepo.findProjectUserByUserAndProject(user, project)).thenReturn(Optional.of(projectUser));

        assertThrows(SQLException.class, () -> projectUserService.assignUserToProject(projectUser));
    }

    @Test
    void unassignUserFromProject_PositiveTest() throws SQLException {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        Project project = new Project();
        project.setProjectId(UUID.randomUUID());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        when(userRepo.findById(user.getUserID())).thenReturn(Optional.of(user));
        when(projectRepo.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(projectUserRepo.findProjectUserByUserAndProject(user, project)).thenReturn(Optional.of(projectUser));

        projectUserService.unassignUserFromProject(projectUser);

        verify(projectUserRepo).deleteByUserAndProject(user, project);
    }

    @Test
    void unassignUserFromProject_UserNotFound() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        Project project = new Project();
        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        when(userRepo.findById(user.getUserID())).thenReturn(Optional.empty());

        assertThrows(SQLException.class, () -> projectUserService.unassignUserFromProject(projectUser));
    }

    @Test
    void unassignUserFromProject_ProjectNotFound() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        Project project = new Project();
        project.setProjectId(UUID.randomUUID());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        when(userRepo.findById(user.getUserID())).thenReturn(Optional.of(user));
        when(projectRepo.findById(project.getProjectId())).thenReturn(Optional.empty());

        assertThrows(SQLException.class, () -> projectUserService.unassignUserFromProject(projectUser));
    }

    @Test
    void unassignUserFromProject_NotAssigned() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        Project project = new Project();
        project.setProjectId(UUID.randomUUID());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);

        when(userRepo.findById(user.getUserID())).thenReturn(Optional.of(user));
        when(projectRepo.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(projectUserRepo.findProjectUserByUserAndProject(user, project)).thenReturn(Optional.empty());

        assertThrows(SQLException.class, () -> projectUserService.unassignUserFromProject(projectUser));
    }
}
