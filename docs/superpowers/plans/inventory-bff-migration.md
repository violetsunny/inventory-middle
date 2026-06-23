# inventory-center-bff 迁移到 inventory-middle 实现计划

> **面向 AI 代理的工作者：** 必需子技能：使用 superpowers:subagent-driven-development（推荐）或 superpowers:executing-plans 逐任务实现此计划。步骤使用复选框（`- [ ]`）语法来跟踪进度。

**目标：** 将 inventory-center-bff 的全部 HTTP Controller 层迁移至 inventory-middle，以本地 ApplicationService 直接替代原 Dubbo/RDFA 远程调用，去除 RDFA 认证框架，改用 Spring Security / ThreadLocal 传递用户上下文。

**架构：** BFF 层迁入 `inventory-middle-interfaces/web/`（Controller）；BizService 逻辑内联至现有 ApplicationService；原 RemoteXxxService（Dubbo）全部替换为本地 ApplicationService 直接调用；RemoteParticipantCenterService（用户/租户）改为 `UserContextHolder`（本地 ThreadLocal）；ProductCenter HTTP 调用保留为 OpenFeign/RestTemplate。

**技术栈：** Java 8、Spring Boot 2.x、KDLA `SingleResponse/PageResponse` + `@CatchAndLog` + `LogInterceptor`（`/Users/kangll13/aiot/java-code/aiot-robot/kdla`，版本 `1.0.1-SNAPSHOT`）、MyBatis-Plus、Spring Cloud OpenFeign、MapStruct、EasyExcel

---
<!-- 详细任务内容见下方各 Section -->

---

## 全局迁移规则

| 原 BFF 机制 | inventory-middle 替换方案 |
|-------------|--------------------------|
| `RdfaResult<T>` / `PagedRdfaResult<T>` | `SingleResponse<T>` / `PageResponse<T>` |
| `RemoteParticipantCenterService.getCurrentTenantId()` | `UserContextHolder.getTenantId()`（需新建）|
| `RemoteParticipantCenterService.getCurrentUserId()` | `UserContextHolder.getUserId()` |
| `RemoteXxxService`（Dubbo 调用 inventory-center） | 本地 `XxxApplicationService` 直接注入 |
| `RemoteProductCenterRemoteService`（HTTP 产品中心） | 保留 `ProductExternalServiceImpl`（已有） |
| `RemoteParticipantCenterService.getUserNameMap` | 本地存根 / 暂用 userId 代替 userName |
| `RemoteParticipantCenterService.getCompanyNameMap` | 本地存根 / 暂用 companyCode 代替 companyName |
| `@Authorize`（RDFA 鉴权注解） | 删除，由 Spring Security 拦截器统一处理 |
| `@RequestHeader("ennUnifiedAuthorization") String token` | 删除，token 不再透传 |
| Controller 返回 `RdfaResult.success(data)` | 改为 `SingleResponse.of(data)` |
| Controller 返回 `PagedRdfaResult.success(...)` | 改为 `PageResponse.of(...)` |
| Controller 异常处理（BizException/SysException 裸抛） | 每个 Controller 类上加 `@CatchAndLog`（kdla-component-log）；同时在 `application.yml` 启用 `kdla.config.catch-log.enabled: true` |

---

## 业务分组总览

| 分组 | 业务域 | 源 Controller | 优先级 |
|------|--------|---------------|--------|
| **P0** | 基础设施：用户上下文 | — | 最高 |
| **A** | 物料凭证（创建/查询/导出） | `MaterialDocController` | 高 |
| **B** | 库存快照（查询/导出/城燃） | `InventorySnapshotController` | 高 |
| **C** | 逻辑仓 / 库位 / 仓库 | `LogicalPlantController` / `StorageLocationController` / `WarehouseController` | 高 |
| **D** | 监控规则 | `MonitorRuleController` | 中 |
| **E** | 在途库存 | `InTransitStockController` | 中 |
| **F** | 移动平均价 | `MapController` | 中 |
| **G** | 库存物料 | `InventoryMaterialController` | 中 |
| **H** | 备件流转码 / 码申请 / 经销商出库单 | `AccessoriesFlowCodeController` / `CodeApplyOrderController` / `DeliveryOrderMgntController` / `DistributorController` | 中 |
| **I** | 备件 SKU / 运单查询 / 文件 / 公共 | `SparePartController` / `ShipmentQueryController` / `FileController` / `CommonController` | 低 |
| **Z** | 全量编译验证 | — | 最后 |

---

## 任务 P-1（前置）：确认 pom.xml 依赖完整

BFF 迁移引入的新用法需要以下依赖，在 `inventory-middle-interfaces/pom.xml` 中确认是否已有：

- [ ] **P-1-1：检查必需依赖**

```bash
grep -E "easyexcel|fastjson|commons-lang3" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/pom.xml \
  /Users/kangll13/aiot/java-code/self/inventory-middle/pom.xml
```

| 依赖 | 用途 | 若缺失则加入 |
|------|------|------------|
| `easyexcel` | MonitorRule/MaterialDoc Excel 导入导出 | `inventory-middle-interfaces/pom.xml` |
| `fastjson` | MaterialBizServiceImpl JSON 处理 | 同上 |
| `commons-lang3` | StringUtils 工具 | 同上（一般已有）|

- [ ] **P-1-2：若有缺失，在 `inventory-middle-interfaces/pom.xml` 追加（版本与 BFF pom 保持一致）**

---

## 任务 P0：UserContextHolder（用户上下文）

**背景：** BFF 层大量依赖 `RemoteParticipantCenterService.getCurrentTenantId()` / `getCurrentUserId()`，迁移后由本地 ThreadLocal 持有当前用户上下文，由拦截器在请求入口注入。

> ⚠️ **已实现状态（执行前勿重建）：** 以下文件已存在，执行时以确认为主，不要覆盖。
> - ✅ `UserContext.java`（含 userId / tenantId / username 字段）
> - ✅ `UserContextHolder.java`（含 getTenantId / getUserId / getUsername）
> - ✅ `UserContextInterceptor.java`（从 X-Tenant-Id / X-User-Id / X-Username Header 注入，preHandle 永远 return true）
> - ✅ `WebConfig.java`（已注册拦截器，addPathPatterns("/**")）
>
> **注意：** 现有实现无 sandbox 模式，开发联调时直接在请求 Header 中注入固定值即可。

**文件：** 已存在，无需新建

- [x] **P0-1：UserContext 值对象** — 已完成

- [x] **P0-2：UserContextHolder** — 已完成

- [x] **P0-3：UserContextInterceptor（Header 注入）** — 已完成

- [x] **P0-4：WebConfig 注册拦截器** — 已完成（`inventory-middle-starter/config/WebConfig.java`）

- [ ] **P0-5：确认迁移后所有 Controller 中 `RemoteParticipantCenterService.getCurrentTenantId()` 替换完毕**

```bash
grep -rn "getCurrentTenantId\|getCurrentUserId\|RemoteParticipantCenterService" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src \
  --include="*.java" | grep -v target
```
预期：0

