import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuditLogData } from '../interfaces/audit-log-data';

@Injectable({
  providedIn: 'root',
})
export class AuditLogService {
  private API_URL = 'http://localhost:8080/audit-logs';

  constructor(private httpClient: HttpClient) {}

  getAllAuditLogs(): Observable<AuditLogData[]> {
    return this.httpClient.get<AuditLogData[]>(this.API_URL);
  }

  getAuditLogsByEntityType(entityType: string): Observable<AuditLogData[]> {
    return this.httpClient.get<AuditLogData[]>(`${this.API_URL}/entity/${entityType}`);
  }

  getAuditLogsForEntity(entityType: string, entityId: string): Observable<AuditLogData[]> {
    return this.httpClient.get<AuditLogData[]>(`${this.API_URL}/entity/${entityType}/${entityId}`);
  }

  getAuditLogsByUser(userEmail: string): Observable<AuditLogData[]> {
    return this.httpClient.get<AuditLogData[]>(`${this.API_URL}/user/${userEmail}`);
  }
}
