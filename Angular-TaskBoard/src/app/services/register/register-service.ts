import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtStorage } from '../jwt/jwt-storage';
import { TokenTransport } from '../../interfaces/token-transport';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  API_URL : string = "http://localhost:8080/users";

  constructor(private httpClient : HttpClient){}

  attemptRegistration(name : string, email : string,
    password : string, role : string){
      const body = {
        "name": name,
        "email": email.toLowerCase(),
        "password": password,
        "role": role.toUpperCase()
      }

      return this.httpClient.post<TokenTransport>(this.API_URL + '/register', body);
    }

}
