# AGENTS.md - inventory-middle

Focused guidance for AI agents working in this repository. Only includes high-signal facts that differ from defaults or are easy to miss.

---

## Architecture Overview

**6-module DDD-ish monorepo with clean layering:**
- `inventory-middle-starter` — Spring Boot entry point (`ProviderApplication`), runs on port 8081
- `inventory-middle-interfaces` — Controllers, DTOs, API contracts
- `inventory-middle-application` — Use cases, orchestration, service beans
- `inventory-middle-domain` — Business logic, entities, repositories, specifications, validators
- `inventory-middle-infra` — Persistence (MyBatis+), external clients (OpenFeign), infrastructure adapters
- `inventory-middle-client` — SDK/consumer client (separate artifact)

**Dependency flow:** starter → interfaces/application → domain → infra  
**Key: domain has NO Spring dependencies** (plain business logic layer).

---

## Build & Test

### Requirements
- **Java:** 1.8 target (but running on Java 21) — Lombok + Java 21 has compatibility issues
  - If build fails with `LombokProcessor` accessing `JavacProcessingEnvironment`: Update Lombok in pom or use Java 8
- **Maven:** 3.3+

### Build Commands
```bash
# Full build (skips tests due to missing DB/Redis/RocketMQ locally)
mvn clean install -DskipTests

# Build single module
mvn clean install -DskipTests -pl inventory-middle-domain

# Compile + check (no install)
mvn clean compile

# Tests (requires Nacos, MySQL, Redis, RocketMQ running)
mvn clean test
```

### No automated tests in repo currently
- No JUnit 5 tests, no test coverage plugins configured
- Integration tests would need: MySQL (port 3306), Redis (port 6379), RocketMQ (port 9876), Nacos (as registry)

---

## Key Technologies & Quirks

| Layer | Stack | Notes |
|-------|-------|-------|
| Core | Spring Boot 2.7.7, Spring Cloud 2021.0.5 | Java 1.8 target |
| Persistence | MyBatis Plus 3.5.3, MySQL 8, Druid pool | Logic soft-delete enabled |
| Domain Objects | Lombok 1.18.24 | Java 21 compiler compatibility issue exists |
| Mapping | MapStruct 1.4.2 | Processor annotations commented out in starter/pom |
| Messaging | RocketMQ (via spring-boot-starter) | Topics/groups/consumers defined in `application.yml` |
| Distributed | Redisson 3.17.7 (Redis locks) | Optional, for concurrency |
| Clients | Spring Cloud OpenFeign 3.1.5 | Base: product-center, uniform-push (URLs in config) |
| Monitoring | KDLA framework 1.0.1-SNAPSHOT | @CatchAndLog, @StopWatchWrapper, @MdcDot, unified exception handler |

### Config Defaults (application.yml)
- **MySQL:** `root:root@localhost:3306/inventory`
- **Redis:** `localhost:6379` (no password)
- **RocketMQ:** `127.0.0.1:9876`
- **App port:** 8081 with context `/inventory`
- **Log level:** `DEBUG` for `com.inventory.middle`, `INFO` for MyBatis Plus

---

## Module-Specific Gotchas

### inventory-middle-domain
- **Structure:** `common/`, `model/` (entities), `factory/`, `repository/` (interfaces), `service/`, `specification/`, `validator/`, `handles/`, `helper/`, `plan/`
- **Rule:** No Spring imports — domain is framework-agnostic business logic
- **Mappings:** Some mapping code may be generated; check if MapStruct is working before assuming hand-written

### inventory-middle-starter
- **Main class:** `com.inventory.middle.starter.ProviderApplication`
- **Scan packages:** Explicitly scans `top.kdla.framework` and `com.inventory.middle` (not package-root auto-scan)
- **Features:** `@EnableFeignClients`, `@EnableScheduling` enabled
- **MapStruct compile:** Annotation processor is commented out (line 64-80) — may be inactive

### inventory-middle-interfaces
- **Role:** Controllers + API DTOs
- **Recent work:** BFF migration with `@CatchAndLog` decorators on all controllers (see git history)

