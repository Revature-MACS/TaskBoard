export interface IssueData {
    title: string,
    description: string,
    status: string,
    priority: string,
    severity: string,
    issueId: string,
    timeCreatedAtEpoch: string,
    timeUpdatedAtEpoch: string,
    projectId: string
    owner: {
        email: string
    } 
}
