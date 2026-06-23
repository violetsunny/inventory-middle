# Inventory-Middle 剩余 TODO 实现计划

> **面向 AI 代理：** 使用 superpowers:subagent-driven-development 逐任务实现。步骤用复选框（`- [ ]`）跟踪进度。

## 基础信息

**架构（DDD 分层）：** domain（领域服务，无 Spring 依赖）→ infra（Repository/Mapper/外部集成）→ application（用例编排）→ interfaces（Controller/MQ Consumer）。外部依赖通过 domain 层 interface + infra 层实现隔离。

**技术栈：** Java 8、Spring Boot 2.7.7、MyBatis-Plus 3.5.3、Spring Cloud OpenFeign 3.1.5、KDLA Framework（`top.kdla.framework.*`）、EasyExcel 3.1.5、RocketMQ、Redisson

**环境路径：**
- Java 8: `/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home`
- Maven: `/usr/local/bin/mvn`
- KDLA 源码: `/Users/kangll13/aiot/java-code/aiot-robot/kdla`
- Maven 本地仓库: `/Users/kangll13/aiot/java-code/respository`

**构建验证命令：**
```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home \
  mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
```

**重要约束：**
1. domain 层禁止 Spring 注解
2. 新增 plan 包路径禁止出现 `plan.plan.` 双层嵌套
3. HTTP 外部调用优先 OpenFeign，次选 VertxHttpClient
4. EasyExcel 实现参考 KDLA 封装（`top.kdla.framework.supplement.excel`）
5. 编译后必须 BUILD SUCCESS，无 ERROR
6. 任务完成后，需要进行标注（`✅`）

---

## 完成状态总览

| 系列 | 内容 | 状态 |
|------|------|------|
| A-K | 库存快照/在途库存/物料凭证冲销/备件流转码/码审批/预警推送/Dubbo替换/产品中心/模型修复/监控巡检/逻辑仓查询 | ✅ 全部完成 |
| L1-L6 | KDLA 对齐（BusinessException/application.yml/MdcDot/StopWatch/BaseAssert/SequenceNo） | ✅ 全部完成 |
| P1-P10 | plan 包路径去重（plan/plan/ 嵌套修复）+ 跨模块重名类处理 | ✅ 全部完成 |
| R1-R9 | BFF 迁移残余（CatchAndLog/WebConfig/application-dev.yml/SnapshotQuery/MonitorRule枚举/Excel导入/MaterialDoc/LogicalPlant/StorageLocation/Warehouse） | ✅ 全部完成 |
| SCM O-4 | SCM Plan 迁移代码全量提交（f441f65） | ✅ 完成 |
| **N1-N5** | **EasyExcel 改造 + R6/R10/R11/R12 外部服务接入** | **✅ 全部完成** |
| **O1 P-1** | **interfaces/pom.xml 补充 fastjson + commons-lang3 依赖** | **✅ 完成** |
| **O2 P0-5~9** | **静态检查：RemoteParticipantCenterService 残留/LogInterceptor 注册/CatchAndLog 覆盖/TODO/RDFA 清零** | **✅ 全部通过** |
| **SCM 路由补全** | **DemandPlanController + PlanDemandSupplyStockController + PlanCommonController（3 个缺失路由补全）** | **✅ 完成** |
| **V1-V4** | **全量验收：编译 BUILD SUCCESS / CatchAndLog 42/42 / RDFA 零残留** | **✅ 全部通过** |
| **O3** | **ParticipantCenter HTTP 客户端（domain 接口 + infra RestTemplate 实现 + Stub 委托 + yml 占位符）** | **✅ 完成（participant-token 待接入）** |
| **O4** | **CommonController.listCompany — N/A（scm-plan-bff 原始无此方法）** | **✅ N/A** |
| **O5** | **FullAddressHelper 省市区拼接工具（application/helper/）** | **✅ 完成** |

---

