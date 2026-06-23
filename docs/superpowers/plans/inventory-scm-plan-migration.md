# SCM Plan 迁移计划（scm-plan-management + scm-plan-bff → inventory-middle）

> **面向 AI 代理的工作者：** 必需子技能：superpowers:executing-plans 或 superpowers:subagent-driven-development
> 步骤使用复选框（`- [ ]`）语法来跟踪进度。

**目标：** 将 `scm-plan-management` + `scm-plan-bff` 迁移进 `inventory-middle` 现有各模块，以 `plan` 子包与原 inventory 代码并列，原有业务逻辑不变。
**路径：** /Users/kangll13/aiot/java-code/self/inventory/scm-plan-management 和 /Users/kangll13/aiot/java-code/self/inventory/scm-plan-bff
**kdla：** /Users/kangll13/aiot/java-code/aiot-robot/kdla

**架构决策：**
- **不新建子模块**，代码落在现有模块的 `plan` 子包下，与 `inventory` 包并列，包名以 `plan` 区分：
  - `inventory-middle-client`     → `com.inventory.middle.client.plan.*`（DTO + RpcService 接口）
  - `inventory-middle-domain`     → `com.inventory.middle.domain.plan.*`（实体 / 仓储接口）
  - `inventory-middle-application`→ `com.inventory.middle.application.plan.*`（ApplicationService）
  - `inventory-middle-infra`      → `com.inventory.middle.infra.plan.*`（Mapper/PO/Sequence/外部存根）
  - `inventory-middle-interfaces` → `com.inventory.middle.interfaces.web.plan.*`（Controller）
                                    `com.inventory.middle.interfaces.task.plan.*`（定时任务）
                                    `com.inventory.middle.interfaces.consumer.plan.*`（MQ Consumer）
- RpcService 接口保留定义，由 ApplicationService 实现（去掉 `@DubboService`）；**接口方法签名中的 `RdfaResult<T>` → `SingleResponse<T>`，`PagedRdfaResult<T>` → `PageResponse<T>`**。
- `@DubboReference` → 注入本地 ApplicationService 直调。
- `RemoteInventoryCenterService`（8 个方法）→ 调用 inventory-middle 已有服务（见规则速查），部分方法 inventory-middle 尚无等价实现，在组 P0 预检后补充。
- 外部服务（ParticipantCenter / ProductCenter / PlanTask）→ `// TODO: 待接入` 存根，落在 `infra/plan/stub/`。
- RDFA 全面去除：`RdfaResult` → `SingleResponse`，`PagedRdfaResult` → `PageResponse`，`@Authorize` 删除，`token` header 删除，`UserInfo` → `UserContextHolder`。
- `@RdfaJob` 定时任务 → 替换为 `@Scheduled`；MQ Consumer 全量迁移，topic/group 写入本地 `application.yml`。
- `@ExternalMaterialProcess` AOP（外部物料编码填充）→ 迁移到 `infra/plan/aop/`，切点改为 `application/plan/` 下方法（见组 A-Pre）。
- `bff/InventoryService.java` + `InventoryTestConverter.java`（仅被 TestController 引用）→ **跳过，不迁移**。

**技术栈：** Java 8（/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home）、Spring Boot 2.x、Spring Cloud、KDLA（`top.kdla.framework.*`）（/Users/kangll13/aiot/java-code/aiot-robot/kdla）、MyBatis-Plus、MapStruct、EasyExcel、RocketMQ

> **📌 编译里程碑（2026-06-22）：** `mvn clean compile -DskipTests` 全量 **BUILD SUCCESS**（7/7 模块通过）。代码迁移 + 编译修复 + P0 预检方法补充 + yaml 配置已全部完成。剩余工作为冒烟验证、路由注册检查、git commit（组 O 步骤 2-4）。

---

## 迁移规则速查

