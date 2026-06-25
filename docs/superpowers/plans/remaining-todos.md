# Inventory-Middle 迁移 TODO 追踪

> 多源迁移（inventory-center + inventory-center-bff + scm-plan-management + scm-plan-bff → inventory-middle）任务追踪文档。

## 构建

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home \
  mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
```

## 约束

1. **新增代码**不在 domain 层引入 Spring 注解（现有 105 处历史违规属于已有债务，见 Q12/Q13，不作为约束对象）
2. plan 包路径禁止 `plan.plan.` 双层嵌套
3. HTTP 外部调用优先 OpenFeign，次选 RestTemplate
4. 编译后必须 BUILD SUCCESS 7/7，然后对完成任务进行标记为 `✅`、[X]

---

## 剩余项（19 项 + 新增 8 项 + 代码审计新增 14 项，建议按 9+5+7 组理解）

### 建议执行顺序

#### 阶段 0：立即修复 — 运行时崩溃 / 数据损坏 / 消息永久丢失

> 这三组问题无论何时动其他任何代码，都应优先处理。修复成本极低（1–5 行），不修复则后续所有验收都建立在损坏的运行时上。

1. **G15（Q1+Q2+Q6）：Spring 启动失败 + 移动均价数据损坏 + 消息类字段分裂**
   - Q1：`MaterialDocOutboundService` 接口无实现 → Spring 注入失败（`InventoryAdjustDecHandle` 所在 handle chain 无法初始化）
   - Q2：消费者反序列化到 9 字段版本 `InventoryChangeMessage`，`price`/`adjustQuantity` 静默 null → 移动均价计算数据损坏
   - Q6：两个同名 `InventoryChangeMessage` 类（domain 9 字段 vs infra 15 字段），与 Q2 强关联，需同步修复
2. **G10（N2+N3）：MonitorRule MQ 消费链路完全失效**
   - N3：`monitor-rule.topic` 硬编码为 `inventory-monitor-topic`，正确值应为 `stock-inventory-center-topic`（一行 yml 修改）
   - N2：`MonitorRuleLineConsumer` 缺失，规则行变更消息被静默丢弃

**阶段目标：** 在进入任何功能迭代之前，先确保 Spring 能正常启动、移动均价计算不损坏数据、预警规则 MQ 消费链路不空转。

#### 阶段 1：先补 Server 语义兼容基础

1. **M17：补齐 BFF 登录态到本地 `UserContext` 的兼容承接**
   - 先明确 `ennUnifiedAuthorization -> tenantId/userId/username` 的承接链路
   - 若该链路未补，后续依赖 `UserContextHolder` 的接口即使补了回填，也仍可能拿到空上下文
2. **M18：补齐 BFF 跨域暴露头兼容配置**
   - 与 M17 一起完成前端登录态链路兼容验证

**阶段目标：** 让 `inventory-middle` 至少在“登录态进入服务端上下文”这件事上与 BFF 行为等价，避免后续所有依赖 `UserContextHolder` 的改造建立在空上下文之上。

**统一原则：**
- 先确认唯一可信的用户上下文来源，再扩散到具体 controller
- 先补上下文承接，再补字段回填，避免重复返工

#### 阶段 2：优先收口高危写接口

1. **M19：修复已确认的 BFF 迁移入口对请求体上下文字段的直接信任**
   - 首批优先：`InventoryMaterialController`、`CodeApplyOrderController`
2. **M21-P1：批量收口高危写接口**
   - 重点处理 `create/save/update/approval/occupy/use/import` 这类变更型入口
   - 首批优先：`AccessoriesFlowCodeController`、`FileImportController`、生成式 CRUD controller 中的写接口

**阶段目标：** 让所有对外写接口不再信任请求体中的租户、操作人、来源系统、创建人、更新人等内部字段。

**统一原则：**
- 对外 web 入口一律在 controller 层覆盖内部上下文字段
- application service 不再假设调用方传入的 `tenantId/operatorId/sourceSystem` 可信
- 先处理会改数据的接口，再处理只读接口

#### 阶段 3：补查询/详情/删除的租户边界

1. **M21-P2：补齐局部漏口**
   - 重点处理 `page/detail/query/delete` 中未回填 `tenantId` 的接口
   - 优先关注：`WarehouseController.detail`、`InventorySnapshotController.queryDetail`、`MaterialDocController.queryMaterialBatchNo/cityGasExcelPage`、`InventoryTransitController.findById/delete`
2. **M15：将仍直连 external service 的 BFF 迁移 Controller 下沉到本地 ApplicationService**
   - 在上下文边界收口后处理架构分层问题，避免同时混改带来排查困难

**阶段目标：** 让 `page/detail/query/delete` 这类入口在租户边界上保持一致，不再出现“同一个 controller 里有的接口补 tenant，有的接口没补 tenant”的情况。

**统一原则：**
- 详情、删除、分页查询都需要明确 tenant-aware 约束
- 对仅靠 `id/ids` 的接口，优先补服务端租户过滤，而不是继续信任调用方语义
- 架构下沉放在租户边界收口之后进行，降低问题定位复杂度

#### 阶段 4：补 BFF 路由兼容与其他收尾项

1. **M12-M14：补 BFF 路由兼容、接口覆盖和 common 菜单能力**
2. **M16：确认非业务 BFF HTTP 入口是否需要保留兼容迁移**
3. **M20：确认 BFF 热点缓存能力是否需要恢复**
4. **M9-M11：清理 RDFA 构建残留并补本地缺失服务实现**

**阶段目标：** 完成迁移收尾，消除 BFF 路由断裂、运行时注入风险和构建残留，让审计结果从"基本迁移完成但有缺口"收敛到"功能、架构、运行时都可闭环"。

**统一原则：**
- 先补功能兼容缺口，再处理优化类或确认类事项
- 运行时风险项优先于代码洁癖类清理项
- 所有收尾项以源码证据和重新验证结果为准，不沿用历史口头结论

#### 阶段 5：plan 子包迁移缺口补齐

1. **M22：PlanConfigController 导入/删除空壳实现 + ennUnifiedAuthorization 残留清理**
   - `importPlanMaterialParam`、`importPlanMaterial`、`deletePlanMaterial` 直接 `buildSuccess()` 无业务逻辑（注：`generatePlan` 已有真实实现，仅需清理 `ennUnifiedAuthorization` 形参）
   - 底层 `PlanConfigService` 已有 `batchCreatePlanMaterialParam`、`batchCreatePlanMaterial`、`batchDeletePlanMaterial`，controller 未接通
   - 需补 Excel BO `@ExcelProperty` 注解 + 新建两个 Excel Listener
2. **M23：PlanDemandSupplyStockController detail/tree VO 映射补全**
   - `demandSupplyStockBoardDetail` 返回空 VO，未映射 application service 的 `boardData`/`chartData`
   - `renderBomTree` 的 `children` 为空列表，未调用 `BomCaseApplicationService.renderBomTree`
3. **M24：ProjectTask 完整本地化**
   - `ProjectTaskController`（3 方法）和 `ProjectTaskManageController`（1 方法）全部返回 TODO 占位
   - 需新建完整链路：DTO/VO → ApplicationService → DomainService → Persistence → DDL
   - 涉及申请、查询、通知回调、详情四个核心能力
4. **M25：PlanOrderController / SparePartController 的 ennUnifiedAuthorization 形参残留清理**
   - `PlanOrderController.issuePlanOrder`（line 70）保留 `ennUnifiedAuthorization` 形参
   - `SparePartController`（line 40）保留 `ennUnifiedAuthorization` 形参
   - 与 M17 登录态承接方案联动，确认承接后统一清理
5. **N6：4 个 plan 定时任务为纯 stub（见 G12）**
   - `PlanGenerateJob`、`PlanDemandSupplyStockGenerateJob`、`PlanRedisOperateJob`、`PlanOrderOverdueCheckJob` 执行体均为空或 stub

**阶段目标：** 将 plan 子包中剩余的空壳实现、VO 映射缺失、ProjectTask 占位和认证形参残留全部补齐，使 plan 模块达到与 BFF 功能等价的本地化状态。

**统一原则：**
- 先接通已有底层 service 的空壳 controller（M22），再补 VO 映射（M23），最后处理需要新建完整链路的 ProjectTask（M24）
- ennUnifiedAuthorization 清理（M25）依赖 M17 登录态承接方案，应在 M17 之后处理
- 所有新建代码遵循现有 plan 包路径规范，禁止 `plan.plan.` 双层嵌套

#### 阶段 6：RDFA/Nacos/Dubbo 代码残留全量清理

1. **M26：RDFA/Nacos/Dubbo 代码级残留清理**
   - 3 个 domain service 中 11 处 `rdfaResult` 变量名重命名（`MaterialBatchSysHandle`、`MonitorAnnualInspectionHandleChain`、`InventoryLogDomainServiceImpl`）
   - `HelloDubboDto.java` 死代码类删除（全仓库无引用）
   - `PlanExecutorConfig.java` 注释掉的 `RDFAExecutor` 代码块清理
   - 17 个 client service 文件的 `// RDFA import removed;` 占位注释清理
   - 8 处 Dubbo Javadoc + 7 处 RDFA Javadoc + 2 处 Nacos YAML 注释清理
   - 与 M9（pom.xml shade plugin）互补：M9 处理构建配置，M26 处理源码/注释

