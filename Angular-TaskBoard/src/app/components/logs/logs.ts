import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuditLogService } from '../../services/audit-log-service';
import { AuditLogData } from '../../interfaces/audit-log-data';
import { UserService } from '../../services/user-service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-logs',
    imports: [CommonModule, FormsModule],
    templateUrl: './logs.html',
    styleUrl: './logs.css'
})
export class Logs implements OnInit {
    logs: WritableSignal<AuditLogData[]> = signal([]);
    filteredLogs: WritableSignal<AuditLogData[]> = signal([]);
    errorMessage: WritableSignal<string> = signal('');
    isLoading: WritableSignal<boolean> = signal(false);

    filterEntityType: string = '';
    filterActionType: string = '';
    filterUser: string = '';

    entityTypes = ['PROJECT', 'ISSUE', 'USER', 'PROJECT_USER'];
    actionTypes = ['CREATE', 'UPDATE', 'DELETE', 'VIEW'];

    constructor(
        private auditLogService: AuditLogService,
        private userService: UserService,
        private router: Router
    ) {}

    ngOnInit(): void {
        // Load current user and check admin status
        this.userService.getCurrentUser().subscribe({
            next: (user) => {
                if (user.role !== 'ADMIN') {
                    this.router.navigate(['/dashboard']);
                    return;
                }
                this.loadLogs();
            },
            error: () => {
                this.router.navigate(['/dashboard']);
            }
        });
    }

    loadLogs(): void {
        this.isLoading.set(true);
        this.errorMessage.set('');
        
        this.auditLogService.getAllAuditLogs().subscribe({
            next: (data) => {
                this.logs.set(data);
                this.applyFilters();
                this.isLoading.set(false);
            },
            error: (err) => {
                this.errorMessage.set('Failed to load audit logs');
                this.isLoading.set(false);
                console.error(err);
            }
        });
    }

    applyFilters(): void {
        let filtered = this.logs();

        if (this.filterEntityType) {
            filtered = filtered.filter(log => log.entityType === this.filterEntityType);
        }
        if (this.filterActionType) {
            filtered = filtered.filter(log => log.actionType === this.filterActionType);
        }
        if (this.filterUser) {
            filtered = filtered.filter(log => 
                log.performedBy?.toLowerCase().includes(this.filterUser.toLowerCase())
            );
        }

        this.filteredLogs.set(filtered);
    }

    clearFilters(): void {
        this.filterEntityType = '';
        this.filterActionType = '';
        this.filterUser = '';
        this.applyFilters();
    }

    formatTimestamp(timestamp: string): string {
        return new Date(timestamp).toLocaleString();
    }
}
