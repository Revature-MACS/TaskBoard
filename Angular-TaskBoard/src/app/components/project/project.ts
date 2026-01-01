import { Component, signal, WritableSignal } from '@angular/core';
import { ProjectService } from '../../services/project-service';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-project',
    imports: [FormsModule],
    templateUrl: './project.html',
    styleUrl: './project.css',
})
export class Project {

    constructor(private projectService: ProjectService) {
    }

    projectId: WritableSignal<string> = signal("");
    projectDeleteId: WritableSignal<string> = signal("");

    updateProjectId: WritableSignal<string> = signal("");

    nameText: string = "";
    descriptionText: string = "";
    ownerEmailText: string = "";
    idValue: string = "";

    updateNameText: string = "";
    updateDescriptionText: string = "";
    updateOwnerEmailText: string = "";
    updateIdText: string = "";

    ownerEmailSearch: string = "";

    getProjects() {
        this.projectService.getProjects();
    }

    getProjectById() {
        this.projectService.getProjectById(this.idValue);
    }

    getProjectsByOwnerEmail() {
        this.projectService.getProjectsByOwnerEmail(this.ownerEmailSearch);
    }

    postProject() {
        this.projectService.postProject(this.nameText, this.descriptionText, this.ownerEmailText);
    }

    updateProjectById() {
        this.projectService.updateProjectById(this.updateIdText, this.updateNameText, this.updateDescriptionText, this.updateOwnerEmailText);
    }

    deleteProjectById() {
        this.projectService.deleteProjectById(this.idValue);
    }

}