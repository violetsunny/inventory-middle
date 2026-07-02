# AGENTS.md - inventory-middle

Focused guidance for AI agents working in this repository. Only includes high-signal facts that differ from defaults or are easy to miss.

---

## CQRS 调用路径规范（硬约束）

遵循 DDD CQRS 思想，application 层调用 domain 层时按操作类型区分：

- **Query 操作（Read）：** application service 可直接调用 domain `*Repository` 接口（无 Spring 注解，纯 Java），Repository 返回领域对象或 DTO
- **Command 操作（Write）：** application service 必须通过 domain `*DomainService`（聚合/业务逻辑封装）操作，DomainService 内部使用 Repository 完成持久化，保证业务规则、事务边界、聚合一致性

```
Query:  ApplicationService → Repository (domain interface)
Command: ApplicationService → DomainService → Repository (domain interface)
```

- **禁止** application 层直接注入 infra 层的 Mapper/DAO/Do/PO/外部 remote service
- 违反本规范的现有代码必须在后续迭代中修复

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

### Code Style: Prefer `java.time.*` over `java.util.Date`
- **All timestamp fields** in PO / BO / DTO / VO must use `java.time.LocalDateTime`, not `java.util.Date`
- MyBatis 3.5.3+ auto-maps `LocalDateTime` ↔ `TIMESTAMP`, no extra config needed
- This applies to the entire plan module and all new code

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
| Persistence | MyBatis Plus 3.5.3, MySQL 8, Druid pool | Logic soft-delete: `deleted` field (0/1) |
| Domain Objects | Lombok 1.18.30 (client: 1.18.16) | Java 21 compiler compatibility issue exists |
| Mapping | MapStruct 1.4.2 | **Processor commented out** in starter/pom (lines 64-80) — may be inactive |
| Messaging | RocketMQ 2.2.3 | Topics/groups/consumers defined in `application.yml` |
| Distributed | Redisson 3.17.7 (Redis locks) | `RedissonDistributedLockService` available |
| Clients | Spring Cloud OpenFeign 3.1.5 | Base: product-center, uniform-push (URLs in config) |
| Monitoring | KDLA framework 1.0.1-SNAPSHOT | @CatchAndLog, @StopWatchWrapper, @MdcDot, unified exception handler |

### KDLA Framework Conventions
- **`@CatchAndLog`** — on every controller and facade; new endpoints must have it
- **`@StopWatchWrapper`** — method timing on key application services
- **`@MdcDot(bizCode=...)`** — trace ID injection on MQ consumers
- **`SingleResponse`** — standard API wrapper: `.buildSuccess(data)` / `.buildFailure(code, msg)` / `.of(data)`
- **`BizException`** / **`SysException`** — KDLA exception hierarchy; `BusinessException` extends `BizException` (domain layer)
- **`SequenceNoGenerator`** — KDLA distributed sequence, delegated via `SequenceNoHelperImpl` (infra)

### Config Defaults (application.yml)
- **MySQL:** `root:root@localhost:3306/inventory`
- **Redis:** `localhost:6379` (no password)
- **RocketMQ:** `127.0.0.1:9876`
- **App port:** 8081 with context `/inventory`
- **Log level:** `DEBUG` for `com.inventory.middle`, `INFO` for MyBatis Plus
- **Plan cron jobs:** `plan.job.*.cron` keys wired (6 `@Scheduled` task classes in `interfaces/schedule/` and `interfaces/task/`)

---

## Module-Specific Gotchas

### inventory-middle-domain
- **Structure:** `common/`, `model/` (entities), `factory/`, `repository/` (interfaces), `service/`, `specification/`, `validator/`, `handles/`, `helper/`, `plan/`
- **Rule:** No Spring imports — domain is framework-agnostic business logic (currently has ~145 Spring imports which are existing debt, not a pattern to copy)
- **Mappings:** Some mapping code may be generated; check if MapStruct is working before assuming hand-written

### inventory-middle-starter
- **Main class:** `com.inventory.middle.starter.ProviderApplication`
- **Scan packages:** Explicitly scans `top.kdla.framework` and `com.inventory.middle` (not package-root auto-scan)
- **Features:** `@EnableFeignClients`, `@EnableScheduling` enabled
- **MapStruct compile:** Annotation processor is commented out (line 64-80) — may be inactive

### inventory-middle-interfaces
- **Role:** Controllers + API DTOs; consumers (`consumer/`); scheduled tasks (`schedule/` and `task/`)
- **Recent work:** BFF migration with `@CatchAndLog` decorators on all controllers (see git history)

### inventory-middle-infra
- **Persistence:** Entity classes live here (`com.inventory.middle.infra.persistence.entity`), NOT in domain
- **MyBatis:** Mapper XMLs at `classpath*:/mapper/**/*.xml`
- **`BasePO`** fields: `creatorId`, `createTime`, `updatorId` (intentional column-name typo!), `updateTime`, `deleted(int)`
- **Plan PO** classes use two patterns: inheriting BasePO (`creator_id`/`updator_id`/`deleted`) or standalone (`create_user_id`/`update_user_id`/`is_delete`)
- **Dead directory:** `inventory-middle-infra/src/main/resources/mybatis/mapper/` — 25 XML files here are **never loaded**. Active XMLs must be in `src/main/resources/mapper/**/*.xml`. Do not add new XMLs to the dead path.