- [ ] **P0-6：编译验证**

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home
cd /Users/kangll13/aiot/java-code/self/inventory-middle
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -15
```
预期：BUILD SUCCESS

---

- [ ] **P0-7：在 WebConfig 注册 LogInterceptor（请求 traceId + 耗时日志）**

> **背景：** `kdla-component-log` 中的 `LogInterceptor` 在每个请求入口生成 Snowflake traceId 写入 MDC，并在请求完成后打印 URI + 远端 IP + 耗时。目前 `WebConfig` 只注册了 `UserContextInterceptor`，全链路无 traceId，日志无法串联。
>
> `kdla-component-log` 已在 `inventory-middle-domain/pom.xml` 声明（传递依赖到 starter），无需新增依赖。

在 `inventory-middle-starter/src/main/java/com/inventory/middle/starter/config/WebConfig.java` 中：
1. 注入 `LogInterceptor`（`@Resource` 注入）
2. 在 `addInterceptors` 里**先于** `UserContextInterceptor` 注册（顺序：`LogInterceptor` → `UserContextInterceptor`）

```java
// WebConfig.java — 改动示意
@Resource
private LogInterceptor logInterceptor;  // 新增

@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logInterceptor).addPathPatterns("/**");          // 新增，先注册
    registry.addInterceptor(userContextInterceptor).addPathPatterns("/**");  // 原有
}
```

> `LogInterceptorAutoConfigure` 已通过 `@ConditionalOnMissingBean` 自动注册 Bean，无需手动 `@Bean`。

- [ ] **P0-8：在 application.yml 启用 @CatchAndLog 和 RequestLog**

在 `inventory-middle-starter/src/main/resources/application.yml` 追加：

```yaml
kdla:
  config:
    catch-log:
      enabled: true   # 启用 @CatchAndLog 切面：BizException→warn+错误码，未知异常→error+UNKNOWN_ERROR
```

在 `inventory-middle-starter/src/main/resources/application-dev.yml`（若无则新建）追加：

```yaml
kdla:
  config:
    request-log:
      enabled: true   # 仅 dev/test profile：自动打印全量 HTTP 请求体/响应体/耗时，便于联调
```

> `RequestLogAutoConfigure` 带有 `@Profile({"dev", "test"})`，仅在对应 profile 下激活，生产安全。

- [ ] **P0-9：每个迁移 Controller 类上加 @CatchAndLog 注解**

迁移 A-I 每个任务的 Controller 时，类声明上加注解（**随各任务一并完成，无需单独操作**）：

```java
import top.kdla.framework.log.catchlog.CatchAndLog;

@CatchAndLog   // ← 新增，放在 @RestController 之前
@RestController
@RequestMapping("/materialDoc")
public class MaterialDocController { ... }
```

效果：
- `BizException` → 返回业务错误码 + warn 日志，不再 HTTP 500
- `SysException` / `LockFailException` → 对应错误码 + warn 日志
- 未知异常 → `UNKNOWN_ERROR` + error 日志
- 自动记录入参 + 返回值 + 执行耗时（StopWatch）

> **验证：** 完成 P0-8 配置后，任意 Controller 抛 `BizException`，响应体应返回业务错误码而非 HTTP 500。

---

## 任务 A：物料凭证 HTTP 接口

**背景：** 原 `MaterialDocController`（BFF）通过 `RemoteMaterialDocService`（Dubbo）调用 inventory-center；迁移后直接调用已有的 `MaterialDocMainApplicationService` 和 `MaterialDocQueryService`。

**源文件：**
- `inventory-center-bff/...web/controller/MaterialDocController.java`（18 个接口）
- `inventory-center-bff/...biz/service/impl/MaterialBizServiceImpl.java`（业务逻辑）

**目标文件：**
- 已有（需扩展）：`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/MaterialDocController.java`
- 已有（需扩展）：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/MaterialDocMainApplicationServiceImpl.java`

**接口清单（需迁移）：**

| BFF 接口 | HTTP 方法 + 路径 | 对应 ApplicationService 方法 |
|----------|-----------------|------------------------------|
| queryMaterialTypeMapping | GET /materialDoc/queryMaterialTypeMapping | `MaterialDocQueryService.queryMaterialTypeMapping()` |
| queryMaterialDocId | GET /materialDoc/queryMaterialDocId | `MaterialDocMainApplicationService.getMaterialDocId()` |
| queryMaterialDoc | GET /materialDoc/queryMaterialDoc | `MaterialDocQueryService.getByOriginalNo()` |
| queryMaterialBatchNo | POST /materialDoc/queryMaterialBatchNo | `MaterialDocQueryService.queryMaterialBatchNo()` |
| createMaterialDoc | POST /materialDoc/createMaterialDoc | `MaterialDocMainApplicationService.createMaterialDocIn/Out` |
| inboundMaterialDoc | POST /materialDoc/inboundMaterialDoc | `MaterialDocMainApplicationService.createMaterialDocIn()` |
| outboundMaterialDoc | POST /materialDoc/outboundMaterialDoc | `MaterialDocMainApplicationService.createMaterialDocOut()` |
| cancelMaterialDoc | POST /materialDoc/cancelMaterialDoc | `MaterialDocMainApplicationService.reverseMaterialDoc()` |
| checkParam | POST /materialDoc/checkParam | `MaterialDocMainApplicationService.checkMaterialDoc()` |
| queryBuildMaterialInfo | GET /materialDoc/queryBuildMaterialInfo | `ProductExternalServiceImpl.queryBuildMaterialInfo()` |
| queryMaterialInfoByName | POST /materialDoc/queryMaterialInfoByName | `ProductExternalServiceImpl.fuzzyQueryByName()` |
| page/query | POST /materialDoc/page/query | `MaterialDocQueryService.pageList()` |
| queryMaterialDocType | GET /materialDoc/queryMaterialDocType | 本地枚举 |
| queryMaterialDocGroup | GET /materialDoc/queryMaterialDocGroup | 本地枚举 |
| export | POST /materialDoc/export | `MaterialDocQueryService.exportList()` |
| updateMaterialAnnualDate | POST /materialDoc/updateMaterialAnnualDate | `MaterialDocMainApplicationService.updateAnnualDate()` |
| city-gas/excel/import | POST /materialDoc/city-gas/excel/import | `FileImportApplicationService.cityGasImport()` |
| city-gas/excel/page | POST /materialDoc/city-gas/excel/page | `FileImportApplicationService.pageQuery()` |

- [ ] **A-1：检查 inventory-middle 中现有 MaterialDocController 已有哪些接口**

```bash
grep -n "@GetMapping\|@PostMapping\|@RequestMapping" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/MaterialDocController.java
```

- [ ] **A-2：检查 MaterialDocQueryService 有哪些方法，确认缺失方法**

```bash
cat /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-application/src/main/java/com/inventory/middle/application/service/MaterialDocQueryService.java
```

- [ ] **A-3：检查 MaterialDocMainApplicationService 已有方法**

```bash
cat /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-application/src/main/java/com/inventory/middle/application/service/MaterialDocMainApplicationService.java
```

- [ ] **A-4：在 MaterialDocQueryService 补充缺失方法（queryMaterialTypeMapping / getByOriginalNo / queryMaterialBatchNo / exportList）**

以 `queryMaterialBatchNo` 为例（其余同理）：
```java
// MaterialDocQueryService.java（接口）
PageResponse<QueryMaterialBatchNoResDTO> queryMaterialBatchNo(QueryMaterialBatchNoReqDTO reqDTO);
```

```java
// MaterialDocQueryServiceImpl.java（实现）
@Override
public PageResponse<QueryMaterialBatchNoResDTO> queryMaterialBatchNo(QueryMaterialBatchNoReqDTO reqDTO) {
    // 直接查 MdocSubMaterialRepository，组装结果
    // ...
}
```

- [ ] **A-5：在 MaterialDocMainApplicationService 补充 checkMaterialDoc / updateAnnualDate / getMaterialDocId**

- [ ] **A-6：迁移 MaterialDocController（替换 BFF 版本）**

