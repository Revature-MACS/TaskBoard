import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { LoginService } from '../../services/login/login-service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  constructor(private loginService : LoginService){

  }

  email : string = "";
  password : string = "";

  attemptLogin(){
    this.loginService.attemptLogin(this.email, this.password);
  }

}
