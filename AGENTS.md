# AGENTS.md - inventory-middle

## Architecture

**6-module Maven monorepo:**
- `inventory-middle-starter` — Spring Boot entry (`ProviderApplication`), port 8081, context `/inventory`
- `inventory-middle-interfaces` — Controllers (`web/`), facades (`facade/`), consumers (`consumer/`), scheduled tasks (`schedule/` and `task/`)
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

**No tests exist.** Zero `@Test` annotations in repo. Running `mvn test` will pass vacuously but needs MySQL/Redis/RocketMQ for any real integration test.

## Key Technologies

| Stack | Version | Notes |
|-------|---------|-------|
| Spring Boot | 2.7.7 | Java 1.8 source/target |
| Spring Cloud | 2021.0.5 | OpenFeign 3.1.5 |
| MyBatis Plus | 3.5.3 | Soft-delete: `deleted` field (0/1) |
| Lombok | 1.18.30 | Client module: 1.18.16 |
| MapStruct | 1.4.2 | **Processor commented out** in starter/pom (lines 64-80) |
| RocketMQ | 2.2.3 | Topics/groups in `application.yml` |
| Redisson | 3.17.7 | Distributed locks (`RedissonDistributedLockService` available) |
| KDLA framework | 1.0.1-SNAPSHOT | `top.kdla.framework` — decorators, exceptions, DTOs |

## KDLA Framework Conventions

- **`@CatchAndLog`** — on every controller and facade. New endpoints must have it.
- **`@StopWatchWrapper`** — method timing on key application services.
- **`@MdcDot(bizCode=...)`** — trace ID injection on MQ consumers.
- **`SingleResponse`** (`top.kdla.framework.dto.SingleResponse`) — standard API response wrapper:
  - `SingleResponse.buildSuccess(data)` / `SingleResponse.buildFailure(code, msg)` / `SingleResponse.of(data)`
- **`BizException`** / **`SysException`** — KDLA exception hierarchy. `BusinessException` extends `BizException` (domain layer).
- **`SequenceNoGenerator`** — KDLA distributed sequence, delegated via `SequenceNoHelperImpl` (infra). Rules in `code_generator_cfg` DB table.
- **KDLA config** in `application.yml` under `kdla:` key — exception handler, catch-log, stop-watch, mdc all enabled.

## Persistence

- **`BasePO`** (`infra.persistence.entity`): `creatorId`, `createTime`, `updatorId` (intentional typo in column name!), `updateTime`, `deleted(int)`
- Plan PO classes use two patterns: inheriting BasePO (`creator_id`/`updator_id`/`deleted`) or standalone (`create_user_id`/`update_user_id`/`is_delete`)
- **Mapper XMLs** live in `src/main/resources/mapper/**/*.xml` — this is the loaded path (`classpath*:/mapper/**/*.xml`)
- **`/mybatis/mapper/`** is a dead directory: 25 XML files sit in `inventory-middle-infra/src/main/resources/mybatis/mapper/` and are **never loaded** by MyBatis (Q5 known bug)
- Type aliases: `com.inventory.middle.infra.persistence.entity`

## Config Defaults (application.yml)

- MySQL: `root:root@localhost:3306/inventory`
- Redis: `localhost:6379` (no password)
- RocketMQ: `127.0.0.1:9876`
- External URLs: `${PRODUCT_CENTER_URL:http://localhost:8082/product}`, others default to `""`
- Plan cron jobs: `plan.job.*.cron` keys wired. **6 `@Scheduled` task classes exist** in `interfaces/schedule/` and `interfaces/task/` — but 4 plan jobs are stubs and none have distributed locks (N5/N6 known issues)

## Known Active Bugs (do not introduce workarounds — fix the root cause)

These are tracked in `docs/superpowers/plans/remaining-todos.md`. Do not paper over them:

