INSERT INTO users (user_id, email, name, password, role) VALUES ('00000000-0000-0000-0000-000000000001', 'admin@taskboard.com', 'Admin User', 'admin123', 'ADMIN');
INSERT INTO users (user_id, email, name, password, role) VALUES ('00000000-0000-0000-0000-000000000002', 'dev1@taskboard.com', 'John Developer', 'dev123', 'DEVELOPER');
INSERT INTO users (user_id, email, name, password, role) VALUES ('00000000-0000-0000-0000-000000000003', 'tester1@taskboard.com', 'Alice Tester', 'test123', 'TESTER');
INSERT INTO users (user_id, email, name, password, role) VALUES ('00000000-0000-0000-0000-000000000004', 'dev2@taskboard.com', 'Bob Developer', 'dev123', 'DEVELOPER');
INSERT INTO projects (project_id, name, description, owner_id, created_at) VALUES ('11111111-1111-1111-1111-111111111111', 'E-Commerce Platform', 'A full-featured online shopping platform.', 'admin@taskboard.com', 1705152000000);
INSERT INTO projects (project_id, name, description, owner_id, created_at) VALUES ('22222222-2222-2222-2222-222222222222', 'Mobile Banking App', 'Secure mobile banking application.', 'admin@taskboard.com', 1705152000000);
INSERT INTO projects (project_id, name, description, owner_id, created_at) VALUES ('33333333-3333-3333-3333-333333333333', 'Task Management System', 'Task management system.', 'admin@taskboard.com', 1705152000000);

INSERT INTO projectusers (project_user_id, FK_user_id, FK_project_id) VALUES ('a1111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000002', '11111111-1111-1111-1111-111111111111');
INSERT INTO projectusers (project_user_id, FK_user_id, FK_project_id) VALUES ('a2222222-2222-2222-2222-222222222222', '00000000-0000-0000-0000-000000000003', '22222222-2222-2222-2222-222222222222');
INSERT INTO projectusers (project_user_id, FK_user_id, FK_project_id) VALUES ('a3333333-3333-3333-3333-333333333333', '00000000-0000-0000-0000-000000000002', '33333333-3333-3333-3333-333333333333');
INSERT INTO projectusers (project_user_id, FK_user_id, FK_project_id) VALUES ('a4444444-4444-4444-4444-444444444444', '00000000-0000-0000-0000-000000000004', '33333333-3333-3333-3333-333333333333');

INSERT INTO issues (issue_id, title, description, status, priority, severity, project_id, owner_id, created_at) VALUES ('b1111111-1111-1111-1111-111111111111', 'Shopping cart not persisting', 'Items disappear from cart after page refresh', 0, 2, 2, '11111111-1111-1111-1111-111111111111', 'tester1@taskboard.com', 1705152000000);

INSERT INTO comments (comment_id, comment, creation_date, creation_time, creator_name, issue_id) VALUES ('c1111111-1111-1111-1111-111111111111', 'I can reproduce this consistently', '2024-01-13', '12:05:00', 'tester1@taskboard.com', 'b1111111-1111-1111-1111-111111111111');

INSERT INTO audit_logs (audit_id, entity_type, entity_id, action_type, performed_by, details, timestamp) VALUES ('d1111111-1111-1111-1111-111111111111', 'PROJECT', '11111111-1111-1111-1111-111111111111', 'CREATE', 'admin@taskboard.com', 'Created E-Commerce Platform project', 1705152000000);
INSERT INTO audit_logs (audit_id, entity_type, entity_id, action_type, performed_by, details, timestamp) VALUES ('d2222222-2222-2222-2222-222222222222', 'PROJECT', '22222222-2222-2222-2222-222222222222', 'CREATE', 'admin@taskboard.com', 'Created Mobile Banking App project', 1705152300000);
INSERT INTO audit_logs (audit_id, entity_type, entity_id, action_type, performed_by, details, timestamp) VALUES ('d3333333-3333-3333-3333-333333333333', 'ISSUE', 'b1111111-1111-1111-1111-111111111111', 'CREATE', 'tester1@taskboard.com', 'Created issue: Shopping cart not persisting', 1705155600000);
INSERT INTO audit_logs (audit_id, entity_type, entity_id, action_type, performed_by, details, timestamp) VALUES ('d4444444-4444-4444-4444-444444444444', 'USER', '00000000-0000-0000-0000-000000000002', 'VIEW', 'admin@taskboard.com', 'Viewed user profile', 1705159200000);