关键改写规则：
1. 返回类型：`RdfaResult<X>` → `SingleResponse<X>`，`PagedRdfaResult<X>` → `PageResponse<X>`
2. 去除 `@RequestHeader("ennUnifiedAuthorization") String token` 参数
3. 租户/用户：`remoteParticipantCenterService.getCurrentTenantId()` → `UserContextHolder.getTenantId()`
4. 公司名注入（`injectPagedCompanyNames`）：暂时跳过，用 companyCode 返回
5. **注意 createMaterialDoc 的取消分支**：当 `materialDocCategory == CANCEL` 时需路由到 `reverseMaterialDoc`，迁移时保留此分支逻辑

```java
// 示例：迁移后的 createMaterialDoc（含取消分支）
@PostMapping("/createMaterialDoc")
public SingleResponse<MaterialDocNoResVO> createMaterialDoc(@RequestBody MaterialDocumentVO vo) {
    MaterialDocumentBO bo = materialDocConverter.toBO(vo);
    bo.setTenantId(UserContextHolder.getTenantId());
    bo.setOperator(UserContextHolder.getUserId());
    MaterialDocNoResBO res;
    if (MaterialDocCategoryEnum.CANCEL.getCode().equals(bo.getMaterialDocCategory())) {
        // 取消场景路由到 reverseMaterialDoc
        ReverseMaterialDocumentBO reverse = toReverseBo(bo);
        res = materialDocMainApplicationService.reverseMaterialDoc(reverse);
    } else {
        res = materialDocMainApplicationService.createMaterialDoc(bo);
    }
    return SingleResponse.of(materialDocConverter.toNoResVO(res));
}
```

- [ ] **A-7：编译验证**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -15
```
预期：BUILD SUCCESS

- [ ] **A-8：Commit**

```bash
git add -A && git commit -m "feat(interfaces): migrate MaterialDocController from BFF"
```

---

## 任务 B：库存快照 HTTP 接口

**背景：** 原 `InventorySnapshotController`（BFF）→ `InventorySnapshotBizService` → `RemoteInventorySnapshotService`（Dubbo）。迁移后调用已有的 `InventorySnapshotQueryService` / `InventorySnapshotApplicationService`。

**接口清单：**

| BFF 接口 | HTTP 路径（BFF 实际路径） | 目标 ApplicationService |
|----------|-----------|------------------------|
| page/query | POST /inventorySnapshot/page/query | `InventorySnapshotQueryService.pageList()` |
| city-gas/page/query | POST /inventorySnapshot/city-gas/page/query | `InventorySnapshotQueryService.pageListCityGas()` |
| detail/query | POST /inventorySnapshot/detail/query | `InventorySnapshotQueryService.detail()` |
| export | POST /inventorySnapshot/export | `InventorySnapshotQueryService.exportList()` |
| queryByBatchNo | POST /inventorySnapshot/queryByBatchNo | `InventorySnapshotQueryService.queryByBatchNo()` |
| queryCurrentInventory | POST /inventorySnapshot/queryCurrentInventory | `InventorySnapshotQueryService.queryCurrentInventory()` |

- [ ] **B-1：检查 InventorySnapshotQueryService 已有方法**

```bash
cat /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-application/src/main/java/com/inventory/middle/application/service/InventorySnapshotQueryService.java
```

- [ ] **B-2：在 InventorySnapshotQueryService 补充缺失方法（pageListCityGas / queryByBatchNo / queryCurrentInventory）**

参考原 `InventorySnapshotBizServiceImpl` 中 `RemoteInventorySnapshotService` 的调用，改为直接查 `InventorySnapshotRepository`。

- [ ] **B-3：检查并扩展 inventory-middle 中 InventorySnapshotController**

```bash
grep -n "@GetMapping\|@PostMapping" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventorySnapshotController.java
```

- [ ] **B-4：迁移 InventorySnapshotController（补充缺失接口，调用本地 Service）**

- [ ] **B-5：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(interfaces): migrate InventorySnapshotController from BFF"
```

---

## 任务 C：逻辑仓 / 库位 / 仓库 HTTP 接口

**背景：** 原 `LogicalPlantController` / `StorageLocationController` / `WarehouseController` 通过 Dubbo 调用 inventory-center 的仓库领域服务。inventory-middle 已有对应 ApplicationService。

**涉及接口（逻辑仓）：**

| BFF 接口 | 路径 | 目标 ApplicationService |
|----------|------|------------------------|
| create | POST /logicalPlant/create | `LogicalPlantApplicationService.create()` |
| update | POST /logicalPlant/update | `LogicalPlantApplicationService.update()` |
| page | POST /logicalPlant/page | `LogicalPlantQueryService.page()` |
| list | GET /logicalPlant/list | `LogicalPlantQueryService.list()` |
| detail | GET /logicalPlant/detail | `LogicalPlantQueryService.detail()` |
| types | GET /logicalPlant/types | 本地枚举 |
| listByIdList | POST /logicalPlant/listByIdList | `LogicalPlantQueryService.listByIdList()` |

**涉及接口（库位）：** create / update / list / listByAdjust / detail / queryStorageLocationType / getByDescription

**涉及接口（仓库）：** create / update / pageList / list / detail

- [ ] **C-1：检查三个 Controller 在 inventory-middle 中的现状**

```bash
grep -c "@GetMapping\|@PostMapping" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/LogicalPlantController.java \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/StorageLocationController.java \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/WarehouseController.java
```

- [ ] **C-2：检查 LogicalPlantApplicationService / LogicalPlantQueryService 缺失的方法（listByIdList / types）**

- [ ] **C-3：在 LogicalPlantApplicationService 补充 `listByIdList(List<Long> ids)` 方法**

```java
// LogicalPlantApplicationService.java（接口追加）
MultiResponse<LogicalPlantDTO> listByIdList(List<Long> ids);
```

```java
// LogicalPlantApplicationServiceImpl.java（实现）
@Override
public MultiResponse<LogicalPlantDTO> listByIdList(List<Long> ids) {
    if (CollectionUtils.isEmpty(ids)) return MultiResponse.of(Collections.emptyList());
    List<LogicalPlantBO> list = logicalPlantCoreService.listByIdList(ids);
    return MultiResponse.of(list.stream().map(this::toDTO).collect(Collectors.toList()));
}
```

- [ ] **C-4：检查 StorageLocationApplicationService 缺失的 `getByDescription` / `listByAdjust`**

- [ ] **C-5：迁移三个 Controller（补全接口，去除 RDFA/Dubbo 依赖，改 UserContextHolder）**

注意：BFF 中地址注入（省市区名称 `fullAddressHelper.injectProvince`）暂时去掉，仅返回编码字段；在迁移计划中标注 TODO 注释。

