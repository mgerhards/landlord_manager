@echo off
setlocal enabledelayedexpansion
REM Enable strict error checking
set ERRLEV=0

REM Run docker compose for dev environment
call docker compose -f docker-compose.dev.yml up --build

REM Check for errors
if %ERRORLEVEL% NEQ 0 (
    echo Docker Compose failed with exit code %ERRORLEVEL%.
    exit /b %ERRORLEVEL%
)