### inventory-middle-infra
- **Persistence:** Entity classes live here (`com.inventory.middle.infra.persistence.entity`), NOT in domain
- **MyBatis:** Mapper XMLs at `classpath*:/mapper/**/*.xml`

---

## Git & Commit Convention

**Commits follow Conventional Commits:**
```
feat(module/aspect): description (ticket ref)
fix(module/aspect): description (ticket ref)
docs(path): description
```

**Example (recent actual commits):**
```
feat(application/interfaces): implement checkMaterialDoc; wire checkParam in MaterialDocController (R6+R10)
fix(config/interfaces): fix framework config keys and stub responses
```

**Ref format:** Single letters like `R1`, `R2`, `F2-F7`, `H1+H2+M1+M3+M4` encode tracked tasks.

---

## Configuration & Injection

### External Service URLs (environment-driven)
```yaml
remote:
  product:
    center:
      url: ${PRODUCT_CENTER_URL:http://localhost:8082/product}
  uniformPush:
    url: ${UNIFORM_PUSH_URL:}
```

Set these via env vars or `application-dev.yml` overrides.

### KDLA Framework (top.kdla.framework)
- Auto-enabled decorators: `@CatchAndLog`, `@StopWatchWrapper`, `@MdcDot`
- Unified exception handling: `BizException` → warn, `SysException` → error
- MDC/TraceId: Injected via `@MdcDot(bizCode=...)` or framework interceptor (see `LogInterceptor` registration in starter)

### RocketMQ Consumers
Multiple topics/consumers defined in config (inventory-change, sn-material-doc-in, plan-demand, etc.).  
When adding new message handlers, register `@RocketMQMessageListener` with topic/group/tag from `application.yml`.

---

## What NOT to Do

1. **Don't put Spring annotations in domain layer** — it must stay plain Java
2. **Don't assume MapStruct is active** — check if annotations are commented out in starter/pom
3. **Don't run full test suite locally without infra** — MySQL/Redis/RocketMQ/Nacos are required
4. **Don't ignore the KDLA framework** — @CatchAndLog and @MdcDot are already in use; respect the convention
5. **Don't add new modules without updating parent pom.xml** `<modules>` section

---

## Common Tasks

### Add a new controller endpoint
1. Create `@RequestMapping` in `inventory-middle-interfaces/...Controller.java`
2. Add `@CatchAndLog` decorator (framework standard)
3. Inject service from `inventory-middle-application`
4. Service calls domain repository/service from `inventory-middle-domain`
5. Infra layer handles persistence via MyBatis

### Debug MQ flow
1. Check consumer config in `application.yml` (topic, group, tag)
2. Find `@RocketMQMessageListener` handler (usually in application or domain)
3. Trace `BizException` in logs (logged as WARN via KDLA handler)

### Check why build fails
1. **Lombok + Java 21:** If `LombokProcessor` error → use Java 8 or update Lombok
2. **MapStruct:** If generated mappers missing → uncomment processor lines in starter/pom
3. **MyBatis XMLs:** If mapper not found → ensure XMLs are in `src/main/resources/mapper/**/*.xml`

---

## Useful Files to Read First

- `pom.xml` — Dependency versions, module list, compiler settings
- `inventory-middle-starter/src/main/resources/application.yml` — All config: DB, Redis, RocketMQ, KDLA settings
- `inventory-middle-starter/src/main/java/.../ProviderApplication.java` — Entry point, feature flags
- Recent git log — Shows actual module/feature structure and commit patterns

---

## Avoid These Mistakes

**Surface assumptions.** The KDLA framework, RocketMQ routing, and soft-delete logic are non-obvious. Ask if uncertain.

**Don't hide config issues.** Missing or wrong topic names in RocketMQ config will silently fail at runtime.

**Don't refactor MapStruct mappings without testing.** If processor is inactive, hand-edited mappers may fail silently.

---

*Last updated: 2026-06-17*  
*See also: `/Users/kangll13/aiot/java-code/self/inventory-middle/CLAUDE.md` for skill/workflow guidance.*