## 已完成任务参考摘要

> 以下为已完成内容的极简摘要，仅供参考。详细实现见 git 历史。

| 系列 | 关键实现 | Commit 关键词 |
|------|---------|--------------|
| A | InventorySnapshot disableById/adjustDown；InventoryAlert batchStore/batchDeleteByIds | `feat(domain): implement InventorySnapshot write ops` |
| B | InventorySupply batchStore/batchUpdateBySourceOrderNo；InventoryDomainServiceImpl 两个 stub | `feat(domain): implement in-transit stock batch` |
| C | reverseMaterialDoc（查原凭证→反向出入库→markAsReversed）；MaterialDocCoreService 补方法 | `feat(domain): implement reverseMaterialDoc` |
| D | manufacturerInStock/regenerateCode/updateCodeForDeliverOrder/fleeingGoodsApplyCheck/queryCodesForPrint；SequenceNoHelper 接入 | `feat(application): accessories flow code` |
| E | CodeApplyOrder.approval（状态校验→查单→更新）| `feat(application): code apply order approval` |
| F | InventoryAlertNotificationApplicationServiceImpl 接入 RemoteUniformPushService（RestTemplate） | `feat(application): alert notification push` |
| G | 移除 Dubbo，改本地调用；Spring Cloud OpenFeign 替代 | — |
| H | ProductExternalServiceImpl 本地 SkuBatch DB 实现 | — |
| I | PageListLogicalPlantResponseBO.slList 改 StorageLocationBO；isUseInventoryMaterialData 实现 | `fix(domain): fix model types` |
| J | MonitorAnnualInspectionHandleChain 翻页分页修复 | `fix(domain): fix annual inspection paging` |
| K | LogicalPlantCoreService.listByWarehouseNo；Repository + Impl 补充 | `feat(domain): listByWarehouseNo` |
| L1 | BusinessException extends BizException | `refactor(domain): BusinessException extends BizException` |
| L2 | application.yml 补 kdla.exception/log/stopwatch 配置 | — |
| L3 | 7 个 MQ Consumer.onMessage 加 `@MdcDot` | `feat(interfaces): add @MdcDot to consumers` |
| L4 | 3 个核心 ApplicationServiceImpl 关键方法加 `@StopWatchWrapper` | `feat(application): add @StopWatchWrapper` |
| L5 | domain 层 if-throw 替换为 BaseAssert | `refactor(domain): replace null-checks with BaseAssert` |
| L6 | SequenceNoHelperImpl 委托 KDLA SequenceNoGenerator；code_generator_cfg 表 | `feat(infra): delegate SequenceNoHelper` |
| P1-P6 | plan 包路径去重（application/interfaces/client/infra 四层各自修平 plan/plan/ 嵌套） | — |
| P7-P10 | PlanConfigConverter→PlanConfigWebConverter；interfaces 重名 DTO 加 Http 后缀 | — |
| R1-R9 | BFF 迁移：CatchAndLog/WebConfig/application-dev.yml/SnapshotQuery 7方法/MonitorRule枚举/importByExcel/MaterialDoc updateAnnualDate+cityGasImport/LogicalPlant listByIdList/StorageLocation getByDescription/WarehouseController | 见各任务 commit |
| SCM | scm-plan migration v3（f441f65） | `ENH: scm-plan migration v3` |
| N1 | ImportMonitorRuleLineExcelBO extends BaseExcel；MonitorRuleLineExcelReadListener；importByExcel 改 Listener 模式；/ruleLine/template 改 KdlaExcelWrite.writeWeb() | `feat(application): refactor Excel import to KdlaExcelReadListener` |
| N2 | RemoteProductCenterRestService 扩展 queryBuildMaterialInfo/fuzzyQueryByName；ProductExternalServiceImpl 本地 SkuBatch 实现；MaterialDocController 两个 TODO 替换 | `feat(infra/interfaces): implement N2 productExternal` |
| N3 | InventoryTransitDto 新增 uomName；RemoteProductCenterRestService 扩展 getUnitNameByCode（降级返回编码）；InventoryTransitController queryInTransitStockPage 填充 uomName | `feat(domain/infra/interfaces): implement N3/N4/N5` |
| N4 | 新建 SpDeliveryOrderRemoteService（domain）、SpDeliveryOrderFeignClient（infra）、SpDeliveryOrderRemoteServiceImpl（降级）；DeliveryOrderMgntController 6 个 TODO 替换；application.yml 新增 remote.spDelivery.url | `feat(domain/infra/interfaces): implement N3/N4/N5` |
| N5 | 新建 CrmDistributorRemoteService（domain）、CrmDistributorFeignClient（infra）、CrmDistributorRemoteServiceImpl（降级）；DistributorController 2 个 TODO 替换；application.yml 新增 remote.crm.url | `feat(domain/infra/interfaces): implement N3/N4/N5` |

