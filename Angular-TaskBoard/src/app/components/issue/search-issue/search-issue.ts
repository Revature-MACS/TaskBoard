import { Component, signal, WritableSignal } from '@angular/core';
import { IssueData } from '../../../interfaces/issue-data';
import { IssueService } from '../../../services/issue-service';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-issue',
  imports: [CommonModule, FormsModule],
  templateUrl: './search-issue.html',
  styleUrl: './search-issue.css',
})
export class SearchIssue {

  issueList: WritableSignal<IssueData[]> = signal([]);
  filteredIssueList: WritableSignal<IssueData[]> = signal([]);

  constructor(private issueService: IssueService) {
    this.issueService.getIssueListSubject().subscribe(issueListData => {
      this.issueList.set(issueListData);
      this.filteredIssueList.set(issueListData); // initialize with full list
    });
  }

  applyFilters(titleText: string, status: string, priority: string, severity: string) {
    const searchText = titleText.toLowerCase().trim();

    const filtered = this.issueList().filter(issue => {
      const matchesTitle = !searchText || issue.title.toLowerCase().includes(searchText);
      const matchesStatus = !status || issue.status === status;
      const matchesPriority = !priority || issue.priority === priority;
      const matchesSeverity = !severity || issue.severity === severity;

      return matchesTitle && matchesStatus && matchesPriority && matchesSeverity;
    });

    this.filteredIssueList.set(filtered);
  }

}
