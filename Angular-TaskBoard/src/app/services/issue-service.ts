import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IssueData } from '../interfaces/issue-data';
import { JwtStorage } from './jwt/jwt-storage';

@Injectable({
  providedIn: 'root',
})
export class IssueService {

  constructor(private httpClient: HttpClient, private jwtStorage: JwtStorage){

  }

  getIssues(){

    let headers = new HttpHeaders();

    this.httpClient.get<IssueData>("http://localhost:8080/issues")
    .subscribe({
      next: responseData => {
        console.log(responseData);
      },
      error: err => {
        console.log(err);
      }
    });
  }

  getIssueById(issueId: string){
    this.httpClient.get<IssueData>(`http://localhost:8080/issues/${issueId}`)
    .subscribe({
      next: responseData =>{
        console.log(responseData)
      },
      error: err => {
        console.log(err);
      }
    })
  }

  postIssue(issueTitle: string, issueDescription: string, issueStatus: string, issuePriority: string, issueSeverity: string){
    const body = {
      "title":issueTitle,
      "description":issueDescription,
      "status": issueStatus.replace(/ /g, '_').toUpperCase(),
      "priority": issuePriority.toUpperCase(),
      "severity": issueSeverity.toUpperCase()
    }
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', `Bearer ${this.jwtStorage.getToken()}`);
    this.httpClient.post<IssueData>(`http://localhost:8080/issues`, body, {headers: headers})
    .subscribe({
      next: responseData =>{
        console.log(responseData)
      },
      error: err => {
        console.log(err);
      }
    })
  }

  deleteIssueById(issueId: string){
    this.httpClient.delete(`http://localhost:8080/issues/${issueId}`)
    .subscribe({
      next: responseData => {
        console.log(responseData);
      },
      error: err => {
        console.log(err);
      }
    })
  }

  updateIssueById(issueId: string, issueTitle: string, issueDescription: string, issueStatus: string, issuePriority: string, issueSeverity: string){
    const body = {
      "title":issueTitle,
      "description":issueDescription,
      "status": issueStatus.replace(/ /g, '_').toUpperCase(),
      "priority": issuePriority.toUpperCase(),
      "severity": issueSeverity.toUpperCase()
  }
    this.httpClient.put(`http://localhost:8080/issues/${issueId}`, body)
    .subscribe({
      next: responseData => {
        console.log(responseData);
      },
      error: err => {
        console.log(err);
      }
    })
  }

  
}