| 原始 | 迁移后 |
|---|---|
| `@DubboReference XxxRpcService` | 注入本地对应 `XxxApplicationService` |
| `RdfaResult<T>` 接口签名 / 返回值 | `SingleResponse<T>`（含 RpcService 接口方法签名） |
| `PagedRdfaResult<T>` 接口签名 / 返回值 | `PageResponse<T>` |
| `RemoteInventoryCenterService.listLogicalPlants()` | `LogicalPlantQueryService.list(pageQuery)`（已有，传 tenantId 条件） |
| `RemoteInventoryCenterService.queryLogicalPlantByPlantNo()` | `LogicalPlantQueryService.findByNo(logicalPlantNo)`（已有） |
| `RemoteInventoryCenterService.queryLogicalPlantByOutPlantNo()` | P0 新增 `findByOutPlantNo(outPlantNo, tenantId)` |
| `RemoteInventoryCenterService.queryCurrentInventory()` | `InventorySnapshotQueryService`（或 P0 补充的精确查询方法） |
| `RemoteInventoryCenterService.querySupplyInventory()` | P0 预检后补充（`InventorySupplyQueryService` 目前仅分页接口） |
| `RemoteInventoryCenterService.queryOverdueSupplyInventory()` | P0 预检后补充 |
| `RemoteInventoryCenterService.queryDemandInventory()` | P0 预检后补充（`InventoryDemandQueryService` 目前仅分页接口） |
| `RemoteInventoryCenterService.queryOverdueDemandInventory()` | P0 预检后补充 |
| `RemoteInventoryCenterService.queryMaterialsByLogicalPlantNos()` | P0 预检后补充 |
| `RemoteInventoryCenterService.queryMaterialsByExternalCodes()` | `InventoryMaterialApplicationService.listByMaterialCodeList()`（按 externalCode 查询，P0 确认签名对齐） |
| `RemoteInventoryCenterService.fuzzyQueryMaterialCode()` | P0 补充：在 `InventoryMaterialApplicationService` 新增 `fuzzyQueryByMaterialCodeAndLogicalPlant(keyword, logicalPlantNo, tenantId, maxSize)` |
| `RemoteParticipantCenterService.*` | `PlanParticipantStub`（infra/plan/stub/） |
| `RemoteProductCenterService.*` | `PlanProductStub`（infra/plan/stub/） |
| `PlanTaskService` / `PlanCalResultService` | Controller 层直接返回 TODO 占位响应（不经过 stub，见组 J） |
| `@RdfaJob("xxx")` + `extends RdfaJobHandler` | `@Scheduled(cron="...")` + `@Component` |
| `UserInfo userInfo = getUserInfo()` | `UserContext ctx = UserContextHolder.get()` |
| `top.rdfa.framework.*` import | `top.kdla.framework.*` 对应类 |
| `throw new BusinessException("xxx")` | **继续使用 `BusinessException`**（`com.inventory.middle.domain.common.exception`，已继承 kdla `BizException`，被 `UnifiedExceptionControllerAdvice` 正确捕获）；**不要**改为直接 `throw new BizException(...)` |
| rdfa 异常工厂 / `ExceptionUtils.buildXxx()` | `ExceptionFactory.buildBizException(...)` / `ExceptionFactory.buildSysException(...)` |
| plan PO 的父类（rdfa/自定义基类） | 继承 `BasePO`（`com.inventory.middle.infra.persistence.entity`），字段对齐：`creatorId / updatorId / deleted(int)` |
| 方法内手动 null 检查 `if(x==null) throw` | `BaseAssert.notNull(x, "msg")` / `BaseAssert.isTrue(expr, "msg")`（`top.kdla.framework.validator`） |
| 重要 ApplicationService 方法（无性能埋点） | 酌情加 `@StopWatchWrapper(logHead="xxx", msg="yyy")`（性能耗时）与 `@MdcDot(bizCode="xxx")`（链路 traceId）以对齐现有 inventory 可观测性规范 |

---


## 包落地结构

```
inventory-middle-client/
└── com/inventory/middle/client/
    └── plan/                          ← plan DTO + RpcService 接口（与现有 dto/ 并列）
        ├── dto/bom/
        ├── dto/demand/
        ├── dto/plan/
        ├── dto/common/                ← PlanCommonController 相关 VO/DTO
        ├── dto/inventory/             ← InventoryQueryRequest、LogicalPlant 等 model
        └── service/                   ← BomCaseRpcService 等 7 个接口

inventory-middle-domain/
└── com/inventory/middle/domain/
    └── plan/                          ← 与现有 model/ handles/ repository/ 并列
        ├── bom/
        ├── demand/
        ├── plan/
        └── common/                    ← BusinessException、枚举、CommonConstants

inventory-middle-application/
└── com/inventory/middle/application/
    └── plan/                          ← 与现有 service/ convertor/ 并列
        ├── bom/
        ├── demand/
        └── plan/

inventory-middle-infra/
└── com/inventory/middle/infra/
    └── plan/                          ← 与现有 persistence/ external/ mq/ 并列
        ├── persistence/mapper/
        ├── persistence/entity/
        ├── persistence/convertor/
        ├── sequence/
        ├── aop/                       ← ExternalMaterialFillingProcessSlot + Processor 实现
        └── stub/                      ← PlanParticipantStub / PlanProductStub

inventory-middle-interfaces/
└── com/inventory/middle/interfaces/
    ├── web/plan/                      ← 与现有 web/sparepart/ 并列
    ├── task/plan/                     ← 与现有 task/ 并列（定时任务）
    └── consumer/plan/                 ← 与现有 consumer/ 并列（MQ Consumer）
```

---

## 任务清单

---

### 组 P0：预检 + yaml 补充

