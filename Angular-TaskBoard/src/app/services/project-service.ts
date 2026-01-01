import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ProjectData } from "../interfaces/project-data";

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
}