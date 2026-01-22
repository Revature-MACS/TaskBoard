import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../../services/issue-service';
import { FormsModule } from '@angular/forms';
import { IssueData } from '../../../interfaces/issue-data';
import { Comments } from '../../comments/comments';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-fetch-issue',
  imports: [FormsModule, Comments],
  templateUrl: './fetch-issue.html',
  styleUrl: './fetch-issue.css',
})
export class FetchIssue {

  idValue: string = "";
  titleText: string = "";
  descriptionText: string = "";
  statusValue: string = "";
  priorityValue: string = "";
  severityValue: string = "";
  idDeleteValue: string = "";
  timeCreatedValue: string = "";
  timeUpdatedValue: string = "";
  projectIdValue: string = "";

  issueTitle: WritableSignal<string> = signal("");
  issueDescription: WritableSignal<string> = signal("");
  issueStatus: WritableSignal<string> = signal("");
  issuePriority: WritableSignal<string> = signal("");
  issueSeverity: WritableSignal<string> = signal("");
  issueId:WritableSignal<string> = signal("");
  issueTimeCreated:WritableSignal<string> = signal("");
  issueTimeUpdated:WritableSignal<string> = signal("");
  issueProjectId:WritableSignal<string> = signal("");
  issueData:WritableSignal<IssueData | null> = signal(null)
  errorMessage : WritableSignal<String> = signal("");
  
  constructor(private issueService: IssueService){
    this.issueService.getIssueSubject().subscribe(
      issueData => {
        this.issueTitle.set(issueData.title);
        this.issueDescription.set(issueData.description);
        this.issueStatus.set(issueData.status);
        this.issuePriority.set(issueData.priority);
        this.issueSeverity.set(issueData.severity);
        this.issueTimeCreated.set(issueData.timeCreatedAtEpoch);
        this.issueTimeUpdated.set(issueData.timeUpdatedAtEpoch);
        this.issueId.set(issueData.issueId)
        this.issueProjectId.set(issueData.projectId)
      }
    );
  }

  getIssue(){
    this.issueService.getIssueById(this.idValue).subscribe({
      next: (responseData) => {
        this.issueData.set(responseData);
        this.errorMessage.set("");
        console.log(responseData);
      },
      error: (err) => {
        console.log(err);
        this.issueData.set(null);
        if(err instanceof HttpErrorResponse){
            if(err.status == HttpStatusCode.NotFound.valueOf() || err.status == HttpStatusCode.BadRequest.valueOf()){
              this.errorMessage.set("Issue not found");
            }
        }
        else{
          this.errorMessage.set("An unknown error has occured")
        }
      }
    });
  }

}