- [ ] **C-6：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(interfaces): migrate LogicalPlant/StorageLocation/Warehouse Controller from BFF"
```

---

## 任务 D：监控规则 HTTP 接口

**背景：** 原 `MonitorRuleController` → `InventoryMonitorRuleBizService` → `RemoteMonitorRuleService`（Dubbo）。inventory-middle 已有 `InventoryMonitorRuleApplicationService` / `InventoryMonitorRuleLineApplicationService`。

**接口清单（BFF 实际 14 个）：**

| BFF 接口 | 路径 | 目标 |
|----------|------|------|
| page/query | POST /monitorRule/page/query | `InventoryMonitorRuleQueryService.page()` |
| create | POST /monitorRule/create | `InventoryMonitorRuleApplicationService.create()` |
| update | POST /monitorRule/update | `InventoryMonitorRuleApplicationService.update()` |
| delete | POST /monitorRule/delete | `InventoryMonitorRuleApplicationService.delete()` |
| type/query | GET /monitorRule/type/query | 本地枚举 |
| sendMode/query | GET /monitorRule/sendMode/query | 本地枚举 |
| dimension/query | GET /monitorRule/dimension/query | 本地枚举 |
| enableStatus/query | GET /monitorRule/enableStatus/query | 本地枚举 |
| ruleAndLine/update | POST /monitorRule/ruleAndLine/update | `InventoryMonitorRuleApplicationService.updateWithLines()` |
| ruleLine/save | POST /monitorRule/ruleLine/save | `InventoryMonitorRuleLineApplicationService.save()` |
| ruleLine/page | POST /monitorRule/ruleLine/page | `InventoryMonitorRuleLineQueryService.page()` |
| ruleLine/excel/import | POST /monitorRule/ruleLine/excel/import | `InventoryMonitorRuleLineApplicationService.importExcel()` |
| ruleLine/import/result | GET /monitorRule/ruleLine/import/result | `InventoryMonitorRuleLineQueryService.importResult()`（读 Redis key）|
| templateExport | GET /monitorRule/templateExport | 下载本地模板文件 |

- [ ] **D-1：检查已有 MonitorRuleController 接口覆盖情况**

```bash
grep -n "@GetMapping\|@PostMapping" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventoryMonitorRuleController.java
```

- [ ] **D-2：检查枚举类是否已存在，按需补充**

```bash
find /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-domain/src -name "*MonitorRule*Enum*" | grep -v target
```

- [ ] **D-3：补充 `updateWithLines` 方法（若无）**

参考 BFF `MonitorRuleAndLineUpdateRequestVO` + `InventoryMonitorRuleBizServiceImpl.ruleAndLineUpdate()` 逻辑。

- [ ] **D-4：迁移 EasyExcel 监听器，实现 `importExcel` + `importResult`（读 Redis 导入进度）**

- [ ] **D-5：迁移 MonitorRuleController（补全 14 个接口）**

- [ ] **D-6：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(interfaces): migrate MonitorRuleController from BFF"
```

---

## 任务 E：在途库存 HTTP 接口

**背景：** 原 `InTransitStockController` → `InTransitStockBizService` → `RemoteInTransitStockService`（Dubbo 调 `inventory-center.InTransitStockService`）。注意：inventory-middle 中 `InventorySupplyController` 是入库需求管理，与在途库存不同；应映射到 `InventoryTransitQueryService`。另外 `InTransitStockBizServiceImpl` 还额外调用 `remoteProductCenterRemoteService.getUnitById()` 填充单位名称，迁移时需一并处理。

**接口清单（BFF 1 个）：**

| BFF 接口 | 路径 | 目标 ApplicationService |
|----------|------|------------------------|
| page/query | POST /inTransitStock/page/query | `InventoryTransitQueryService.pageList()` |

- [ ] **E-1：确认 InventoryTransitQueryService 有 pageList 方法**

```bash
grep -n "pageList\|pagedInTransit" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-application/src/main/java/com/inventory/middle/application/service/InventoryTransitQueryService.java
```

- [ ] **E-2：若缺少单位名称注入，补充 `ProductExternalServiceImpl.getUnitById()` 调用**

BFF 中 `getUnitById(stockDTO.getUom(), token)` 用于填充 `uomName`，迁移后去掉 token 参数，直接调 `productExternalService.getUnitById(uom)`。

- [ ] **E-3：在 InventoryTransitController 补全 pageList 接口（路径 `/inTransitStock/page/query`）**

- [ ] **E-4：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(interfaces): migrate InTransitStockController from BFF"
```

---

## 任务 F：移动平均价 HTTP 接口

**背景：** BFF `MapController` 实际只有 **1 个接口**（`POST /map/queryMap`），不存在 `journalPage`。`journalPage` 历史逻辑在 inventory-middle 的 `InventoryMapHisController` 中独立管理，无需迁移。

**接口清单（BFF 1 个）：**

| BFF 接口 | 实际路径 | 目标 ApplicationService |
|----------|----------|------------------------|
| queryMap | POST /map/queryMap | `InventoryMapQueryService.queryMapPage()` |

- [ ] **F-1：检查 inventory-middle InventoryMapController 是否已有 `/map/queryMap`**

```bash
grep -n "@PostMapping\|@GetMapping" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventoryMapController.java
```

- [ ] **F-2：若路径不对齐，补全 `/map/queryMap` 接口（QueryMapReqVO → InventoryMapQueryService）**

- [ ] **F-3：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(interfaces): migrate MapController from BFF"
```

---

## 任务 G：库存物料（城燃）HTTP 接口

**背景：** BFF `InventoryMaterialController` 实际只有 **1 个接口**（城燃物料分页），通过 `RemoteInventoryMaterialService`（Dubbo）查询。inventory-middle 中无对应 Controller，需新增。

**接口清单（BFF 1 个）：**

| BFF 接口 | 实际路径 | 目标 ApplicationService |
|----------|----------|------------------------|
| queryCityGasMaterialPage | POST /inventoryMaterial/queryCityGasMaterialPage | `InventoryMaterialApplicationService.queryCityGasPage()` |

- [ ] **G-1：确认 InventoryMaterialApplicationService 是否有城燃物料分页方法**

```bash
grep -n "cityGas\|CityGas" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-application/src/main/java/com/inventory/middle/application/service/InventoryMaterialApplicationService.java
```

- [ ] **G-2：若无，补充 `queryCityGasPage(PageCityGasInventoryMaterialReqDTO)` 方法**

- [ ] **G-3：在 InventoryMaterialController 补全该接口**

