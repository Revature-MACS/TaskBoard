import { Component, signal, WritableSignal } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { JwtStorage } from './services/jwt/jwt-storage';
import { TokenTransport } from './interfaces/token-transport';
import { UserData } from './interfaces/user-data';
import { UserService } from './services/user-service';
import { Logout } from './components/logout/logout';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, Logout],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Angular-TaskBoard');

  protected token : WritableSignal<TokenTransport | null> = signal(null);
  protected currentUser : WritableSignal<UserData | null> = signal(null);

  private tokenSub : Subscription;

  constructor(private jwtToken : JwtStorage, private userService: UserService){
    this.tokenSub = jwtToken.getTokenSubject().subscribe(currToken => {
      this.token.set(currToken);
      // Load user info when token changes
      if(currToken?.token){
        this.userService.getCurrentUser().subscribe({
          next: (user) => this.currentUser.set(user),
          error: () => this.currentUser.set(null)
        });
      } else {
        this.currentUser.set(null);
      }
    });
  }
}
