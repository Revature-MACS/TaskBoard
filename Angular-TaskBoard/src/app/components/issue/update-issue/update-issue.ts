import { Component, signal, WritableSignal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IssueService } from '../../../services/issue-service';
import { IssueData } from '../../../interfaces/issue-data';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-update-issue',
  imports: [FormsModule, CommonModule],
  templateUrl: './update-issue.html',
  styleUrl: './update-issue.css',
})
export class UpdateIssue {
  
  issueStatusList: string[] = ["Open", "In progress", "Resolved", "Closed"];
  lowMedHighList: string[] = ["Low", "Medium", "High"];

  issueData: WritableSignal<IssueData | null> = signal(null);
  successMessageUpdateIssue: WritableSignal<string> = signal("");
  prevIssueData: WritableSignal<IssueData | null> = signal(null);
  

  updateIssueTitle: WritableSignal<string> = signal("");
  updateIssueDescription: WritableSignal<string> = signal("");
  updateIssueStatus: WritableSignal<string> = signal("");
  updateIssuePriority: WritableSignal<string> = signal("");
  updateIssueSeverity: WritableSignal<string> = signal("");
  updateIssueId:WritableSignal<string> = signal("");
  errorMessage: WritableSignal<string> = signal("");

  updateTitleText: string = "";
  updateDescriptionText: string = "";
  updateStatusValue: string = "Open";
  updatePriorityValue: string = "Low";
  updateSeverityValue: string = "Low";
  updateIdText: string = "";
  updateOwnerEmailText: string = '';
  projectId: string = '';

  constructor(private issueService: IssueService){

  }

  updateIssue(){
    this.issueService.getIssueById(this.updateIdText).subscribe({
      next: (responseData) => {
        this.prevIssueData.set(responseData);
        console.log(responseData);
      }
    })
    this.issueService.updateIssueById(
      this.updateIdText, 
      this.updateTitleText, 
      this.updateDescriptionText, 
      this.updateStatusValue, 
      this.updatePriorityValue, 
      this.updateSeverityValue,
      this.updateOwnerEmailText
    )
    .subscribe({
      next: (responseData) => {
        this.issueData.set(responseData);
        this.successMessageUpdateIssue.set(`Issue ${responseData.issueId} Updated`)
        this.errorMessage.set("");
      },
      error: (err) => {
        console.log(err);
        this.successMessageUpdateIssue.set("");
        if(err instanceof HttpErrorResponse){
            if(err.status == HttpStatusCode.Forbidden.valueOf()){
              this.errorMessage.set("Invalid Role");
            }
            else if(err.status == HttpStatusCode.NotFound.valueOf())
            {
              this.errorMessage.set("Issue not found");
            }
            else if(err.status == HttpStatusCode.BadRequest.valueOf())
            {
              this.errorMessage.set("Invalid Issue Data");
            }
        }
        else{
          this.errorMessage.set("An unknown error has occured")
        }
      }
    });
  }

}
