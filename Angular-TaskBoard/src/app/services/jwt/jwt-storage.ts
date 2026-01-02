import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class JwtStorage {
  private TOKEN_KEY = 'TASK_MANAGER_TOKEN';

  setToken(jwt: string){
    localStorage.setItem(this.TOKEN_KEY, jwt);
  }

  getToken(){
    return localStorage.getItem(this.TOKEN_KEY);
  }

  clearToken(){
    localStorage.removeItem(this.TOKEN_KEY);
  }
}
