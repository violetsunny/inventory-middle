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