**阶段目标：** 消除代码库中所有 RDFA/Nacos/Dubbo 命名残留，使源码从变量名、类名、注释到构建配置全面去 RDFA/Nacos/Dubbo 化。

**统一原则：**
- 先处理有运行时影响的代码（变量名、死代码类），再处理纯注释/Javadoc
- `rdfaResult` 变量名重命名为 `result` 或 `response`，保持语义清晰
- `HelloDubboDto` 确认无引用后直接删除，不做迁移
- Javadoc/YAML 注释清理属于收尾美化，优先级最低
- 与已有条目的关系：M9 覆盖 pom.xml 构建残留，M17/M22/M25 覆盖 `ennUnifiedAuthorization` 形参残留，M26 覆盖其余代码级残留

#### 阶段 7：新增遗漏项（检查发现，原计划未覆盖）

**最高优先级 — 运行时完全失效：**

1. **N3（G10）：`monitor-rule.topic` 默认值与源不匹配 → `MonitorRuleConsumer` 永远空转**
   - 一行 yml 修改，风险极高，建议立即处理
2. **N2（G10）：`MonitorRuleLineConsumer` 缺失 → 规则行变更消息静默丢弃**
   - 与 N3 同属 MQ 消费修复，建议一并处理

**高优先级 — 数据库初始化不完整：**

3. **N4（G11）：13 张表有 mapper XML 但无 DDL**
   - 影响全量环境初始化

**中优先级 — 功能静默损坏：**

4. **N1（G14）：17 个 `list()` 方法返回 null**
   - 包含所有自动生成 CRUD controller 的 list 查询
5. **N5（G12）：所有 6 个 `@Scheduled` 任务无分布式锁**
6. **N6（G12）：4 个 plan 定时任务为纯 stub**

**低优先级 — 架构/代码质量：**

7. **N7（G13）：`PlanCommonController` 直接注入 infra 层 stub**
8. **N8（G14）：`InventoryMonitorRuleLineController` 与 `/monitorRule/ruleLine` 路由二义性**

#### 阶段 8：代码深度审计发现的新问题

**最高优先级 — 运行时崩溃/数据损坏：**

1. **Q1（G15）：`MaterialDocOutboundService` 接口无任何实现类 → Spring 启动/注入失败**
2. **Q2（G15）：`InventoryChangeMqConsumer` 反序列化到字段不全的类 → 移动均价计算丢失字段**
3. **Q3（G16）：`UserContextHolder.get()` 无 null 检查 → 8 处 NPE 风险点**
4. **Q4（G16）：`MaterialDocQueryServiceImpl.queryMaterialBatchNo` 返回 null → 已上线接口静默无数据**

**高优先级 — 功能/数据正确性：**

5. **Q5（G17）：25 个 XML mapper 文件在 `/mybatis/mapper/` 未被加载 + DemandPlanMapper 双份**
6. **Q6（G15）：两个 `InventoryChangeMessage` 类字段不一致（producer 15 字段 vs consumer 9 字段）** — 已移入 G15 与 Q2 一并处理
7. **Q7（G18）：11 个 MQ 消费者无幂等保护 + 抛原始 `RuntimeException` 导致无限重试**
8. **Q8（G18）：3 个 MyBatis 查询缺 tenant_id 过滤 → 跨租户数据泄露风险**

**中优先级 — 逻辑错误/架构债：**

9. **Q9（G19）：`DemandPlanServiceImpl` 3 处业务逻辑 TODO + 2 处 ParseException 被静默吞掉**
10. **Q10（G19）：`@Transactional` self-invocation 导致事务失效**
11. **Q11（G20）：`FileController` 直接注入 infra `OssFileService`（M15 列表未覆盖）**
12. **Q12（G20）：20 个 application 层 convertor 直接 import `infra.persistence.*Do`**

**低优先级 — 代码洁癖/重复类：**

13. **Q13（G21）：11 个孤儿实体类在 `entity/inventory/` 和 `entity/material/` 子包（零引用）**
14. **Q14（G21）：两个 `ResponseCodeEnum` 在 domain 层重叠定义**

### 实施分批

#### P1：高风险写接口

- `CodeApplyOrderController`
  - `/create`
  - `/approval`
- `InventoryMaterialController`
  - `/page`
  - `/queryCityGasMaterialPage`
- `AccessoriesFlowCodeController`
  - `/manufacturer-in-stock`
  - `/occupy`
  - `/use`
  - `/regenerate-code`
  - `/update-code-for-deliver-order`
  - `/query-codes-for-print`
- `FileImportController`
  - `/create`
  - `/update`
  - `/create-line-records`
- 生成式 CRUD controller 的写接口
  - `InventoryAlertController`
  - `InventoryDemandController`
  - `InventoryPlanController`
  - `InventorySupplyController`
  - `InventoryMapHisController`
  - `InventoryMonitorRuleLineController`
  - `MaterialDocMainController`
  - `MaterialDocItemController`
  - `MdocSub*Controller`
  - `ShipmentController`
  - `ShipmentLineController`

**处理口径：** 这一批不追求一次性做 DTO 重构，先统一实现“请求体可传、但服务端会强制覆盖内部字段”的边界收口。

#### P2：查询/详情/删除漏口

- `WarehouseController.detail`
- `InventorySnapshotController.queryDetail`
- `MaterialDocController.queryMaterialBatchNo`
- `MaterialDocController.cityGasExcelPage`
- `InventoryTransitController.findById`
- `InventoryTransitController.delete`
- 其他“同一 controller 大部分方法已补上下文，但少数 detail/delete/query 漏掉”的入口

**处理口径：** 这一批重点不是改接口形状，而是补齐 `tenantId` 回填、tenant-aware 查询/删除约束，以及 controller 内部风格统一。

#### P3：DTO/VO 结构整理

- 对明显暴露内部字段的请求对象，评估拆分 web VO 与内部 DTO
- 后续统一约束这些字段不再信任请求体：
  - `tenantId`
  - `userId`
  - `operatorId`
  - `creatorId`
  - `updatorId`
  - `sourceSystem`

**处理口径：** 这一批属于结构治理，放在前两批稳定后再做，避免在迁移收尾阶段同时引入大范围 DTO 变更。

### 归并视图

- [ ] **G1：部署与构建收尾** `M8`
  - 包含外部 URL 环境配置确认
  - 适合合并原因：属于迁移最后阶段的部署/构建侧清理，不涉及业务链路设计
  - 注：M9（pom.xml RDFA shade include）已归入 G9，与源码级残留统一处理

- [x] **G2：缺失本地服务实现与运行时注入风险** `M10 + M11` ✅
  - 包含 `InTransitStockService`、`MaterialDocService` 的本地承接缺口
  - 适合合并原因：都属于“接口还在，但本地实现或 Bean 装配缺失”的同类运行时风险

- [x] **G3：BFF HTTP 路由与菜单能力兼容** `M12 + M13 + M14` ✅
  - 包含 `ShipmentQuery`、备件流转码/申请单、`CommonController` 的兼容路由与菜单能力
  - 适合合并原因：都属于 BFF HTTP 入口兼容层问题，适合统一按“保路径、补接口、复用本地 service”处理

- [x] **G4：BFF 登录态、上下文与租户边界治理** `M17 + M18 + M19 + M21` ✅
  - 包含登录态承接、CORS 暴露头、请求体字段信任边界、遗留 CRUD 上下文收口
  - 适合合并原因：本质都在解决“可信用户上下文从哪里来、哪些字段不能再信任请求体”这一条主线

- [x] **G5：BFF 迁移后的分层与调用方向收尾** `M15` ✅
  - 包含 controller 直连 external/infra service 的架构收口
  - 单独保留原因：它是架构分层问题，建议晚于 G4 处理

- [x] **G6：非业务 HTTP 入口保留策略确认** `M16` ✅
  - 包含 demo/config 类接口是否继续保留的决策
  - 单独保留原因：这是策略确认项，不是纯技术实现项

- [x] **G7：BFF 性能特征与缓存等价性确认** `M20` ✅
  - 包含缓存是否需要恢复的确认
  - 单独保留原因：这是性能/运行特征确认项，不应与功能兼容项混做

