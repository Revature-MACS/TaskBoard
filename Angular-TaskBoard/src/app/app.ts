import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Issue } from "./components/issue/issue";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Issue],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Angular-TaskBoard');
}