**目的：** 全面核查 `RemoteInventoryCenterService` 8 个方法在 inventory-middle 的对应情况，缺失的方法在此组新增；补充 plan 专属 yaml 配置；启用 `@EnableScheduling`。

- [x] **步骤 1：预检并补全 inventory-middle 物料 / 逻辑仓 / 库存查询方法**

  对照下表，逐一确认 inventory-middle-application 中是否存在语义等价的方法，**缺失则补充**（含 domain + infra 一并补全）：

  | management 原始方法 | 预期对应 inventory-middle 服务 | 状态 |
  |---|---|---|
  | `listLogicalPlants(tenantId)` | `LogicalPlantQueryService.list(pageQuery)`（已有，传 tenantId 条件即可，**无需新增**） | ✅ 已有 |
  | `queryLogicalPlantByPlantNo(plantNo, tenantId)` | `LogicalPlantQueryService.findByNo(logicalPlantNo)`（已有，**无需新增**；tenantId 在调用层过滤） | ✅ 已有 |
  | `queryLogicalPlantByOutPlantNo(outPlantNo, tenantId)` | `LogicalPlantQueryService.findByOutPlantNo(String, String)` | ✅ 已补充 |
  | `queryCurrentInventory(materialCode, logicalPlantNo, tenantId)` | `InventorySnapshotQueryService.queryCurrentInventoryByCondition(String, String, String)` | ✅ 已补充 |
  | `querySupplyInventory(request)` | `InventorySupplyQueryService.querySupplyByDay(InventorySupplyByDayQueryBO)` | ✅ 已补充 |
  | `queryOverdueSupplyInventory(request)` | `InventorySupplyQueryService.queryOverdueSupplyTotal(InventorySupplyByDayQueryBO)` | ✅ 已补充 |
  | `queryDemandInventory(request)` | `InventoryDemandQueryService.queryDemandByDay(InventoryDemandByDayQueryBO)` | ✅ 已补充 |
  | `queryOverdueDemandInventory(request)` | `InventoryDemandQueryService.queryOverdueDemandTotal(InventoryDemandByDayQueryBO)` | ✅ 已补充 |
  | `queryMaterialsByLogicalPlantNos(tenantId, logicalPlantNos)` | `InventoryMaterialApplicationService.listByLogicalPlantNos(List<String>, String)` | ✅ 已补充 |
  | `queryMaterialsByExternalCodes(externalCodes, tenant)` | `InventoryMaterialApplicationService.listByMaterialCodeList()` — `ListMaterialCodeRequest` 含 `outMaterialCodeList` 字段，可直接用 | ✅ 已有 |
  | `fuzzyQueryMaterialCode(tenantId, logicalPlantNo, keywords, maxSize)` | `InventoryMaterialApplicationService.fuzzyQueryByMaterialCodeAndLogicalPlant(String, String, String, int)` | ✅ 已补充 |

  > **✅ 2026-06-22 补充完成：** 新增 `InventorySupportServiceImpl`（`application/plan/support/impl/`）桥接 plan 代码到已有 inventory 服务。所有 8 个方法均已实现并通过编译。

- [x] **步骤 2：在 `inventory-middle-starter` 配置类上添加 `@EnableScheduling`**

  ✅ 已存在（`ProviderApplication.java` 第 17 行）

- [x] **步骤 3：补充 `application.yml` 配置**

  ✅ 已补充（`urbanGasProject`、`rocketmq.plan`、`plan.job` 均已写入 `application.yml`）

- [x] **验证：** `mvn clean compile` 通过（2026-06-22 BUILD SUCCESS）

---

### 组 A-Pre：ExternalMaterialProcess AOP 迁移

**目的：** 在业务组（B~H）之前迁移 AOP，避免迁移后各 ApplicationService 的外部物料编码填充逻辑静默失效（无编译报错，但业务数据错误）。

**源文件：**
- `ServiceHandlerAspect.java`（Aspect 定义）
- `ExternalMaterialFillingProcessSlot.java`（AOP slot）
- `ExternalMaterialProcess.java`（注解）
- `ExternalMaterialFillingProcessor.java`（扩展点接口）
- `DefaultExternalMaterialFillingProcessor.java`（默认实现：直接用 materialCode）
- `UrbanGasExternalMaterialFillingProcessor.java`（城燃实现）
- `ExtensionManager.java`（扩展点查找工具）

- [x] **步骤 1：将上述文件迁移到 `infra/plan/aop/`，换包名**

- [x] **步骤 2：修改 `ServiceHandlerAspect` 切点**，从 facade 层改为 application 层：
  ```java
  // Before
  @Pointcut("execution(* com.enn.plan.management.facade.*.service..*.*(..))")

  // After
  @Pointcut("execution(* com.inventory.middle.application.plan..*.*(..))")
  ```

