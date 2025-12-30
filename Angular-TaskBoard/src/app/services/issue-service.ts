import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IssueData } from '../interfaces/issue-data';

@Injectable({
  providedIn: 'root',
})
export class IssueService {

  constructor(private httpClient: HttpClient){

  }

  getIssues(){
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

  postIssue(issueTitle: string, issueDescription: string, issueStatus: number, issuePriority: number, issueSeverity: number){
    const body = {
      "title":issueTitle,
      "description":issueDescription,
      "status": issueStatus,
      "priority": issuePriority,
      "severity": issueSeverity
  }
    this.httpClient.post<IssueData>(`http://localhost:8080/issues`, body)
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

  updateIssueById(issueId: string, issueTitle: string, issueDescription: string, issueStatus: number, issuePriority: number, issueSeverity: number){
    const body = {
      "title":issueTitle,
      "description":issueDescription,
      "status": issueStatus,
      "priority": issuePriority,
      "severity": issueSeverity
  }
    this.httpClient.post(`http://localhost:8080/issues/${issueId}`, body)
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
