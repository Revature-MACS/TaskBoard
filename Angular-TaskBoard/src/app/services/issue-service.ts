import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IssueData } from '../interfaces/issue-data';
import { JwtStorage } from './jwt/jwt-storage';
import { BehaviorSubject } from 'rxjs';
import { ResponseData } from '../interfaces/response-data';

@Injectable({
  providedIn: 'root',
})
export class IssueService {

  private issueSubject: BehaviorSubject<IssueData>;

  private issueListSubject: BehaviorSubject<Array<IssueData>>;

  constructor(private httpClient: HttpClient, private jwtStorage: JwtStorage){
    this.issueSubject = new BehaviorSubject<IssueData>({
      issueId: "",
      projectId: "",
      title: "",
      description: "",
      status: "",
      priority: "",
      severity: "",
      timeCreatedAtEpoch: "",
      timeUpdatedAtEpoch: "",
      owner: {
        email: ""
      }
    })

    this.issueListSubject = new BehaviorSubject<Array<IssueData>>([]);
  }

  getIssues(){
    this.httpClient.get<Array<IssueData>>("http://localhost:8080/issues")
    .subscribe({
      next: responseData => {
        this.issueListSubject.next(responseData);
        console.log(responseData);
      },
      error: err => {
        console.log(err);
      }
    });
  }

  getIssueById(issueId: string){
    return this.httpClient.get<IssueData>(`http://localhost:8080/issues/${issueId}`);
  }

  postIssue(issueTitle: string, issueDescription: string, issueStatus: string, issuePriority: string, issueSeverity: string, ownerEmailText: string, projectId: string){
    const body = {
      "title":issueTitle,
      "description":issueDescription,
      "status": issueStatus.replace(/ /g, '_').toUpperCase(),
      "priority": issuePriority.toUpperCase(),
      "severity": issueSeverity.toUpperCase(),
      "projectId": projectId,
      "owner": {
        "email": ownerEmailText
      }
    }
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', `Bearer ${this.jwtStorage.getToken()}`);
    return this.httpClient.post<IssueData>(`http://localhost:8080/issues`, body, {headers: headers})
  }

  deleteIssueById(issueId: string){
    return this.httpClient.delete<IssueData>(`http://localhost:8080/issues/${issueId}`);

  }

  updateIssueById(issueId: string, issueTitle: string, issueDescription: string, issueStatus: string, issuePriority: string, issueSeverity: string, ownerEmailText: string){
    const body = {
      "title":issueTitle,
      "description":issueDescription,
      "status": issueStatus.replace(/ /g, '_').toUpperCase(),
      "priority": issuePriority.toUpperCase(),
      "severity": issueSeverity.toUpperCase(),
      "owner": {
        "email": ownerEmailText
      }
    }
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', `Bearer ${this.jwtStorage.getToken()}`);
    return this.httpClient.put<IssueData>(`http://localhost:8080/issues/${issueId}`, body, {headers: headers});
  }

  getIssueSubject(){
    return this.issueSubject;
  }

  getIssueListSubject(){
    return this.issueListSubject;
  }
  
}
