# SiteTrack - Construction Management Backend API

A Spring Boot REST backend for managing construction sites, projects, site managers, trades, and daily progress reports.

---

## 🛠 Tech Stack

- **Java**: 21+
- **Framework**: Spring Boot 3.4.3 (Spring Web, Spring Data JPA)
- **Database**: PostgreSQL 16 (running via Docker)
- **Build Tool**: Maven Wrapper (`mvnw`)

---

## 🚀 Getting Started

### 1. Start the Database
Ensure Docker is running, then start the PostgreSQL container:
```bash
docker compose up -d
```
The database runs on port `5433` (`sitetrack_db`, user: `postgres`, password: `postgres`).

### 2. Run the Application
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```
The server will start at `http://localhost:8080`.

---

## 📖 API Documentation & Demo Payloads

### 1. Sites (`/api/sites`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/sites/save` | Create a new site |
| `GET` | `/api/sites/all` | List all sites |
| `GET` | `/api/sites/{id}` | Get site by UUID |
| `PUT` | `/api/sites/update/{id}` | Update site details |
| `DELETE` | `/api/sites/delete/{id}` | Delete a site |
| `GET` | `/api/sites/free` | List sites currently not assigned to any site manager |

#### Demo Request (`POST /api/sites/save`):
```json
{
  "siteNumber": 5,
  "name": "Kacyiru Site"
}
```
**Response (`201 Created`):**
```
Site saved successfully
```

---

### 2. Trades (`/api/trades`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/trades/save` | Create a new trade |
| `GET` | `/api/trades/all` | List all trades |
| `GET` | `/api/trades/{id}` | Get trade by UUID |
| `PUT` | `/api/trades/update/{id}` | Update trade name |
| `DELETE` | `/api/trades/delete/{id}` | Delete a trade |
| `GET` | `/api/trades/unused` | List trades not held by any site manager |

#### Demo Request (`POST /api/trades/save`):
```json
{
  "name": "Welding"
}
```
**Response (`201 Created`):**
```
Trade saved successfully
```

---

### 3. Projects (`/api/projects`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/projects/save` | Create a new project |
| `GET` | `/api/projects/all` | List all projects |
| `GET` | `/api/projects/{id}` | Get project by UUID |
| `PUT` | `/api/projects/update/{id}` | Update project details |
| `DELETE` | `/api/projects/delete/{id}` | Delete a project |
| `GET` | `/api/projects/by-district?district=Gasabo` | Filter projects by district (case-insensitive) |
| `GET` | `/api/projects/of-site-manager/{siteManagerId}` | Get all projects managed by a specific site manager |

#### Demo Request (`POST /api/projects/save`):
```json
{
  "projectName": "Vision City Phase 2",
  "clientName": "Ultimate Developers Ltd",
  "projectCode": "PRJ-2026-06",
  "district": "Gasabo"
}
```
**Response (`201 Created`):**
```
Project saved successfully
```

---

### 4. Site Managers (`/api/site-managers`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/site-managers/save` | Register a new site manager |
| `GET` | `/api/site-managers/all` | List all site managers with their site and trades |
| `GET` | `/api/site-managers/{id}` | Get site manager by UUID |
| `PUT` | `/api/site-managers/update/{id}` | Update site manager info |
| `DELETE` | `/api/site-managers/delete/{id}` | Delete site manager |
| `GET` | `/api/site-managers/by-trade?name=Mason` | Filter managers possessing a specific trade |
| `PUT` | `/api/site-managers/{siteManagerId}/trades/{tradeId}` | Assign an existing trade to a site manager |
| `PATCH` | `/api/site-managers/{siteManagerId}/site?siteNumber={num}` | Move a site manager to another site |

#### Demo Request (`POST /api/site-managers/save`):
```json
{
  "firstName": "Patrick",
  "lastName": "Mugabe",
  "phone": "0788112233",
  "site": {
    "id": "4b055380-a950-4aba-a533-ec1658b59e86"
  },
  "trades": [
    {
      "id": "b906ad6c-b708-4775-98f2-39d7fd526cd7"
    }
  ]
}
```

#### Demo Assign Trade (`PUT /api/site-managers/{smId}/trades/{tradeId}`):
- **Response (`200 OK`):** `Trade added to the site manager`
- **Response (`409 Conflict`):** `The site manager already has that trade`

#### Demo Change Site (`PATCH /api/site-managers/{smId}/site?siteNumber=4`):
- **Response (`200 OK`):** `Site changed successfully`
- **Response (`409 Conflict`):** `That site is taken by another site manager`

---

### 5. Daily Reports (`/api/daily-reports`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/daily-reports/save` | Save a new report (safe validation applied) |
| `GET` | `/api/daily-reports/all` | List all daily reports |
| `GET` | `/api/daily-reports/{id}` | Get daily report by UUID |
| `PUT` | `/api/daily-reports/update/{id}` | Update report content (preserves existing status) |
| `DELETE` | `/api/daily-reports/delete/{id}` | Delete daily report |
| `GET` | `/api/daily-reports/by-status?status=DRAFT` | Filter reports by status |
| `GET` | `/api/daily-reports/between?start=YYYY-MM-DD&end=YYYY-MM-DD` | Filter reports in date range |
| `PATCH` | `/api/daily-reports/{id}/status?status={status}` | Transition report workflow status |

#### Safe Saving Rules (`POST /api/daily-reports/save`):
1. **Date Validation**: Cannot submit reports for past dates (`400 Bad Request`).
2. **One Per Day**: A site manager cannot have more than one non-rejected report on the same date (`409 Conflict`).
3. **Forced Draft**: New reports are always saved in `DRAFT` status regardless of the request body (`201 Created`).

#### Status Workflow Rules (`PATCH /api/daily-reports/{id}/status`):
- `DRAFT` ➔ `SUBMITTED` or `REJECTED`
- `SUBMITTED` ➔ `APPROVED` or `REJECTED`
- `APPROVED` / `REJECTED` ➔ Terminal states (cannot be altered)

#### Demo Request (`POST /api/daily-reports/save`):
```json
{
  "reportDate": "2027-01-15",
  "status": "DRAFT",
  "workersPresent": 16,
  "summary": "Completed foundation concrete curing and inspection.",
  "project": {
    "id": "906b6d36-ebaf-4f95-9076-1c59f9f88d03"
  },
  "siteManager": {
    "id": "e8920d29-96d4-4f3a-9cb1-ffc35bc80e00"
  }
}
```
**Response (`201 Created`):**
```
Daily report saved successfully
```

#### Demo Status Transition (`PATCH /api/daily-reports/{id}/status?status=SUBMITTED`):
**Response (`200 OK`):**
```
Daily report status updated successfully
```
