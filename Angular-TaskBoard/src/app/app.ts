import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Issue } from "./components/issue/issue";
import { Login } from "./components/login/login";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Issue, Login],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Angular-TaskBoard');
}
