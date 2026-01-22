import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../../services/issue-service';
import { FormsModule } from '@angular/forms';
import { IssueData } from '../../../interfaces/issue-data';
import { Comments } from '../../comments/comments';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-fetch-issue',
  imports: [FormsModule, Comments],
  templateUrl: './fetch-issue.html',
  styleUrl: './fetch-issue.css',
})
export class FetchIssue {

  // issueData: IssueData | null = null;
  id: string = "";
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
  // issueDataValue: IssueData | null = null;

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
  
  constructor(private issueService: IssueService, private router: Router, private route: ActivatedRoute ) {
    this.route.queryParams.subscribe(params => {
      this.id = params['id'];
    });
  }
  

  ngOnInit(){
      this.issueService.getIssueById(this.id).subscribe(data => {
          // this.issueData = data;
            this.issueData.set(data);
        });
  }  


}