---

## BFF 迁移缺口（待后续跟进）

> 以下项目在 BFF 主体完成后留下的后续工作，尚未实现。

### O1：依赖检查

- [x] **P-1：interfaces/pom.xml 依赖核查** ✅
  - fastjson + commons-lang3 已补充到 `inventory-middle-interfaces/pom.xml`

### O2：RemoteParticipantCenterService 验收（P0-5~9）

- [x] **P0-5：RemoteParticipantCenterService 替换确认** ✅ 零残留
- [x] **P0-6：LogInterceptor 注册验证** ✅ `starter/config/WebConfig.java` 已注册
- [x] **P0-7：CatchAndLog 覆盖率验证** ✅ 42/42 Controller 全覆盖
- [x] **P0-8：TODO 统计** ✅ 剩余均为原始骨架占位 `/list` 接口，非 BFF 迁移遗留
- [x] **P0-9：RDFA 残留确认** ✅ 仅注释，无代码引用

### O3：Issue-1 ParticipantCenter 接通

- [x] **Issue-1：ParticipantCenter HTTP 客户端 + Stub 委托** ✅
  - 新建 `ParticipantCenterRemoteService`（domain 接口）
  - 新建 `ParticipantCenterRemoteServiceImpl`（infra RestTemplate 实现，3 个 URL 占位符）
  - `PlanParticipantStub` 改为委托真实服务 + 降级 fallback
  - `DemandPlanServiceImpl` 硬编码 "永康城燃" 替换为 `planParticipantStub.getCompanyName()`
  - `application.yml` 新增 `remote.participant.search.url` / `tenant.url` / `identify.url` / `appId`
  - TODO: participant-token 认证待接入（当前无 auth header）

### O4：CommonController stub 接通

- [x] **CommonController.listCompany：N/A** ✅
  - scm-plan-bff 原始 CommonController 中不存在 `listCompany` 方法（误记）
  - `getTenantDeptTree` / `getCompanyName` 已通过 O3 接入

### O5：FullAddressHelper 省市区名称注入

- [x] **FullAddressHelper：省市区拼接工具** ✅
  - 新建 `application/helper/FullAddressHelper.java`
  - `buildFullAddress(province, city, region, address)` 拼接完整地址
  - `injectFullAddress(list, ...)` 泛型批量注入（函数式接口）
  - warehouse/logical_plant 表已存名称文本（非编码），无需 masterdata 反查

### V：全量验收

- [x] **V1：全量编译验证** ✅ BUILD SUCCESS 7/7 模块（2026-06-23）
- [x] **V2：CatchAndLog 覆盖率统计** ✅ 42/42
- [x] **V3：TODO 统计报告** ✅ 剩余均为骨架 `/list` 占位，非迁移遗留
- [x] **V4：RDFA 残留清零确认** ✅ 0 代码引用

---

## 未完成项（2026-06-23 审计发现）