- [ ] **G8：plan 子包迁移缺口补齐** `M22 + M23 + M24 + M25`
  - 包含 PlanConfig 空壳实现、DemandSupplyStock VO 映射、ProjectTask 本地化、ennUnifiedAuthorization 残留
  - 适合合并原因：都属于 plan 子包迁移后遗留的功能缺口，且 M22-M24 可独立于 G4 的登录态治理先行推进

- [ ] **G9：RDFA/Nacos/Dubbo 代码残留全量清理** `M9 + M26`
   - 包含 pom.xml shade plugin RDFA include（M9）+ 源码变量名/死代码类/注释残留（M26）
   - 适合合并原因：都属于"去 RDFA/Nacos/Dubbo 化"的代码洁癖类清理，不涉及业务逻辑变更
   - 与 G1 的区别：G1 聚焦部署/构建配置确认，G9 聚焦源码级命名和注释清理
   - 与 G4 的区别：G4 聚焦 `ennUnifiedAuthorization` 认证形参的运行时承接，G9 聚焦非认证类的 RDFA 命名残留

- [x] **G10：MQ 消费链路修复** `N2 + N3` ✅
   - 包含 `MonitorRuleLineConsumer` 缺失（N2）和 `monitor-rule.topic` 默认值与源不匹配（N3）
   - 适合合并原因：同属预警规则 MQ 消费链路的两个关键缺口，修复时需同步验证消费链路端到端
   - **优先级：最高** — topic 不匹配导致 `MonitorRuleConsumer` 永远空转；Consumer 缺失导致规则行事件静默丢弃

- [ ] **G11：数据库 DDL 补全** `N4`
   - 包含 13 张有 mapper XML 但无 DDL 的业务表
   - 独立保留原因：DDL 补写属于文档/初始化工程类任务，与业务逻辑变更正交；修复后需重新验证全量数据库初始化流程

- [ ] **G12：定时任务健壮化** `N5 + N6`
   - 包含 6 个 `@Scheduled` 任务无分布式锁（N5）和 4 个 plan 定时任务为空 stub（N6）
   - 适合合并原因：都属于 plan 定时任务的质量缺口；stub 任务补实现时应同步加锁
   - 建议顺序：先补有业务逻辑的任务的分布式锁（`MonitorAnnualInspectionJob`、`DemandPlanDetailGenerateJob`），再补 stub 任务实现，最后统一加锁

- [ ] **G13：分层架构收口补充** `N7`
   - `PlanCommonController` 直接注入 infra 层 `PlanParticipantStub`/`PlanProductStub`（N7）
   - 与 G5（M15）合并处理原因：属于同一类 controller→infra 直接依赖问题，但 M15 未覆盖 plan common controller

- [ ] **G14：自动生成 CRUD list() stub 批量实现** `N1 + N8`
   - 包含 17 个 `list()` null stub controller（N1）和 `InventoryMonitorRuleLineController` 路由二义性（N8）
   - 处理顺序：先清理 `InventoryMonitorRuleLineController` 重复路由（N8），再逐一实现 `list()` 方法

- [x] **G15：运行时启动/数据损坏风险修复** `Q1 + Q2 + Q6` ✅
   - `MaterialDocOutboundService` 接口无实现 → Spring 注入失败（Q1）
   - `InventoryChangeMqConsumer` 反序列化到字段不全的类 → 移动均价数据损坏（Q2）
   - 两个 `InventoryChangeMessage` 类字段不一致（Q6，与 Q2 强关联，需同步修复）
   - **优先级：最高** — Q1 可能导致 Spring 应用启动失败；Q2+Q6 导致已在运行的计算逻辑静默丢失关键字段

- [ ] **G16：UserContext null 检查 + MaterialDocQueryService 空实现** `Q3 + Q4`
   - 8 处 `UserContextHolder.get()` 无 null 检查（Q3）
   - `MaterialDocQueryServiceImpl.queryMaterialBatchNo` 返回 null（Q4）
   - 适合合并原因：都属于"方法返回 null/context 为 null 时未防御"的同类运行时崩溃模式

- [ ] **G17：MyBatis XML 文件完整性修复** `Q5`
   - 25 个 XML mapper 在 `/mybatis/mapper/` 目录未被加载（Q5）；另有两份 `DemandPlanMapper.xml` 同时存在，需确认 Java 接口是否都存在
   - 注：Q6 已移入 G15 与 Q2 一并处理

- [ ] **G18：MQ 消费者健壮化** `Q7 + Q8`
   - 21 个消费者无幂等保护 + `RuntimeException` 导致无限重试（Q7）
   - 3 个 MyBatis 查询缺 `tenant_id` 过滤（Q8）
   - 注：Q7 中的无幂等问题与 N6 中 stub 消费者是不同问题；N6 是 stub，Q7 是已实现但无幂等的消费者

- [ ] **G19：DemandPlanServiceImpl 逻辑错误修复** `Q9 + Q10`
   - 3 处业务逻辑 TODO + 2 处 ParseException 静默返回空（Q9）
   - `@Transactional` self-invocation 事务失效（Q10）
   - 适合合并原因：都属于 `DemandPlanServiceImpl` 内部的逻辑/事务错误

- [ ] **G20：架构分层违规收口（M15 补充）** `Q11 + Q12`
   - `FileController` 直接注入 `infra.OssFileService`（Q11，M15 列表未覆盖）
   - 20 个 application 层 convertor 直接 import `infra.persistence.*Do`（Q12）
   - 与 G5（M15）合并处理原因：同属"interfaces/application 层直依 infra"的架构违规，M15 只列了 controller→remote service 部分

- [ ] **G21：代码洁癖 — 重复类和孤儿实体清理** `Q13 + Q14`
   - 11 个孤儿实体类（Q13）和两个 `ResponseCodeEnum`（Q14）
   - 低优先级，不影响运行时，属于代码可维护性清理

### 原始编号映射

- `G1 = M8`
- `G2 = M10 + M11`
- `G3 = M12 + M13 + M14`
- `G4 = M17 + M18 + M19 + M21`
- `G5 = M15`
- `G6 = M16`
- `G7 = M20`
- `G8 = M22 + M23 + M24 + M25`
- `G9 = M9 + M26`
- `G10 = N2 + N3`
- `G11 = N4`
- `G12 = N5 + N6`
- `G13 = N7`
- `G14 = N1 + N8`
- `G15 = Q1 + Q2 + Q6`
- `G16 = Q3 + Q4`
- `G17 = Q5`
- `G18 = Q7 + Q8`
- `G19 = Q9 + Q10`
- `G20 = Q11 + Q12`
- `G21 = Q13 + Q14`

- [ ] **M8：6 个外部 URL 默认值为空** ⏳ 部署配置
  - `application.yml` 中 `remote.*.url` 均为 `${ENV_VAR:}` 占位符
  - 生产环境必须配置，否则相关功能静默失效

- [x] **M9：清理 `inventory-middle-client` 的 RDFA 构建残留** ✅ 迁移收尾 → **归入 G9**
  - `inventory-middle-client/pom.xml` 的 `maven-shade-plugin` 仍显式包含 `top.rdfa.framework:rdfa-biz`
  - 与"已去除 RDFA 运行/构建依赖"的迁移目标不一致，需要确认是否仍有外部兼容诉求
  - 处理建议：删除该 `include`，重新验证 `inventory-middle-client` 打包结果与下游依赖
  - 与 M26 互补：M9 处理构建配置，M26 处理源码级残留，统一归入 G9

- [x] **M10：补齐 `InTransitStockService` 的本地实现或替换调用链** ✅ 运行时风险
  - `MaterialDocInTmHandle`、`MaterialDocInOutTmHandle` 仍通过 `@Resource` 注入 `com.inventory.middle.client.service.InTransitStockService`
  - 全仓库仅发现接口与调用点，未发现任何实现类或 `@Bean` 装配，存在 Spring 启动注入失败/相关链路不可用风险
  - 处理建议：改为依赖本地 application/domain service，或提供明确的 adapter bean；至少覆盖入库/出入库在途调整链路验收

- [x] **M11：补齐 `MaterialDocService` 的本地查询实现或替换取消单查询链路** ✅ 运行时风险
  - `SnMaterialDocCancelBuilderHandleChain` 仍通过 `@Resource` 注入 `com.inventory.middle.client.service.MaterialDocService`
  - 全仓库仅发现接口声明与 `getMaterialDocument()` 调用，未发现实现类或 `@Bean` 装配
  - 处理建议：改为依赖本地查询服务/领域服务，重点验收 `SnMaterialDocCancelConsumer` 取消单构建与冲销链路

- [x] **M12：补齐 `inventory-center-bff` 的 ShipmentQuery HTTP 兼容迁移** ✅ BFF Controller 缺口
  - 源 BFF 存在 `ShipmentQueryController`：`/shipmentQuery/v1/query`、`/detail/query`、`/type/query`
  - `inventory-middle` 当前仅有 `ShipmentController`（`/shipment` CRUD 风格），未见 BFF 同路径兼容入口
  - 处理建议：在 `interfaces/web/` 增加 BFF 兼容 controller，复用本地 query/application service，避免前端/BFF 调用断裂

