export interface AuditLogData {
    auditId: string;
    entityType: 'PROJECT' | 'ISSUE' | 'USER' | 'PROJECT_USER';
    entityId: string;
    actionType: 'CREATE' | 'UPDATE' | 'DELETE' | 'VIEW';
    performedBy: string;
    details: string;
    timestamp: string;
}