### Scheduled Tasks
- 6 classes total: `MonitorAnnualInspectionJob` (`task/`), plus 5 in `schedule/`
- **None have distributed locks** — multi-instance deployment will double-execute. Redisson is available; use `RedissonDistributedLockService`.
- 4 plan jobs (`PlanGenerateJob`, `PlanDemandSupplyStockGenerateJob`, `PlanRedisOperateJob`, `PlanOrderOverdueCheckJob`) are stub/log-only bodies.

### Known Active Issues (do not paper over — fix root cause)
Tracked in `docs/superpowers/plans/remaining-todos.md`:
- 🚨 `domain.service.external.MaterialDocOutboundService` has no implementation → Spring injection fails for `InventoryAdjustDecHandle`. Same-named class exists in `domain.service.outbound` (different package) — do not confuse them.
- 🚨 `InventoryChangeMqConsumer` deserializes into `domain.model.bo.mq.sub.InventoryChangeMessage` (9 fields) but producer sends `infra.produce.model.InventoryChangeMessage` (15 fields). `adjustQuantity` and `price` are silently null → moving-average calculation corrupted.
- 🚨 `UserContextHolder.get()` used without null-check at several locations (PlanConfigController, PlanReportController, PlanOrderController). Use `UserContextHolder.getTenantId()` / `.getUserId()` which have null guards.
- 🚨 `rocketmq.consumer.monitor-rule.topic` is hardcoded `inventory-monitor-topic` but real producer uses `stock-inventory-center-topic` → MonitorRuleConsumer is permanently idle.
- ⚠️ **Two `InventoryChangeMessage` classes**: `domain.model.bo.mq.sub` (9 fields) vs `infra.produce.model` (15 fields). Always import the correct one for context.

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

**Ref format:** Single letters like `R1`, `R2`, `F2-F7`, `H1+H2+M1+M3+M4`, `Q1`, `N3` encode tracked tasks from `remaining-todos.md`.

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

## 个人编码偏好（硬约束）

### Bean 转换
- **统一使用 MapStruct**，禁止使用 `BeanUtils.copyProperties`
- 转换逻辑集中在 infra impl（Do ↔ Entity）和 interfaces 层（Entity/BO ↔ DTO）
- MapStruct Mapper 接口放在对应层，不跨层共用

### 代码风格
- **Stream 流式写法优先**，避免命令式 for 循环
- 优先使用 **KDLA 框架封装的工具类和特性**，不重复造轮子：
  - 异常：`BizException`（业务异常）、`SysException`（系统异常）
  - 装饰器：`@CatchAndLog`、`@StopWatchWrapper`、`@MdcDot`
  - 响应：`SingleResponse`、`MultiResponse`、`PageResponse`

### JDK 21 新特性（积极使用）
- **虚拟线程**：`Thread.ofVirtual()` 或 `Executors.newVirtualThreadPerTaskExecutor()` 替代传统线程池，用于 I/O 密集型并发任务
- **Record**：不可变值对象 / 局部 DTO / QueryParam 优先用 `record` 声明
- **文本块**（`"""`）：SQL 片段、JSON 模板、日志消息等多行字符串统一用文本块
- **switch 表达式**：替代多分支 if-else 或传统 switch
- **sealed 类**：领域中有封闭继承体系时使用

### 时间字段
- 统一使用 `java.time.LocalDateTime`，禁止使用 `java.util.Date`

---

## What NOT to Do

1. **Don't put Spring annotations in domain layer** — it must stay plain Java
2. **Don't assume MapStruct is active** — check if annotations are commented out in starter/pom
3. **Don't run full test suite locally without infra** — MySQL/Redis/RocketMQ/Nacos are required
4. **Don't ignore the KDLA framework** — @CatchAndLog and @MdcDot are already in use; respect the convention
5. **Don't add new modules without updating parent pom.xml** `<modules>` section
6. **Don't inject infra Mapper/DAO/Do/PO in application layer** — Query 走 domain Repository，Command 走 domain DomainService
7. **Don't use `BeanUtils.copyProperties`** — 统一用 MapStruct，copyProperties 会绕过类型检查且字段名不一致时静默失败
8. **Don't write imperative for loops** when Stream API expresses the same intent more clearly
9. **Don't use `plan.plan.` double-nested package paths** — always `plan.` single level
10. **Don't trust request-body fields** for `tenantId`, `userId`, `operatorId`, `creatorId`, `updatorId`, `sourceSystem` — these must be overwritten from `UserContextHolder` in the controller layer
11. **Don't add new XMLs to `/mybatis/mapper/`** — that directory is never scanned; use `/mapper/` instead

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
- `docs/sql/inventory.sql` — inventory DDL; `docs/sql/plan.sql` — plan DDL
- `docs/superpowers/plans/remaining-todos.md` — **authoritative backlog** of migration gaps
- Recent git log — Shows actual module/feature structure and commit patterns

---

## Avoid These Mistakes

**Surface assumptions.** The KDLA framework, RocketMQ routing, and soft-delete logic are non-obvious. Ask if uncertain.

**Don't hide config issues.** Missing or wrong topic names in RocketMQ config will silently fail at runtime.

**Don't refactor MapStruct mappings without testing.** If processor is inactive, hand-edited mappers may fail silently.

---

*Last updated: 2026-07-02*
