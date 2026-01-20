import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ProjectData } from "../interfaces/project-data";
import { UserData } from "../interfaces/user-data";

@Injectable({
    providedIn: 'root'
})
export class ProjectService {
    constructor(private httpClient: HttpClient) { }

    getProjects() {
        return this.httpClient.get<ProjectData[]>("http://localhost:8080/projects");
    }

    getProjectById(projectId: string) {
        return this.httpClient.get<ProjectData>(`http://localhost:8080/projects/${projectId}`);
    }

    getProjectsByOwnerEmail(ownerEmail: string) {
        return this.httpClient.get<ProjectData[]>(`http://localhost:8080/projects/owner/${ownerEmail}`);
    }

    postProject(projectName: string, projectDescription: string, projectOwnerEmail: string) {
        const body = {
            "name": projectName,
            "description": projectDescription,
            "owner": {
                "email": projectOwnerEmail
            }
        }
        return this.httpClient.post<ProjectData>(`http://localhost:8080/projects`, body);
    }

    updateProjectById(projectId: string, projectName: string, projectDescription: string, projectOwnerEmail: string) {
        const body = {
            "name": projectName,
            "description": projectDescription,
            "owner": {
                "email": projectOwnerEmail
            }
        }
        return this.httpClient.put<ProjectData>(`http://localhost:8080/projects/${projectId}`, body);
    }

    deleteProjectById(projectId: string) {
        return this.httpClient.delete<ProjectData>(`http://localhost:8080/projects/${projectId}`);
    }

    assignUserToProject(userId: string, projectId: string) {
        const body = {
            "user": {
                "userID": userId
            },
            "project": {
                "projectId": projectId
            }
        }
        return this.httpClient.post(`http://localhost:8080/users/assign`, body);
    }

    unassignUserFromProject(userId: string, projectId: string) {
        const body = {
            "user": {
                "userID": userId
            },
            "project": {
                "projectId": projectId
            }
        }
        return this.httpClient.delete(`http://localhost:8080/users/unassign`, { body });
    }

    getAssignedUsers(projectId: string) {
        return this.httpClient.get<UserData[]>(`http://localhost:8080/users/project/${projectId}`);
    }
}