- [ ] **G-4：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(interfaces): migrate InventoryMaterialController from BFF"
```

---

## 任务 H：备件流转码 / 码申请 / 经销商出库单 HTTP 接口

**背景：** BFF 的 sparepart 域（`screw.bff.web`）：`AccessoriesFlowCodeController`、`CodeApplyOrderController`、`DeliveryOrderMgntController`、`DistributorController`，全部调用已经在 inventory-middle 中实现的 `AccessoriesFlowCodeApplicationService` / `CodeApplyOrderApplicationService`。

**文件：**
- 已有：`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/AccessoriesFlowCodeController.java`
- 已有：`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/CodeApplyOrderController.java`
- 需新建：`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/DeliveryOrderMgntController.java`
- 需新建：`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/DistributorController.java`

**DeliveryOrderMgntController 接口清单：**

| BFF 接口 | 路径 |
|----------|------|
| getDeliverState | GET /deliveryOrderMgnt/getDeliverState |
| spDeliveryOrderPage | POST /deliveryOrderMgnt/spDeliveryOrderPage |
| spDeliveryOrderDetail | GET /deliveryOrderMgnt/spDeliveryOrderDetail |
| createSpDeliveryOrder | POST /deliveryOrderMgnt/createSpDeliveryOrder |
| deliverPrint | POST /deliveryOrderMgnt/deliverPrint |
| deliverPrintConfirm | POST /deliveryOrderMgnt/deliverPrintConfirm |

**DistributorController 接口清单：**

| BFF 接口 | 路径 |
|----------|------|
| fuzzyQueryDistributor | GET /distributor/fuzzyQuery |
| distributorApplyOrderPage | POST /distributor/applyOrderPage |
| manufacturerApplyOrderPage | POST /distributor/manufacturerApplyOrderPage |

- [ ] **H-1：检查 AccessoriesFlowCodeController / CodeApplyOrderController 已有接口覆盖情况**

```bash
grep -n "@GetMapping\|@PostMapping" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/AccessoriesFlowCodeController.java \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/CodeApplyOrderController.java
```

- [ ] **H-2：检查 BFF AccessoriesFlowCodeController 完整接口及调用链**

```bash
cat /Users/kangll13/aiot/java-code/self/inventory/inventory-center-bff/inventory-center-bff-web/src/main/java/com/enn/sparepart/screw/bff/web/controller/AccessoriesFlowCodeController.java
cat /Users/kangll13/aiot/java-code/self/inventory/inventory-center-bff/inventory-center-bff-web/src/main/java/com/enn/sparepart/screw/bff/web/controller/DeliveryOrderMgntController.java
cat /Users/kangll13/aiot/java-code/self/inventory/inventory-center-bff/inventory-center-bff-web/src/main/java/com/enn/sparepart/screw/bff/web/controller/DistributorController.java
```

- [ ] **H-3：确认 SpDeliveryOrderApplicationService / CrmDistributorApplicationService 是否存在**

```bash
find /Users/kangll13/aiot/java-code/self/inventory-middle -name "SpDeliveryOrder*Service*" -o -name "CrmDistributor*Service*" | grep -v target | grep -v Test
```
若不存在，需在 `inventory-middle-application` 中新建，参考 BFF `SpDeliveryOrderBizServiceImpl` / `CrmDistributorBizServiceImpl` 逻辑实现。

- [ ] **H-4：迁移 AccessoriesFlowCodeController（补全缺失接口）**

- [ ] **H-5：迁移 CodeApplyOrderController（补全缺失接口）**

- [ ] **H-6：新建 DeliveryOrderMgntController**

```java
@RestController
@RequestMapping("/deliveryOrderMgnt")
@Slf4j
public class DeliveryOrderMgntController {
    @Resource
    private SpDeliveryOrderApplicationService spDeliveryOrderApplicationService;
    // 按 H 接口清单逐一实现，调用 ApplicationService，返回 SingleResponse/PageResponse
}
```

- [ ] **H-7：新建 DistributorController**

- [ ] **H-8：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(interfaces): migrate sparepart controllers (AccessoriesCode/CodeApply/Delivery/Distributor) from BFF"
```

---

## 任务 I：备件SKU / 运单查询 / 文件 / 公共接口

**背景：** 这四个 Controller 依赖外部服务（产品中心 HTTP 调用、OSS、participant-center），优先级较低，部分可暂时做 stub。

**文件：**
- `SparePartController` → 调用 `RemoteSparePartService`（产品中心 HTTP），迁移为 `ProductExternalServiceImpl`
- `ShipmentQueryController` → 调用 `ShipmentQueryService`（已在 inventory-middle 中）
- `FileController` → 调用 `FileImportApplicationService` + OSS（已在 inventory-middle 中）
- `CommonController` → 枚举查询 + 公司/部门树（participant-center HTTP），暂时 stub

- [ ] **I-1：检查 SparePartController 调用链**

```bash
cat /Users/kangll13/aiot/java-code/self/inventory/inventory-center-bff/inventory-center-bff-web/src/main/java/com/enn/inventory/bff/web/controller/SparePartController.java
cat /Users/kangll13/aiot/java-code/self/inventory/inventory-center-bff/inventory-center-bff-web/src/main/java/com/enn/inventory/bff/web/controller/CommonController.java
```

- [ ] **I-2：将 SparePartController 迁移，调用 ProductExternalServiceImpl**

- [ ] **I-3：将 ShipmentQueryController 迁移，调用 ShipmentQueryService**

- [ ] **I-4：将 FileController 迁移，调用 FileImportApplicationService**

- [ ] **I-5：新建 CommonController（枚举接口），公司树接口暂返回 empty stub**

```java
@GetMapping("/listCompany")
public SingleResponse<List<String>> listCompany() {
    // TODO: 对接 participant-center HTTP 服务
    log.warn("CommonController.listCompany: participant-center not wired, return empty");
    return SingleResponse.of(Collections.emptyList());
}
```

- [ ] **I-6：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(interfaces): migrate SparePartController/ShipmentController/FileController/CommonController from BFF"
```

---

## 任务 Z：全量验证

- [ ] **Z-1：全量编译**

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home
cd /Users/kangll13/aiot/java-code/self/inventory-middle
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -20
```
预期：所有 7 个模块 BUILD SUCCESS

- [ ] **Z-2：统计残留 RDFA import（全项目范围）**

```bash
grep -r "import top.rdfa\|RdfaResult\|PagedRdfaResult" \
  /Users/kangll13/aiot/java-code/self/inventory-middle \
  --include="*.java" --exclude-dir=target | wc -l
```
预期：0

- [ ] **Z-3：统计 UserContextHolder 使用覆盖率**

```bash
grep -r "getCurrentTenantId\|getCurrentUserId" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src \
  --include="*.java" | grep -v target
```
预期：0（全部已替换为 UserContextHolder）

- [ ] **Z-4：校验所有 Controller 路径与 BFF 保持一致**

```bash
grep -r "@RequestMapping\|@GetMapping\|@PostMapping" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web \
  --include="*.java" | grep -v target | sort
```
与 BFF 原路径对照确认无遗漏。

- [ ] **Z-5：TODO 清单汇总**

```bash
grep -rn "// TODO" \
  /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web \
  --include="*.java" | grep -v target
```
列出剩余 TODO 项，按优先级记录至后续 issue。

---

## Check 清单（自检）

**规格覆盖度确认：**

| BFF Controller | 迁移任务 | 覆盖 |
|----------------|----------|------|
| MaterialDocController（18接口） | 任务 A | ✅ |
| InventorySnapshotController（6接口） | 任务 B | ✅ |
| LogicalPlantController | 任务 C | ✅ |
| StorageLocationController | 任务 C | ✅ |
| WarehouseController | 任务 C | ✅ |
| MonitorRuleController | 任务 D | ✅ |
| InTransitStockController | 任务 E | ✅ |
| MapController | 任务 F | ✅ |
| InventoryMaterialController | 任务 G | ✅ |
| AccessoriesFlowCodeController | 任务 H | ✅ |
| CodeApplyOrderController | 任务 H | ✅ |
| DeliveryOrderMgntController | 任务 H | ✅ |
| DistributorController | 任务 H | ✅ |
| SparePartController | 任务 I | ✅ |
| ShipmentQueryController | 任务 I | ✅ |
| FileController | 任务 I | ✅ |
| CommonController | 任务 I | ✅（部分 stub）|
| UserContextHolder 基础设施 | 任务 P0 | ✅ |

**已知缺口（需人工确认）：**
1. `RemoteParticipantCenterService.getUserNameMap` / `getCompanyNameMap` — 暂返回 userId/companyCode，需接入 participant-center HTTP 时补充
2. `FullAddressHelper.injectProvince/City/Region` — 省市区名称注入暂省略，返回编码
3. 城燃（city-gas）导入逻辑 — `CityGasBizService` 调用 ext-client，需确认 FileImportApplicationService 已覆盖
4. `ConfigserviceDemoController` — RDFA nacos 演示接口，直接删除不迁移

---

## 后续优化 Issue（迁移完成后跟进）

### Issue-1：用 FieldValueFindHelp 批量回填 userName / companyName

**背景（已知缺口 #1 的完整解法）：**

