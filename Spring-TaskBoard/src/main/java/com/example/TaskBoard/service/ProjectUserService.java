package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.ProjectUser;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.ProjectUserRepository;
import com.example.TaskBoard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectUserService {
    private final ProjectUserRepository proUserRepo;
    private final ProjectRepository proRepo;
    private final UserRepository userRepo;

    public List<ProjectUser> getAllUserProjectAssignments(){
        return proUserRepo.findAll();
    }

    public List<Project> getAllUserProjects(User user){
        List<ProjectUser> projectList = proUserRepo.findProjectUserByUser(user);
        ArrayList<Project> userProjects = new ArrayList<>();

        projectList.forEach(projectUser -> {
            proRepo.findById(projectUser.getProject().getProjectId()).ifPresent(userProjects::add);
        });

        return userProjects;
    }

    public List<User> getAllProjectAssignedUsers(Project project){
        List<ProjectUser> userList = proUserRepo.findProjectUserByProject(project);
        ArrayList<User> projectAssignees = new ArrayList<>();

        userList.forEach(projectUser -> {
            userRepo.findById(projectUser.getUser().getUserID()).ifPresent(projectAssignees::add);
        });

        return projectAssignees;
    }

    public ProjectUser assignUserToProject(ProjectUser newAssignedPair) throws SQLException {

        User user = newAssignedPair.getUser();
        Project project = newAssignedPair.getProject();

        if(userRepo.findById(user.getUserID()).isEmpty()){
            throw new SQLException("User with userID " + user.getUserID() + " does not exist!");
        }

        if(proRepo.findById((project.getProjectId())).isEmpty()){
            throw new SQLException(("Project with projectID " + project.getProjectId() + " does not exist!"));
        }
        if(proUserRepo.findProjectUserByUserAndProject
                (newAssignedPair.getUser(), newAssignedPair.getProject()).isPresent()){
            throw new SQLException("User is already assigned to the project!");
        }

        return proUserRepo.save(newAssignedPair);
    }

    @Transactional
    public void unassignUserFromProject(ProjectUser assignedPair) throws SQLException {

        User user = assignedPair.getUser();
        Project project = assignedPair.getProject();

        if(userRepo.findById(user.getUserID()).isEmpty()){
            throw new SQLException("User with userID " + user.getUserID() + " does not exist!");
        }

        if(proRepo.findById((project.getProjectId())).isEmpty()){
            throw new SQLException(("Project with projectID " + project.getProjectId() + " does not exist!"));
        }

        if(proUserRepo.findProjectUserByUserAndProject(user, project).isEmpty()){
            throw new SQLException("User is not assigned to this project!");
        }

        proUserRepo.deleteByUserAndProject(user, project);
    }
}
