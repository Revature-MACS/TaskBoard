import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
import { Comments } from "../../components/comments/comments";
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  private apiBaseUrl: String;
  // header = new HttpHeaders({'X-API-KEY': environment.apiKey, 'X-API-SECRET': environment.apiSecret});

  constructor(private http: HttpClient, private router: Router) { 
    this.apiBaseUrl = environment.apiBaseCommentUrl;
  }

  public allComments(): Observable<Comments[]>{
    return this.http.get<Comments[]>(`${this.apiBaseUrl}/`);
  } 

  public readAllCommentsOfAnIssue(issueId: number): Observable<Comments[]>{
    return this.http.get<Comments[]>(`${this.apiBaseUrl}/projects/issues/${issueId}`);
  } 

  public addComment(comment: Comments): Observable<Comments>{
    return this.http.post<Comments>(`${this.apiBaseUrl}/projects/issue`, comment);
  }

  public editComment(comment: Comments): Observable<Comments>{
    return this.http.put<Comments>(`${this.apiBaseUrl}/projects/issue`, comment);
  }

  public removeComment(commentId: number): Observable<void>{
    return this.http.delete<void>(`${this.apiBaseUrl}/${commentId}`);
  }

  // public checkUser(value: Admin){
  //   if(value.email == environment.email && value.password == environment.password){ 
  //     localStorage.setItem('trueOrFalse', JSON.stringify(true));      
  //   }else{
  //     localStorage.setItem('trueOrFalse', JSON.stringify(false));
  //   }
  //  }

  //  public setUser(){
  //   return localStorage.getItem('trueOrFalse');
  //  }

  // public logout(){
  //   localStorage.setItem('trueOrFalse', JSON.stringify(false));
  //   localStorage.removeItem('trueOrFalse');
  // }
}
