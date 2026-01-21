import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../../../services/issue-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IssueData } from '../../../../interfaces/issue-data';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';

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
  errorMessage : WritableSignal<String> = signal("");

  titleText: string = "";
  descriptionText: string = "";
  statusValue: string = "Open";
  priorityValue: string = "Low";
  severityValue: string = "Low";
  idValue: string = "";
  ownerEmailText: string = "";
  projectIdText: string = "";

  postIssue(){
    this.issueService
      .postIssue(this.titleText, this.descriptionText, this.statusValue, this.priorityValue, this.severityValue, this.ownerEmailText, this.projectIdText)
      .subscribe({
        next: (responseData) => {
          this.issueData.set(responseData); 
          this.successMessageAddIssue.set(`Issue ${responseData.issueId} Created`);
          this.errorMessage.set("");
          console.log(responseData);
        },
        error: (err) => {
          console.log(err)
          this.successMessageAddIssue.set("");
          if(err instanceof HttpErrorResponse){
            if(err.status == HttpStatusCode.Unauthorized.valueOf()){
              this.errorMessage.set("Unauthorized user - Must be Tester");
            }

            else{
              this.errorMessage.set(`Invalid Data - Server returned HTTP status ${err.status}`);
            }
          }
          else {
            this.errorMessage.set("An unknown error has occured")
          }
        }
      });
  }

}
