### Phase 1 (Days 1–4): Foundations

Goal: Auth works, roles exist, basic entities and APIs in place.

Backend:
- Set up Spring Boot project, SQLite config, base entities and repos:
    - Users (with roles Admin/Tester/Developer, project membership).
    - Projects.
    - Issues (basic fields: title, description, status, severity, priority, timestamps).
    - Comments, AuditLog.
- Implement `/users`:
    - JWT authentication.
    - Role-based authorization skeleton.
- Implement minimal `/projects` and `/issues` CRUD (no workflow rules yet).
- Seed data (one Admin, a couple projects) for faster UI testing.

Frontend:
- Angular app setup, routing, global layout.
- Implement login/logout against JWT endpoint; store token securely.
- Basic navigation shell: login → dashboard placeholder.
- Simple project list view consuming `/projects`.

Owners suggestion:
- Backend dev A: Auth, users, JWT, roles.
- Backend dev B: Entities, repositories, basic project/issue APIs.
- Frontend dev C: Auth UI, token handling, guards.
- Frontend dev D: Layout, project list page.

Exit criteria:
- You can log in as Admin.
- See a list of projects from API.
- DB schema is stable enough for next layers.

---

### Phase 2 (Days 5–11): Core flows (MVP essentials)

Goal: End-to-end issue lifecycle for Admin, Tester, Developer, with audit logs and comments.

Backend:
- Enforce business rules on `/projects` and `/issues`:
    - Admin-only: create/update/delete (or archive) projects; assign testers/devs.
    - Tester: create issues (Open), move Open ↔ Closed.
    - Developer: move Open → In Progress → Resolved.
- Implement audit logging:
    - On project changes (create/update/delete).
    - On issue changes: status, assignee, key fields.
- Implement comments API tied to issues.
- Implement basic search and filtering on issues:
    - By status, assignee, severity, priority, keyword (title/description).
- Issue history endpoint (reads from audit log + comments).

Frontend:
- Project detail page:
    - View project details, members, issues list.
    - Admin actions: create/update/delete project, add/remove members.
- Issues list per project:
    - Columns: key, title, status, severity, priority, assignee.
    - Filtering controls (status, assignee, severity, priority, text search).
- Issue detail page:
    - View/update attributes according to role.
    - Status transition buttons respecting allowed actions.
    - Comment thread.
    - History tab pulling audit log.

Owners suggestion:
- Backend dev A: Workflow rules, state machine, audit logging.
- Backend dev B: Comments, search/filtering, history endpoint.
- Frontend dev C: Project detail + members management.
- Frontend dev D: Issue list + detail, status transitions, comments/history UI.

Exit criteria:
- As Admin: set up a project and assign users.
- As Tester: create an issue, see it, close/reopen it.
- As Developer: change statuses to In Progress/Resolved.
- All transitions recorded in history; lists support basic filtering/search.

---

### Phase 3 (Days 12–21): MVP-plus features and polish

Goal: Make it feel like a real product: dashboard, assignees, labels, attachments, saved filters, simple SLA indicators.

Backend:
- Extend Issue model:
    - Assignee, labels (simple string list), environment, resolved_at.
- Implement saved filters:
    - `saved_filters` entity (user, project, name, filter JSON).
    - CRUD endpoints.
- Attachments:
    - Simple file upload endpoint per issue.
    - Store metadata + file path/URL (keep storage simple).
- Dashboard APIs:
    - Aggregations per project (issues by status).
    - “My issues” (assigned to current user, open/in progress).
    - “Issues I opened” (for Testers).
    - “Recent activity” based on audit logs.
- SLA-like queries:
    - Issues open longer than X days, time-open calculation.

Frontend:
- Enhance issue forms:
    - Assignee dropdown (only project developers).
    - Labels input (chips or comma-separated).
    - Environment field.
    - Visual indicator for age/time open (e.g., colored badge).
- Attachments UI:
    - Upload widget on issue detail.
    - List of existing attachments with download links.
- Saved filters:
    - Build filters in UI; “Save as view” with name.
    - Display saved filters as quick-access items in project and/or dashboard.
- Dashboard:
    - Cards/charts: issues by status per project.
    - Sections: “My assigned issues”, “Issues I opened”, “Recently updated”.
    - Links from dashboard tiles into filtered issue lists.

Owners suggestion:
- Backend dev A: Dashboard/aggregation, SLA indicators, “My issues”/recent activity.
- Backend dev B: Saved filters, attachments, extended issue fields.
- Frontend dev C: Dashboard, saved filters UX.
- Frontend dev D: Issue detail enhancements (assignee, labels, env, attachments, SLA visuals).

Exit criteria:
- A real team can:
    - Log in, see what matters to them on the dashboard.
    - Work issues end-to-end with ownership (assignee), context (labels, env), and history.
    - Attach screenshots.
    - Use saved views to avoid re-building the same filters.

---
