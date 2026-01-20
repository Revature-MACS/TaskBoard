package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.AuditLog;
import com.example.TaskBoard.repository.AuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuditLogServiceUnitTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditLogService auditLogService;

    private AuditLog testAuditLog;

    @BeforeEach
    void setUp() {
        testAuditLog = new AuditLog();
        testAuditLog.setEntityType(AuditLog.EntityType.PROJECT);
        testAuditLog.setEntityId("test-project-id");
        testAuditLog.setActionType(AuditLog.ActionType.CREATE);
        testAuditLog.setPerformedBy("admin@test.com");
        testAuditLog.setDetails("Test project created");
    }

    @Test
    void testLog_ShouldSaveAuditLog() {
        // Arrange
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(testAuditLog);

        // Act
        auditLogService.log(
                AuditLog.EntityType.PROJECT,
                "test-project-id",
                AuditLog.ActionType.CREATE,
                "admin@test.com",
                "Test project created");

        // Assert
        ArgumentCaptor<AuditLog> auditLogCaptor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository, times(1)).save(auditLogCaptor.capture());

        AuditLog savedLog = auditLogCaptor.getValue();
        assertEquals(AuditLog.EntityType.PROJECT, savedLog.getEntityType());
        assertEquals("test-project-id", savedLog.getEntityId());
        assertEquals(AuditLog.ActionType.CREATE, savedLog.getActionType());
        assertEquals("admin@test.com", savedLog.getPerformedBy());
        assertEquals("Test project created", savedLog.getDetails());
    }

    @Test
    void testLogProjectAction_ShouldCallLogWithProjectEntityType() {
        // Arrange
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(testAuditLog);

        // Act
        auditLogService.logProjectAction(
                "project-123",
                AuditLog.ActionType.UPDATE,
                "user@test.com",
                "Updated project");

        // Assert
        ArgumentCaptor<AuditLog> auditLogCaptor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository, times(1)).save(auditLogCaptor.capture());

        AuditLog savedLog = auditLogCaptor.getValue();
        assertEquals(AuditLog.EntityType.PROJECT, savedLog.getEntityType());
        assertEquals("project-123", savedLog.getEntityId());
        assertEquals(AuditLog.ActionType.UPDATE, savedLog.getActionType());
    }

    @Test
    void testLogIssueAction_ShouldCallLogWithIssueEntityType() {
        // Arrange
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(testAuditLog);

        // Act
        auditLogService.logIssueAction(
                "issue-456",
                AuditLog.ActionType.DELETE,
                "admin@test.com",
                "Deleted issue");

        // Assert
        ArgumentCaptor<AuditLog> auditLogCaptor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository, times(1)).save(auditLogCaptor.capture());

        AuditLog savedLog = auditLogCaptor.getValue();
        assertEquals(AuditLog.EntityType.ISSUE, savedLog.getEntityType());
        assertEquals("issue-456", savedLog.getEntityId());
        assertEquals(AuditLog.ActionType.DELETE, savedLog.getActionType());
    }

    @Test
    void testGetAuditLogsForEntity_ShouldReturnLogsForSpecificEntity() {
        // Arrange
        List<AuditLog> expectedLogs = Arrays.asList(testAuditLog);
        when(auditLogRepository.findByEntityTypeAndEntityId(
                AuditLog.EntityType.PROJECT,
                "test-project-id")).thenReturn(expectedLogs);

        // Act
        List<AuditLog> result = auditLogService.getAuditLogsForEntity(
                AuditLog.EntityType.PROJECT,
                "test-project-id");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testAuditLog, result.get(0));
        verify(auditLogRepository, times(1)).findByEntityTypeAndEntityId(
                AuditLog.EntityType.PROJECT,
                "test-project-id");
    }

    @Test
    void testGetAuditLogsByEntityType_ShouldReturnLogsForEntityType() {
        // Arrange
        AuditLog log2 = new AuditLog();
        log2.setEntityType(AuditLog.EntityType.PROJECT);
        log2.setEntityId("another-project-id");
        log2.setActionType(AuditLog.ActionType.UPDATE);

        List<AuditLog> expectedLogs = Arrays.asList(testAuditLog, log2);
        when(auditLogRepository.findByEntityType(AuditLog.EntityType.PROJECT))
                .thenReturn(expectedLogs);

        // Act
        List<AuditLog> result = auditLogService.getAuditLogsByEntityType(
                AuditLog.EntityType.PROJECT);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(auditLogRepository, times(1)).findByEntityType(AuditLog.EntityType.PROJECT);
    }

    @Test
    void testGetAuditLogsByUser_ShouldReturnLogsForUser() {
        // Arrange
        List<AuditLog> expectedLogs = Arrays.asList(testAuditLog);
        when(auditLogRepository.findByPerformedBy("admin@test.com"))
                .thenReturn(expectedLogs);

        // Act
        List<AuditLog> result = auditLogService.getAuditLogsByUser("admin@test.com");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("admin@test.com", result.get(0).getPerformedBy());
        verify(auditLogRepository, times(1)).findByPerformedBy("admin@test.com");
    }

    @Test
    void testGetAllAuditLogs_ShouldReturnAllLogs() {
        // Arrange
        AuditLog log2 = new AuditLog();
        log2.setEntityType(AuditLog.EntityType.ISSUE);
        log2.setEntityId("issue-789");
        log2.setActionType(AuditLog.ActionType.VIEW);

        List<AuditLog> expectedLogs = Arrays.asList(testAuditLog, log2);
        when(auditLogRepository.findAll()).thenReturn(expectedLogs);

        // Act
        List<AuditLog> result = auditLogService.getAllAuditLogs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(auditLogRepository, times(1)).findAll();
    }

    @Test
    void testGetAuditLogsForEntity_ShouldReturnEmptyListWhenNoLogsFound() {
        // Arrange
        when(auditLogRepository.findByEntityTypeAndEntityId(
                AuditLog.EntityType.USER,
                "nonexistent-id")).thenReturn(Arrays.asList());

        // Act
        List<AuditLog> result = auditLogService.getAuditLogsForEntity(
                AuditLog.EntityType.USER,
                "nonexistent-id");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
