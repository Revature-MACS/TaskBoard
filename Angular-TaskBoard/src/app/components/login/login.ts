import { Component, signal, WritableSignal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../services/login/login-service';
import { JwtStorage } from '../../services/jwt/jwt-storage';
import { Router } from '@angular/router';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  constructor(private loginService : LoginService, private jwtStorage: JwtStorage, private router: Router){}

  emailErrorMessage : WritableSignal<String> = signal("");
  passwordErrorMessage : WritableSignal<String> = signal("");
  errorMessage : WritableSignal<String> = signal("");

  email : string = "";
  password : string = "";

  attemptLogin(){
    this.emailErrorMessage.set(this.email == "" ? "Email cannot be empty" : "");
    this.passwordErrorMessage.set(this.password == "" ? "Password cannot be empty" : "");

    if(! (this.emailErrorMessage() || this.passwordErrorMessage())){
      this.loginService.attemptLogin(this.email, this.password).subscribe({
        next: (response) => {
          if (response.body) {
            this.jwtStorage.setToken(response.body.token);
            this.router.navigate(['/dashboard']);
          }
        },
        error: (err) => {
          console.log(err);

          if(err instanceof HttpErrorResponse){
              if(err.status == HttpStatusCode.Unauthorized.valueOf()){
                this.errorMessage.set("Invalid email or password");
              }

              else{
                this.errorMessage.set(`Server returned HTTP status ${err.status}`);
              }
          }

          else{
            this.errorMessage.set("An unknown error has occured")
          }
        },
      });
    }

  }

}
