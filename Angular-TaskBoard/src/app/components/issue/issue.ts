import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../services/issue-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-issue',
  imports: [FormsModule],
  templateUrl: './issue.html',
  styleUrl: './issue.css',
})
export class Issue {

  constructor(private issueService: IssueService){

  }

  headerText: string = "This is a page to test the Issue API";
  createIssueInstructions: string = "Create Issue";
  searchType: string = "number";

  issueTitle: WritableSignal<string> = signal("");
  issueDescription: WritableSignal<string> = signal("");
  issueStatus: WritableSignal<string> = signal("");
  issuePriority: WritableSignal<string> = signal("");
  issueSeverity: WritableSignal<string> = signal("");
  issueId:WritableSignal<string> = signal("");
  issueDeleteId: WritableSignal<string> = signal("");

  updateIssueTitle: WritableSignal<string> = signal("");
  updateIssueDescription: WritableSignal<string> = signal("");
  updateIssueStatus: WritableSignal<string> = signal("");
  updateIssuePriority: WritableSignal<string> = signal("");
  updateIssueSeverity: WritableSignal<string> = signal("");
  updateIssueId:WritableSignal<string> = signal("");

  titleText: string = "";
  descriptionText: string = "";
  statusValue: string = "";
  priorityValue: string = "";
  severityValue: string = "";
  idValue: string = "";
  idDeleteValue: string = "";

  updateTitleText: string = "";
  updateDescriptionText: string = "";
  updateStatusValue: string = "";
  updatePriorityValue: string = "";
  updateSeverityValue: string = "";
  updateIdText: string = "";

  postIssue(){
    this.issueService.postIssue(this.titleText, this.descriptionText, Number(this.statusValue), Number(this.priorityValue), Number(this.severityValue));
  }

  getIssues(){
    this.issueService.getIssues();
  }

  getIssue(){
    this.issueService.getIssueById(this.idValue);
  }
  
  deleteIssue(){
    this.issueService.deleteIssueById(this.idDeleteValue);
  }

  updateIssue(){
    this.issueService.updateIssueById(this.updateIdText, this.updateTitleText, this.updateDescriptionText, Number(this.updateStatusValue), Number(this.updatePriorityValue), Number(this.updateSeverityValue));
  }

}
