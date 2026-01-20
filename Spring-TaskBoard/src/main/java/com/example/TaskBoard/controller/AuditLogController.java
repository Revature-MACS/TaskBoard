package com.example.TaskBoard.controller;

import com.example.TaskBoard.entity.AuditLog;
import com.example.TaskBoard.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
@RequiredArgsConstructor
@CrossOrigin
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        List<AuditLog> logs = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/entity/{entityType}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByEntityType(@PathVariable AuditLog.EntityType entityType) {
        List<AuditLog> logs = auditLogService.getAuditLogsByEntityType(entityType);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<List<AuditLog>> getAuditLogsForEntity(
            @PathVariable AuditLog.EntityType entityType,
            @PathVariable String entityId) {
        List<AuditLog> logs = auditLogService.getAuditLogsForEntity(entityType, entityId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUser(@PathVariable String userEmail) {
        List<AuditLog> logs = auditLogService.getAuditLogsByUser(userEmail);
        return ResponseEntity.ok(logs);
    }
}
