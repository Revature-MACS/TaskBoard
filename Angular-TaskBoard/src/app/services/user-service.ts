import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { UserData } from '../interfaces/user-data';
import { JwtStorage } from './jwt/jwt-storage';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private API_URL = 'http://localhost:8080/users';
  private currentUser: BehaviorSubject<UserData | null> = new BehaviorSubject<UserData | null>(null);

  constructor(private httpClient: HttpClient, private jwtStorage: JwtStorage) {}

  getCurrentUser(): Observable<UserData> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', `Bearer ${this.jwtStorage.getToken()}`);
    return this.httpClient.get<UserData>(`${this.API_URL}/me`, { headers });
  }

  loadCurrentUser(): void {
    if (!this.jwtStorage.getToken()) {
      this.currentUser.next(null);
      return;
    }
    
    this.getCurrentUser().subscribe({
      next: (user) => {
        this.currentUser.next(user);
      },
      error: (err) => {
        console.error('Failed to load current user', err);
        this.currentUser.next(null);
      }
    });
  }

  getCurrentUserSubject(): BehaviorSubject<UserData | null> {
    return this.currentUser;
  }

  isAdmin(): boolean {
    return this.currentUser.value?.role === 'ADMIN';
  }

  clearCurrentUser(): void {
    this.currentUser.next(null);
  }
}
