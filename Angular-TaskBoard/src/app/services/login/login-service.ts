import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtStorage } from '../jwt/jwt-storage';
import { TokenTransport } from '../../interfaces/token-transport';
import { Router } from '@angular/router';



@Injectable({
  providedIn: 'root',
})
export class LoginService {
  API_URL : string = "http://localhost:8080/users";

  constructor(private httpClient : HttpClient){

  }

  attemptLogin(email : string, password : string){
    const body = {
      "email": email.toLowerCase(),
      "password": password,
    };

    return this.httpClient.post<TokenTransport>(this.API_URL + "/login", body, {observe:"response"});
  }
}
