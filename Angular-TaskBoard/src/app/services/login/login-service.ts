import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginInfo } from '../../interfaces/login-info';



@Injectable({
  providedIn: 'root',
})
export class LoginService {
  API_URL : string = "http://localhost:8080/users";

  constructor(private httpClient : HttpClient){

  }

  attemptLogin(email : string, password : string){
    const body = {
      "email": email,
      "password": password,
    };

    this.httpClient.post<LoginInfo>(this.API_URL + `/login`, body)
      .subscribe({
        next: responseData =>{
          console.log(responseData);
        },
        error: err =>{
          console.log(err);
        }
      });
  }
}
