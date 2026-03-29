# Plan: Option B – JWT-Implementierung reparieren & absichern

Die bestehende JWT-Lösung wird in **6 zusammenhängenden Schritten** repariert:
Rollen-System im Backend einführen, JWT-Claims vervollständigen, Secret absichern,
Python-Service auf lokale Validierung umstellen und das Frontend um Expiry-Prüfung erweitern.

---

## Status

| Schritt | Beschreibung | Status |
|---------|-------------|--------|
| 1 | Rollen-Enum und `role`-Feld in `UserAccount` einführen | ✅ Erledigt |
| 2 | `AppUserDetailsDTO` und `DataInitializer` anpassen | ✅ Erledigt |
| 3 | JWT-Claims erweitern: `role` und `email` ins Token schreiben | ✅ Erledigt |
| 4 | JWT-Secret aus Umgebungsvariable, Logging bereinigen | ✅ Erledigt |
| 5 | Python-Service: lokale JWT-Validierung mit `python-jose` | ⬜ Offen |
| 6 | Frontend: Token-Expiry clientseitig prüfen & `role` auslesen | ✅ Erledigt |

---

## Schritt 1 – Rollen-Enum und `role`-Feld in `UserAccount` einführen ✅

**Dateien:** `models/enums/UserRole.java` (neu), `models/UserAccount.java`

Neues Enum `UserRole` mit drei Werten: `LANDLORD`, `TENANT`, `CRAFTSMAN` im Paket
`de.propadmin.rentalmanager.models.enums`. In `UserAccount` ein `@Enumerated(EnumType.STRING)`-Feld
`role` vom Typ `UserRole` hinzufügen. Hibernate aktualisiert das Schema automatisch via `ddl-auto=update`.

### Änderungen
- **`UserRole.java`** (neu): Enum mit `LANDLORD("Vermieter")`, `TENANT("Mieter")`, `CRAFTSMAN("Handwerker")`,
  `@Getter` von Lombok, konsistent mit `TradeType`.
- **`UserAccount.java`**: Import `UserRole`, neues Feld `@Enumerated(EnumType.STRING) private UserRole role`.
- **`DataInitializer.java`**: Import `UserRole`, alle 5 UserAccounts erhalten beim Anlegen die passende Rolle:
  - `info@gendis.de` → `UserRole.LANDLORD`
  - `jane.smith@example.com` → `UserRole.TENANT`
  - `michael.brown@example.com` → `UserRole.TENANT`
  - `hans.roehrich@roehrich-gmbh.de` → `UserRole.CRAFTSMAN`
  - `anna.mueller@roehrich-gmbh.de` → `UserRole.CRAFTSMAN`

> **Hinweis:** Da `AppUser`-Tabelle eine neue Spalte bekommt, muss bei vorhandenen Testdaten
> die Datenbank einmal zurückgesetzt werden:
> ```powershell
> docker compose -f docker-compose.dev.yml down -v
> docker compose -f docker-compose.dev.yml up db -d
> ```

---

## Schritt 2 – `AppUserDetailsDTO` und `DataInitializer` anpassen ✅

**Dateien:** `dto/AppUserDetailsDTO.java`, `DataInitializer.java`

`getAuthorities()` reparieren: Rolle aus `UserAccount.role` als
`SimpleGrantedAuthority("ROLE_LANDLORD")` zurückgeben. `DataInitializer` bei allen
`UserAccount`-Erstellungen die passende Rolle setzen.

### Änderungen
- **`AppUserDetailsDTO.java`**:
  - Imports `SimpleGrantedAuthority` und `List` ergänzt.
  - `getAuthorities()` gibt jetzt `List.of(new SimpleGrantedAuthority("ROLE_" + userAccount.getRole().name()))` zurück.
  - Null-sicher: falls `role == null` (alte DB-Einträge), wird `Collections.emptyList()` zurückgegeben.
- **`DataInitializer.java`**: Rollen bereits in Schritt 1 gesetzt – keine weiteren Änderungen nötig.

### Effekt
Ab jetzt enthält jeder generierte JWT den `authorities`-Claim mit dem richtigen Wert
(z. B. `["ROLE_LANDLORD"]`), da `JwtService.generateToken()` die Authorities aus
`authentication.getAuthorities()` liest – und diese nun erstmals befüllt sind.

---

## Schritt 3 – JWT-Claims erweitern: `role` und `email` ins Token ✅

**Datei:** `service/JwtService.java`

`generateToken()` um zwei weitere Claims ergänzen: `role` (aus dem `UserAccount`) und
`email`. Das `subject` nutzt bereits die E-Mail – kein Bruch.

### Änderungen
- **`JwtService.java`**:
  - `getUserRole()` (neu): liest `userAccount.getRole().name()` aus `AppUserDetailsDTO`.
  - `getUserEmail()` (neu): liest `userAccount.getEmail()`.
  - `getUserId()` vereinfacht: direktes Pattern Matching auf `AppUserDetailsDTO`, `UserDetails`-Zwischenschritt entfernt.
  - `generateToken()`: setzt `role`- und `email`-Claim wenn nicht `null`.
  - Ungenutzter `UserDetails`-Import entfernt.

