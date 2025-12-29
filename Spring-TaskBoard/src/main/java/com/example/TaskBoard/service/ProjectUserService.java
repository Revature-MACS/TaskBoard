package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.Project;
import com.example.TaskBoard.entity.ProjectUser;
import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.repository.ProjectRepository;
import com.example.TaskBoard.repository.ProjectUserRepository;
import com.example.TaskBoard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<Project> getAllUserProjects(UUID userID){
        Optional<List<ProjectUser>> projectList = proUserRepo.findProjectUserByUserID(userID);
        ArrayList<Project> userProjects = new ArrayList<>();

        projectList.ifPresent(projectUsers -> projectUsers.forEach(
                projectUser -> {
                    proRepo.findById(projectUser.getProjectID()).ifPresent(userProjects::add);
                }));

        return userProjects;
    }

    public List<User> getAllProjectAssignedUsers(UUID projectID){
        Optional<List<ProjectUser>> userList = proUserRepo.findProjectUserByProjectID(projectID);
        ArrayList<User> projectAssignees = new ArrayList<>();

        userList.ifPresent(projectUsers -> { projectUsers.forEach(
                projectUser -> {
                    userRepo.findById(projectUser.getUserID()).ifPresent(projectAssignees::add);
                }
        );});

        return projectAssignees;
    }

    public ProjectUser assignUserToProject(User user, Project project) throws SQLException {

        ProjectUser newAssignedPair = new ProjectUser(user.getUserID(), project.getProjectId());

        if(userRepo.findById(user.getUserID()).isEmpty()){
            throw new SQLException("User with userID " + user.getUserID() + " does not exist!");
        }

        if(proRepo.findById((project.getProjectId())).isEmpty()){
            throw new SQLException(("Project with projectID " + project.getProjectId() + " does not exist!"));
        }

        if(proUserRepo.findById(newAssignedPair).isPresent()){
            throw new SQLException("User is already assigned to the project!");
        }

        return proUserRepo.save(newAssignedPair);
    }
}
