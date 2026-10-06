# Softonica Backend — Base44 Dev Environment

## Stack
- **Spring Boot 3.3.5** (Java 17) backend API
- **MySQL 8.0** — primary database (Flyway migrations)
- **Redis 7.0** — cache (Jedis client)
- **MailHog** — local SMTP capture for email testing (web UI on port 8025)
- **JWT auth** — secret hardcoded in `application.properties` (no external secrets needed)
- **Swagger UI** at `/swagger-ui/index.html`, OpenAPI JSON at `/v3/api-docs`

## Running
```
docker compose -f docker-compose.base44.yml up -d --build
```
The app listens on port **8087** inside the container, mapped to host port **3000** for the preview.

## Dev mode
- Source is bind-mounted; `spring-boot-devtools` is on the classpath.
- A polling file watcher recompiles on source changes; devtools then restarts the app automatically.
- Maven dependencies are cached in the `maven-cache` volume.

## Infrastructure credentials (all local dev, not secrets)
- MySQL: `admin/admin`, database `softonica`, root password `dev`
- Redis: no password
- MailHog: no auth, SMTP port 1025, web UI port 8025

## API endpoints (public, no auth)
- `POST /api/user/register` — register new user
- `POST /api/user/login` — login, returns JWT
- All other endpoints require a `Authorization: Bearer <jwt>` header

## Flyway
- Migrations in `src/main/resources/db/migration/`
- `spring.flyway.baseline-on-migrate=true` is set
- The migration filename has a trailing space: `V1__Initial_schema.sql .sql`

## Notes
- The original `docker-compose.yml` builds a production JAR image — do NOT use it for dev.
- The original `Dockerfile` is a multi-stage production build — do NOT use it for dev.
