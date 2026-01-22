import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../../services/issue-service';
import { IssueData } from '../../../interfaces/issue-data';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-fetch-all-issues',
  imports: [FormsModule, CommonModule],
  templateUrl: './fetch-all-issues.html',
  styleUrl: './fetch-all-issues.css',
})
export class FetchAllIssues {

  issueList: WritableSignal<Array<IssueData>> = signal([]);
  
  constructor(private issueService: IssueService, private router: Router) {
    this.issueService.getIssueListSubject().subscribe(
      issueListData => {
        this.issueList.set(issueListData);
      }
    )
  }

  getIssues(){
    this.issueService.getIssues();
  }

  selectIssue(issueId: string){
   this.issueService.getIssueById(issueId).subscribe({
      next: (responseData) => {
        this.router.navigate(['/fetch-issue'], { queryParams: {id: issueId} });
        // console.log(responseData)
      }
    });
  }

  showCreateIssueForm() {
    // this.router.navigate(['/candidate-profile'], { queryParams: {id} });
    this.router.navigate(['/create-issue']);
  }

  showSearchIssueForm() {
    this.router.navigate(['/search-issue']);
  } 

  delete(id: string){
    // if(confirm(`Are you sure you want to delete issue with ID ${id}?`)) {
    // this.issueService.deleteIssueById(id).subscribe({
    //   next: (responseData) => {
    //     console.log(responseData);
    //   }
    // });
    // } else {
    //   console.log('Delete action cancelled by user.');
    // }
  }

  update(id: string){
    window.alert(`Issue with ID ${id} has been updated.`);
  }
}
