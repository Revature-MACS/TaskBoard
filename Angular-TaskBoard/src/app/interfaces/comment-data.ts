import { UserData } from './user-data';
import { IssueData } from './issue-data';

export interface CommentData {
    commentId: number;
    comment: string;
    date: string;
    time: string;
    user: UserData | null;
    issue: IssueData | null;
}

export interface CreateCommentRequest {
    comment: string;
    date: string;
    time: string;
    user: { email: string };
    issue: { issueId: string };
}
