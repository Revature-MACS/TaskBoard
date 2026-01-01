import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Issue } from "./components/issue/issue";
import { Project } from "./components/project/project";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Issue, Project],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Angular-TaskBoard');
}
