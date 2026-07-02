# Inventory-Middle 迁移 TODO 追踪

> 多源迁移（inventory-center + inventory-center-bff + scm-plan-management + scm-plan-bff → inventory-middle）

## 构建

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home \
  mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
```

## 约束

1. 新增代码不在 domain 层引入 Spring 注解
2. plan 包路径禁止 `plan.plan.` 双层嵌套
3. HTTP 外部调用优先 OpenFeign，次选 RestTemplate
4. 编译后必须 BUILD SUCCESS 7/7，完成后标记 `✅` / `[x]`

---

## 待完成任务

### [x] G24：BeanUtils.copyProperties 全量替换为 MapStruct（60 处）✅ **完成**

**优先级：高** — 编码偏好硬约束，`BeanUtils.copyProperties` 绕过类型检查，字段名不一致时静默失败

**构建依赖已添加：**
- `inventory-middle-infra/pom.xml`：已添加 mapstruct + mapstruct-processor ✅
- `inventory-middle-application/pom.xml`：已添加 mapstruct + mapstruct-processor ✅
- `inventory-middle-domain/pom.xml`：原已有 mapstruct + mapstruct-processor ✅

**MapStruct 约定：**
- `@Mapper(componentModel = "spring")`
- 值对象 `id` 需 `default id2id(Long)` / `default id2id(XxxId)` 互转（参考 `InventorySnapshotConvertor`）
- 所有 infra 层 Convertor 放在 `infra/persistence/convertor/` 包（已有 25 个同类 Convertor 在此）
- 所有 application 层 Mapper 放在 `application/convertor/` 包
- domain 层 Mapper 放在对应 service 子包或 `domain/service/xxx/converter/` 包

---

#### G24-S1：infra Repository 层（8 个文件）✅ **完成**

每个 RepositoryImpl 当前用 `BeanUtils.copyProperties` 实现 `toDoObject/toEntity/toQueryPO` 私有方法，
替换为注入对应 Convertor 并调用其方法。

**需新建 Convertor（放在 `infra/persistence/convertor/`）：**

| Convertor 接口 | 映射对（方向） | 特殊说明 |
|---|---|---|
| `InventoryMaterialConvertor` | `InventoryMaterial ↔ InventoryMaterialDo`，`InventoryMaterialQueryParam → InventoryMaterialQueryPO`，`ListMaterialCodeQueryParam → ListMaterialCodeParamPO` | 字段不完全同名（skuCode 等仅在 entity），MapStruct 只映射同名字段，行为与 BeanUtils 一致，无需 `@Mapping` |
| `CodeApplyOrderConvertor` | `CodeApplyOrder ↔ CodeApplyOrderDo`，`CodeApplyOrderQueryParam → CodeApplyOrderParamPO` | 纯同名字段，无特殊处理 |
| `CodeApplyOrderDetailConvertor` | `CodeApplyOrderDetail ↔ CodeApplyOrderDetailDo` | 纯同名字段 |
| `CodeApplyApprovalRecordConvertor` | `CodeApplyApprovalRecord ↔ CodeApplyApprovalRecordDo` | 纯同名字段 |
| `CodeConvertor` | `Code ↔ CodeDo`，`CodeQueryParam → ListCodeParamPO` | 纯同名字段 |
| `InventoryLogConvertor` | `InventoryLog ↔ InventoryLogDo` | 纯同名字段 |
| `FileImportRecordConvertor` | `FileImportRecord(domain entity) ↔ FileImportRecordDo`，`FileImportRecordQueryParam → ListFileImportRecordParam` | 纯同名字段 |
| `MaterialLogicalPlantRefConvertor` | `MaterialLogicalPlantRef ↔ MaterialLogicalPlantRefDo` | 纯同名字段 |

**需修改 RepositoryImpl（替换私有方法 → 注入 Convertor）：**

| 文件 | 当前 BeanUtils 处数 |
|---|---|
| `infra/persistence/repository/impl/InventoryMaterialRepositoryImpl.java` | 4 处 |
| `infra/persistence/repository/impl/CodeApplyOrderRepositoryImpl.java` | 3 处 |
| `infra/persistence/repository/impl/CodeApplyOrderDetailRepositoryImpl.java` | 2 处 |
| `infra/persistence/repository/impl/CodeApplyApprovalRecordRepositoryImpl.java` | 2 处 |
| `infra/persistence/repository/impl/CodeRepositoryImpl.java` | 3 处 |
| `infra/persistence/repository/impl/InventoryLogRepositoryImpl.java` | 2 处 |
| `infra/persistence/repository/impl/FileImportRecordRepositoryImpl.java` | 3 处 |
| `infra/persistence/repository/impl/MaterialLogicalPlantRefRepositoryImpl.java` | 2 处 |

---

#### G24-S2：infra external 层（3 个文件）✅ **完成**

**注意**：`InventoryMaterialExternalServiceImpl` 直接注入了 `InventoryMaterialMapper`（infra Mapper），属于 G23 架构债务范畴，本次只替换其中的 `BeanUtils`，不拆架构。

**需新建 Convertor（放在 `infra/persistence/convertor/`）：**

| Convertor 接口 | 映射对 | 特殊说明 |
|---|---|---|
| `InventoryMapExternalConvertor` | `InventoryMap → InventoryMapDTO` | 同名字段，单向 |
| `InventoryMaterialExternalConvertor` | `InventoryMaterialDo → InventoryMaterialDTO` | 同名字段，单向 |
| `ProductSkuBatchConvertor` | `SkuBatchDo → domain.SkuBatchResponse`，`SkuBatchDo → SkuResponse` | 同名字段，两个单向方法 |

**需修改 ServiceImpl：**

| 文件 | 当前 BeanUtils 处数 |
|---|---|
| `infra/external/InventoryMapExternalServiceImpl.java` | 1 处 |
| `infra/external/InventoryMaterialExternalServiceImpl.java` | 1 处 |
| `infra/external/ProductExternalServiceImpl.java` | 2 处（`skuBatchListByRequest` + `skuListByRequest`） |

---

#### G24-S3：application 层（7 个文件）✅ **完成**

**需新建 Mapper（放在 `application/convertor/`）：**

| Mapper 接口 | 映射对 | 特殊说明 |
|---|---|---|
| `AccessoriesFlowCodeAppConvertor` | `Code → AccessoriesFlowCodeResponse`，`ListAccessoriesFlowCodeRequest → CodeQueryParam`，`ListUnUsedAccessoriesFlowCodeRequest → CodeQueryParam` | 同名字段；`listCode` / `listUnUsedCode` 里 BeanUtils 复制 request→param 后再手动 `setStatus`，MapStruct 自动映射同名字段，`setStatus` 保留手动调用 |
| `CodeApplyOrderAppConvertor` | `CodeApplyOrderCreateRequest → CodeApplyOrder`，`CodeApplyOrder → CodeApplyOrderCreateResponse`，`CodeApplyOrder → CodeApplyOrderInfoResponse`，`CodeApplyOrder → CodeApplyOrderDTO`，`CodeApplyOrderPageRequest → CodeApplyOrderQueryParam` | 同名字段 |
| `InventoryMaterialAppConvertor` | `InventoryMaterialCreateRequest → InventoryMaterial`，`InventoryMaterialUpdateRequest → InventoryMaterial`，`ListMaterialCodeRequest → ListMaterialCodeQueryParam`，`InventoryMaterial → InventoryMaterialDTO`，`InventoryMaterialPageRequest → InventoryMaterialQueryParam` | 同名字段 |
| `FileImportAppConvertor` | `CreateFileImportRecordRequest → domain.FileImportRecord`，`domain.FileImportRecord → client.FileImportRecord`，`PageQueryFileImportRecordRequest → FileImportRecordQueryParam`，`UpdateFileImportRecordRequest → domain.FileImportRecord` | 注意两个 `FileImportRecord`：`domain.model.entity` vs `client.file.dto.response` |
| `MaterialDocAppConvertor` | `MaterialDocumentDTO → MaterialDocumentBO` | 用于 `createMaterialDocFromMessage` / `reverseMaterialDocFromMessage` 中 DTO→BO 转换（2 处） |
| `ProjectTaskAppConvertor` | `ProjectTaskBO → ProjectTaskPO` | 在 `apply()` 方法中 BeanUtils 后追加手动 set createTime/updateTime/isDelete，MapStruct 生成基础映射，手动 set 保留；`toBO(ProjectTaskPO)` 已是手动字段赋值，**不是 BeanUtils**，无需改动 |
| `DemandPlanAppConvertor` | `DemandPlanSelectReqBO → DemandPlanSelectReqCondition` | 需检索 `DemandPlanServiceImpl` 中的具体调用位置；`DemandPlanSelectReqCondition` 在 `infra.plan.persistence.condition` 包，application 层引用 infra 类（G23 架构债务），本次只替换 BeanUtils，不拆架构 |

---

#### G24-S4：domain 层（5 个文件）✅ **完成**

**注意**：domain 层 Mapper **禁止** `@Mapper(componentModel = "spring")`（违反 domain 无 Spring 约束）。
改用 `@Mapper(componentModel = "default")` + 手动实例化，或使用 `Mappers.getMapper(XxxMapper.class)` 静态持有。

**需新建 Mapper（放在对应 service 子包）：**

| Mapper 接口 | 位置 | 映射对 | 特殊说明 |
|---|---|---|---|
| `InventorySnapshotDomainMapper` | `domain/service/impl/converter/` | `InventoryAlertBO → InventoryAlert`（无 id 值对象），`InventorySnapshot → InventorySnapshotBO`（含 `id` 值对象 `InventorySnapshotId`） | 需 `default id2id` 互转；`InventorySnapshotCoreService.saveAlertInfo` 和 `toBO` 各 1 处 |
| `LogicalPlantDomainMapper` | `domain/service/impl/converter/` | `LogicalPlant → LogicalPlantBO`（含 `id` 值对象 `LogicalPlantId`） | 需 `default id2id` 互转 |
| `StorageLocationDomainMapper` | `domain/service/impl/converter/` | `StorageLocation → StorageLocationBO`（含 `id` 值对象 `StorageLocationId`） | 需 `default id2id` 互转；`StorageLocationId` 已有，参考 `StorageLocationDtoConvertor` |
| `InventoryDomainMapper` | `domain/service/impl/converter/` | `InventorySnapshot → InventorySnapshotBO`（含 `id`），`InventorySupplyBO → InventorySupply`，`CreateInTransitStockRequest → InventorySupply` | 可与 `InventorySnapshotDomainMapper` 合并，或拆分 |
| `MaterialDocCancelDomainMapper` | `domain/service/material/builder/converter/` | `MaterialDocumentBO → MaterialDocumentDTO`（**排除** `materialDocumentItems`），`MaterialDataBO → MaterialDataDTO`，`WarehouseDataBO → WarehouseDataDTO`，`QuantityAndAmountDataBO → QuantityAndAmountDataDTO`，`FinancialDataBO → FinancialDataDTO`，`MaterialExtDataBO → MaterialExtDataDTO` | `documentBO → documentDTO` 需 `@Mapping(target="materialDocumentItems", ignore=true)`；itemBO 内嵌对象仍需单独转换（已有逐字段手动赋值的 null 清除逻辑，部分字段有特殊处理，**此处 BeanUtils 仅占 5 处嵌套对象**，可按字段清除逻辑选择保留手动赋值或抽 MapStruct 方法） |

**domain 层 Mapper 实例化方式（2 选 1）：**

- 方案 A（推荐）：`@Mapper(componentModel = "default")`，在 service 中 `private static final XxxDomainMapper MAPPER = Mappers.getMapper(XxxDomainMapper.class);`
- 方案 B：拆出 converter 至 infra 层（更彻底但改动面大）

---

### [ ] G23：application 层直接引用 infra 持久化类（历史架构债务）

**优先级：低** — 不影响运行时，属于架构可维护性债务

- **Q19**：`InventoryMaterialApplicationServiceImpl.java:45` 直接注入 `MaterialLogicalPlantRefMapper`（infra Mapper）
  - 修复：在 `MaterialLogicalPlantRefRepository`（domain 接口）新增查询方法，application 层改走 domain 接口
- **Q20**：`application/plan/` 下 31 个文件直接 import `infra.plan.persistence.*`（Dao/PO/Condition）
  - 受影响：`PlanOrderApplicationServiceImpl`、`PlanGenerateServiceImpl`、`PlanConfigServiceImpl`、`BomCaseApplicationServiceImpl`、`DemandPlanServiceImpl`、`PlanDemandSupplyStockApplicationServiceImpl` 等
  - 修复：引入 domain `*Repository` 接口，PO→BO 转换下沉到 infra RepositoryImpl，分批推进

---

## 已完成总览

| 组 | 内容 | 状态 |
|----|------|------|
| G1 `M8` | 外部 URL 环境配置确认 | ✅ |
| G2 `M10+M11` | InTransitStockService / MaterialDocService 本地实现 | ✅ |
| G3 `M12+M13+M14` | BFF HTTP 路由兼容（ShipmentQuery / 流转码 / CommonController） | ✅ |
| G4 `M17+M18+M19+M21` | BFF 登录态承接 / CORS / 写接口上下文收口 | ✅ |
| G5 `M15` | Controller 直连 external/infra service 架构收口 | ✅ |
| G6 `M16` | 非业务 BFF 入口保留策略确认 | ✅ |
| G7 `M20` | BFF 热点缓存等价性确认 | ✅ |
| G8 `M22+M23+M24+M25` | plan 子包：PlanConfig 空壳 / DemandSupplyStock VO / ProjectTask 本地化 / ennUnifiedAuthorization 清理 | ✅ |
| G9 `M9+M26` | RDFA/Nacos/Dubbo 代码残留全量清理 | ✅ |
| G10 `N2+N3` | MonitorRule MQ 消费链路修复（topic 配置 + Consumer 补建） | ✅ |
| G11 `N4` | 13 张表 DDL 补全 + InvetoryLogMapper 文件名修正 | ✅ |
| G12 `N5+N6` | 6 个定时任务分布式锁 + 4 个 plan stub 任务实现 | ✅ |
| G13 `N7` | PlanCommonController 直注 infra stub → ApplicationService 封装 | ✅ |
| G14 `N1+N8` | 16 个 list() null stub → NOT_IMPLEMENTED + 路由二义性清理 | ✅ |
| G15 `Q1+Q2+Q6` | Spring 启动失败 / 移动均价数据损坏 / InventoryChangeMessage 分裂 | ✅ |
| G16 `Q3+Q4+Q15+Q18` | UserContextHolder null 检查 + MaterialDocQueryService 空实现 | ✅ |
| G17 `Q5` | /mybatis/mapper/ 死区目录删除 + DemandPlanMapper 重复 XML 清理 | ✅ |
| G18 `Q7+Q8` | 22 个 MQ 消费者 RuntimeException→BusinessException + 幂等去重 + tenant_id 过滤 | ✅ |
| G19 `Q9+Q10` | DemandPlanServiceImpl TODO 清理 / ParseException 修复 / @Transactional self-invocation 修复 | ✅ |
| G20 `Q11+Q12` | FileController 直注 OssFileService 收口 + application/convertor/ 无 infra import | ✅ |
| G21 `Q13+Q14` | 11 个孤儿实体类删除 + ResponseCodeEnum 重复定义合并 | ✅ |
| G22 `Q16+Q17` | demandSupplyStockBoardDetail Map 强转修复 + materialDesc @ExcelProperty 补加 | ✅ |

---

## plan 包路径规范

```
✅ 正确                                  ❌ 禁止
application/plan/config/service/        application/plan/plan/config/service/
interfaces/web/plan/PlanXxx.java        interfaces/web/plan/plan/PlanXxx.java
```

- interfaces 层 Converter 必须加 `Web`/`VO` 后缀
- 新增 DTO 前先 grep `client/plan/` 是否已有同名类

---

## 参考文件

**已有 MapStruct Convertor 参考（infra 层，`componentModel = "spring"`）：**
- `infra/persistence/convertor/InventorySnapshotConvertor.java` — 展示 `id2id` 值对象互转标准写法
- `infra/persistence/convertor/LogicalPlantConvertor.java`、`StorageLocationConvertor.java` 等

**已有 MapStruct Mapper 参考（application 层，`componentModel = "spring"`）：**
- `application/convertor/StorageLocationDtoConvertor.java`

**已有 MapStruct Mapper 参考（domain 层，`componentModel = "spring"`，注意此处 domain 有 Spring 依赖例外情况）：**
- `domain/service/external/converter/MaterialDocBizConverter.java`

*Last updated: 2026-07-02*
*See also: `/Users/kangll13/aiot/java-code/self/inventory-middle/CLAUDE.md` for skill/workflow guidance.*
