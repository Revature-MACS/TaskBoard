# TaskBoard (RevaIssue)

A lightweight issue tracking application designed for small to mid-size software teams. TaskBoard provides a focused, JIRA-style tool that enables efficient issue management with role-based permissions, audit logging, and comprehensive workflow management.

## 👥 Team

**Team MACS** - Maurice Grant, Anthony Siragusa, Cory Myers, Samuel Akinwusi

## 📋 Description

TaskBoard is a full-stack issue tracking system that helps teams manage software defects from discovery to closure. The application supports three primary user roles:

- **Admins**: Create and manage projects, assign team members, and configure project settings
- **Testers**: Report defects, add comments, attach files, and validate issue resolution
- **Developers**: Track issues, update status, and resolve defects through the development lifecycle

## ✨ Features

### Core Functionality
- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **Project Management**: Create, update, and manage projects with team member assignments
- **Issue Lifecycle Management**: Track issues through states (Open → In Progress → Resolved → Closed)
- **Audit Logging**: Complete history of all changes and state transitions
- **Comments & Collaboration**: Thread-based comments on issues for team communication

### Advanced Features
- **Search & Filtering**: Filter issues by status, assignee, severity, priority, labels, and keywords
- **Dashboard**: Role-aware dashboards with issue aggregations and quick views
- **Saved Filters**: Save frequently used filter combinations as named views
- **Attachments**: Upload and manage files/screenshots related to issues
- **SLA Indicators**: Visual indicators for issue age and time-based metrics
- **Activity Feed**: Recent changes and updates across projects

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 4.0.0
- **Language**: Java 21
- **ORM**: Spring Data JPA
- **Database**: SQLite
- **Build Tool**: Gradle
- **Utilities**: Lombok

### Frontend (Planned)
- **Framework**: Angular
- **Features**: Reactive forms, routing, JWT token management

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Gradle 7.x or higher (or use the included Gradle wrapper)

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd TaskBoard
```

2. Build the project:
```bash
./gradlew build
```

3. Run the application:
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080` (default Spring Boot port).

### Development

Run the application in development mode:
```bash
./gradlew bootRun
```

Run tests:
```bash
./gradlew test
```

## 📁 Project Structure

```
TaskBoard/
├── src/
│   ├── main/
│   │   ├── java/com/example/TaskBoard/
│   │   │   └── TaskBoardApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/TaskBoard/
│           └── TaskBoardApplicationTests.java
├── build.gradle
├── settings.gradle
├── PRD.md                 # Product Requirements Document
└── SPRINT.md              # Development Sprint Plan
```

## 🔌 API Endpoints (Planned)

Based on the PRD, the following REST APIs will be implemented:

### Authentication
- `POST /users/login` - User authentication (JWT)
- `POST /users/logout` - User logout

### Projects
- `GET /projects` - List all projects
- `GET /projects/{id}` - Get project details
- `POST /projects` - Create project (Admin only)
- `PUT /projects/{id}` - Update project (Admin only)
- `DELETE /projects/{id}` - Delete project (Admin only)

### Issues
- `GET /projects/{projectId}/issues` - List issues for a project
- `GET /issues/{id}` - Get issue details
- `POST /projects/{projectId}/issues` - Create issue
- `PUT /issues/{id}` - Update issue
- `GET /issues/{id}/history` - Get issue audit history

### Comments
- `GET /issues/{issueId}/comments` - Get comments for an issue
- `POST /issues/{issueId}/comments` - Add comment to issue

### Dashboard & Analytics
- `GET /dashboard` - Get dashboard data (role-aware)
- `GET /dashboard/my-issues` - Get issues assigned to current user
- `GET /projects/{id}/activity` - Get recent activity for a project

## 📊 Issue Workflow

### Status Transitions by Role

**Testers:**
- Create issue → **Open**
- **Open** ↔ **Closed** (can reopen closed issues)

**Developers:**
- **Open** → **In Progress**
- **In Progress** → **Resolved**

**Admins:**
- Full access to all status transitions

### Issue States
1. **Open**: Newly created issue awaiting developer attention
2. **In Progress**: Developer actively working on the issue
3. **Resolved**: Issue has been fixed and is awaiting tester validation
4. **Closed**: Issue has been validated and closed

## 📝 Issue Attributes

Each issue includes:
- Title and description
- Status (Open, In Progress, Resolved, Closed)
- Severity (Critical, High, Medium, Low)
- Priority (Critical, High, Medium, Low)
- Labels (custom tags for categorization)
- Assignee (developer assigned to the issue)
- Environment (e.g., Development, Staging, Production)
- Timestamps (created_at, updated_at, resolved_at)
- Comments

## 🏗️ Development Phases

The project is organized into three implementation phases (see `SPRINT.md` for details):

1. **Phase 1**: Foundations - Authentication, roles, basic entities and APIs
2. **Phase 2**: Core flows - Issue lifecycle, audit logging, comments, search/filtering
3. **Phase 3**: MVP-plus features - Dashboard, saved filters, attachments, SLA indicators

## 📄 Documentation

- **PRD.md**: Complete Product Requirements Document
- **SPRINT.md**: Detailed development sprint plan with phases and exit criteria
- **HELP.md**: Additional Gradle and Spring Boot help

## 🎯 Success Criteria

The application will be considered successful when:
- Defects are created in TaskBoard
- All issues in active projects have an assignee and clear status
- Users can answer key questions in under 30 seconds:
  - "What's assigned to me?"
  - "What's still open for this project?"
  - "What changed recently?"

## 🔄 Status

**Current Status**: Early Development - Phase 1

The project is currently in its initial setup phase. Core functionality is being developed according to the sprint plan outlined in `SPRINT.md`.