- [x] **M13：补齐备件流转码与申请单的 BFF 路由兼容及接口覆盖** ✅ BFF Controller 缺口
  - `AccessoriesFlowCodeController`：源 BFF 路径为 `/code/accessoriesFlow/**`，当前迁移为 `/accessories-flow-code/**`
  - 当前缺少或未对齐的 BFF 接口包括：`/accessoriesFlow/logs`、`/accessoriesFlow/print`、`/accessoriesFlow/detail` 等兼容入口
  - `CodeApplyOrderController`：源 BFF 路径为 `/code/applyOrder/**`，并区分 `distributor/manufacturer detailInfo/pageList`，当前迁移仅提供通用 `/code-apply-order/{create,approval,info,page}`
  - 处理建议：补 BFF 原路径兼容层，并补齐 `approvalStatus/list`、经销商/厂商分页与详情拆分接口

- [x] **M14：补齐 `CommonController` 的 BFF 路径与菜单能力** ✅ BFF Controller 缺口
  - 源 BFF 路径为 `/common/**`，当前迁移为 `/inventory-common/**`
  - 源 BFF 包含 `/getMenus`，当前 `InventoryCommonController` 未提供该接口
  - `province/city/region` 当前仍是空列表 stub，需要确认是否接受该降级行为
  - 处理建议：补 `/common/**` 兼容入口，评估 `getMenus` 是否必须恢复为真实能力

- [x] **M15：将仍直连 external service 的 BFF 迁移 Controller 下沉到本地 ApplicationService** ✅ 架构收尾
  - 当前仍有多个 web controller 直接注入 external/infra service，而不是本地 application service：
  - `MaterialDocController` → `RemoteProductCenterRestService`
  - `InventoryTransitController` → `RemoteProductCenterRestService`
  - `InventoryCommonController` → `ParticipantCenterRemoteService`
  - `sparepart/DistributorController` → `CrmDistributorRemoteService`
  - `sparepart/DeliveryOrderMgntController` → `SpDeliveryOrderRemoteService`
  - 处理建议：增加本地 ApplicationService 封装外部调用与组装逻辑，保持 `interfaces -> application -> domain/infra` 依赖方向，满足“本地 Controller 和 ApplicationService 替代原 Dubbo/RDFA 远程调用”目标

- [x] **M16：确认非业务 BFF HTTP 入口是否需要保留兼容迁移** ✅ 收尾确认
  - 源 BFF 仍有 `ConfigserviceDemoController`（`/config/logger/level`）这类非业务 HTTP 入口
  - `inventory-middle` 当前未见对应接口
  - 处理建议：与业务方确认该类 demo/调试接口是否可以显式废弃；若需保留，迁移到 `interfaces/web/` 并统一鉴权/日志规范

- [x] **M17：补齐 BFF 登录态到本地 `UserContext` 的兼容承接** ✅ 🚨 Server 迁移缺口
  - 源 BFF starter 启用了 `@EnableUnifiedAuthenticationClient(runType = RunTypeEnum.INTERCEPTOR)`，Controller 普遍依赖 `ennUnifiedAuthorization` 登录态
  - `inventory-middle` 当前仅通过 `UserContextInterceptor` 从 `X-Tenant-Id`、`X-User-Id`、`X-Username` 读取上下文，未见对 `ennUnifiedAuthorization` 的解析/换取/透传拦截处理
  - 现状会导致大量已迁移 Controller 虽仍接收 `ennUnifiedAuthorization`，但 `UserContextHolder` 依赖的租户/用户信息可能为空，与 BFF 原行为不等价
  - 处理建议：补统一鉴权/上下文适配层，至少明确 `ennUnifiedAuthorization -> tenantId/userId/username` 的承接方案，并覆盖 `MaterialDoc`、`InventoryTransit`、`Common`、`Plan` 等依赖 `UserContextHolder` 的入口验收

- [x] **M18：补齐 BFF 跨域暴露头兼容配置** ✅ ⚠️ Server 迁移缺口
  - 源 BFF `CorsConfig` 暴露头默认为 `ennUnifiedAuthorization,ennUnifiedCsrfToken`
  - `inventory-middle` 当前 `CorsConfig` 仅显式暴露 `Authorization`
  - 若前端仍沿用 BFF 登录态/CSRF 头读取方式，浏览器跨域场景下会出现响应头不可见的兼容性问题
  - 处理建议：确认前端实际依赖的响应头集合；若需兼容 BFF，补充 `ennUnifiedAuthorization`、`ennUnifiedCsrfToken` 等 exposed headers，并重新验收登录相关链路

- [x] **M19：修复仍直接信任请求体租户/操作人的 web 入口** ✅ 🚨 鉴权与租户隔离缺口
  - 源 BFF 由统一鉴权上下文提供当前租户/用户，并在 biz/service 层回填请求：例如 `InventoryMaterialBizServiceImpl`、`CodeApplyOrderBizServiceImpl` 都通过 `RemoteParticipantCenterService.getCurrentTenantId()/getCurrentUserId()` 注入真实上下文
  - `inventory-middle` 当前仍有部分 web controller 直接把请求 DTO 透传到 application service，没有使用 `UserContextHolder` 覆盖 `tenantId/operatorId/sourceSystem` 等内部字段：
  - `InventoryMaterialController`：`/page`、`/queryCityGasMaterialPage`
  - `CodeApplyOrderController`：`/create`、`/approval`、`/info`、`/page`
  - 其中 `CodeApplyOrderCreateRequest`、`CodeApplyOrderApprovalRequest` 甚至把 `tenantId`、`operatorId`、`sourceSystem` 设计成请求必填字段，这与原 BFF 由服务端注入上下文的模式不一致
  - 处理建议：web 层统一改为从 `UserContextHolder` 派生租户/用户并补内部字段，避免调用方伪造 tenant/operator；同时复核这些接口是否应拆出 BFF 专用 VO，而不是直接暴露内部 client DTO

- [x] **M20：确认 BFF 的热点缓存能力是否需要在 middle 恢复** ✅ Server 等价性确认
  - 源 BFF starter 开启了 `@EnableCaching`，并配置 `ehcache.xml`
  - 业务上至少存在两个已使用缓存的热点查询：
  - `LogicalPlantBizService.getByName()` → `@Cacheable(cacheNames = "plantGetByName")`
  - `StorageLocationBizService.getByDescription()` → `@Cacheable(cacheNames = "storageGetByDescription")`
  - `inventory-middle` 当前未见 `@EnableCaching`、`ehcache` 配置或对应 `@Cacheable` 替代实现
  - 处理建议：与业务确认这些查询是否仍属于高频热点；若需要维持原性能特征，补缓存方案（Spring Cache/Redis/本地缓存均可）并验收命中行为

- [x] **M21：批量收口未接入 `UserContextHolder` 的遗留 CRUD / 文件 / 流转码入口** ✅（P1 写接口已完成）🚨 统一上下文治理
  - 当前不仅 `InventoryMaterialController`、`CodeApplyOrderController` 存在问题，还发现一批 controller 完全未在 web 层注入租户/操作人上下文，直接透传请求 DTO/Command 到 application service
  - 已确认的高风险入口包括：
  - `AccessoriesFlowCodeController`：`operatorId/sourceSystem` 仍由请求体传入
  - `FileImportController`：`tenantId/operatorId/operator` 仍由请求体传入
  - 生成式 CRUD controller：`InventoryAlertController`、`InventoryDemandController`、`InventoryPlanController`、`InventorySupplyController`、`InventoryMapHisController`、`InventoryMonitorRuleLineController`、`MaterialDocMainController`、`MaterialDocItemController`、`MdocSub*Controller`、`ShipmentController`、`ShipmentLineController` 等，仍直接透传包含 `tenantId/creatorId/updatorId` 的 Command/PageQuery
  - 同时存在“同一 controller 大部分方法已补上下文，但少数 detail/delete/query 漏掉”的局部缺口：如 `WarehouseController.detail`、`InventorySnapshotController.queryDetail`、`MaterialDocController.queryMaterialBatchNo/cityGasExcelPage`、`InventoryTransitController.findById/delete`
  - 建议实施顺序：
  - P1 写接口：优先收口 `create/save/update/approval/occupy/use/import` 这类变更型入口，避免前端伪造 `tenantId/operatorId/sourceSystem/creatorId/updatorId`
  - P2 查询接口：补 `page/detail/query` 中遗漏的 `tenantId` 回填，以及仅靠 `id/ids` 的 tenant-aware 查询/删除约束
  - P3 DTO 整理：对明显暴露内部字段的请求对象，评估拆分 web VO 与内部 DTO，减少后续再次漏收口
  - 处理建议：按“写接口优先、查接口次之”的顺序统一治理；对 BFF/对外入口一律在 web 层覆盖 `tenantId/userId/operatorId/creatorId/updatorId/sourceSystem`，对仅靠 `id/ids` 的详情/删除接口补租户约束或改为 tenant-aware 查询

