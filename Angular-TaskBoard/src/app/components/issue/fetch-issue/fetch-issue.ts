import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../../services/issue-service';
import { FormsModule } from '@angular/forms';
import { IssueData } from '../../../interfaces/issue-data';
import { Comments } from '../../comments/comments';

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
        console.log(responseData)
      }
    });
  }

}
