# Landlord Manager - GitHub Copilot Instructions

Landlord Manager is a multi-service property management application built with Spring Boot (backend), React+Vite (frontend), Python FastAPI (data visualization), and MariaDB (database). The application manages properties, tenants, contracts, and maintenance tickets with OAuth2 authentication.

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

### Bootstrap and Build the Application
**CRITICAL**: Use native builds, NOT Docker for development. Docker Compose fails due to SSL certificate issues with Maven.

1. **Start the database** (required for backend):
   ```bash
   docker compose -f docker-compose.dev.yml up db -d
   ```
   - Takes <1 second, database ready immediately
   - Database runs on localhost:3306 with credentials: root/root
   - Database name: landlord

2. **Build and test backend**:
   ```bash
   cd backend
   ./mvnw clean compile    # Takes 5 seconds, NEVER CANCEL
   ./mvnw test            # Takes 12 seconds, NEVER CANCEL. Set timeout to 30+ seconds
   ```
   - First-time dependency download can take 2-3 minutes, subsequent builds are 5 seconds
   - Tests use H2 in-memory database, not MariaDB
   - Requires Java 17, Maven wrapper included

3. **Build and test frontend**:
   ```bash
   cd react_frontend
   npm install            # Takes 21 seconds, NEVER CANCEL. Set timeout to 60+ seconds  
   npm run build          # Takes 2 seconds
   npm run lint           # Takes 1 second, has warnings (unused React imports) but builds succeed
   ```
   - If build fails with Rollup error, run: `rm -rf package-lock.json node_modules && npm install`
   - Requires Node.js 18+, uses Vite build system

4. **Setup Python service**:
   ```bash
   cd data_viz_service
   pip3 install -r requirements.txt    # Takes <5 seconds
   ```

### Run the Application Stack
**NEVER CANCEL any of these startup commands. Set timeouts to 60+ seconds.**

1. **Start database** (if not running):
   ```bash
   docker compose -f docker-compose.dev.yml up db -d
   ```

2. **Start backend** (in separate terminal):
   ```bash
   cd backend
   ./mvnw spring-boot:run    # Takes 6 seconds to start, NEVER CANCEL
   ```
   - Backend runs on http://localhost:8080
   - Data initializer creates test data automatically
   - All endpoints require authentication except actuator

3. **Start frontend** (in separate terminal):
   ```bash
   cd react_frontend  
   npm run dev              # Takes 0.3 seconds, NEVER CANCEL
   ```
   - Frontend runs on http://localhost:4173
   - Hot reload enabled for development

4. **Start Python service** (optional, in separate terminal):
   ```bash
   cd data_viz_service
   uvicorn main:app --reload    # Takes <5 seconds, NEVER CANCEL
   ```
   - Python service runs on http://localhost:8000
   - Has public and secured endpoints

## Validation

### ALWAYS Test These User Scenarios After Making Changes
**MANUAL VALIDATION REQUIREMENT**: You MUST test actual functionality by running through complete user scenarios. Simply starting and stopping the application is NOT sufficient validation.

1. **Login Flow Validation**:
   - Navigate to http://localhost:4173
   - Login with: `info@gendis.de` / `password` (landlord account)
   - Alternative: `jane.smith@example.com` / `password` (tenant account)
   - Verify dashboard loads correctly
   - **Expected result**: User sees main dashboard with navigation menu

2. **Ticket Management Workflow**:
   - Click "Tickets" in the navigation menu
   - Click "Create Ticket" button
   - Verify form loads with populated tenant/property dropdowns
   - Fill out description, select tenant and property  
   - **Expected result**: Form shows test data (Jane Smith, Michael Brown tenants)

3. **Navigation Test**:
   - Test all menu items: Properties (Immobilien), Tenants (Mieter), Companies (Firmen), Tickets
   - **Expected result**: All pages load without JavaScript errors

4. **API Health Check**:
   ```bash
   # Backend health (requires authentication, should return 401)
   curl -i http://localhost:8080/actuator/health
   
   # Python service public endpoint
   curl http://localhost:8000/public-endpoint
   ```