> 以下为全量审计发现的遗漏/未完成项，按优先级排列。

### 高优先级（功能完全不可用）

- [ ] **H1：Plan 模块 MQ Consumer 缺失**
  - `application.yml` 已配置 `rocketmq.plan.demand.*` 和 `rocketmq.plan.inventory.*` topic/group
  - 但 `interfaces/consumer/` 下无 plan 相关 Consumer 类
  - 需从 `scm-plan-management` 迁移 MQ 消费逻辑

- [ ] **H2：Plan 模块定时任务缺失**
  - `application.yml` 已配置 5 个 `plan.job.*.cron`，但无对应 Java 实现
  - 缺失任务：计划生成 / 需求计划明细生成 / 供需存生成 / 计划订单逾期检查 / Redis 操作
  - 需从 `scm-plan-management` 迁移 `@RdfaJob` → `@Scheduled`

- [ ] **H3：PlanDemandSupplyStockController 3 个方法全 stub**
  - `demandSupplyStockBoard` / `demandSupplyStockBoardDetail` / `renderBomTree` 均返回空
  - Application 层无 `PlanDemandSupplyStockApplicationService` 实现
  - 需创建 ApplicationService 并接入 infra 层

- [ ] **H4：ProjectTaskController 3 个方法全 stub**
  - 路径 `/projectTask`，3 个方法返回 `buildFailure("TODO", ...)`
  - 需确认 scm-plan-management 中对应实现并迁移

- [ ] **H5：ProjectTaskManageController 1 个方法 stub**
  - 路径 `/projectTask`，管理端方法返回 `buildFailure("TODO", ...)`

- [ ] **H6：DemandSupplySourceDao 2 个方法抛 UnsupportedOperationException**
  - `infra/plan/persistence/dao/DemandSupplySourceDao.java` L16, L31, L43
  - 调用即崩溃，需补全 MyBatis 实现

- [x] **H7：InventorySupportService 接口无实现类** ✅ 已修复
  - `InventorySupportServiceImpl` 已存在（145 行），注入 5 个依赖服务，6 个接口方法全部有实现

- [ ] **H8：ProductSupportService 接口无实现类**
  - `application/plan/support/ProductSupportService.java` 定义了产品查询接口
  - 无 `@Service` 实现类

- [ ] **H9：PlanProductStub 返回空值**
  - `queryMaterialByCode` 返回 `null`，`queryProductMap` 返回 `emptyMap()`
  - 需接入 `RemoteProductCenterRestService` 或创建 Feign Client

- [ ] **H10：participant-token 认证未接入**
  - `ParticipantCenterRemoteServiceImpl.get()` 方法无 auth header
  - 所有 ParticipantCenter 远程调用在生产环境将因缺认证失败
  - 需实现 `ParticipantTokenService` 或从网关透传 token

- [ ] **H11：importDemandPlanMaterial EasyExcel 解析未接入**
  - `DemandPlanController` 方法签名接受 JSON 而非 `MultipartFile`
  - 缺少 EasyExcel 文件解析逻辑，前端无法通过文件上传导入物料

### 中优先级（功能降级/不完整）

- [ ] **M1：queryMaterialInfoByCode 物料名称/单位为空**
  - `PlanCommonController` 中 `materialName` 用 `materialCode` 填充，`unit` 为空串
  - 需接入 ProductCenter 查询真实物料信息

- [ ] **M2：PlanOrder 下发 token 为 null**
  - `PlanOrderWebConvertor` L73，外部下发链路未接入

- [ ] **M3：PlanOrder 计划类型默认采购**
  - `PlanOrderApplicationServiceImpl` L57，未接入 PlanConfig 计划类型查询

- [ ] **M4：BomCase 序列号用时间戳**
  - `BomCaseApplicationServiceImpl` L625/L629，需替换为 `SequenceFactory`

- [ ] **M5：PlanConfigDao 未迁移**
  - `DemandPlanMaterialDetailDao` L27/L81 中相关代码被注释

