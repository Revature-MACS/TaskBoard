import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentData, CreateCommentRequest } from '../interfaces/comment-data';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private API_URL = 'http://localhost:8080/api/v1/comments';

  constructor(private httpClient: HttpClient) {}

  getAllComments(): Observable<CommentData[]> {
    return this.httpClient.get<CommentData[]>(`${this.API_URL}/`);
  }

  getCommentsByIssueId(issueId: string): Observable<CommentData[]> {
    return this.httpClient.get<CommentData[]>(`${this.API_URL}/projects/issues/${issueId}`);
  }

  createComment(comment: CreateCommentRequest): Observable<CommentData> {
    return this.httpClient.post<CommentData>(`${this.API_URL}/projects/issue`, comment);
  }

  updateComment(comment: CommentData): Observable<CommentData> {
    return this.httpClient.put<CommentData>(`${this.API_URL}/projects`, comment);
  }

  deleteComment(commentId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.API_URL}/comments/project/issues/${commentId}`);
  }
}