- [x] **步骤 3：搜索所有使用 `@ExternalMaterialProcess` 注解的方法**（在 management facade 层），确认迁移到 ApplicationService 后注解也一并保留。

- [x] **验证：** `mvn clean compile` 通过

---

### 组 A：公共类（DTO / 枚举 / 异常 / RpcService 接口）

**目标模块：** `inventory-middle-client`（DTO + 接口）、`inventory-middle-domain`（common/枚举）

**源：**
- `scm-plan-management-client/` → bom/demand/plan 三包 DTO
- `scm-plan-management-client/` → RpcService 接口（7 个）
- `scm-plan-bff-common/` → `CommonConstants`、`BusinessException`、枚举

- [x] **步骤 1：复制 management client DTO 到 `client/plan/dto/`**
  - 换根包：`com.enn.plan.management.client` → `com.inventory.middle.client.plan`
  - `top.rdfa.framework.biz.ro.*` → `top.kdla.framework.dto.*`（`SingleResponse`、`PageResponse` 等）
  - 落地包：`bom/dto/`（13）、`demand/dto/`（26）、`plan/dto/`（44）；`BaseDTO`、`PageRequest` 在根包

- [x] **步骤 2：复制 RpcService 接口（7 个）到 `client/plan/service/`**
  - 去掉 `@DubboService` 注解
  - `RdfaResult<T>` → `SingleResponse<T>`，`PagedRdfaResult<T>` → `PageResponse<T>`
  - 落地：`bom/service/BomCaseRpcService`、`demand/service/{DemandBoardDetailRpcService,DemandPlanRpcService}`、`plan/service/{PlanConfigRpcService,PlanDemandSupplyStockRpcService,PlanGenerateRpcService,PlanOrderRpcService}`

- [x] **步骤 3-Pre：`BusinessException` 评估结论**
  - 现有 `com.inventory.middle.domain.common.exception.BusinessException` 已继承 kdla `BizException`，且构造器签名与 bff 版完全对齐（6 个构造器），**不需要新建**；plan 代码继续用现有 BusinessException 即可。

- [x] **步骤 3：复制 bff-common（`CommonConstants`、枚举）到 `domain/plan/common/`**
  - 8 个枚举（`DeliveryParamsKeyEnum`/`DemandStrategyEnum`/`MaterialDemandTypeEnum`/`PlanMaterialParamPlanTypeEnum`/`PlanOrderCreateTypeEnum`/`PlanOrderStatusEnum`/`PlanRelatedBomEnum`/`SafetyStockOptionEnum`）→ `domain/plan/common/enums/`
  - `CommonConstants` → `domain/plan/common/constants/PlanCommonConstants`
  - `BusinessException` 跳过（复用现有）；`UnifyAuthException` 跳过（plan 代码无需独立认证异常）

- [x] **步骤 4：迁移 bff-integration 层的 integration model 类到 `client/plan/dto/inventory/`**
  - `InventoryQueryRequest`、`LogicalPlant`（bff 侧的 model）、`ParticipantUser`、`ParticipantMenuDTO`、`ParticipantDeptDTO`

- [x] **验证：** `mvn clean compile` 通过（BUILD SUCCESS）

---

### 组 B：BOM 清单

**接口（8 个，路径 `/bomCase`）：** create / update / pageQueryBOM / detail/queryBasicDetail / detail/pageQueryChildrenDetail / detail/queryChildrenDetail / renderBomTree / changeBomCaseStatus

**外部依赖：** `RemoteParticipantCenterService.getCompanyName()` → `PlanParticipantStub`

- [x] **步骤 1：迁移 BOM domain 层到 `domain/plan/bom/`**（实体 + 仓储接口）

- [x] **步骤 2：迁移 BOM infra 层到 `infra/plan/persistence/`**（`BomCasePO`、`BomNodePO`、mapper XML 到 `resources/mapper/plan/`、`BomCaseDao`、`BomNodeDao`）

- [x] **步骤 3：创建 `infra/plan/stub/PlanParticipantStub.java`**（本组首次引入，后续各组复用）

  ```java
  @Service @Slf4j
  public class PlanParticipantStub {
      public String getCompanyName(String tenantId, String userId, String companyCode) {
          log.warn("TODO: 待接入 ParticipantCenter - getCompanyName tenantId={}", tenantId);
          return null;
      }
      public UserContext getCurrentUserInfo() {
          log.warn("TODO: 待接入 ParticipantCenter - getCurrentUserInfo，降级使用 UserContextHolder");
          return UserContextHolder.get();
      }
      public List<ParticipantUser> fuzzyQueryUserInfo(String keywords) {
          log.warn("TODO: 待接入 ParticipantCenter - fuzzyQueryUserInfo");
          return Collections.emptyList();
      }
      public List<ParticipantMenuDTO> getMenuAndFunc(String tenantId, String userId) {
          log.warn("TODO: 待接入 ParticipantCenter - getMenuAndFunc");
          return Collections.emptyList();
      }
  }
  ```