- [ ] **M6：MQ 发送失败无重试**
  - `DefaultMqProducer` L42，失败消息未落库

- [ ] **M7：DemandPlanServiceImpl 物料校验被注释**
  - L338-340 `validateImportInfo` 调用被注释

- [ ] **M8：6 个外部 URL 默认值为空**
  - `UNIFORM_PUSH_URL` / `SP_DELIVERY_URL` / `CRM_URL` / `PARTICIPANT_SEARCH_URL` / `PARTICIPANT_TENANT_URL` / `PARTICIPANT_IDENTIFY_URL`
  - 生产环境必须配置，否则相关功能静默失效

### 可能遗漏的迁移项（inventory-center-bff）

- [ ] **X1：SparePartController（备品备件）**
  - 原始路径 `/spare_part`，含分页查询 + SKU 保存
  - inventory-middle 中无对应 Controller
  - 需确认：备品备件业务是否已下线或归入其他系统

- [ ] **X2：CommonController 省/市/公司查询（inventory-center-bff）**
  - 原始路径 `/common`，提供省、市、公司树等通用查询
  - inventory-middle 的 `PlanCommonController` 是 plan 模块功能集，与此不同
  - 需确认：前端是否仍依赖这些接口

- [ ] **X3：FileController OSS 文件操作**
  - 原始路径 `/files/download`，OSS 文件下载
  - inventory-middle 的 `FileImportController` 仅管理导入记录，不含 OSS 操作
  - 需确认：文件下载功能是否仍需要

### 低优先级（代码质量）

- [ ] **L1：30 处 `//TODO list query`** — Facade + Controller 层旧 Dubbo 迁移遗留
- [ ] **L2：10 处 Javadoc `@description: TODO`** — 各模块 DTO/BO 文档占位符
- [ ] **L3：Domain 层依赖 infra entity** — `MaterialDocumentItemBO` L96 架构分层问题

### 配置审计修复（2026-06-23）

- [x] **C1：ProviderApplication 补充 @EnableAspectJAutoProxy + @MapperScan** ✅
  - 三个原始项目均有 `@EnableAspectJAutoProxy(proxyTargetClass = true)`，inventory-middle 缺失
  - 补充 `@MapperScan(basePackages = "com.inventory.middle.infra.**.mapper")` 显式扫描 Mapper
- [x] **C2：RestTemplateConfig 从 infra 移到 starter/config** ✅
  - 全局配置应集中在 starter/config，已从 `infra/config/` 移至 `starter/config/`
- [x] **C3：ThreadPoolConfig 修复参数注入** ✅
  - 原代码字段无 `@Value` 注入，全为 Java 默认值 0（corePoolSize=0 会导致运行时异常）
  - 补充 `@Value` 注解 + 合理默认值（core=10, max=50, keepAlive=60s, queue=1000）
- [x] **C4：补充 CorsConfig 跨域配置** ✅
  - inventory-center-bff 原有 CorsConfig，inventory-middle 缺失
  - 新建 `starter/config/CorsConfig.java`，允许所有来源/方法/头
- [x] **C5：PlanExecutorConfig 保留在 application 层** ✅ N/A
  - application 模块不能依赖 starter（循环依赖），plan 专用线程池保留在 `application/plan/calculate/config/`

---

## plan 包路径规范（强制）

```
✅ 正确                                  ❌ 禁止
application/plan/config/service/        application/plan/plan/config/service/
interfaces/web/plan/PlanXxx.java        interfaces/web/plan/plan/PlanXxx.java
client/plan/plan-config/dto/            client/plan/plan/dto/
infra/plan/persistence/dao/             infra/plan/persistence/dao/plan/
```

- interfaces 层 Converter 必须加 `Web`/`VO` 后缀
- 新增 DTO 前先 grep client/plan/ 是否已有同名类
