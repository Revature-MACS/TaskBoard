import { Routes } from '@angular/router';
import { Project } from './components/project/project';
import { Issue } from './components/issue/issue';
import { Dashboard } from './components/dashboard/dashboard';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Logs } from './components/logs/logs';
import { authGuard } from './guards/auth-guard';
import { noAuthGuard } from './guards/no-auth-guard';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/login' },
  { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
  { path: 'login', component: Login, canActivate: [noAuthGuard] },
  { path: 'register', component: Register, canActivate: [noAuthGuard] },
  { path: 'issue', component: Issue, canActivate: [authGuard] },
  { path: 'project', component: Project, canActivate: [authGuard] },
  { path: 'logs', component: Logs, canActivate: [authGuard] },
];
