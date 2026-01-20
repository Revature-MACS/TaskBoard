import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../services/project-service';
import { ProjectData } from '../../interfaces/project-data';
import { UserData } from '../../interfaces/user-data';

@Component({
    selector: 'app-dashboard',
    imports: [CommonModule, FormsModule],
    templateUrl: './dashboard.html',
    styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {
    projects: WritableSignal<ProjectData[]> = signal([]);
    successMessage: WritableSignal<string> = signal('');
    errorMessage: WritableSignal<string> = signal('');
    assignedUsersMap: WritableSignal<{ [projectId: string]: UserData[] }> = signal({});

    constructor(private projectService: ProjectService) {}

    ngOnInit(): void {
        this.loadProjects();
    }

    loadProjects(): void {
        this.projectService.getProjects().subscribe({
            next: (data) => {
                this.projects.set(data);
                data.forEach(project => {
                    this.loadAssignedUsers(project.projectId);
                });
            },
            error: (err) => {
                this.errorMessage.set('Failed to load projects');
                console.error(err);
            }
        });
    }

    loadAssignedUsers(projectId: string): void {
        this.projectService.getAssignedUsers(projectId).subscribe({
            next: (users) => {
                this.assignedUsersMap.update(map => ({ ...map, [projectId]: users }));
            },
            error: (err) => {
                console.error('Failed to load assigned users for project ' + projectId, err);
            }
        });
    }
}
