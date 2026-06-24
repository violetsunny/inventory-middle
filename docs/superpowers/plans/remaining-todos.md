# Inventory-Middle 迁移 TODO 追踪

> 多源迁移（inventory-center + inventory-center-bff + scm-plan-management + scm-plan-bff → inventory-middle）任务追踪文档。

## 构建

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home \
  mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
```

## 约束

1. domain 层禁止 Spring 注解
2. plan 包路径禁止 `plan.plan.` 双层嵌套
3. HTTP 外部调用优先 OpenFeign，次选 RestTemplate
4. 编译后必须 BUILD SUCCESS 7/7，然后对完成任务进行标记为 `✅`、[X]

---

## 剩余项（19 项，建议按 9 组理解）

### 建议执行顺序

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

**阶段目标：** 完成迁移收尾，消除 BFF 路由断裂、运行时注入风险和构建残留，让审计结果从“基本迁移完成但有缺口”收敛到“功能、架构、运行时都可闭环”。

**统一原则：**
- 先补功能兼容缺口，再处理优化类或确认类事项
- 运行时风险项优先于代码洁癖类清理项
- 所有收尾项以源码证据和重新验证结果为准，不沿用历史口头结论

#### 阶段 5：plan 子包迁移缺口补齐

1. **M22：PlanConfigController 导入/删除空壳实现 + ennUnifiedAuthorization 残留清理**
   - `importPlanMaterialParam`、`importPlanMaterial`、`deletePlanMaterial`、`generatePlan` 直接 `buildSuccess()` 无业务逻辑
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

- [ ] **G2：缺失本地服务实现与运行时注入风险** `M10 + M11`
  - 包含 `InTransitStockService`、`MaterialDocService` 的本地承接缺口
  - 适合合并原因：都属于“接口还在，但本地实现或 Bean 装配缺失”的同类运行时风险

- [ ] **G3：BFF HTTP 路由与菜单能力兼容** `M12 + M13 + M14`
  - 包含 `ShipmentQuery`、备件流转码/申请单、`CommonController` 的兼容路由与菜单能力
  - 适合合并原因：都属于 BFF HTTP 入口兼容层问题，适合统一按“保路径、补接口、复用本地 service”处理

- [ ] **G4：BFF 登录态、上下文与租户边界治理** `M17 + M18 + M19 + M21`
  - 包含登录态承接、CORS 暴露头、请求体字段信任边界、遗留 CRUD 上下文收口
  - 适合合并原因：本质都在解决“可信用户上下文从哪里来、哪些字段不能再信任请求体”这一条主线

- [ ] **G5：BFF 迁移后的分层与调用方向收尾** `M15`
  - 包含 controller 直连 external/infra service 的架构收口
  - 单独保留原因：它是架构分层问题，建议晚于 G4 处理

- [ ] **G6：非业务 HTTP 入口保留策略确认** `M16`
  - 包含 demo/config 类接口是否继续保留的决策
  - 单独保留原因：这是策略确认项，不是纯技术实现项

- [ ] **G7：BFF 性能特征与缓存等价性确认** `M20`
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

- [ ] **M8：6 个外部 URL 默认值为空** ⏳ 部署配置
  - `application.yml` 中 `remote.*.url` 均为 `${ENV_VAR:}` 占位符
  - 生产环境必须配置，否则相关功能静默失效

- [ ] **M9：清理 `inventory-middle-client` 的 RDFA 构建残留** ⚠️ 迁移收尾 → **归入 G9**
  - `inventory-middle-client/pom.xml` 的 `maven-shade-plugin` 仍显式包含 `top.rdfa.framework:rdfa-biz`
  - 与"已去除 RDFA 运行/构建依赖"的迁移目标不一致，需要确认是否仍有外部兼容诉求
  - 处理建议：删除该 `include`，重新验证 `inventory-middle-client` 打包结果与下游依赖
  - 与 M26 互补：M9 处理构建配置，M26 处理源码级残留，统一归入 G9

- [ ] **M10：补齐 `InTransitStockService` 的本地实现或替换调用链** 🚨 运行时风险
  - `MaterialDocInTmHandle`、`MaterialDocInOutTmHandle` 仍通过 `@Resource` 注入 `com.inventory.middle.client.service.InTransitStockService`
  - 全仓库仅发现接口与调用点，未发现任何实现类或 `@Bean` 装配，存在 Spring 启动注入失败/相关链路不可用风险
  - 处理建议：改为依赖本地 application/domain service，或提供明确的 adapter bean；至少覆盖入库/出入库在途调整链路验收

- [ ] **M11：补齐 `MaterialDocService` 的本地查询实现或替换取消单查询链路** 🚨 运行时风险
  - `SnMaterialDocCancelBuilderHandleChain` 仍通过 `@Resource` 注入 `com.inventory.middle.client.service.MaterialDocService`
  - 全仓库仅发现接口声明与 `getMaterialDocument()` 调用，未发现实现类或 `@Bean` 装配
  - 处理建议：改为依赖本地查询服务/领域服务，重点验收 `SnMaterialDocCancelConsumer` 取消单构建与冲销链路

- [ ] **M12：补齐 `inventory-center-bff` 的 ShipmentQuery HTTP 兼容迁移** ⚠️ BFF Controller 缺口
  - 源 BFF 存在 `ShipmentQueryController`：`/shipmentQuery/v1/query`、`/detail/query`、`/type/query`
  - `inventory-middle` 当前仅有 `ShipmentController`（`/shipment` CRUD 风格），未见 BFF 同路径兼容入口
  - 处理建议：在 `interfaces/web/` 增加 BFF 兼容 controller，复用本地 query/application service，避免前端/BFF 调用断裂

- [ ] **M13：补齐备件流转码与申请单的 BFF 路由兼容及接口覆盖** ⚠️ BFF Controller 缺口
  - `AccessoriesFlowCodeController`：源 BFF 路径为 `/code/accessoriesFlow/**`，当前迁移为 `/accessories-flow-code/**`
  - 当前缺少或未对齐的 BFF 接口包括：`/accessoriesFlow/logs`、`/accessoriesFlow/print`、`/accessoriesFlow/detail` 等兼容入口
  - `CodeApplyOrderController`：源 BFF 路径为 `/code/applyOrder/**`，并区分 `distributor/manufacturer detailInfo/pageList`，当前迁移仅提供通用 `/code-apply-order/{create,approval,info,page}`
  - 处理建议：补 BFF 原路径兼容层，并补齐 `approvalStatus/list`、经销商/厂商分页与详情拆分接口

- [ ] **M14：补齐 `CommonController` 的 BFF 路径与菜单能力** ⚠️ BFF Controller 缺口
  - 源 BFF 路径为 `/common/**`，当前迁移为 `/inventory-common/**`
  - 源 BFF 包含 `/getMenus`，当前 `InventoryCommonController` 未提供该接口
  - `province/city/region` 当前仍是空列表 stub，需要确认是否接受该降级行为
  - 处理建议：补 `/common/**` 兼容入口，评估 `getMenus` 是否必须恢复为真实能力

- [ ] **M15：将仍直连 external service 的 BFF 迁移 Controller 下沉到本地 ApplicationService** ⚠️ 架构收尾
  - 当前仍有多个 web controller 直接注入 external/infra service，而不是本地 application service：
  - `MaterialDocController` → `RemoteProductCenterRestService`
  - `InventoryTransitController` → `RemoteProductCenterRestService`
  - `InventoryCommonController` → `ParticipantCenterRemoteService`
  - `sparepart/DistributorController` → `CrmDistributorRemoteService`
  - `sparepart/DeliveryOrderMgntController` → `SpDeliveryOrderRemoteService`
  - 处理建议：增加本地 ApplicationService 封装外部调用与组装逻辑，保持 `interfaces -> application -> domain/infra` 依赖方向，满足“本地 Controller 和 ApplicationService 替代原 Dubbo/RDFA 远程调用”目标

- [ ] **M16：确认非业务 BFF HTTP 入口是否需要保留兼容迁移** ℹ️ 收尾确认
  - 源 BFF 仍有 `ConfigserviceDemoController`（`/config/logger/level`）这类非业务 HTTP 入口
  - `inventory-middle` 当前未见对应接口
  - 处理建议：与业务方确认该类 demo/调试接口是否可以显式废弃；若需保留，迁移到 `interfaces/web/` 并统一鉴权/日志规范

- [ ] **M17：补齐 BFF 登录态到本地 `UserContext` 的兼容承接** 🚨 Server 迁移缺口
  - 源 BFF starter 启用了 `@EnableUnifiedAuthenticationClient(runType = RunTypeEnum.INTERCEPTOR)`，Controller 普遍依赖 `ennUnifiedAuthorization` 登录态
  - `inventory-middle` 当前仅通过 `UserContextInterceptor` 从 `X-Tenant-Id`、`X-User-Id`、`X-Username` 读取上下文，未见对 `ennUnifiedAuthorization` 的解析/换取/透传拦截处理
  - 现状会导致大量已迁移 Controller 虽仍接收 `ennUnifiedAuthorization`，但 `UserContextHolder` 依赖的租户/用户信息可能为空，与 BFF 原行为不等价
  - 处理建议：补统一鉴权/上下文适配层，至少明确 `ennUnifiedAuthorization -> tenantId/userId/username` 的承接方案，并覆盖 `MaterialDoc`、`InventoryTransit`、`Common`、`Plan` 等依赖 `UserContextHolder` 的入口验收

- [ ] **M18：补齐 BFF 跨域暴露头兼容配置** ⚠️ Server 迁移缺口
  - 源 BFF `CorsConfig` 暴露头默认为 `ennUnifiedAuthorization,ennUnifiedCsrfToken`
  - `inventory-middle` 当前 `CorsConfig` 仅显式暴露 `Authorization`
  - 若前端仍沿用 BFF 登录态/CSRF 头读取方式，浏览器跨域场景下会出现响应头不可见的兼容性问题
  - 处理建议：确认前端实际依赖的响应头集合；若需兼容 BFF，补充 `ennUnifiedAuthorization`、`ennUnifiedCsrfToken` 等 exposed headers，并重新验收登录相关链路

- [ ] **M19：修复仍直接信任请求体租户/操作人的 web 入口** 🚨 鉴权与租户隔离缺口
  - 源 BFF 由统一鉴权上下文提供当前租户/用户，并在 biz/service 层回填请求：例如 `InventoryMaterialBizServiceImpl`、`CodeApplyOrderBizServiceImpl` 都通过 `RemoteParticipantCenterService.getCurrentTenantId()/getCurrentUserId()` 注入真实上下文
  - `inventory-middle` 当前仍有部分 web controller 直接把请求 DTO 透传到 application service，没有使用 `UserContextHolder` 覆盖 `tenantId/operatorId/sourceSystem` 等内部字段：
  - `InventoryMaterialController`：`/page`、`/queryCityGasMaterialPage`
  - `CodeApplyOrderController`：`/create`、`/approval`、`/info`、`/page`
  - 其中 `CodeApplyOrderCreateRequest`、`CodeApplyOrderApprovalRequest` 甚至把 `tenantId`、`operatorId`、`sourceSystem` 设计成请求必填字段，这与原 BFF 由服务端注入上下文的模式不一致
  - 处理建议：web 层统一改为从 `UserContextHolder` 派生租户/用户并补内部字段，避免调用方伪造 tenant/operator；同时复核这些接口是否应拆出 BFF 专用 VO，而不是直接暴露内部 client DTO

- [ ] **M20：确认 BFF 的热点缓存能力是否需要在 middle 恢复** ℹ️ Server 等价性确认
  - 源 BFF starter 开启了 `@EnableCaching`，并配置 `ehcache.xml`
  - 业务上至少存在两个已使用缓存的热点查询：
  - `LogicalPlantBizService.getByName()` → `@Cacheable(cacheNames = "plantGetByName")`
  - `StorageLocationBizService.getByDescription()` → `@Cacheable(cacheNames = "storageGetByDescription")`
  - `inventory-middle` 当前未见 `@EnableCaching`、`ehcache` 配置或对应 `@Cacheable` 替代实现
  - 处理建议：与业务确认这些查询是否仍属于高频热点；若需要维持原性能特征，补缓存方案（Spring Cache/Redis/本地缓存均可）并验收命中行为

- [ ] **M21：批量收口未接入 `UserContextHolder` 的遗留 CRUD / 文件 / 流转码入口** 🚨 统一上下文治理
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
  - `generatePlan`（line 290-305）保留 `ennUnifiedAuthorization` 形参
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