- [x] **步骤 4：迁移 `BomCaseApplicationService`（`application/plan/bom/`），实现 `BomCaseRpcService`**
  - facade 层 Impl 的 `return RdfaResult.success(x)` → `return SingleResponse.of(x)`
  - `@ExternalMaterialProcess` 注解保留（切点已在 A-Pre 中更新）
  - `RemoteParticipantCenterService.getCompanyName()` → `planParticipantStub.getCompanyName()`

- [x] **步骤 5：迁移 `BomCaseController` 到 `interfaces/web/plan/`（去 RDFA，注入 `BomCaseApplicationService`）**
  - 双侧 Converter 一并迁移到 `application/plan/bom/convertor/`

- [x] **验证：** `mvn clean compile` 通过

---

### 组 C：需求计划（DemandPlan）

**接口（10 个，路径 `/demandPlan`）：** createDemandPlan / updateDemandPlan / downloadDemandPlanMaterialTemplate / importDemandPlanMaterial / updateDemandPlanStatus / cancelDemandPlanMaterial / modifyDemandPlanMaterialAmount / selectDemandPlanByPage / selectDemandPlanByLogicalPlantNos / demandPlanDetail

**外部依赖：** `RemoteParticipantCenterService.getCurrentUserInfo()` → `UserContextHolder.get()`

- [x] **步骤 1：迁移 demand domain/infra 层**（`DemandPlanPO`、`DemandPlanMaterialPO` + mapper XML 到 `resources/mapper/plan/` + Dao）

- [x] **步骤 2：迁移 `DemandPlanApplicationService`（`application/plan/demand/`），实现 `DemandPlanRpcService`**
  - `return RdfaResult.success(x)` → `return SingleResponse.of(x)`
  - `remoteParticipantCenterService.getCurrentUserInfo()` → `UserContextHolder.get()`

- [x] **步骤 3：迁移 `DemandPlanController` 到 `interfaces/web/plan/`（去 RDFA、去 token header）**
  - 删除 `requestBO.setToken(token)`
  - Converter + `DemandPlanMaterialImportExcelListener` 到 `application/plan/demand/`

- [x] **验证：** `mvn clean compile` 通过

---

### 组 D：需求看板（DemandBoard）

**接口（2 个，路径 `/demandBoard`）：** detail / selectMaterialsByName

**外部依赖：** `RemoteInventoryCenterService.queryMaterialsByExternalCodes()` → P0 预检后的 `InventoryMaterialApplicationService` 方法

- [x] **步骤 1：迁移 `DemandBoardDetailApplicationService`（`application/plan/demand/`），实现 `DemandBoardDetailRpcService`**
  - 注入 P0 步骤 1 确认后的 `InventoryMaterialApplicationService.listByMaterialCodeList()`

- [x] **步骤 2：迁移 `DemandBoardDetailController` 到 `interfaces/web/plan/`（去 RDFA）**

- [x] **步骤 3：Converter 到 `application/plan/demand/convertor/`**

- [x] **验证：** `mvn clean compile` 通过

---

### 组 E：计划配置（PlanConfig）

**接口（16 个，路径 `/planConfig`）：** downloadPlanMaterialParamTemplate / importPlanMaterialParam / pageQueryPlanMaterialParam / queryPlanMaterialParam / updatePlanMaterialParam / createPlan / updatePlan / pageQueryPlan / changePlanStatus / downloadPlanMaterialTemplate / importPlanMaterial / deletePlanMaterial / queryPlanMaterial / generate / queryPlanParamList / queryPlanMaterialParamList / queryPlanTransferLogicalPlants

**外部依赖：**
- `RemoteInventoryCenterService.listLogicalPlants()` → `LogicalPlantQueryService.list(pageQuery)`（已有，传 tenantId 条件即可）
- `RemoteParticipantCenterService.*`（bff 侧）→ `PlanParticipantStub`
- `generate()` 中 `PlanGenerateRpcService` → 本地 `PlanGenerateApplicationService`（组 F）

- [x] **步骤 1：迁移 plan config domain/infra**（`PlanPO`、`PlanMaterialPO`、`PlanMaterialParameterPO` + mapper XML 到 `resources/mapper/plan/`）

- [x] **步骤 2：迁移 `PlanConfigApplicationService`（`application/plan/plan/`），实现 `PlanConfigRpcService`**
  - `return RdfaResult.success(x)` → `return SingleResponse.of(x)`
  - 注入 `LogicalPlantQueryService`；`RemoteParticipantCenterService.*` → `PlanParticipantStub`

- [x] **步骤 3：迁移 `PlanConfigController` 到 `interfaces/web/plan/`（去 RDFA、去 token）**
  - `RemotePlanReportService.generate()` → 注入 `PlanGenerateApplicationService`

