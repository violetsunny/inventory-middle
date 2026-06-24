# inventory-center + inventory-center-ext 迁移实现计划

> **状态：✅ 全部完成**（2026-06-22 前）

## 迁移目标

将 `inventory-center`（/Users/kangll13/aiot/java-code/self/inventory/inventory-center） + `inventory-center-ext`（/Users/kangll13/aiot/java-code/self/inventory/inventory-center-ext） 完整迁移至 `inventory-middle` DDD 架构，去除 RDFA/Nacos/Dubbo，替换为 KDLA 和 SpringBoot 、 SpringCloud 原生组件。

## 完成摘要

| 阶段 | 内容 | 状态 |
|------|------|------|
| P0/P1/P2 | 基础设施：Redisson/RocketMQ 依赖、RedissonLockService、Domain 层接口隔离 | ✅ |
| A1-A4 | Handle 链框架 + 16 个 Handler + 6 个 Chain + MQ Producer | ✅ |
| A2 | 出库特殊处理 outbound（10+ HandlerService） | ✅ |
| A3 | MaterialDocDomainServiceImpl（出入库/冲销核心逻辑） | ✅ |
| B1 | 移动平均价独立领域模块（InventoryMapDomainService） | ✅ |
| C1/C2 | Monitor Handle 链 + InventoryAlertDomainServiceImpl | ✅ |
| D1 | 7 个 MQ Consumer @RocketMQMessageListener | ✅ |
| E1 | Nacos/Dubbo 依赖清除 + application.yml 本地化 | ✅ |
| F1 | Infra 层 Mapper/Repository 补全 | ✅ |
| G1 | MaterialDocMainApplicationServiceImpl 接入 DomainService | ✅ |
| H1 | SnMaterialDocInConsumer 完整消费逻辑 | ✅ |
| Z1 | 全量编译 BUILD SUCCESS，0 RDFA/Nacos 残留 | ✅ |

## RDFA 平替对照（参考）

| RDFA | 平替 |
|------|------|
| `RdfaResult<T>` / `PagedRdfaResult<T>` | `SingleResponse<T>` / `PageResponse<T>` |
| `RdfaMqClient.send()` | `RocketMQTemplate.syncSend()` |
| `RdfaDistributeLockFactory` | `RedissonClient.getLock()` |
| `@RdfaJob` | `@Scheduled(cron="...")` |
| `NacosValue` / `NacosPropertySource` | `@Value` + `application.yml` |
| `@DubboReference` / `@DubboService` | `@FeignClient` + `RestTemplate` |

## 外部依赖处理

- `RemoteProductCenterRestService` → `ProductExternalServiceImpl`（本地 SkuBatch DB）
- `RemoteInventoryMapService` → `InventoryMapExternalServiceImpl`（本地 DB）
- `RemoteUniformPushService` → OpenFeign HTTP
- 原 ext Dubbo 调 inventory-center → 本地直调（已整体迁入）

## 出入库完整链路

```
MQ(SnMaterialDocIn) → SnMaterialDocInConsumer → MaterialDocMqValidator
  → MaterialDocMainApplicationService.createMaterialDocIn()
  → MaterialDocDomainService → SnMaterialDocInBuilderHandleChain
    → MaterialDocInAddHandleChain → [各 Handler...]
```

## 待后续跟进

V1-V4 全量验收 + Issue-1 ParticipantCenter 接通均已完成，详见 `remaining-todos.md`。

当前未完成项集中在 SCM Plan 模块功能缺口（H1-H11/M1-M8）和可能遗漏的 BFF Controller（X1-X3）。
