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

- [ ] **P-1：interfaces/pom.xml 依赖核查**
  - 确认 `easyexcel`、`fastjson`、`commons-lang3` 已在 `inventory-middle-interfaces/pom.xml` 或父 pom 中声明
  - 若缺失则补充对应 `<dependency>`（版本与 `inventory-middle-infra` 保持一致）

### O2：RemoteParticipantCenterService 验收（P0-5~9）

- [ ] **P0-5：RemoteParticipantCenterService 替换确认**
  - grep `RemoteParticipantCenterService` 确认 interfaces/application 层无残留直接引用
  - 所有调用已改为 `UserContextHolder.get()` 或对应本地方法

- [ ] **P0-6：LogInterceptor 注册验证（静态检查）**
  - 确认 `LogInterceptor`（MDC/TraceId 注入）已在 `WebConfig` 中注册为 `addInterceptors`
  - `grep -rn "LogInterceptor\|addInterceptors" inventory-middle-interfaces/src --include="*.java"` 确认有注册调用，无需启动服务

- [ ] **P0-7：CatchAndLog 覆盖率验证**
  - `grep -r "@CatchAndLog" inventory-middle-interfaces/src` 统计数量
  - 对比 `interfaces/web/` 下 Controller 数量，确认全部覆盖（无遗漏）

- [ ] **P0-8：TODO 统计**
  - `grep -r "TODO" inventory-middle-interfaces/src` 输出所有残留 TODO
  - 逐一确认是否为已知存根（stub）还是未完成功能

- [ ] **P0-9：RDFA 残留确认**
  - `grep -r "rdfa\|RdfaResult\|@Authorize\|RdfaJob" inventory-middle-interfaces/src inventory-middle-application/src`
  - 确认零残留（若有，逐一替换）

### O3：Issue-1 ParticipantCenter 接通后回填

- [ ] **Issue-1：participant-center 接通后批量回填 userName / companyName**

  **背景：** 当前 BFF 多处返回 `userId` / `companyCode` 但前端需要显示名称，原 BFF 通过
  `RemoteParticipantCenterService` 批量查询后回填，迁移时降级为空 stub。

  **方案（已设计）：**
  1. 创建 `ParticipantCenterClient`（`infra/external/feign/ParticipantCenterClient.java`）— OpenFeign 客户端
  2. 在 KDLA `@FieldValueFind` 注解驱动的 `FieldValueFindHelp` 中注册回填处理器
  3. 在 BO/VO 中对 `userName`/`companyName` 字段加 `@FieldValueFind(...)` 注解，自动批量回填
  4. 前置条件：`remote.participantCenter.url` 已在 `application.yml` 配置

  **涉及文件：**
  - `infra/external/feign/ParticipantCenterClient.java`（新建）
  - `infra/external/feign/impl/ParticipantCenterRemoteServiceImpl.java`（新建）
  - `application.yml` 新增 `remote.participantCenter.url`
  - 各 BO/VO 中 `userName`/`companyName` 字段加注解（按需，逐文件修改）

### O4：CommonController stub 接通

- [ ] **CommonController.listCompany：接入 participant-center**

  **背景：** `interfaces/web/CommonController.listCompany()` 当前返回空列表 stub。

  **方案：**
  - Issue-1 中的 `ParticipantCenterClient` 完成后，注入并调用 `getCompanyList(tenantId)` 接口
  - 返回 `List<CompanyVO>`（companyCode + companyName）

### O5：FullAddressHelper 省市区名称注入

- [ ] **FullAddressHelper：省市区名称注入（当前返回编码）**

  **背景：** `FullAddressHelper.getFullAddress(provinceCode, cityCode, districtCode)` 当前直接拼接编码返回，未查询名称。

  **方案选项：**
  1. 接入行政区划数据表（本地 MySQL `t_area` 或类似表），通过 Mapper 查询名称
  2. 接入外部地址服务（participant-center 或独立地址服务）
  3. 静态配置文件（properties/json）映射编码→名称（适合数据量小且不常变）

  **建议先确认：** 项目中是否已有 `t_area` 或类似行政区划表，再决定方案。

### V：全量验收

- [ ] **V1：全量编译验证**
  ```bash
  JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home \
    mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
  ```
  期望：BUILD SUCCESS，0 ERROR

- [ ] **V2：CatchAndLog 覆盖率统计**
  - `grep -rl "@CatchAndLog" inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/` | wc -l
  - 对比 `ls inventory-middle-interfaces/src/.../web/ | grep Controller | wc -l`
  - 期望：全部 Controller 类均有 `@CatchAndLog`

- [ ] **V3：TODO 统计报告**
  - `grep -rn "TODO" inventory-middle-interfaces/src inventory-middle-application/src --include="*.java" | grep -v "stub\|占位\|待接入"` 
  - 期望：仅剩已知存根 TODO（stub 类中的 log.warn），无意外遗漏

- [ ] **V4：RDFA 残留清零确认**
  - `grep -rn "rdfa\|RdfaResult\|@Authorize\|RdfaJob\|top\.rdfa" inventory-middle-interfaces/src inventory-middle-application/src --include="*.java"`
  - 期望：0 匹配

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
