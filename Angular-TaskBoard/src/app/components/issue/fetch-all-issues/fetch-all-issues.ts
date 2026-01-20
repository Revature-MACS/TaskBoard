import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../../services/issue-service';
import { IssueData } from '../../../interfaces/issue-data';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-fetch-all-issues',
  imports: [FormsModule, CommonModule],
  templateUrl: './fetch-all-issues.html',
  styleUrl: './fetch-all-issues.css',
})
export class FetchAllIssues {

  issueList: WritableSignal<Array<IssueData>> = signal([]);
  
  constructor(private issueService: IssueService){
    this.issueService.getIssueListSubject().subscribe(
      issueListData => {
        this.issueList.set(issueListData);
      }
    )
  }

  getIssues(){
    this.issueService.getIssues();
  }

}
