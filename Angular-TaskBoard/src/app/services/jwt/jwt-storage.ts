import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class JwtStorage {
  private token = '';

  setToken(jwt : string){
    this.token = jwt;
  }

  getToken(){
    return this.token;
  }

  removeToken(){
    this.token = '';
  }
}
