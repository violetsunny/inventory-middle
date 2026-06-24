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
4. 编译后必须 BUILD SUCCESS 7/7

---

## 剩余项（1 项）

- [ ] **M8：6 个外部 URL 默认值为空** ⏳ 部署配置
  - `application.yml` 中 `remote.*.url` 均为 `${ENV_VAR:}` 占位符
  - 生产环境必须配置，否则相关功能静默失效

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
| V1-V4 | 全量验收（BUILD SUCCESS/CatchAndLog 42/42/RDFA 零残留） | 全部通过 |
| C1-C5 | 配置审计（@EnableAspectJAutoProxy/RestTemplateConfig/ThreadPool/Cors） | 全部完成 |
| H1 | Plan MQ Consumer（15 个） | `interfaces/consumer/plan/` |
| H2 | Plan 定时任务（5 个） | `interfaces/schedule/` |
| H3 | PlanDemandSupplyStock 全链路 | PO/Mapper/Dao/ApplicationService/Controller |
| H4-H5 | ProjectTask stub | 源码无实现，保留占位 |
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