- [ ] **M22：PlanConfigController 导入/删除空壳实现接通 + ennUnifiedAuthorization 残留清理** 🚨 plan 迁移缺口
  - `importPlanMaterialParam`（line 89-99）、`importPlanMaterial`（line 236-242）、`deletePlanMaterial`（line 251-257）均直接 `buildSuccess()` 无业务逻辑，保留 `ennUnifiedAuthorization` 形参
  - `generatePlan`（line 290-305）保留 `ennUnifiedAuthorization` 形参 **（注：generatePlan 已实现真实逻辑，不是 stub，仅需清理形参）**
  - 底层 `PlanConfigService` 已有 `batchCreatePlanMaterialParam`、`batchCreatePlanMaterial`、`batchDeletePlanMaterial` 方法，controller 未接通
  - Excel BO 类（`PlanMaterialParamImportExcelBO`、`PlanMaterialImportExcelBO`）缺少 `@ExcelProperty` 注解
  - Excel Listener 类（`PlanMaterialParamImportExcelListener`、`PlanMaterialImportExcelListener`）在 middle 中不存在，需新建
  - 处理建议：
    1. 补 `PlanMaterialParamImportExcelBO` 和 `PlanMaterialImportExcelBO` 的 `@ExcelProperty` 注解（参考 BFF 源码 18 列 / 2 列定义）
    2. 新建两个 Excel Listener，使用 `UserContext`（而非 BFF 的 `UserInfo`）获取租户/用户信息，产出 `PlanMaterialParameterBO` / `PlanMaterialBO` 列表
    3. 接通 controller 到 `PlanConfigService` 的 `batchCreatePlanMaterialParam`、`batchCreatePlanMaterial`、`batchDeletePlanMaterial`
    4. 清理 `ennUnifiedAuthorization` 形参（依赖 M17 登录态承接方案）

- [ ] **M23：PlanDemandSupplyStockController detail/tree VO 映射补全** ⚠️ plan 迁移缺口
  - `demandSupplyStockBoardDetail`（line 49-57）：application service 返回 `Map<String, Object>` 包含 `boardData`/`chartData`，但 controller 创建空 `DemandSupplyStockBoardDetailResVO` 未映射
  - `renderBomTree`（line 61-72）：application service 返回的 `children` 是空列表，未实际查 BOM 树
  - 底层 `PlanDemandSupplyStockApplicationServiceImpl` 的 `renderBomTree` 只返回物料基本信息，未调用 `BomCaseApplicationService.renderBomTree`
  - 处理建议：
    1. 在 controller 层将 application service 返回的 `boardData`/`chartData` 映射到 `DemandSupplyStockBoardDetailResVO` 的对应字段
    2. 在 `PlanDemandSupplyStockApplicationServiceImpl.renderBomTree` 中调用 `BomCaseApplicationService.renderBomTree` 获取完整 BOM 树结构
    3. 确认 `DemandSupplyStockBoardDetailDataVO` 和 `DemandSupplyStockBoardDetailChartVO` 字段与 application service 返回数据的对应关系

- [ ] **M24：ProjectTask 完整本地化（DTO/VO → ApplicationService → DomainService → Persistence → DDL）** 🚨 plan 迁移缺口
  - `ProjectTaskController`（3 方法：`projectTaskApply`、`projectTaskQuery`、`projectTaskNotify`）全部返回 `buildFailure("TODO", "计划任务服务待接入")`
  - `ProjectTaskManageController`（1 方法：`projectTaskDetail`）返回 `buildFailure("TODO", "计划任务服务待接入")`
  - 原 BFF 依赖独立 `plan-task` 远端服务（`com.enn.plan.task.client`），在 `inventory-middle` 和 `scm-plan-management` 中均无本地实现
  - 无对应 SQL 表、无 domain service、无 application service
  - 需要新建完整本地链路，涉及四个核心能力：
    - **申请**（`projectTaskApply`）：接收 requestId/projectId/projectType/taskRule/taskData，返回 requestId/taskNo
    - **查询**（`projectTaskQuery`）：按 requestId/taskNo 查询任务结果（含 shipmentPlanOut/predictInventory/optimizeResult）
    - **通知**（`projectTaskNotify`）：接收算法计算结果回调（code/msg/requestId/optimizeResult/predictInventory/shipPlanCheckAmount/shipPlanTime/shipPlanId/availableInventoryDown）
    - **详情**（`projectTaskDetail`）：按 taskNo 查询完整任务详情（含 requestBody/originalBody/tempData/calResultCode/optTarget 等）
  - 需要新建的组件：
    - `interfaces/web/plan/dto/` — `ProjectTaskApplyReqDTO`、`ProjectTaskQueryReqDTO`、`ProjectTaskNotifyReqDTO`
    - `interfaces/web/plan/vo/` — `ProjectTaskApplyResVO`、`ProjectTaskQueryResVO`、`ProjectTaskDetailVO`（含 `TaskResultVO`/`ShipmentPlanOutVO`/`PredictInventoryVO` 等子结构）
    - `interfaces/web/plan/` — `ProjectTaskWebConverter`
    - `application/plan/task/` — `ProjectTaskApplicationService` 接口 + 实现
    - `domain/plan/task/` — `ProjectTask` 领域模型 + `ProjectTaskDomainService`
    - `infra/plan/persistence/` — `ProjectTaskPO`、`ProjectTaskMapper`、`ProjectTaskDao`、对应 XML
    - `docs/sql/plan.sql` — 新增 `pl_project_task` 表 DDL
  - 处理建议：参考 BFF 源码 `ProjectTaskBizServiceImpl`、`ProjectTaskManageBizServiceImpl`、`ProjectTaskObjectConverter` 的业务逻辑，按 middle 现有 plan 包路径规范新建完整链路

- [ ] **M25：PlanOrderController / SparePartController 的 ennUnifiedAuthorization 形参残留清理** ⚠️ plan 迁移缺口
  - `PlanOrderController.issuePlanOrder`（line 70）：`@RequestHeader(value = "ennUnifiedAuthorization", required = false) String token`
  - `SparePartController`（line 40）：`@RequestHeader(value = "ennUnifiedAuthorization", required = false) String token`
  - 与 M17 登录态承接方案联动：M17 确认 `ennUnifiedAuthorization -> tenantId/userId/username` 的承接方案后，这两个 controller 的形参应统一清理
  - 处理建议：在 M17 完成后，统一清理 plan 子包中所有 `ennUnifiedAuthorization` 形参，改为从 `UserContextHolder` 获取上下文

- [ ] **N1：17 个自动生成 CRUD Controller 的 `list()` 方法均为 null stub** ⚠️ 静默损坏查询
   - 以下 controller 的 `list()` 全部返回 `MultiResponse.buildSuccess(null)`（注释 `// 分页查询待实现`）：
     `MdocSubQuantityController`、`MdocSubMaterialController`、`InventoryAlertNotificationController`、`InventoryDemandController`、`InventoryAlertController`、`ShipmentLineController`、`MaterialDocMainController`、`MdocSubFinanceController`、`MdocSubInOutController`、`InventorySupplyController`、`MdocSubMapController`、`InventoryMapHisController`、`ShipmentController`、`MdocSubExtController`、`MaterialDocItemController`、`InventoryPlanController`、`InventoryMonitorRuleLineController`
   - 对比：`WarehouseController`、`LogicalPlantController`、`StorageLocationController` 的 `list()` 已正确实现
   - 处理建议：对每个 stub，接入对应的 ApplicationService 查询方法，或改返回明确的 NOT_IMPLEMENTED 错误（避免调用方收到 `null` 数据误以为是空集合）

- [x] **N2：`MonitorRuleLineConsumer` 缺失 — 预警规则行消息被静默丢弃** ✅ 运行时事件缺口
   - `InventoryTagEnum` 中定义了 `monitorRuleLineUpdateTag` 和 `monitorRuleLineCreateTag` 两个枚举值，说明消息标签已规划
   - 全仓库无任何 `@RocketMQMessageListener` 消费这两个 tag — 源 `inventory-center` 有专用 `MonitorRuleLineConsumer` 处理这两类事件
   - 影响：预警规则行的新建/更新消息在 `stock-inventory-center-topic` 上产出后，`inventory-middle` 无消费者，所有规则行变更触发的预警重算均不生效
   - 处理建议：新建 `MonitorRuleLineConsumer` 监听 `stock-inventory-center-topic`，消费 `monitorRuleLineCreateTag||monitorRuleLineUpdateTag`，触发对应预警规则行的重算逻辑