### Ergebnis-JWT (Beispiel `info@gendis.de`)
```json
{
  "iss": "landlord-manager",
  "sub": "info@gendis.de",
  "exp": 1234567890,
  "authorities": ["ROLE_LANDLORD"],
  "userId": 1,
  "role": "LANDLORD",
  "email": "info@gendis.de"
}
```

---

## Schritt 4 – JWT-Secret aus Umgebungsvariable, Logging bereinigen ✅

**Dateien:** `src/main/resources/application.properties`,
`src/test/resources/application.properties`

### Änderungen
- **`application.properties`** (main):
  - `jwt.secret=${JWT_SECRET:changeme-replace-in-production}` – liest aus Umgebungsvariable,
    Fallback für lokale Entwicklung.
  - Alle 7 Logging-Zeilen von `DEBUG`/`TRACE` auf `WARN` gesetzt – keine Token-Inhalte
    oder Passwörter mehr in den Logs.
- **`application.properties`** (test): **unverändert** – `jwt.secret=myTestSecretKey...`
  bleibt explizit hartcodiert, damit Tests ohne Umgebungsvariable deterministisch laufen.

### Lokale Entwicklung
```powershell
$env:JWT_SECRET = "mein-sicheres-lokales-secret-mit-mindestens-32-zeichen"
cd backend ; .\mvnw spring-boot:run
```

---

## Schritt 5 – Python-Service: lokale JWT-Validierung mit `python-jose` ⬜

**Dateien:** `data_viz_service/main.py`, `data_viz_service/oauth.py`

`python-jose` ist bereits in `requirements.txt` eingetragen. Der Service ruft aktuell
`/oauth2/check_token` und `/oauth2/token` auf – diese Endpunkte existieren im Backend nicht.

### Geplante Änderungen
- **`oauth.py`**: Token lokal mit `jose.jwt.decode()` + shared secret (aus Umgebungsvariable
  `JWT_SECRET`) und Algorithmus `HS256` validieren – kein HTTP-Call mehr nötig.
  - `tokenUrl` in `OAuth2PasswordBearer` auf `http://localhost:8080/api/auth/login` korrigieren.
  - `validate_token_with_spring()` ersetzen durch `validate_token_locally()`.
  - Claims `sub` (E-Mail) und `role` aus dem dekodierten Token extrahieren.
- **`main.py`**: Import auf neue `oauth.py`-Funktionen umstellen, toten Code entfernen.

### Beispiel (Zielzustand `oauth.py`)
```python
import os
from jose import jwt, JWTError
from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer

JWT_SECRET = os.getenv("JWT_SECRET", "changeme-replace-in-production")
ALGORITHM = "HS256"

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="http://localhost:8080/api/auth/login")

def get_current_user(token: str = Depends(oauth2_scheme)):
    try:
        payload = jwt.decode(token, JWT_SECRET, algorithms=[ALGORITHM])
        email: str = payload.get("sub")
        role: str = payload.get("role")
        if email is None:
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid token")
        return {"email": email, "role": role}
    except JWTError:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid token")
```

---

## Schritt 6 – Frontend: Token-Expiry prüfen & `role` auslesen ✅

**Datei:** `react_frontend/src/config/auth.js`

### Änderungen
- **`isAuthenticated()`** erweitert:
  - `jwtDecode(token)` → `exp`-Claim in Sekunden gegen `Date.now() / 1000` prüfen.
  - Token abgelaufen → `removeToken()` + `return false`.
  - Token nicht dekodierbar (manipuliert/kaputt) → `removeToken()` + `return false`.
- **`getRoleFromToken()`** (neu): analog zu `getUserIdFromToken()`, liest `decoded.role`
  aus dem JWT. Gibt z. B. `"LANDLORD"`, `"TENANT"`, `"CRAFTSMAN"` zurück oder `null`.

### Effekt
Abgelaufene Tokens werden bereits beim nächsten Seitenaufruf oder React-Render-Zyklus
clientseitig erkannt und entfernt – ohne dass erst ein `401` vom Backend kommen muss.

---

## Weitere Überlegungen

### Datenbankreset (einmalig nach Schritt 1 nötig)
```powershell
docker compose -f docker-compose.dev.yml down -v
docker compose -f docker-compose.dev.yml up db -d
```

### Rolle-basierte Endpoint-Absicherung (optional, Folgeschritt)
Mit den nun korrekt befüllten Authorities könnten in `WebSecurityConfig.java` gezielt
Endpoints per `hasRole("LANDLORD")` abgesichert werden:
```java
.requestMatchers("/api/tenants/**").hasRole("LANDLORD")
.requestMatchers("/api/tickets/**").hasAnyRole("LANDLORD", "TENANT")
```

### Token-Storage (langfristig)
Für Produktionsumgebungen sollte der Token in einem `httpOnly`-Cookie gespeichert werden
statt in `localStorage`, um XSS-Angriffe zu erschweren.

### Architektur-Hinweis
Das System ist kein „OAuth2" im klassischen Sinne (kein Authorization Server, kein
Authorization Code Flow) – es ist ein **custom JWT-Auth-System** auf Basis des
Spring OAuth2 Resource Servers. Das ist legitim für eine interne Anwendung, sollte
aber so dokumentiert bleiben.
