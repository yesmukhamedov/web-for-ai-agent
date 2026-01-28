# AI-First Government Services Directory

This demo proves an “AI-first website” architecture: a machine-readable knowledge layer is primary, and the human UI is a thin client over the same service layer.

## Requirements
- Java 17+
- Maven 3.9+

## Run the app
```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

## Human UI
- Directory: `http://localhost:8080/`
- Service detail: `http://localhost:8080/services/{slug}`

## Admin UI (HTTP Basic)
- URL: `http://localhost:8080/admin/services`
- Username: `admin`
- Password: `admin` (TODO: replace in production)

## API
Base path: `/api/v1`

### Swagger UI
`http://localhost:8080/swagger-ui.html`

### JSON-LD example
```bash
curl -H "Accept: application/ld+json" \
  http://localhost:8080/api/v1/services/slug/business-license
```

### Example JSON
```bash
curl http://localhost:8080/api/v1/services?page=0&size=10
```

### Admin API (basic auth)
```bash
curl -u admin:admin -X POST \
  -H "Content-Type: application/json" \
  http://localhost:8080/api/v1/admin/services \
  -d '{
    "slug": "parking-permit",
    "title": "Residential Parking Permit",
    "summary": "Permit for overnight residential parking.",
    "description": "Allows residents to park overnight in designated zones.",
    "authorityName": "City Parking Authority",
    "status": "ACTIVE",
    "validFrom": "2024-01-01",
    "validTo": null,
    "requirements": [],
    "documents": [],
    "fees": [],
    "timeframes": [],
    "legalSources": []
  }'
```
