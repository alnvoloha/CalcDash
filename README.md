# CalcDash 🧠⚡️

A full‑stack web app for training fast mental arithmetic: **login/registration + marathon gameplay + personal records + theory articles**.

This project is intentionally built like a “real product” (not a toy snippet): a secured backend API, a separate frontend client, persistent storage, and a clear feature roadmap.

---

## Why this project matters (for an employer)

- **Full‑stack delivery:** I built the frontend UI and the backend REST API, wired them together, and made them run as a single product.
- **Security basics done properly:** JWT authentication, protected endpoints, request filtering, and user‑scoped data.
- **Real persistence:** PostgreSQL + JPA/Hibernate (records, users, settings).
- **Clean separation of concerns:** controllers → services → repositories; frontend screens → components → API layer.
- **Production-ish habits:** validation, structured API calls, token storage, and predictable app state.

---

## Features

### ✅ Implemented

- **Auth:** registration + login with JWT, token stored on device/browser.
- **Marathon mode:**
  - start a marathon session
  - answer tasks
  - finish session and save result
  - **record (best score)** is fetched and displayed
- **Theory / Learning section:** topic list → article screen (text content comes from the backend).
- **Responsive UI:** runs in the browser via React Native Web (Expo), layout works for desktop and mobile screens.

### 🚧 In progress / next

- **Training mode** (separate from Marathon) with custom operations & difficulty scaling.
- Richer **Profile** stats (accuracy, streaks, per‑mode progress).
- Better error UX (offline, expired token, retry logic).
- Tests + CI and a simple deploy setup.

---

## Tech stack

**Frontend**

- React Native (Expo) + React Native Web
- React Navigation
- Axios (single API client, token injection)

**Backend**

- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- PostgreSQL

**Build**

- Gradle Wrapper (`./gradlew bootRun`)

---

## Architecture overview

### Backend (Spring Boot)

- `controller/` – REST endpoints (`/api/...`)
- `service/` – business logic (marathon flow, profile building, settings)
- `repository/` – JPA repositories for DB access
- `filter/` – `JwtAuthenticationFilter` parses `Authorization: Bearer <token>` and sets the SecurityContext
- `util/` – token utils, current user extraction

### Frontend (Expo / RN Web)

- `src/api/` – API client + endpoint functions
- `src/context/` – auth state and persistent token storage
- `src/screens/` – screens by feature (Marathon, Training, Learning, Profile)
- `src/components/` – reusable UI pieces (buttons, keypad, screen wrapper)

---

## API (high level)

Typical endpoints used by the client:

- `POST /api/users/register`
- `POST /api/users/login`
- `GET /api/users/profile` _(protected)_
- `GET /api/marathon/record` _(protected)_
- `GET /api/marathon/start?difficulty=...` _(protected)_
- `POST /api/marathon/answer` _(protected)_
- `POST /api/marathon/finish` _(protected)_
- `GET /api/theory/{topic}` _(public)_

---

## Getting started (local)

### Prerequisites

- **Java 17**
- **Node.js + npm**
- **PostgreSQL** running locally

### 1) Database

Create DB:

- name: `calcdash`

Configure credentials in:

- `backend/src/main/resources/application.properties`

By default it expects:

- host: `localhost`
- port: `5432`
- db: `calcdash`

### 2) Run backend

```bash
cd backend
./gradlew bootRun
```

Backend will start on:

- `http://localhost:8085`

### 3) Run frontend

```bash
cd frontend
npm install
npm start
```

Open the web build (usually):

- `http://localhost:8081`

---

## Notes on security

JWT secret is currently stored in code (`JwtUtil`). It’s fine for local dev, but one of the next improvements is moving it to environment variables and adding refresh tokens.

---

## Roadmap

- Training mode with:
  - choosing operations (+ − × ÷, etc.)
  - difficulty‑based number ranges
  - per‑mode stats and progression
- Better profile screen and saved preferences
- Tests for service layer + API smoke tests
- Docker compose for one‑command local start

---
