# Ticket Creation Feature

This feature allows tenants to create tickets through the React frontend interface.

## Features Implemented

### Ticket Creation Form
- **Path**: `/tickets/create` 
- **Access**: Available via "Create Ticket" button on the tickets overview page
- **Form Fields**:
  - Description (required) - free text area for describing the issue
  - Tenant Selection (required) - dropdown populated from `/tenants` API endpoint
  - Property Selection (required) - dropdown populated from `/realEstateObjects` API endpoint

### Navigation
- Added new route `/tickets/create` to the React Router configuration
- "Create Ticket" button on the `/tickets` page navigates to the creation form
- Form has "Cancel" button that navigates back to tickets overview
- Successful creation redirects to tickets overview

### API Integration
- Uses existing backend endpoint `POST /api/tickets`
- Fetches tenant data from Spring Data REST endpoint `/tenants`
- Fetches property data from Spring Data REST endpoint `/realEstateObjects`
- Includes proper error handling and loading states

### UI/UX
- Consistent with existing AdminLTE styling
- Form validation with required field indicators
- Loading states during API calls
- Error messages displayed to user
- Responsive design

## How to Test

1. Start the development environment:
   ```bash
   # Start database
   docker compose -f docker-compose.dev.yml up db -d
   
   # Start backend
   cd backend && mvn spring-boot:run
   
   # Start frontend (in another terminal)
   cd react_frontend && npm run dev
   ```

2. Navigate to `http://localhost:5173/tickets`

3. Click "Create Ticket" button

4. Fill out the form:
   - Enter a description of the issue
   - Select a tenant from the dropdown
   - Select a property from the dropdown
   - Click "Create Ticket"

## Default Login Credentials

For testing, use these default accounts created by the DataInitializer:

- **Tenant**: `jane.smith@example.com` / `password`
- **Landlord**: `info@gendis.de` / `password`

## Code Structure

- **TicketCreate.jsx** - Main ticket creation form component
- **Overview.jsx** - Updated tickets overview with create button
- **App.jsx** - Updated routing configuration
- **api.js** - API endpoint configuration

## Backend Integration

The form integrates with these backend endpoints:

- `POST /api/tickets` - Creates new ticket
- `GET /tenants` - Fetches available tenants
- `GET /realEstateObjects` - Fetches available properties

The ticket data structure matches the backend Ticket model:
```json
{
  "description": "Issue description",
  "tenant": { "id": 1 },
  "asset": { "id": 1 }
}
```