- [x] **N3：`monitor-rule.topic` 默认值与源系统 topic 不匹配** ✅ 消息消费完全失效
   - `application.yml` 中 `rocketmq.consumer.monitor-rule.topic` 硬编码为 `inventory-monitor-topic`
   - 源 `inventory-center` 对应 producer/consumer 使用的是 `stock-inventory-center-topic`
   - 目前 `MonitorRuleConsumer` 注册的是 `inventory-monitor-topic`，而该 topic 上无任何消息产出 — 消费者永远处于空转状态
   - 其他 consumer topic（`monitor-inoutbound.topic: stock-inventory-center-topic`、`inventory-alert.topic: stock-inventory-center-topic`）与源系统一致 ✅
   - 处理建议：将 `monitor-rule.topic` 的默认值改为 `stock-inventory-center-topic`，与源系统对齐，并重新验收预警规则消费链路

- [ ] **N4：13 张业务表在 `docs/sql/` 中无 DDL，但已有完整 mapper XML** 🚨 数据库初始化不完整
   - 以下表的 mapper XML 已实现完整 INSERT/SELECT/UPDATE 操作，但 `docs/sql/inventory.sql` 和 `plan.sql` 中均无对应 `CREATE TABLE`：
     - 流转码相关（4 张）：`code_apply_order`、`code_apply_order_detail`、`code_apply_approval_record`、`code_record`（mapper: `CodePOMapper.xml`）
     - 文件导入相关（2 张）：`file_import_record`、`file_import_line_record`
     - 库存核心（3 张）：`inventory_log`（注：mapper 文件名有错别字 `InvetoryLogMapper.xml`）、`inventory_material`、`material_logical_plant_ref`
     - 备件相关（2 张）：`sp_delivery_order`、`sp_delivery_order_detail`
     - 其他（2 张）：`unit`、`pl_plan_demand_supply_stock`
   - 影响：从 `docs/sql/` 初始化的新环境将缺少这 13 张表，导致相关功能在运行时直接报错
   - 处理建议：为每张表补写 `CREATE TABLE` DDL 并归入对应的 SQL 文件；同时修正 `InvetoryLogMapper.xml` 文件名拼写错误

- [ ] **N5：所有 `@Scheduled` 任务均无分布式锁，多实例部署将重复执行** ⚠️ 多节点并发风险
   - 受影响的任务（共 6 个）：
     - `MonitorAnnualInspectionJob`（`0 0 2 * * ?`）— 有真实业务逻辑，无分布式锁
     - `DemandPlanDetailGenerateJob`（`0 0 2 * * ?`）— 有真实业务逻辑（调用 `demandPlanService.generateData`），无分布式锁
     - `PlanGenerateJob`（`0 0 1 * * ?`）— 纯 log stub，无业务逻辑，无锁（见 N6 stub 问题）
     - `PlanDemandSupplyStockGenerateJob`（`0 30 1 * * ?`）— 纯 log stub
     - `PlanRedisOperateJob`（`0 0 4 * * ?`）— 纯 log stub
     - `PlanOrderOverdueCheckJob`（`0 0 3 * * ?`）— 注入 `PlanOrderDao` 但执行体为 stub
   - 源系统使用 RDFA 分布式调度器（`@RdfaJob`），保证单节点执行；middle 替换为 Spring `@Scheduled` 后未补充任何并发保护
   - Redisson 已在项目依赖中（`RedissonDistributedLockService` 存在），可直接复用
   - 处理建议：对有真实业务逻辑的任务（`MonitorAnnualInspectionJob`、`DemandPlanDetailGenerateJob`）优先加 Redisson 分布式锁；stub 任务在补实现时同步加锁

- [ ] **N6：4 个 plan 定时任务为 stub，无真实业务逻辑** 🚨 plan 迁移缺口
   - `PlanGenerateJob.execute()`：仅打印日志，无业务逻辑（注释：`stub body`）
   - `PlanDemandSupplyStockGenerateJob.execute()`：仅打印日志，无业务逻辑
   - `PlanRedisOperateJob.execute()`：仅打印日志，无业务逻辑
   - `PlanOrderOverdueCheckJob.execute()`：注入 `PlanOrderDao` 但执行体为 stub
   - 源 `scm-plan-management` 中对应的调度任务均有真实业务逻辑（计划生成、库存计算、Redis 预热、订单逾期检查）
   - 处理建议：按源系统调度逻辑逐一补齐，优先顺序：`PlanOrderOverdueCheckJob`（已有 dao 注入）→ `PlanGenerateJob`（核心计划生成）→ 其余两个

- [ ] **N7：`PlanCommonController` 直接注入 infra 层 stub，违反 DDD 分层约束** ⚠️ 架构越权
   - `PlanCommonController` 在 interfaces 层直接注入：
     - `com.inventory.middle.infra.plan.stub.PlanParticipantStub`（line 48）
     - `com.inventory.middle.infra.plan.stub.PlanProductStub`（line 51，使用全限定名内联注入）
   - interfaces 层（controller）应只依赖 application 层，不得直接调用 infra 层实现
   - 对比：M15 已列出其他 controller 的相同问题（`MaterialDocController`、`InventoryTransitController` 等），但 `PlanCommonController` 未包含在内
   - 处理建议：将 `PlanParticipantStub` 和 `PlanProductStub` 的调用封装到 plan application service 中，controller 改为依赖 application service

- [ ] **N8：`InventoryMonitorRuleLineController`（`/inventorymonitorruleline`）与 `InventoryMonitorRuleController`（`/monitorRule/ruleLine`）形成路由二义性** ℹ️ 代码冗余
   - `/inventorymonitorruleline/**`：自动生成，基础 CRUD stub（含 list() null stub）
   - `/monitorRule/ruleLine/**`：BFF 迁移版，实现 `save`、`page`、`import`、`template`（功能完整）
   - 两个 controller 操作同一张 `inventory_monitor_rule_line` 表，路径不同、实现深度不同
   - 影响：前端若按 BFF 路径调用 `/monitorRule/ruleLine` 可正常使用；若误用 `/inventorymonitorruleline` 会得到 stub 响应
   - 处理建议：评估是否删除或明确废弃 `InventoryMonitorRuleLineController`（自动生成版）

- [ ] **M26：RDFA/Nacos/Dubbo 代码级残留全量清理** ⚠️ 代码洁癖收尾
  - **变量名（11 处，3 个文件）：**
    - `domain/service/material/ihandle/MaterialBatchSysHandle.java`（line 77-78）：`rdfaResult` → `result`
    - `domain/service/monitor/MonitorAnnualInspectionHandleChain.java`（line 217-224）：`rdfaResult` → `result`
    - `domain/service/impl/InventoryLogDomainServiceImpl.java`（line 38-53）：`rdfaResult` → `result`
  - **死代码类（1 个文件）：**
    - `client/dto/HelloDubboDto.java`：全仓库无引用，直接删除
  - **注释掉的 RDFA 代码块（1 处）：**
    - `application/plan/calculate/config/PlanExecutorConfig.java`（line 28-34）：删除注释掉的 `RDFAExecutor.Builder` 代码
  - **`// RDFA import removed;` 占位注释（17 个 client service 文件）：**
    - `client/service/InventoryLogService.java`、`MaterialLogicalPlantRefService.java`、`InventorySnapshotService.java`、`StorageLocationService.java`、`InventoryService.java`、`InventoryMonitoryRuleService.java`、`InventorySupplyService.java`、`WarehouseService.java`、`LogicalPlantService.java`、`InTransitStockService.java`、`InventoryMonitoryRuleLineService.java`、`SparePartService.java`、`InventoryMasterDataSourceService.java`、`MaterialDataService.java`、`InventoryDemandService.java`、`MaterialDocService.java`
    - `client/dto/logicalPlant/CreateLogicalPlantResponse.java`
  - **Javadoc/YAML 注释残留（17 处）：**
    - 8 处 Dubbo Javadoc（`InventoryMaterialExternalServiceImpl`、`InventoryMapExternalServiceImpl`、`RemoteUniformPushService`、`AccessoriesFlowCodeController`、`CodeApplyOrderController`、`InventoryMaterialController`、`FileImportController`、`RemoteInventoryMaterialService`）
    - 7 处 RDFA Javadoc（`DefaultMqProducer`、`InventoryMqProducerImpl`、`RedissonDistributedLockService`、`RedissonLockService`、`MonitorAnnualInspectionJob`、`PlanProductBO`、`PlanBaseBO`）
    - 2 处 Nacos YAML 注释（`application.yml` line 103、line 195）
  - 处理建议：
    1. 先处理变量名重命名和死代码删除（有代码影响）
    2. 再清理注释掉的代码块和占位注释（无运行时影响，但影响代码可读性）
    3. 最后处理 Javadoc/YAML 注释（纯美化，优先级最低）
  - 与 M9 的关系：M9 处理 `inventory-middle-client/pom.xml` 的 maven-shade-plugin RDFA include，M26 处理源码级残留，两者互补，归入 G9