- [x] **步骤 4：迁移 Excel Listener（`PlanMaterialParamImportExcelListener`、`PlanMaterialImportExcelListener`）到 `application/plan/plan/`**

- [x] **步骤 5：Converter 到 `application/plan/plan/convertor/`**

- [x] **验证：** `mvn clean compile` 通过

---

### 组 F：计划报表（PlanReport）

**接口（3 个，路径 `/planReport`）：** pageQueryMaterialPlans / queryMaterialInstanceReport / renderMaterialBomTree

- [x] **步骤 1：迁移 PlanInstance domain/infra**（`MaterialPlanInstancePO`、`MaterialPlanDetailPO` + mapper XML 到 `resources/mapper/plan/`）

- [x] **步骤 2：迁移 `PlanGenerateApplicationService`（`application/plan/plan/`），实现 `PlanGenerateRpcService`**
  - `return RdfaResult.success(x)` → `return SingleResponse.of(x)`
  - 注入 P0 补充的 `querySupplyInventory` / `queryDemandInventory` 等方法

- [x] **步骤 3：迁移 `PlanReportController` 到 `interfaces/web/plan/`（去 RDFA）+ Converter**

- [x] **验证：** `mvn clean compile` 通过

---

### 组 G：计划订单（PlanOrder）

**接口（7 个，路径 `/planOrder`）：** createManualPlanOrder / queryPlanOrder / updatePlanOrder / issuePlanOrder / confirmPlanOrder / pageQueryPlanOrder / pageQueryPlanOrderIssueDetail

**外部依赖：** `issuePlanOrder` 中 token → 删除，相关 DTO 的 token 字段设 null + `// TODO: 待接入外部下发 token`

- [x] **步骤 1：迁移 PlanOrder domain/infra**（`PlanOrderPO`、`PlanOrderIssueDetailPO`、`PlanOrderReceiptPO` + mapper XML）

- [x] **步骤 2：迁移 `PlanOrderApplicationService`（`application/plan/plan/`），实现 `PlanOrderRpcService`**
  - `return RdfaResult.success(x)` → `return SingleResponse.of(x)`

- [x] **步骤 3：迁移 `PlanOrderController` 到 `interfaces/web/plan/`（去 RDFA、去 token）+ Converter**

- [x] **验证：** `mvn clean compile` 通过

---

### 组 H：供需存看板（PlanDemandSupplyStock）

**接口（3 个，路径 `/planDemandSupplyStock`）：** demandSupplyStockBoard / demandSupplyStockBoardDetail / renderBomTree

**外部依赖：**
- `demandSupplyStockBoard` 中 token header → 删除，DTO token 字段设 null + `// TODO`
- bff `PlanDemandSupplyStockServiceImpl.renderBomTree()` 中 `@DubboReference BomCaseRpcService` → `BomCaseApplicationService`

- [x] **步骤 1：迁移 `PlanDemandSupplyStockApplicationService`（`application/plan/plan/`），实现 `PlanDemandSupplyStockRpcService`**
  - 先读 management `PlanDemandSupplyStockServiceImpl` 确认内部依赖链
  - `return RdfaResult.success(x)` → `return SingleResponse.of(x)`

- [x] **步骤 2：迁移 `PlanDemandSupplyStockController` 到 `interfaces/web/plan/`（去 RDFA、去 token）**
  - bff service 中 `BomCaseRpcService` → `BomCaseApplicationService`

- [x] **步骤 3：Converter 到 `application/plan/plan/convertor/`**

- [x] **验证：** `mvn clean compile` 通过

---

### 组 I：公共资源（PlanCommonController）

**接口（6 个，路径 `/common`）：**

| 路径 | 迁移策略 |
|---|---|
| `/common/queryMaterialInfoByCode` | `LogicalPlantQueryService` + `PlanProductStub` |
| `/common/queryLogicalPlantByTenantId` | `LogicalPlantQueryService.list(pageQuery)`（已有，传 tenantId 条件） |
| `/common/queryWarehouseByTenantId` | `WarehouseApplicationService` |
| `/common/fuzzyQueryUserInfo` | `PlanParticipantStub` |
| `/common/fuzzyQueryMaterialCode` | P0 补充的 `InventoryMaterialApplicationService.fuzzyQueryByMaterialCodeAndLogicalPlant()` |
| `/common/getMenuAndFunc` | `PlanParticipantStub` |

- [x] **步骤 1：创建 `infra/plan/stub/PlanProductStub.java`**

  ```java
  @Service @Slf4j
  public class PlanProductStub {
      public Object queryMaterialByCode(String materialCode, String tenantId) {
          log.warn("TODO: 待接入 ProductCenter - queryMaterialByCode materialCode={}", materialCode);
          return null;
      }
  }
  ```

