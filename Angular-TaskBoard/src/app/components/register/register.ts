import { Component, Signal, signal, WritableSignal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RegisterService } from '../../services/register/register-service';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { JwtStorage } from '../../services/jwt/jwt-storage';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  ROLE_TYPES: string[] = ['Tester', 'Developer', 'Admin'];

  constructor(private registerService: RegisterService, private jwtStorage: JwtStorage, private router: Router) {}

  nameErrorMessage: WritableSignal<String> = signal('');
  emailErrorMessage: WritableSignal<String> = signal('');
  passwordErrorMessage: WritableSignal<String> = signal('');
  userRoleErrorMessage: WritableSignal<String> = signal('');
  unknownErrorMessage: WritableSignal<String> = signal('');

  name: string = '';
  email: string = '';
  password: string = '';
  userRole: string = '';

  attemptRegistration() {
    this.nameErrorMessage.set(this.name == '' ? 'Name cannot be empty' : '');
    this.emailErrorMessage.set(this.email == '' ? 'Email cannot be empty' : '');
    this.passwordErrorMessage.set(this.password == '' ? 'Password cannot be empty' : '');
    this.userRoleErrorMessage.set(this.userRole == '' ? 'Please select a role' : '');

    if (!(this.nameErrorMessage() || this.emailErrorMessage() ||
        this.passwordErrorMessage() ||this.userRoleErrorMessage()))
    {
      this.registerService.attemptRegistration(this.name, this.email, this.password, this.userRole)
        .subscribe({
          next: (responseData) => {
            this.jwtStorage.setToken(responseData.token);
            this.router.navigate(['/dashboard']);
          },
          error: (err) => {
            console.error(err);
            if(err instanceof HttpErrorResponse){
              if(err.status == HttpStatusCode.Conflict.valueOf()){
                this.emailErrorMessage.set("Email already has associated account");
              }

              else{
                this.emailErrorMessage.set(`Server returned HTTP status ${err.status}`);
              }
          }

            else {
              this.unknownErrorMessage.set("An unknown error has occured");
            }
          },
        });
    }
  }
}