- [x] **Q1：`domain.service.external.MaterialDocOutboundService` 接口无实现类 → Spring 注入失败** ✅ 运行时启动风险
   - `domain/service/external/MaterialDocOutboundService.java` 是一个接口（4 个方法：`doOutbound`、`checkAdjustNumber`、`doBiz` 等）
   - `InventoryAdjustDecHandle.java` 通过 `@Resource private MaterialDocOutboundService materialDocOutboundDefaultHandlerService` 注入此接口
   - 全仓库**无任何类** `implements com.inventory.middle.domain.service.external.MaterialDocOutboundService`
   - 混淆点：`domain/service/outbound/MaterialDocOutboundService.java` 是同名但不同包的**具体类**（extends `MaterialDocBaseService`），方法签名相似但未声明实现该接口
   - Spring 启动时按类型查找 `external.MaterialDocOutboundService` 的 Bean 会失败，导致 `InventoryAdjustDecHandle` 所在 handle chain 无法初始化
   - 处理建议：让 `domain/service/outbound/MaterialDocOutboundDefaultHandlerService`（或适当类）声明 `implements domain.service.external.MaterialDocOutboundService`，或重命名消除二义性

- [x] **Q2：`InventoryChangeMqConsumer` 反序列化到字段不全的 `InventoryChangeMessage` → 移动均价计算数据损坏** ✅ 数据正确性
   - **Producer** (`infra/produce/InventoryChangeMqProduce.java`) 发送 `com.inventory.middle.infra.produce.model.InventoryChangeMessage`（15 个字段，含 `adjustQuantity`、`price`、`inventoryCategory`）
   - **Consumer** (`interfaces/consumer/InventoryChangeMqConsumer.java` line 36) 反序列化到 `com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage`（9 个字段，**缺少** `adjustQuantity`、`price`、`inventoryCategory`）
   - 结果：消费者收到的 message 对象中 `price = null`、`adjustQuantity = null`，传给 `inventoryMapDomainService.cal(message)` 的移动均价计算将产生错误结果或 NPE
   - 处理建议：统一两个 `InventoryChangeMessage` 类，或让消费者使用 infra 层的完整版本（需评估 domain 层是否应依赖 infra DTO）

- [ ] **Q3：`UserContextHolder.get()` 无 null 检查直接链式调用 → 8 处 NPE 风险** 🚨 运行时崩溃
   - 以下位置使用 `UserContext userInfo = UserContextHolder.get()` 后直接 `.getTenantId()` / `.getUserId()` 等，当 UserContextInterceptor 未设置上下文时（M17 描述的场景）将 NPE：
     - `PlanConfigController.java` line 175、210、263、293、348
     - `PlanReportController.java` line 50、75、90
     - `PlanOrderController.java` line 103
   - 对比：`UserContextHolder.getTenantId()`/`.getUserId()` 有 null guard（先检查 ctx != null）；但直接 `.get()` 返回的对象未检查就使用是不安全的
   - 处理建议：将 `UserContext userInfo = UserContextHolder.get()` 后的使用改为 `UserContextHolder.getTenantId()` 等安全方法，或在 `.get()` 后添加 Objects.requireNonNull 抛出明确异常

- [ ] **Q4：`MaterialDocQueryServiceImpl.queryMaterialBatchNo` 返回 null → 已上线接口静默无数据** 🚨 空实现上线
   - `application/service/impl/MaterialDocQueryServiceImpl.java` line 118：`public QueryMaterialBatchNoResDTO queryMaterialBatchNo(MaterialBatchNoQuery query) { return null; }`
   - 对应 controller 入口：`MaterialDocController.java` 调用后直接 `buildSuccess(null)` 返回给前端
   - 不在 N1 覆盖范围（N1 针对自动生成 CRUD controller 的 list 方法），也未在 M22-M24 中记录
   - 处理建议：在 `MaterialDocQueryServiceImpl` 中实现真实的批次号查询逻辑，参考源 `MaterialDocBizServiceImpl.queryMaterialBatchNo`

- [ ] **Q5：25 个 Mapper XML 文件在 `/mybatis/mapper/` 目录未被 MyBatis 加载** ⚠️ 配置死区
   - `application.yml` 配置 `mapper-locations: classpath*:/mapper/**/*.xml`，只扫描 `/mapper/` 目录
   - `/inventory-middle-infra/src/main/resources/mybatis/mapper/` 中存在 25 个同名 XML 文件（`InventoryMonitorRuleMapper.xml`、`InventoryAlertMapper.xml`、`MaterialDocMainMapper.xml`、`InventoryDemandMapper.xml` 等），与 `/mapper/` 中的 namespace 重叠，但**永远不会被加载**
   - 额外问题：`mapper/plan/DemandPlanMapper.xml`（namespace: `infra.plan.persistence.mapper.DemandPlanMapper`）和 `mapper/plan/demand/DemandPlanMapper.xml`（namespace: `infra.plan.persistence.mapper.demand.DemandPlanMapper`）**两份都被加载**，若对应 Java 接口只有一个，另一个 XML 是无效配置
   - 处理建议：**删除** `inventory-middle-infra/src/main/resources/mybatis/mapper/` 整个目录（已确认为历史遗留，永远不被加载）；确认两个 `DemandPlanMapper` 的 Java 接口是否都存在，删除多余的 XML

- [x] **Q6：两个 `InventoryChangeMessage` 类字段不一致** ✅ 消息模型分裂（与 Q2 强关联）
   - `domain/model/bo/mq/sub/InventoryChangeMessage.java`：9 个字段（无 `adjustQuantity`、`price`、`inventoryCategory`）
   - `infra/produce/model/InventoryChangeMessage.java`：15 个字段（含价格/数量/类别）
   - 两个类同名不同包，极易误用；若其他地方引入了 domain 版本的 `InventoryChangeMessage` 来处理 infra 发出的消息，同样会丢失字段
   - 处理建议：与 Q2 一并处理——统一为一个类，确定规范的包路径（建议 domain 层，infra 层引用 domain 版本）

- [ ] **Q7：21 个 RocketMQ 消费者抛原始 `RuntimeException` + 无幂等保护** ⚠️ 无限重试与重复处理风险
   - **无限重试**：所有消费者 catch 块 `throw new RuntimeException(e)`，RocketMQ 默认对 RuntimeException 触发重试（最多 16 次，默认每次间隔递增），若业务逻辑持续失败（如数据库约束冲突），将陷入无限重试死循环
   - **无幂等保护**：无任何消费者在处理前检查 messageId / Redis 去重键 / 数据库唯一约束，RocketMQ 至少一次投递保证意味着重复消息会导致重复业务处理
   - 高危消费者：`InventoryChangeMqConsumer`（移动均价更新）、`InventoryBffMqConsumer`（流转码创建）、`SnMaterialDocCancelConsumer`（取消单冲销）
   - 注：N6 条目记录的是 stub 定时任务，Q7 记录的是已有业务逻辑但无健壮性保护的消费者，是不同问题
   - 处理建议：优先为高危消费者（移动均价、流转码、取消单）添加 Redis 幂等去重；将 `throw new RuntimeException(e)` 替换为 BizException/SysException 或配置消息消费失败策略

- [ ] **Q8：3 处 MyBatis 查询缺 `tenant_id` 过滤 → 跨租户数据泄露** ⚠️ 多租户安全漏洞
   - `mapper/InventorySnapshotMapper.xml` 的 `pageList`（line 386）和 `pageListCount`（line 393）：`SELECT ... FROM inventory_snapshot LIMIT ...`，无 `tenant_id` 过滤，无 `deleted = 0` 过滤
   - `mapper/InvetoryAlertMapper.xml` 的 `pageList`（line 282）和 `pageListCount`（line 288）：同上
   - `mapper/MaterialDocMainMapper.xml` 的 `queryByUniqueNo`（line 382）：无 `tenant_id` 过滤，`uniqueNo` 跨租户唯一性验证失效
   - 影响：分页查询会返回所有租户的已删除数据；单据号唯一性校验可能匹配到其他租户的单据
   - 处理建议：为三处查询补 `AND tenant_id = #{tenantId}` 和 `AND deleted = 0` 约束

