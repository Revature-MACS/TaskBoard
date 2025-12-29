import { Component, signal, WritableSignal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { Issue } from "./components/issue/issue";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FormsModule, Issue],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Angular-TaskBoard');


}
