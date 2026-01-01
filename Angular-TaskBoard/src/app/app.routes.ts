import { Routes } from '@angular/router';
import { Project } from './components/project/project';
import { Issue } from './components/issue/issue';

export const routes: Routes = [
    { path: 'issue', component: Issue },
    { path: 'project', component: Project }
];