- **Q1** 🚨 `domain.service.external.MaterialDocOutboundService` has no implementation → Spring injection fails for `InventoryAdjustDecHandle`. There is a same-named class in `domain.service.outbound` (different package) — do not confuse them.
- **Q2** 🚨 `InventoryChangeMqConsumer` deserializes into `domain.model.bo.mq.sub.InventoryChangeMessage` (9 fields) but the producer sends `infra.produce.model.InventoryChangeMessage` (15 fields). `adjustQuantity` and `price` are silently null → moving-average calculation corrupted.
- **Q3** 🚨 `UserContextHolder.get()` used without null-check at 8 locations (PlanConfigController, PlanReportController, PlanOrderController). Use `UserContextHolder.getTenantId()` / `.getUserId()` which have null guards.
- **Q5** ⚠️ 25 XML files in `/mybatis/mapper/` are **never loaded**. Active XMLs are in `/mapper/`. Do not add new XMLs to `/mybatis/mapper/`.
- **N3** 🚨 `rocketmq.consumer.monitor-rule.topic` is hardcoded `inventory-monitor-topic` but the real producer uses `stock-inventory-center-topic` → MonitorRuleConsumer is permanently idle.
- **Two `InventoryChangeMessage` classes** (Q6): `domain.model.bo.mq.sub` (9 fields) vs `infra.produce.model` (15 fields). Always import the correct one for context.

## Scheduled Tasks

- 6 classes total: `MonitorAnnualInspectionJob` (`task/`), `DemandPlanDetailGenerateJob`, `PlanGenerateJob`, `PlanDemandSupplyStockGenerateJob`, `PlanRedisOperateJob`, `PlanOrderOverdueCheckJob` (all in `schedule/`)
- **None have distributed locks** — multi-instance deployment will double-execute. Redisson is available; use `RedissonDistributedLockService` when implementing.
- 4 plan jobs (`PlanGenerateJob`, `PlanDemandSupplyStockGenerateJob`, `PlanRedisOperateJob`, `PlanOrderOverdueCheckJob`) are stub/log-only bodies.

## What NOT to Do

1. **Don't assume MapStruct generates code** — processor is commented out; existing `@Mapper` classes rely on hand-written impls
2. **Don't run `mvn test` expecting test execution** — no tests exist
3. **Don't use Java 21 for build** — Lombok 1.18.30 + Java 21 = `LombokProcessor` crash; use Java 8
4. **Don't add new XMLs to `/mybatis/mapper/`** — that directory is never scanned; use `/mapper/` instead
5. **Don't add modules without updating parent `pom.xml` `<modules>`**
6. **Don't put Spring annotations in domain layer** — domain must stay framework-agnostic (currently has 145 Spring imports which are existing debt, not a pattern to copy)
7. **Don't use `plan.plan.` double-nested package paths** — always `plan.` single level
8. **Don't trust request-body fields** for `tenantId`, `userId`, `operatorId`, `creatorId`, `updatorId`, `sourceSystem` — these must be overwritten from `UserContextHolder` in the controller layer
9. **Don't inject infra layer beans into controllers** — interfaces → application → domain/infra only

## Git Convention

```
feat(module/aspect): description (ticket ref)
fix(module/aspect): description (ticket ref)
```

Refs like `R1`, `F2-F7`, `M1+M3`, `Q1`, `N3` encode tracked migration tasks from `remaining-todos.md`.

## Key Files

- `pom.xml` — dependency versions, module list
- `inventory-middle-starter/src/main/resources/application.yml` — all config (DB, Redis, MQ, KDLA, plan cron)
- `inventory-middle-starter/src/main/java/.../ProviderApplication.java` — entry point
- `docs/sql/inventory.sql` — inventory DDL; `docs/sql/plan.sql` — plan DDL (13 tables missing DDL — N4 known issue)
- `docs/superpowers/plans/remaining-todos.md` — **authoritative backlog** of migration gaps (41 items, G1–G21 groups)

*Last updated: 2026-06-26*
