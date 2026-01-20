import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../../../services/issue-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IssueData } from '../../../../interfaces/issue-data';

@Component({
  selector: 'app-create-issue',
  imports: [FormsModule, CommonModule],
  templateUrl: './create-issue.html',
  styleUrl: './create-issue.css',
})
export class CreateIssue {

  constructor(private issueService: IssueService){

  }

  issueData: WritableSignal<IssueData | null> = signal(null);
  successMessageAddIssue: WritableSignal<string> = signal('');

  issueStatusList: string[] = ["Open", "In progress", "Resolved", "Closed"];
  lowMedHighList: string[] = ["Low", "Medium", "High"];
  searchType: string = "number";

  issueTitle: WritableSignal<string> = signal("");
  issueDescription: WritableSignal<string> = signal("");
  issueStatus: WritableSignal<string> = signal("");
  issuePriority: WritableSignal<string> = signal("");
  issueSeverity: WritableSignal<string> = signal("");
  issueId:WritableSignal<string> = signal("");
  issueDeleteId: WritableSignal<string> = signal("");
  issueProjectId: WritableSignal<string> = signal("");

  titleText: string = "";
  descriptionText: string = "";
  statusValue: string = "Open";
  priorityValue: string = "Low";
  severityValue: string = "Low";
  idValue: string = "";
  ownerEmailText: string = "";
  projectId: string = "";

  postIssue(){
    this.issueTitle.set(this.titleText);
    this.issueDescription.set(this.descriptionText);
    this.issueStatus.set(this.statusValue);
    this.issuePriority.set(this.priorityValue);
    this.issueSeverity.set(this.severityValue);
    this.issueProjectId.set(this.projectId);
    this.issueService
      .postIssue(this.titleText, this.descriptionText, this.statusValue, this.priorityValue, this.severityValue, this.ownerEmailText, this.projectId)
      .subscribe({
        next: (responseData) => {
          this.issueData.set(responseData); 
          this.successMessageAddIssue.set(`Issue ${responseData.issueId} Created`)
          console.log(responseData)
        },
      });
  }

}
