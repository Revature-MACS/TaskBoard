import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../../services/issue-service';
import { FormsModule } from '@angular/forms';
import { IssueData } from '../../../interfaces/issue-data';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-delete-issue',
  imports: [FormsModule, CommonModule],
  templateUrl: './delete-issue.html',
  styleUrl: './delete-issue.css',
})
export class DeleteIssue {
  
  idDeleteValue: string = "";
  idDeleteSignal: WritableSignal<string> = signal("")
  responseCode: WritableSignal<string> = signal("");
  errorMessage: WritableSignal<string> = signal("");

  deletedIssueData: WritableSignal<IssueData | null> = signal(null);
  successMessageDeleteIssue: WritableSignal<string> = signal("");


  constructor(private issueService: IssueService){
  }
  
  deleteIssue(){
    if (this.idDeleteValue.trim() === ''){
      this.errorMessage.set('Issue ID is required');
      console.error(this.errorMessage);
      return;
    }
    this.issueService.deleteIssueById(this.idDeleteValue).subscribe({
      next: (responseData) => {
        this.deletedIssueData.set(responseData);
        this.issueService.getIssues()
        this.successMessageDeleteIssue.set(`Issue ${this.idDeleteValue} Deleted`);
        this.errorMessage.set("");
        console.log(responseData);
      },
      error: (err) => {
        console.log(err);
        this.successMessageDeleteIssue.set("");
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