迁移计划里 `getUserNameMap` / `getCompanyNameMap` 暂时返回 userId / companyCode，这是有意识的 stub。`kdla-component-supplement` 中的 `FieldValueFindHelp` 提供了专门的批量字段回填机制，可以在接入 participant-center HTTP 后以最小改动量填上这个缺口。

**FieldValueFindHelp 原理：**

```
列表 VO 字段加注解 @FieldValueFind
         ↓
fieldValueFindHelp.process(list)
         ↓
  ┌──────────────────────────────────────┐
  │  BATCH 策略（无 N+1）                │
  │  1. 收集所有 creatorId 到 Set        │
  │  2. 一次调用 queryMethod(List<Long>) │
  │     → 返回 Map<Long, String>         │
  │  3. 批量注入 creatorName 字段        │
  └──────────────────────────────────────┘
```

**实施前提条件：**
- [ ] 有一个 HTTP Client 可调用 participant-center 的 `/user/listByIds` 接口（返回 `Map<Long, String>` userId→userName）
- [ ] 有一个 HTTP Client 可调用 participant-center 的 `/company/listByCodes` 接口（返回 `Map<String, String>` companyCode→companyName）

**实施方案（接入后）：**

**Step 1：** 新建 `ParticipantCenterClient`（OpenFeign）

```java
@FeignClient(name = "participant-center", url = "${remote.participant.center.url}")
public interface ParticipantCenterClient {
    @PostMapping("/user/listByIds")
    Map<Long, String> listUserNameByIds(@RequestBody List<Long> userIds);

    @PostMapping("/company/listByCodes")
    Map<String, String> listCompanyNameByCodes(@RequestBody List<String> companyCodes);
}
```

**Step 2：** 包装为 Service（便于 `@FieldValueFind` 指向）

```java
@Service
public class ParticipantQueryService {
    @Autowired ParticipantCenterClient client;

    public Map<Long, String> listMapByIds(List<Long> ids) {
        return client.listUserNameByIds(ids);
    }
}
```

**Step 3：** 在需要 userName 的列表 VO 字段加注解

```java
// 例：InventorySnapshotVO.java
@FieldValueFind(
    fromField   = "creatorId",
    queryClass  = ParticipantQueryService.class,
    queryMethod = "listMapByIds",
    queryPolicy = FieldValueFindHelp.QueryPolicy.BATCH
)
private String creatorName;
```

**Step 4：** QueryService 查询后调用一行

```java
List<InventorySnapshotVO> list = inventorySnapshotRepository.pageList(query);
fieldValueFindHelp.process(list);   // 批量回填 creatorName
return PageResponse.of(list, total);
```

**涉及范围（全迁移完成后统一处理）：**

| Controller | 需回填字段 | fromField |
|------------|-----------|-----------|
| InventorySnapshotController | creatorName / updaterName | creatorId / updaterId |
| LogicalPlantController | creatorName | creatorId |
| WarehouseController | creatorName | creatorId |
| MonitorRuleController | creatorName | creatorId |
| MaterialDocController | creatorName | creatorId |

**注意事项：**
- `FieldValueFindHelp` 依赖 `kdla-component-supplement`（已在 parent BOM）
- `queryMethod` 必须是 `public`、接受 `List<T>` 参数、返回 `Map<T, R>` 类型
- 如果 participant-center 不可用，`@FieldValueFind` 抛异常会被 `@CatchAndLog` 捕获并返回错误码，不会影响其他字段（需评估是否改为 try/catch 降级为空字符串）


---

## 进度快照（2026-06-15）

> 以下为根据代码实际现状的进度更新。上方原始任务 A-I 章节保留供参考，但进度以本节为准。

### 已完成

| 任务 | 说明 |
|------|------|
| P0-1~4 | UserContextHolder / UserContextInterceptor / WebConfig 已就绪 |
| A~I Controller 骨架 | 所有 BFF Controller 已迁移到 inventory-middle（RDFA/Dubbo import 已清零） |
| H | DeliveryOrderMgntController / DistributorController 已建（`interfaces/web/sparepart/`） |
| application.yml | `kdla.exception.handler.enabled` / `kdla.log.catchlog.enabled` / `kdla.stopwatch.enabled` 已配置 |

### 已知缺口汇总

```
阶段一（最小可用，无外部依赖）
  R1  @CatchAndLog 缺失 10 个 Controller
  R2  LogInterceptor 未注册
  R3  application-dev.yml 未建

阶段二（业务 TODO，无外部依赖）
  R4  InventorySnapshot：5 个 QueryService 方法缺失
  R5  MonitorRule：4 个枚举 + updateWithLines + importExcel + importResult + templateExport
  R6  MaterialDoc：checkParam + updateAnnualDate + cityGasImport + ProductExternalService 接入
  R7  LogicalPlant：types 枚举 + list(不分页) + listByIdList + detail(按编号)
  R8  StorageLocation：listByAdjust + detail(按编号) + types 枚举
  R9  Warehouse：list(不分页) + detail(按编号)
  R10 InventoryTransit：getUnitById 注入

阶段三（外部依赖，可延后）
  R11 DeliveryOrderMgnt：SpDeliveryOrderRemoteService OpenFeign 接入（6个接口全 TODO）
  R12 Distributor：CrmDistributorRemoteService OpenFeign 接入（3个接口全 TODO）
```

---

## 残余任务 R1：补齐 @CatchAndLog

**背景：** 以下 10 个 Controller 缺少 `@CatchAndLog`，BizException 会直接 HTTP 500 而非返回业务错误码。

**文件（批量修改，每个 Controller 类声明前加注解）：**
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/MaterialDocController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventorySnapshotController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/LogicalPlantController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/StorageLocationController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/WarehouseController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventoryMonitorRuleController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventoryTransitController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventoryMapController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/sparepart/DeliveryOrderMgntController.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/sparepart/DistributorController.java`

- [ ] **R1-1：在每个 Controller 的 `@RestController` 之前加 `@CatchAndLog`**

```java
import top.kdla.framework.log.catchlog.CatchAndLog;

@CatchAndLog   // ← 新增
@RestController
@RequestMapping("/materialDoc")
public class MaterialDocController { ... }
```

对其余 9 个文件同样操作，只改类声明处，不动任何方法逻辑。

- [ ] **R1-2：编译验证**

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home
cd /Users/kangll13/aiot/java-code/self/inventory-middle
mvn clean compile -DskipTests -pl inventory-middle-interfaces -am \
  -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
```
预期：`BUILD SUCCESS`

- [ ] **R1-3：Commit**

```bash
git add inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/
git commit -m "feat(interfaces): add @CatchAndLog to all BFF migrated controllers"
```

---

## 残余任务 R2+R3：LogInterceptor + application-dev.yml

**背景：** `WebConfig` 只注册了 `UserContextInterceptor`，请求无 traceId，日志无法串联。`LogInterceptor` Bean 由 `kdla-component-log` 自动注册，无需手动 `@Bean`。

**文件：**
- 修改：`inventory-middle-starter/src/main/java/com/inventory/middle/starter/config/WebConfig.java`
- 新建：`inventory-middle-starter/src/main/resources/application-dev.yml`

- [ ] **R2-1：在 WebConfig 注入并注册 LogInterceptor（先于 UserContextInterceptor）**

```java
// WebConfig.java 改动：在类内新增以下两处
@Resource
private top.kdla.framework.log.LogInterceptor logInterceptor;  // 新增

@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logInterceptor).addPathPatterns("/**");         // 新增，先注册
    registry.addInterceptor(userContextInterceptor).addPathPatterns("/**"); // 原有
}
```

- [ ] **R2-2：新建 application-dev.yml 启用 requestLog**

