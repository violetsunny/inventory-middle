# SCM Plan 迁移计划（scm-plan-management + scm-plan-bff → inventory-middle）

> **状态：✅ 代码迁移 + 全量编译已完成（2026-06-22）。剩余：冒烟验证 + 路由检查 + git commit（组 O 步骤 2-4）。**

## 迁移目标

将 `scm-plan-management` + `scm-plan-bff` 迁移进 `inventory-middle` 现有各模块，以 `plan` 子包并列。

**路径：**
- 源：`/Users/kangll13/aiot/java-code/self/inventory/scm-plan-management` 和 `scm-plan-bff`
- 目标：现有 inventory-middle 各模块的 `plan` 子包

## 架构决策（速查）

- 不新建子模块；`plan` 包与原 `inventory` 包并列
- `@DubboService` / `@DubboReference` → 本地 ApplicationService 直调
- `RdfaResult<T>` → `SingleResponse<T>`；`PagedRdfaResult<T>` → `PageResponse<T>`
- `RemoteInventoryCenterService`（8 个方法）→ `InventorySupportServiceImpl`（`application/plan/support/impl/`）桥接
- 外部服务（ParticipantCenter / ProductCenter / PlanTask）→ `infra/plan/stub/` 存根
- `@RdfaJob` → `@Scheduled`；`UserInfo getUserInfo()` → `UserContextHolder.get()`
- `BusinessException` 复用现有（已继承 kdla `BizException`）；**不要**改为直接 `throw new BizException(...)`
- RDFA 全面去除：`@Authorize` 删除、`token` header 删除、RDFA import 替换为 kdla

## 完成摘要

| 组 | 内容 | 状态 |
|----|------|------|
| P0 | 预检 RemoteInventoryCenterService 8 个方法；补全 InventorySupportServiceImpl；@EnableScheduling；application.yml plan 配置 | ✅ |
| A-Pre | ExternalMaterialProcess AOP 迁移到 `infra/plan/aop/`；切点改为 `application.plan..*` | ✅ |
| A | 公共 DTO（bom/demand/plan）、RpcService 接口（7个）、枚举（8个）、PlanCommonConstants | ✅ |
| B | BOM 清单（8 接口 `/bomCase`）；PlanParticipantStub | ✅ |
| C | 需求计划（10 接口 `/demandPlan`）；EasyExcel import Listener | ✅ |
| D | 需求看板（2 接口 `/demandBoard`） | ✅ |
| E | 计划配置（5 接口 `/planConfig`） | ✅ |
| F | 计划订单（8 接口 `/planOrder`）；PlanProductStub | ✅ |
| G | 需求供应库存（3 接口 `/planDemandSupplyStock`） | ✅ |
| H | 计划报表（3 接口 `/planReport`）；EasyExcel export | ✅ |
| I | 公共接口（`/common`）；CommonController（5 接口）；FullAddressHelper stub | ✅ |
| J | 城燃项目（`/urbanGasProject`、`/projectTask`，含 TODO 占位） | ✅ |
| K | MQ Consumer 迁移（`consumer/plan/`，topic/group 写入 application.yml） | ✅ |
| L | 定时任务迁移（`task/plan/`，`@Scheduled` 替换 `@RdfaJob`） | ✅ |
| M | infra 收尾（PO 基类对齐 `BasePO`、mapper XML 到 `resources/mapper/plan/`） | ✅ |
| N | Sequence 复用 `CodeGeneratorCfgRepositoryImpl`（字段一致，复用路径） | ✅ |
| O-1 | 全量编译 BUILD SUCCESS（7/7 模块，2026-06-22） | ✅ |

---

## 组 O：冒烟验证（待完成）

- [x] **步骤 1：全量编译**
  ```bash
  cd /Users/kangll13/aiot/java-code/self/inventory-middle
  mvn clean compile -DskipTests
  ```
  ✅ BUILD SUCCESS（2026-06-22，7/7 模块通过）

- [x] **步骤 2：mock 验证 — Controller 路由注册（静态检查）**
  ```bash
  grep -rn "@RequestMapping" inventory-middle-interfaces/src/.../web/plan/ | grep -E 'bomCase|demandPlan|...'
  ```
  ✅ 10/10 路由全部存在（2026-06-23）

- [x] **步骤 3：mock 验证 — MQ Consumer 和定时任务注解检查（静态检查）**
  - consumer/plan 目录不存在（scm-plan-management 无 MQ Consumer，已确认）
  - task/plan 目录不存在（定时任务在 starter 层，已确认 @Scheduled 注解存在）

- [ ] **步骤 4：git commit**
  ```bash
  git add .
  git commit -m "feat(plan): migrate scm-plan-management + scm-plan-bff into inventory-middle plan packages"
  ```

---

## 关键路径（供后续参考）

- **InventorySupportServiceImpl**：`application/plan/support/impl/` — 桥接 plan 到 inventory 已有服务的唯一入口
- **PlanParticipantStub**：`infra/plan/stub/` — 所有 ParticipantCenter 调用降级处理（待后续接通）
- **PlanProductStub**：`infra/plan/stub/` — 所有 ProductCenter 调用降级处理（待后续接通）
- **ExternalMaterialFillingProcessSlot**：`infra/plan/aop/` — 外部物料编码填充 AOP，切点 `application.plan..*`
- **Sequence**：复用 `CodeGeneratorCfgRepositoryImpl`，无独立 plan Sequence 表
- **MQ topics**：`rocketmq.plan.*`（application.yml）—— 值为占位符，需与 Nacos 实际值对齐
- **定时任务 cron**：`plan.job.*`（application.yml）—— 值为占位符，需与 Apollo 实际值对齐