### Build Validation Steps
Run these validation steps before committing any changes:

1. **Backend validation**:
   ```bash
   cd backend
   ./mvnw clean compile test    # Must complete without errors
   ```

2. **Frontend validation**:
   ```bash
   cd react_frontend
   npm run build               # Must complete successfully
   npm run lint               # Will show warnings but should not fail
   ```

## Common Tasks

### Development Workflow
- **ALWAYS** build and exercise your changes with the full application stack running
- **ALWAYS** test the login flow and at least one user scenario after changes
- Changes to backend models may require database reset: `docker compose -f docker-compose.dev.yml down -v && docker compose -f docker-compose.dev.yml up db -d`

### Troubleshooting

**Backend won't start**:
- Ensure database is running: `docker ps` should show `landlord_manager-db-1`
- Check database connectivity: Database must be accessible on localhost:3306
- Verify JWT secret is configured in `application.properties` and test `application.properties`

**Frontend build fails with Rollup error**:
```bash
cd react_frontend
rm -rf package-lock.json node_modules
npm install
```

**Docker Compose SSL errors**:
- Do NOT use `docker compose -f docker-compose.dev.yml up --build` (fails with SSL cert issues)
- Use native builds instead as documented above

**Authentication errors**:
- Data initializer is enabled by default and creates test accounts
- If no test data, check `propadmin.data-initializer.enabled=true` in application.properties

## Critical Timing Expectations

**NEVER CANCEL builds or long-running commands. Always set appropriate timeouts:**

- **Database startup**: <1 second
- **Backend first compile**: 2-3 minutes (dependency download), subsequent: 5 seconds  
- **Backend tests**: 12 seconds - Set timeout to 30+ seconds
- **Backend startup**: 6 seconds - Set timeout to 60+ seconds  
- **Frontend npm install**: 21 seconds - Set timeout to 60+ seconds
- **Frontend build**: 2 seconds
- **Frontend dev server**: 0.3 seconds - Set timeout to 30+ seconds
- **Python service install**: <5 seconds
- **Python service startup**: <5 seconds - Set timeout to 30+ seconds

## Repository Structure

```
landlord_manager/
├── backend/                    # Spring Boot application
│   ├── src/main/java/de/propadmin/rentalmanager/
│   ├── mvnw, mvnw.cmd         # Maven wrapper
│   └── pom.xml                # Maven configuration
├── react_frontend/             # React + Vite application  
│   ├── src/                   # React components
│   ├── package.json           # Node.js dependencies
│   └── vite.config.js         # Vite configuration
├── data_viz_service/           # Python FastAPI service
│   ├── main.py                # FastAPI application
│   ├── requirements.txt       # Python dependencies
│   └── oauth.py               # OAuth2 integration
├── docker-compose.dev.yml      # Development database only
└── start_dev_env.sh/.bat       # DO NOT USE (Docker build fails)
```

## Default Test Accounts

The data initializer creates these test accounts:
- **Landlord**: `info@gendis.de` / `password`
- **Tenant**: `jane.smith@example.com` / `password` 
- **Tenant**: `michael.brown@example.com` / `password`

## API Endpoints

- **Backend**: http://localhost:8080 (Spring Boot REST API, requires authentication)
- **Frontend**: http://localhost:4173 (React development server)  
- **Python Service**: http://localhost:8000 (FastAPI with public and secured endpoints)
- **Database**: localhost:3306 (MariaDB, root/root, database: landlord)

## Key Technologies
- **Backend**: Spring Boot 3.3.5, Java 17, Maven, MariaDB, OAuth2, JPA/Hibernate
- **Frontend**: React 18, Vite 5, AdminLTE UI framework, Bootstrap 5
- **Database**: MariaDB 11 (production), H2 (testing)
- **Python Service**: FastAPI, Uvicorn, OAuth2 integration
- **Build Tools**: Maven wrapper (backend), npm (frontend), pip (Python)