```yaml
# inventory-middle-starter/src/main/resources/application-dev.yml
kdla:
  log:
    requestlog:
      enabled: true  # 仅 dev/test：自动打印全量 HTTP 请求体/响应体/耗时
```

- [ ] **R2-3：编译验证**

```bash
mvn clean compile -DskipTests -pl inventory-middle-starter -am \
  -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
```
预期：`BUILD SUCCESS`

- [ ] **R2-4：Commit**

```bash
git add inventory-middle-starter/src/main/java/com/inventory/middle/starter/config/WebConfig.java \
        inventory-middle-starter/src/main/resources/application-dev.yml
git commit -m "feat(starter): register LogInterceptor for traceId; add application-dev.yml requestlog"
```

---

## 残余任务 R4：InventorySnapshotQueryService 补充缺失方法

**背景：** `InventorySnapshotController` 5 个接口已建框架，但 `InventorySnapshotQueryService` 只有 `queryPage` 一个方法，其余均为 stub。

**文件：**
- 修改接口：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/InventorySnapshotQueryService.java`
- 修改实现：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/InventorySnapshotQueryServiceImpl.java`
- 修改 Controller：`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventorySnapshotController.java`（去掉 TODO，接入真实方法）

参考：`inventory-center-bff/.../InventorySnapshotBizServiceImpl.java`（直接查 `InventorySnapshotRepository`）

- [ ] **R4-1：在 InventorySnapshotQueryService 接口补充 4 个方法**

```java
// 在接口文件追加
PageResponse<InventorySnapshotDto> pageListCityGas(InventorySnapshotPageQuery pageQuery);
SingleResponse<InventorySnapshotDto> detail(Long id);
void exportList(InventorySnapshotPageQuery pageQuery, HttpServletResponse response);
PageResponse<InventorySnapshotDto> queryByBatchNo(InventorySnapshotPageQuery pageQuery);
PageResponse<InventorySnapshotDto> queryCurrentInventory(InventorySnapshotPageQuery pageQuery);
```

- [ ] **R4-2：在 InventorySnapshotQueryServiceImpl 实现 4 个方法**

参考 `queryPage` 实现：从 `inventorySnapshotRepository` 分页查询，转换 DTO 返回。
`pageListCityGas` 增加城燃过滤条件（`materialType = 'CITY_GAS'` 或对应枚举值，以实际 entity 字段为准）。
`exportList` 调用 EasyExcel 写入 `HttpServletResponse`。
`queryByBatchNo` 按 `batchNo` 过滤。
`queryCurrentInventory` 取最新快照（`snapshotDate` 最大值分组）。

- [ ] **R4-3：更新 InventorySnapshotController，去除 TODO，接入真实 Service**

- [ ] **R4-4：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -pl inventory-middle-interfaces -am \
  -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(application): implement InventorySnapshotQueryService missing methods"
```

---

## 残余任务 R5：MonitorRule 枚举 + 缺失方法

**背景：** 4 个枚举返回接口 stub，`updateWithLines` / `importExcel` / `importResult` / `templateExport` 4 个接口 TODO。

**枚举值来源：** `inventory-center/inventory-center-client/enums/monitor/`（已确认）

**文件：**
- 新建枚举：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/model/enums/MonitorRuleTypeEnum.java`（同路径新建 `MonitorRuleSendModeEnum` / `MonitorRuleDimensionEnum` / `MonitorRuleEnableStatusEnum`）
- 修改：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/InventoryMonitorRuleApplicationService.java`（新增 `updateWithLines`）
- 修改实现：`...impl/InventoryMonitorRuleApplicationServiceImpl.java`
- 修改：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/InventoryMonitorRuleLineApplicationService.java`（新增 `importByExcel` / `getImportResult`）
- 修改：`inventory-middle-interfaces/.../web/InventoryMonitorRuleController.java`（去除 TODO）

- [ ] **R5-1：新建 4 个枚举类（domain 层 enums 包下）**

```java
// MonitorRuleTypeEnum.java
public enum MonitorRuleTypeEnum {
    QUANTITY("数量预警"),
    ANNUAL_INSPECTION("年检预警");
    private final String desc;
    MonitorRuleTypeEnum(String desc) { this.desc = desc; }
    public String getDesc() { return desc; }
}

// MonitorRuleSendModeEnum.java
public enum MonitorRuleSendModeEnum {
    SYSTEM("系统通知"), EMAIL("邮件");
    private final String desc;
    MonitorRuleSendModeEnum(String desc) { this.desc = desc; }
    public String getDesc() { return desc; }
}

// MonitorRuleDimensionEnum.java
public enum MonitorRuleDimensionEnum {
    ASSIGN_MATERIAL("指定物料"), LOGICAL_PLANT_MATERIAL("逻辑仓下所有物料");
    private final String desc;
    MonitorRuleDimensionEnum(String desc) { this.desc = desc; }
    public String getDesc() { return desc; }
}

// MonitorRuleEnableStatusEnum.java（复用 BaseEnableStatusEnum 语义）
public enum MonitorRuleEnableStatusEnum {
    ENABLE("启用"), DISABLE("停用");
    private final String desc;
    MonitorRuleEnableStatusEnum(String desc) { this.desc = desc; }
    public String getDesc() { return desc; }
}
```

- [ ] **R5-2：在 InventoryMonitorRuleApplicationService 接口新增 `updateWithLines`**

```java
// 参考 BFF MonitorRuleAndLineUpdateRequestBO → 规则 + 明细列表一起更新
boolean updateWithLines(InventoryMonitorRuleCommand ruleCommand, List<InventoryMonitorRuleLineCommand> lineCommands);
```

- [ ] **R5-3：在 InventoryMonitorRuleApplicationServiceImpl 实现 `updateWithLines`**

调用已有的 `update(ruleCommand)` + `inventoryMonitorRuleLineApplicationService.deleteBatch(oldIds)` + `add(each lineCommand)` 事务内完成。

- [ ] **R5-4：在 InventoryMonitorRuleLineApplicationService 新增 `importByExcel` + `getImportResult`**

`importByExcel`：EasyExcel 读 `MultipartFile`，异步写入，进度存 Redis key（`monitorRuleLine:import:{taskId}`）。
`getImportResult`：从 Redis 读 key 返回进度/结果 DTO。

- [ ] **R5-5：更新 InventoryMonitorRuleController，去除 8 个 TODO，接入真实实现**

枚举接口直接返回 `Arrays.stream(XxxEnum.values()).map(e -> Map.of("code", e.name(), "desc", e.getDesc())).collect(toList())`。

- [ ] **R5-6：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -pl inventory-middle-interfaces -am \
  -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(domain/application): add MonitorRule enums and updateWithLines/importExcel"
```

---

## 残余任务 R6：MaterialDocController 4 个 TODO

**背景：** `checkParam` / `updateAnnualDate` 需在 `MaterialDocMainApplicationService` 补方法；`queryBuildMaterialInfo` / `queryMaterialInfoByName` 需接入已有 `ProductExternalServiceImpl`；`cityGasImport` 需接入 `FileImportApplicationService`。

**文件：**
- 修改接口：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/MaterialDocMainApplicationService.java`
- 修改实现：`...impl/MaterialDocMainApplicationServiceImpl.java`
- 修改 Controller：`inventory-middle-interfaces/.../web/MaterialDocController.java`

- [ ] **R6-1：确认 ProductExternalServiceImpl 已有 `queryBuildMaterialInfo` / `fuzzyQueryByName`**