- [x] **步骤 2：迁移 `PlanCommonController` 到 `interfaces/web/plan/`（去 RDFA，`UserInfo` → `UserContextHolder`）**

- [x] **步骤 3：迁移相关 VO 到 `client/plan/dto/common/`**（`LogicalPlantResVO`、`WarehouseResVO`、`MaterialInfoQueryResVO`、`UserFuzzyQueryResVO` 等）

- [x] **验证：** `mvn clean compile` 通过

---

### 组 J：计划任务（ProjectTask）

**接口（4 个）：** `ProjectTaskController`（3 个）+ `ProjectTaskManageController`（1 个）

全链路依赖外部 `plan-task` 服务（Dubbo `PlanTaskService`）。不引入 `plan-task-client` 依赖，Controller 层直接返回 TODO 占位响应。

- [x] **步骤 1：迁移 `ProjectTaskController` + `ProjectTaskManageController` 到 `interfaces/web/plan/`**

  ```java
  // 每个方法体统一写法：
  @PostMapping("/projectTaskApply")
  public SingleResponse<?> projectTaskApply(@RequestBody Object reqDTO) {
      log.warn("TODO: 待接入 PlanTask - projectTaskApply");
      return SingleResponse.buildFailure("TODO", "计划任务服务待接入");
  }
  ```

- [x] **验证：** `mvn clean compile` 通过

---

### 组 K：城燃项目（UrbanGasProject）

**接口（1 个，路径 `/urbanGasProject/queryMaterialLeadTime`）：** 读本地 yaml，无外部依赖。

- [x] **步骤 1：迁移 `UrbanGasProjectController` 到 `interfaces/web/plan/`（`RdfaResult` → `SingleResponse`，其余不变）**
  - `@Value("${urbanGasProject.standardLeadTime}")` 等注入已在 P0 步骤 3 补充的 yaml 中

- [x] **验证：** `mvn clean compile` 通过

---

### 组 M：Processor 层（MQ Consumer + 定时任务）

**MQ Consumer（14 个）：** 全部监听 `${rocketmq.plan.demand.topic}` 或 `${rocketmq.plan.inventory.topic}`

**定时任务（5 个）：** 原使用 `@RdfaJob` + `extends RdfaJobHandler`，改 `@Scheduled`（`@EnableScheduling` 已在 P0 步骤 2 启用）

- [x] **步骤 1：迁移全部 MQ Consumer 到 `interfaces/consumer/plan/`**
  - 换根包，去掉 RDFA import，保留 `@RocketMQMessageListener` 注解
  - Consumer 内部调用 → 注入对应 `XxxApplicationService`
  - **在每个 Consumer 的 `onMessage()` 方法上添加可观测性注解**，对齐现有 inventory Consumer 规范：
    ```java
    @MdcDot(bizCode = "planXxxConsumer")                           // 链路 traceId 注入（top.kdla.framework.common.aspect.mdc）
    @StopWatchWrapper(logHead = "planXxxConsumer", msg = "消息处理")  // 消息处理耗时监控（top.kdla.framework.common.aspect.watch）
    public void onMessage(MessageExt msg) { ... }
    ```

- [x] **步骤 2：迁移 5 个定时任务到 `interfaces/task/plan/`**

  ```java
  // Before
  @RdfaJob("planGenerateJob")
  public class PlanGenerateJob extends RdfaJobHandler { ... }

  // After
  @Component @Slf4j
  public class PlanGenerateJob {
      @Scheduled(cron = "${plan.job.generate.cron:0 0 1 * * ?}")
      public void execute() { /* 原 execute 方法体 */ }
  }
  ```

- [x] **步骤 3：迁移 `DemandSupplySourceConverter`（processor 内部 Converter，放 `application/plan/plan/convertor/`）**

- [x] **验证：** `mvn clean compile` 通过

---

### 组 N：DAL 基础设施（Sequence）

management 使用自建 Sequence 机制（`SequenceFactory` / `SequenceImpl` / `SequenceMapper`），非 DB 自增，必须迁移。

> **⚠️ 关键前置：** inventory-middle 中已存在 `CodeGeneratorCfgMapper` + `CodeGeneratorCfgDo` + `CodeGeneratorCfgRepositoryImpl`，与 management 的 `SequenceMapper` 操作的是同一类表（`t_code_generator_cfg`）。**在迁移前先执行步骤 0 的复用可行性确认，避免重复建设。**

