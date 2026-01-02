import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginInfo } from '../../interfaces/login-info';
import { JwtStorage } from '../jwt/jwt-storage';
import { TokenTransport } from '../../interfaces/token-transport';
import { Router } from '@angular/router';



@Injectable({
  providedIn: 'root',
})
export class LoginService {
  API_URL : string = "http://localhost:8080/users";

  constructor(private httpClient : HttpClient, private jwtStorage: JwtStorage, private router: Router){

  }

  attemptLogin(email : string, password : string){
    const body = {
      "email": email,
      "password": password,
    };

    this.httpClient.post<TokenTransport>(
      "http://localhost:8080/users/login",
      {
        "email": email,
        "password": password
      },
      {
        observe:"response"
      }
    ).subscribe({
        next: response =>{
          console.log(response.status);
          if(response.body){
            console.log(response.body.token)
            this.jwtStorage.setToken(response.body.token);
            this.router.navigate(['/issue'])
          }
        },
        error: err =>{
          console.log(err);
        }
      });
  }
}
