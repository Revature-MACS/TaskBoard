import { Component, Input, OnChanges, signal, SimpleChanges, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CommentsService } from '../../services/comments-service';
import { CommentData, CreateCommentRequest } from '../../interfaces/comment-data';

@Component({
    selector: 'app-comments',
    imports: [CommonModule, FormsModule],
    templateUrl: './comments.html',
    styleUrl: './comments.css'
})
export class Comments implements OnChanges {
    @Input() issueId: string = '';

    comments: WritableSignal<CommentData[]> = signal([]);
    isLoading: WritableSignal<boolean> = signal(false);
    errorMessage: WritableSignal<string> = signal('');
    successMessage: WritableSignal<string> = signal('');

    newCommentText: string = '';
    userEmail: string = '';

    constructor(
        private commentsService: CommentsService
    ) {}

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['issueId'] && this.issueId) {
            this.loadComments();
        }
    }

    loadComments(): void {
        if (!this.issueId) return;

        this.isLoading.set(true);
        this.errorMessage.set('');

        this.commentsService.getCommentsByIssueId(this.issueId).subscribe({
            next: (data) => {
                this.comments.set(data);
                this.isLoading.set(false);
            },
            error: (err) => {
                this.errorMessage.set('Failed to load comments');
                this.isLoading.set(false);
                console.error(err);
            }
        });
    }

    addComment(): void {
        if (!this.newCommentText.trim() || !this.userEmail.trim()) {
            this.errorMessage.set('Please enter both your email and a comment');
            return;
        }

        this.errorMessage.set('');
        this.successMessage.set('');

        const now = new Date();
        const comment: CreateCommentRequest = {
            comment: this.newCommentText,
            date: now.toISOString().split('T')[0],
            time: now.toTimeString().split(' ')[0],
            user: { email: this.userEmail },
            issue: { issueId: this.issueId }
        };

        this.commentsService.createComment(comment).subscribe({
            next: () => {
                this.successMessage.set('Comment added successfully');
                this.newCommentText = '';
                this.loadComments();
            },
            error: (err) => {
                this.errorMessage.set('Failed to add comment');
                console.error(err);
            }
        });
    }

    formatDateTime(date: string, time: string): string {
        if (!date) return '';
        return `${date} ${time || ''}`;
    }
}
