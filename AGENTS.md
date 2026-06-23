# AGENTS.md - inventory-middle

## Architecture

**6-module Maven monorepo:**
- `inventory-middle-starter` — Spring Boot entry (`ProviderApplication`), port 8081, context `/inventory`
- `inventory-middle-interfaces` — Controllers (`web/`), facades (`facade/`), consumers (`consumer/`), scheduled tasks (`task/`)
- `inventory-middle-application` — Application services, orchestration, DTO convertors
- `inventory-middle-domain` — Business logic, entities, repositories, validators, specifications
- `inventory-middle-infra` — Persistence (MyBatis Plus), external clients (OpenFeign), MQ producers, Redis locks
- `inventory-middle-client` — SDK/consumer client (separate artifact, Lombok 1.18.16)

**Dependency flow:** starter → interfaces/application → domain ← infra  
**Plan subpackage** (`plan/`) exists in domain, application, interfaces, and infra — migrated from `scm-plan-management`.

**ProviderApplication** scans `top.kdla.framework` + `com.inventory.middle` (explicit, not auto-scan).

## Build

```bash
# MUST use Java 8 — Lombok 1.18.30 breaks on Java 21
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home \
  mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository

# Single module
mvn clean compile -DskipTests -pl inventory-middle-domain

# Full install
mvn clean install -DskipTests
```

**No tests exist.** Zero `@Test` annotations in repo. Running `mvn test` will pass vacuously but needs MySQL/Redis/RocketMQ/Nacos for any real integration test.

## Key Technologies

| Stack | Version | Notes |
|-------|---------|-------|
| Spring Boot | 2.7.7 | Java 1.8 source/target |
| Spring Cloud | 2021.0.5 | OpenFeign 3.1.5 |
| MyBatis Plus | 3.5.3 | Soft-delete: `deleted` field (0/1) |
| Lombok | 1.18.30 | Client module: 1.18.16 |
| MapStruct | 1.4.2 | **Processor commented out** in starter/pom (lines 64-80) |
| RocketMQ | 2.2.3 | Topics/groups in `application.yml` |
| Redisson | 3.17.7 | Distributed locks |
| KDLA framework | 1.0.1-SNAPSHOT | `top.kdla.framework` — decorators, exceptions, DTOs |

## KDLA Framework Conventions

- **`@CatchAndLog`** — on every controller and facade (67 classes). Method entry/exit logging + exception handling.
- **`@StopWatchWrapper`** — method timing on key application services.
- **`@MdcDot(bizCode=...)`** — trace ID injection on all 7 MQ consumers.
- **`SingleResponse`** (`top.kdla.framework.dto.SingleResponse`) — standard API response wrapper:
  - `SingleResponse.buildSuccess(data)` / `SingleResponse.buildFailure(code, msg)` / `SingleResponse.of(data)`
- **`BizException`** / **`SysException`** — KDLA exception hierarchy. `BusinessException` extends `BizException` (domain layer).
- **`SequenceNoGenerator`** — KDLA distributed sequence, delegated via `SequenceNoHelperImpl` (infra). Rules in `code_generator_cfg` DB table.
- **KDLA config** in `application.yml` under `kdla:` key — exception handler, catch-log, stop-watch, mdc all enabled.

## Persistence

- **`BasePO`** (`infra.persistence.entity`): `creatorId`, `createTime`, `updatorId` (typo!), `updateTime`, `deleted(int)`
- Plan PO classes use two patterns: inheriting BasePO (`creator_id`/`updator_id`/`deleted`) or standalone (`create_user_id`/`update_user_id`/`is_delete`)
- MyBatis mapper XMLs: `src/main/resources/mapper/**/*.xml`
- Type aliases: `com.inventory.middle.infra.persistence.entity`

## Config Defaults (application.yml)

- MySQL: `root:root@localhost:3306/inventory`
- Redis: `localhost:6379` (no password)
- RocketMQ: `127.0.0.1:9876`
- External URLs: `${PRODUCT_CENTER_URL:http://localhost:8082/product}`, `${UNIFORM_PUSH_URL:}`
- Plan cron jobs configured (`plan.job.*.cron`) but **no @Scheduled classes exist yet** — gap from migration

## Git Convention

```
feat(module/aspect): description (ticket ref)
fix(module/aspect): description (ticket ref)
```

Refs like `R1`, `F2-F7`, `H1+H2+M1+M3+M4` encode tracked migration tasks.

## What NOT to Do

1. **Don't assume MapStruct generates code** — processor is commented out; existing `@Mapper` classes may rely on hand-written or previously generated impls
2. **Don't run `mvn test` expecting test execution** — no tests exist
3. **Don't use Java 21 for build** — Lombok 1.18.30 + Java 21 = `LombokProcessor` crash; use Java 8
4. **Don't ignore `@CatchAndLog`** — it's on every controller/facade; new endpoints must have it
5. **Don't add modules without updating parent `pom.xml` `<modules>`**
6. **Don't assume domain is Spring-free** — it has `@Service`, `@Component`, `@Transactional` throughout (142 Spring imports)

## Key Files

- `pom.xml` — dependency versions, module list
- `inventory-middle-starter/src/main/resources/application.yml` — all config (DB, Redis, MQ, KDLA, plan cron)
- `inventory-middle-starter/src/main/java/.../ProviderApplication.java` — entry point
- `docs/sql/init.sql` — inventory DDL; `docs/sql/plan.sql` — plan DDL (17 tables)
- `docs/superpowers/plans/` — migration plans (BFF, SCM Plan, inventory-center, remaining-todos)

*Last updated: 2026-06-22*
