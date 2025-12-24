## Description

RevaIssue is a lightweight issue tracking application (a focused JIRA-style tool) built with Angular and Spring Boot. It enables Admins to create and manage projects, Testers to report and validate defects, and Developers to track, update, and resolve issues. The product covers authentication, role-based permissions, issue lifecycle management, search/filtering, audit logging, and a set of small but high-impact enhancements like assignees, labels, dashboards, attachments, and saved filters.

## Problem

Teams need a simple, reliable way to track software issues from discovery to closure, but existing tools are often heavy, complex, and overkill for small to mid-size teams. This leads to poor adoption, inconsistent workflows, and limited visibility into what’s happening with defects across projects.

## Why

Today, many small and mid-size teams either use spreadsheets, chat messages, or overpowered tools like JIRA configured in ad-hoc ways. This causes several issues: defects are lost or duplicated; ownership of issues is unclear; status is hard to understand at a glance; and there is limited accountability for who changed what and when. A focused tool, with a clear workflow and role-based permissions, gives these teams just enough structure: consistent issue states, clear ownership (assignees), lightweight classification (labels, severity, priority), and audit logs. The added dashboard, saved filters, and simple activity views increase day-to-day usability without adding major complexity.

## Success

We’ll consider this version of RevaIssue successful if, for a pilot team: at least 80% of new defects are created in RevaIssue instead of side channels (email/chat); all issues in an active project have an assignee and a clear status (Open, In Progress, Resolved, Closed); and users can answer three questions in under 30 seconds from the UI: “What’s assigned to me?”, “What’s still open for this project?”, and “What changed recently?”. Qualitatively, Admins should report that project setup and user assignment are straightforward, and Testers/Developers should feel that the tool is faster and simpler than alternatives they’ve used.

## Audience

Primary users are small to mid-size software teams working on web or mobile projects. Within those teams, we support three roles: Admins who configure projects and assign Testers/Developers; Testers who create, comment on, and validate issues; and Developers who update issue status and details as they work. Initial usage will be via a web interface (desktop-first), with these users likely juggling multiple projects but needing quick, focused views (e.g., “my issues”, “high priority open issues”).

## What

In the product, users authenticate via a login page and see a role-aware dashboard. Admins can create, update, and (optionally) archive/delete projects, and assign Testers and Developers to each project. Within a project, users can view an issue list with filtering and search by status, assignee, label, severity, priority, and keyword.

Issues have a title, description, severity, priority, labels, environment, assignee, status, and timestamps. Testers can open new issues, add comments, attach screenshots/files, and move issues between Open and Closed (including reopening). Developers can move issues through In Progress and Resolved, update details, and add comments. All state changes and key field updates create audit log entries, which feed both the issue history view and the project’s recent activity list.

The dashboard surfaces key counts and views: issues by status per project, “My assigned issues” for Developers, “Issues I opened awaiting closure” for Testers, and quick links to saved filters such as “High priority open” or “Open > 7 days”. Users can define and save their own filters as named views. Simple SLA-style indicators (e.g., time since created, color coding for long-open issues) make it obvious which items are aging. Basic activity highlights show what has changed recently in each project, leveraging the existing audit logs.

## How

We will implement the client using Angular, with modules/components for authentication, dashboard, project management, issue list/detail, and audit history. The server will be a Spring Boot application exposing REST APIs for `/users`, `/projects`, `/issues`, and `/logs`, with JWT-based authentication and role-based authorization. SQLite will be used initially for persistence, modeling Users (with role and project memberships), Projects, Issues (including states, assignee, labels, timestamps), Comments, Attachments, SavedFilters, and AuditLogs.

Work will be organized in slices: first, core authentication and role setup; then project CRUD and user assignment; then issue creation and lifecycle transitions; then comments, severity/priority, labels, and assignees; and finally search/filtering, dashboard views, attachments, saved filters, activity feeds, and simple SLA indicators. Automated tests will focus on permissions, workflow transitions, and audit integrity. A small pilot team will be onboarded to validate usability and identify missing essentials.

## When

For an MVP-plus scope, we can plan roughly three implementation phases: first, build and stabilize core authentication, roles, projects, and basic issue CRUD with state transitions and audit logging; second, add assignees, labels, comments, severity/priority, search/filtering, and a usable dashboard; third, layer on attachments, saved filters, activity views, and basic SLA indicators. Each phase should be demonstrable on its own and deployed to a test environment, with feedback collected between phases to refine the next tranche of work.

---