```bash
grep -n "queryBuildMaterialInfo\|fuzzyQueryByName" \
  $(find /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-infra/src -name "ProductExternalServiceImpl.java")
```

- [ ] **R6-2：在 MaterialDocMainApplicationService 接口新增 `checkMaterialDoc` + `updateAnnualDate`**

参考 BFF `MaterialBizServiceImpl.checkParam()` 逻辑（参数校验 + 返回校验结果 BO）。

- [ ] **R6-3：实现两个方法，并在 Controller 去除 TODO 接入**

- [ ] **R6-4：Controller 的 `queryBuildMaterialInfo` / `queryMaterialInfoByName` 接入 `ProductExternalServiceImpl`**

```java
// MaterialDocController.java（去掉 TODO，直接调注入的 ProductExternalService）
@Resource
private ProductExternalService productExternalService;

@GetMapping("/queryBuildMaterialInfo")
public SingleResponse<?> queryBuildMaterialInfo(@RequestParam String skuCode) {
    return SingleResponse.of(productExternalService.queryBuildMaterialInfo(skuCode));
}
```

- [ ] **R6-5：cityGasImport 接入 FileImportApplicationService**

```bash
grep -n "cityGas\|CityGas" \
  $(find /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-application/src -name "FileImportApplicationService.java")
```
若无城燃导入方法，在 `FileImportApplicationService` 补 `cityGasImport(MultipartFile file)`。

- [ ] **R6-6：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -pl inventory-middle-interfaces -am \
  -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git add -A && git commit -m "feat(application): implement MaterialDoc checkParam/updateAnnualDate/cityGasImport"
```

---

## 残余任务 R7+R8+R9：基础设施 Controller 缺失方法

**背景：** LogicalPlant / StorageLocation / Warehouse 三个 Controller 各有 2-4 个 TODO，均为 QueryService 缺失方法或枚举未定义。

**文件（共 6 个，按 Controller 分组）：**

### R7 LogicalPlant

- [ ] **R7-1：确认 `LogicalPlantTypeEnum` 已存在（已见于 `domain/enums/`），在 Controller `/logicalPlant/types` 接口直接返回枚举**

```java
@GetMapping("/types")
public SingleResponse<List<Map<String,String>>> types() {
    return SingleResponse.of(Arrays.stream(LogicalPlantTypeEnum.values())
        .map(e -> Map.of("code", e.name(), "desc", e.getDesc()))
        .collect(Collectors.toList()));
}
```

- [ ] **R7-2：在 `LogicalPlantQueryService` 补充 `list(LogicalPlantPageQuery)`（不分页返回全量）**

```java
List<LogicalPlantDTO> list(LogicalPlantPageQuery query);
```

- [ ] **R7-3：在 `LogicalPlantQueryService` 补充 `listByIdList(List<Long> ids)` + `detailByNo(String logicalPlantNo)`**

- [ ] **R7-4：Controller 去除 4 个 TODO，接入真实方法 + 编译验证 + Commit**

```bash
git commit -m "feat(interfaces/application): complete LogicalPlantController missing endpoints"
```

### R8 StorageLocation

- [ ] **R8-1：确认 `StorageLocationTypeEnum` 已存在（已见于 `domain/enums/`），Controller `/storageLocation/queryStorageLocationType` 接口直接返回枚举**

- [ ] **R8-2：在 `StorageLocationQueryService` 补充 `listByAdjust(String adjustType)` + `detailByDescription(String description)`**

- [ ] **R8-3：Controller 去除 3 个 TODO + 编译验证 + Commit**

```bash
git commit -m "feat(interfaces/application): complete StorageLocationController missing endpoints"
```

### R9 Warehouse

- [ ] **R9-1：在 `WarehouseQueryService` 补充 `list(WarehousePageQuery)` （不分页）+ `detailByNo(String warehouseNo)`**

- [ ] **R9-2：Controller 去除 2 个 TODO + 编译验证 + Commit**

```bash
git commit -m "feat(interfaces/application): complete WarehouseController missing endpoints"
```

---

## 残余任务 R10：InventoryTransit 单位名称注入

**背景：** `InventoryTransitController` 的 `page/query` 接口已接入 `InventoryTransitQueryService.pageList()`，但 `uomName` 字段未填充（TODO 注释标注）。

**文件：**
- 修改：`inventory-middle-interfaces/.../web/InventoryTransitController.java`
- 确认：`inventory-middle-infra/.../external/ProductExternalServiceImpl.java` 有 `getUnitById` 方法

- [ ] **R10-1：确认 `ProductExternalService` 有 `getUnitById(String uom)` 方法**

```bash
grep -n "getUnitById\|UnitById" \
  $(find /Users/kangll13/aiot/java-code/self/inventory-middle/inventory-middle-domain/src -name "ProductExternalService.java")
```

- [ ] **R10-2：若有，在 Controller pageList 调用后批量回填 uomName**

```java
// InventoryTransitController.java
PageResponse<InTransitStockDto> page = inventoryTransitQueryService.pageList(pageQuery);
page.getData().forEach(dto -> {
    if (StringUtils.isNotBlank(dto.getUom())) {
        dto.setUomName(productExternalService.getUnitById(dto.getUom()));
    }
});
return SingleResponse.of(page);
```

- [ ] **R10-3：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -pl inventory-middle-interfaces -am \
  -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -10
git commit -m "feat(interfaces): inject uomName via ProductExternalService in InventoryTransitController"
```

---

## 残余任务 R11+R12（阶段三，外部依赖）：Delivery + Distributor

> **前提：** 需要外部 SpDeliveryOrder 服务和 CrmDistributor 服务的 HTTP 地址/文档。当前为全 stub，可推迟到外部服务联调阶段再实施。

### R11 DeliveryOrderMgntController

`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/sparepart/DeliveryOrderMgntController.java`：6 个接口全为 TODO stub，等待 `SpDeliveryOrderRemoteService` OpenFeign 接入。

实施时：
1. 新建 `SpDeliveryOrderClient`（`@FeignClient`，url 取自配置）
2. 包装为 `SpDeliveryOrderApplicationService`（`inventory-middle-application`）
3. 去掉 Controller 6 个 TODO，调用 ApplicationService

### R12 DistributorController

`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/sparepart/DistributorController.java`：3 个接口全为 TODO stub，等待 `CrmDistributorRemoteService` OpenFeign 接入。

实施时同 R11 模式：Client → ApplicationService → Controller 去 TODO。

---

## 全量验收清单（R1-R10 完成后执行）

- [ ] **V1：全量编译**

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home
cd /Users/kangll13/aiot/java-code/self/inventory-middle
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository 2>&1 | tail -15
```
预期：所有模块 `BUILD SUCCESS`

- [ ] **V2：@CatchAndLog 覆盖率**

```bash
grep -rl "@CatchAndLog" \
  inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web \
  --include="*.java" | wc -l
```
预期：≥ 30（含原有已覆盖的 21 个 + 新增 10 个）

- [ ] **V3：TODO 残余统计**

```bash
grep -rn "TODO\|待补充\|待接入\|待迁移" \
  inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web \
  --include="*.java" | grep -v target | wc -l
```
R1-R10 完成后预期：≤ 5（仅剩 R11/R12 阶段三的 TODO 以及 `//TODO list query` 通用占位）

- [ ] **V4：RDFA / Dubbo 残留确认（回归）**

```bash
grep -r "RdfaResult\|PagedRdfaResult\|@DubboReference\|@Reference.*dubbo\|RemoteXxxService" \
  inventory-middle-interfaces/src --include="*.java" | grep -v target | wc -l
```
预期：0
