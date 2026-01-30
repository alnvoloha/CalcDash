# CalcDash

**CalcDash** is a mobile app for training fast mental arithmetic.
It includes multiple game modes, timed challenges, and account-based progress.

## Stack
### Frontend
- React Native (Expo)
- NativeWind / Tailwind-like styling

### Backend
- Java Spring Boot
- JWT Authentication
- REST API

## Features (Implemented)
- Registration & login
- JWT-protected endpoints
- Game modes & timed tasks (core logic)
- Mobile UI structure

## Project Structure
calcdash/
frontend/ # React Native app
backend/ # Spring Boot API


## Run locally

### Backend
```bash
cd backend
./gradlew bootRun
```
### Frontend
``` bash
cd frontend
npm install
npx expo start
```