- [ ] **Q9：`DemandPlanServiceImpl` 内 3 处业务逻辑 TODO + 2 处 `ParseException` 被静默吞掉** ⚠️ 逻辑不完整
   - **TODO 标记（未完成业务逻辑）：**
     - `DemandPlanServiceImpl.java` line 308：`//TODO`（tenant_id/user_id/company_code 组装块内）
     - line 313：`// 租户id 用户id 公司编码 TODO`
     - line 346：`//这里的先这样组装一下，621版本优化掉 TODO`
   - **ParseException 静默吞掉：**
     - `calculate()` 方法（line 532）：`ParseException` 被 catch 后返回空 Map，调用方无法区分"没有数据"和"日期解析失败"
     - `checkPeriodDate()` 方法（line 1116）：`ParseException` 被 catch 后返回 `false`，导致有效计划期被误判为无效
   - 处理建议：补全 TODO 逻辑（依赖 G4 的 UserContext 承接方案）；ParseException catch 块改为抛出 BizException，避免静默降级

- [ ] **Q10：`DemandPlanServiceImpl.handleDemandPlanDetailByMaterial` `@Transactional` 通过 `this.` 调用失效** ⚠️ 事务边界错误
   - `application/plan/demand/service/impl/DemandPlanServiceImpl.java` line 438：`@Transactional public List<DemandPlanMaterialPO> handleDemandPlanDetailByMaterial(...)`
   - 该方法在同一类中通过 `this.handleDemandPlanDetailByMaterial(...)` 调用（line 664）
   - Spring AOP 代理不拦截 `this.` 内部调用，`@Transactional` 注解在此场景**完全无效**
   - 影响：外部调用该方法时事务正常；当通过 `generateData()` 内部路径调用时，如果 `handleDemandPlanDetailByMaterial` 内部发生异常，外层事务不会因为此方法的失败被感知，数据可能部分写入
   - 处理建议：将 `handleDemandPlanDetailByMaterial` 提取到单独的 Spring Bean，或改用 `ApplicationContext` 自注入调用（后者是 workaround，不推荐）

- [ ] **Q11：`FileController` 直接注入 `infra.integration.oss.OssFileService` → M15 分层修复遗漏** ⚠️ 架构分层违规
   - `interfaces/web/file/FileController.java` line 4：`import com.inventory.middle.infra.integration.oss.OssFileService`
   - interfaces 层 controller 直接注入 infra 层服务，未通过 application service 封装
   - M15 列出的 5 个 controller 均为 external remote service 注入问题，`FileController` 注入的是 infra integration service，场景相同但未覆盖
   - 处理建议：在 G20 与 M15 修复时一并处理，增加 `FileApplicationService` 封装 `OssFileService` 调用

- [ ] **Q12：20 个 application 层 convertor 直接 import `infra.persistence.*Do` → 分层边界渗透** ⚠️ 架构分层违规
   - `application/convertor/` 目录下所有 convertor 类（20+）直接 import 并使用 `com.inventory.middle.infra.persistence.entity.*Do` 类
   - 规范的 DDD 分层中，Do（Persistence Object）到 Domain Object 的转换应在 infra 层的 Repository 实现中完成，application 层不应感知持久化对象
   - 现状导致：任何数据库字段变更都需要同时修改 infra 层实体和 application 层 convertor
   - 处理建议：将 Do → Domain Object 转换逻辑迁移到 infra 层的 RepositoryImpl 中；application 层 convertor 仅处理 Domain → VO/DTO 的转换（此项改动范围较大，建议在 G5/M15 收口后作为长期重构目标）

- [ ] **Q13：11 个孤儿实体类在 `entity/inventory/` 和 `entity/material/` 子包（零引用，死代码）** ℹ️ 代码洁癖
   - `domain/model/entity/inventory/`：`InventoryAlert`、`InventoryAlertNotification`、`InventoryDemand`、`InventoryMap`、`InventoryMapHis`、`InventoryMonitorRule`、`InventoryMonitorRuleLine`、`InventoryPlan`、`InventorySnapshot`、`InventorySupply`、`InventoryTransit`（共 11 个类）
   - `domain/model/entity/material/`：`MaterialDocItem`、`MaterialDocMain`、`MaterialDocument` 等（与 `entity/` 根目录版本重复）
   - 以上类全仓库零引用（仅有 `MaterialCategoryValidator`/`MaterialAdjustTypeValidator` 引用 `entity/material/MaterialDocument`）
   - 活跃使用的版本在 `domain/model/entity/` 根目录下（直接子类，非 inventory/material 子包）
   - 处理建议：删除 `entity/inventory/` 和 `entity/material/` 子包下的全部类（确认无引用后）

- [ ] **Q14：两个 `ResponseCodeEnum` 在 domain 层重叠定义** ℹ️ 代码洁癖
   - `domain/common/constants/ResponseCodeEnum.java`：库存核心域错误码（10001–29999）
   - `domain/plan/common/enums/ResponseCodeEnum.java`：plan 模块错误码（C_*/D_*系列）
   - 两个 enum 都定义了 `SUCCESS("0","成功")`、`FAILED("1","失败")`、`SYSTEM_ERROR(...)`、`REMOTE_SERVICE_FAILED(...)`，值不同
   - `Ex` 工具类使用 plan 版本；业务异常、domain service 使用库存核心版本；两套并存导致新代码选择混乱
   - 处理建议：保留 `domain/common/constants/ResponseCodeEnum`，将 plan 特有的错误码（C_*/D_*）并入该 enum，删除 `domain/plan/common/enums/ResponseCodeEnum`；或明确文档约定各自用途范围

---

## 已完成总览

| 编号 | 任务 | 要点 |
|------|------|------|
| A-K | 库存核心域（快照/在途/凭证/流转码/预警/Dubbo替换/产品中心/模型/巡检/逻辑仓） | 全部完成 |
| L1-L6 | KDLA 对齐（BizException/yml/MdcDot/StopWatch/BaseAssert/SequenceNo） | 全部完成 |
| P1-P10 | plan 包路径去重 + 跨模块重名类 | 全部完成 |
| R1-R9 | BFF 迁移残余（CatchAndLog/WebConfig/SnapshotQuery/Excel导入等） | 全部完成 |
| N1-N5 | EasyExcel 改造 + 外部服务接入（ProductCenter/SpDelivery/CRM） | 全部完成 |
| O1-O5 | 依赖检查/ParticipantCenter接通/FullAddressHelper | 全部完成 |
| V1-V4 | 全量验收（BUILD SUCCESS/CatchAndLog 42/42/RDFA 依赖零残留） | 全部通过（注：代码级命名/注释残留见 M26） |
| C1-C5 | 配置审计（@EnableAspectJAutoProxy/RestTemplateConfig/ThreadPool/Cors） | 全部完成 |
| H1 | Plan MQ Consumer（15 个） | `interfaces/consumer/plan/` |
| H2 | Plan 定时任务（5 个） | `interfaces/schedule/` |
| H3 | PlanDemandSupplyStock 全链路 | PO/Mapper/Dao/ApplicationService/Controller |
| H4-H5 | ProjectTask stub | 源码无实现，保留占位 → 见 M24 待本地化 |
| H6 | DemandSupplySourceDao 补全 | Mapper + XML（366 行） |
| H7-H8 | SupportService 实现类 | InventorySupport + ProductSupport |
| H9 | PlanProductStub 接入真实服务 | RemoteProductCenterRestService |
| H10 | participant-token 认证 | `ParticipantTokenService` + `participant-token`/`appSecret` header |
| H11 | EasyExcel 导入 | `DemandPlanMaterialImportExcelListener`（动态列）+ MultipartFile |
| M1 | queryMaterialInfoByCode | PlanProductStub 查真实物料 |
| M2 | PlanOrder 下发 | `ennUnifiedAuthorization` header + MQ 按 planType 分发 |
| M3 | PlanOrder 计划类型 | PlanConfigService 查物料计划参数 |
| M4 | BomCase 序列号 | SequenceFactory |
| M5 | PlanConfigDao | 取消注释 |
| M6 | MQ 重试 | DefaultMqProducer 指数退避 3 次 |
| M7 | 物料校验 | validateImportInfo 取消注释 |
| X1 | SparePartController | `/spare_part/page/query` + `/sku/save` |
| X2 | InventoryCommonController | `/inventory-common`（companyTree/currency/settlementType/userInfo，省市 stub） |
| X3 | FileController | `/files/download`（RestTemplate 替代 OssApi） |
| L1-L3 | 代码质量 | TODO 清理 / Javadoc / domain 层无 infra 依赖 |

---

## plan 包路径规范

```
✅ 正确                                  ❌ 禁止
application/plan/config/service/        application/plan/plan/config/service/
interfaces/web/plan/PlanXxx.java        interfaces/web/plan/plan/PlanXxx.java
client/plan/plan-config/dto/            client/plan/plan/dto/
infra/plan/persistence/dao/             infra/plan/persistence/dao/plan/
```

- interfaces 层 Converter 必须加 `Web`/`VO` 后缀
- 新增 DTO 前先 grep `client/plan/` 是否已有同名类