- [x] **步骤 0（前置确认）：核查 Sequence 表结构是否可复用**

  | 检查项 | 方法 |
  |---|---|
  | management `t_code_generator_cfg` DDL | 查 management 的 `db/` 或 flyway 脚本 |
  | inventory-middle 同名表 DDL | 查 `inventory-middle-infra/src/main/resources/db/` 或连接已有数据库 |
  | 字段对比 | 重点关注 `code / rule / max_value / is_cache / cache_num / remark` 是否对齐 |

  - **若字段完全一致**（推荐路径）：plan 的 Sequence 操作**直接注入现有 `CodeGeneratorCfgRepositoryImpl`**，跳过步骤 1~2；仅需在 management 的 SequenceFactory/Impl 中将 `SequenceMapper` 引用替换为注入 `CodeGeneratorCfgRepository`，完成后落在 `infra/plan/sequence/` 下仅保留门面类（约 1 个类）
  - **若字段不一致**：执行步骤 1~2，独立迁移（含独立 DDL 脚本）

- [x] **步骤 1：迁移 Sequence 相关全部类到 `infra/plan/sequence/`（换包名，逻辑不变）**

  > **仅当步骤 0 确认"字段不一致"时执行**

- [x] **步骤 2：Sequence mapper XML 放入 `inventory-middle-infra/src/main/resources/mapper/plan/`**（已被 `classpath*:/mapper/**/*.xml` 自动扫描，无需修改 yaml）

  > **仅当步骤 0 确认"字段不一致"时执行**

- [x] **验证：** 依赖 Sequence 的 ApplicationService 编译通过；确认 `t_code_generator_cfg`（或 plan 专属表）中存在对应 `code` 配置行

---


### 组 O：全量编译 + 冒烟验证

- [x] **步骤 1：全量编译**
  ```bash
  cd /Users/kangll13/aiot/java-code/self/inventory-middle
  mvn clean compile -DskipTests
  ```
  ✅ BUILD SUCCESS（2026-06-22，7/7 模块通过）

- [ ] **步骤 2：启动冒烟**
  ```bash
  cd inventory-middle-starter && mvn spring-boot:run
  ```
  检查：应用启动无报错，`/actuator/health` 返回 UP

- [ ] **步骤 3：验证路由注册（actuator/mappings 中确认以下路径存在）**
  - `/bomCase`、`/demandPlan`、`/planConfig`、`/planOrder`、`/planReport`
  - `/planDemandSupplyStock`、`/demandBoard`、`/common`、`/urbanGasProject`、`/projectTask`

- [ ] **步骤 4：git commit**
  ```bash
  git add .
  git commit -m "feat(plan): migrate scm-plan-management + scm-plan-bff into inventory-middle plan packages"
  ```

---

## 风险提示

1. **P0 新增方法工作量**：`RemoteInventoryCenterService` 中 `querySupplyInventory` / `queryDemandInventory` 等 8 个方法在 inventory-middle 中均无等价实现，补充这些方法需要同时修改 domain / infra 层，是整个迁移中工作量最大的前置任务。
2. **Sequence 机制**：management 自建 Sequence 依赖独立数据库表，迁移后确认表存在且 datasource 指向正确。
3. **cron 表达式**：原配置在 Apollo，迁移时从运维或配置中心查出实际值填入 yaml，P0 中的值是占位符。
4. **MQ topic 实际值**：`rocketmq.plan.demand.topic` 等值以原 Nacos 为准，P0 中是占位符。
5. **EasyExcel 版本**：确认 parent pom 中已有 EasyExcel 依赖；若无，在 `inventory-middle-infra/pom.xml` 中追加。
6. **BusinessException 异常捕获盲区**：若 management 的 `BusinessException` 未继承 kdla 的 `BizException`，迁移后抛出时会命中 `UnifiedExceptionControllerAdvice` 的兜底 `handleThrowable`，响应码为 `SYS_ERROR("007")`，前端无法区分业务错误与系统错误。**组 A 步骤 3-Pre 必须执行，不可跳过**。
7. **PO 基类字段不对齐**：management 的 PO 父类（rdfa/自定义）字段名可能为 `creator / updator / isDelete`，与 inventory-middle `BasePO` 的 `creatorId / updatorId / deleted(int)` 不同；迁移各组 PO 时须逐一比对字段，并同步更新 MyBatis XML 中的 `<resultMap>` 与 `<insert>/<update>` 语句，避免字段映射错误导致审计字段（创建人/更新人）写入失败。
8. **Sequence 表重复建设风险**：inventory-middle 已有 `CodeGeneratorCfgMapper` 操作同类表，若跳过组 N 步骤 0 直接迁移独立 Sequence，会在同一数据库中产生两套流水号配置表，后续维护混乱。**组 N 步骤 0 的复用确认必须在步骤 1 之前完成**。
9. **计划配置（PlanConfig）**：`PlanConfigApplicationService` 中 `listByPlanCodeList()` 方法在 inventory-middle 中无等价实现，迁移后需补充。
10. **包和类重复创建风险**：不要重复创建类、接口、方法、字段、常量、注释、配置项、SQL 等；如果迁移过程中有重复创建，需要进行合并和检查。
