import { Component, signal, WritableSignal } from '@angular/core';
import { IssueService } from '../../services/issue-service';
import { FormsModule } from '@angular/forms';
import { CreateIssue } from "./createIssue/create-issue/create-issue";
import { FetchIssue } from "./fetch-issue/fetch-issue";
import { FetchAllIssues } from './fetch-all-issues/fetch-all-issues';
import { DeleteIssue } from "./delete-issue/delete-issue";
import { UpdateIssue } from "./update-issue/update-issue";
import { SearchIssue } from "./search-issue/search-issue";

@Component({
  selector: 'app-issue',
  imports: [FormsModule, CreateIssue, FetchIssue, FetchAllIssues, DeleteIssue, UpdateIssue, SearchIssue],
  templateUrl: './issue.html',
  styleUrl: './issue.css',
})
export class Issue {



}
