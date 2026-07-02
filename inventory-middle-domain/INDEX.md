# INDEX.md — inventory-middle-domain 模块索引

> Domain 层：纯 Java 业务逻辑，**禁止 Spring 注解**。

---

## 包结构

```
com.inventory.middle.domain
├── common/                    # 通用工具
│   ├── annotation/            # 自定义注解
│   ├── constants/             # 常量
│   ├── exception/             # BusinessException 等
│   └── util/                  # 工具类
├── factory/                   # 领域工厂
├── handles/                   # 处理器
├── model/                     # 领域模型
│   ├── bo/                    # Business Objects
│   │   ├── BaseBO.java
│   │   ├── EnumMapping.java
│   │   ├── inventory/
│   │   ├── inventorysnapshot/
│   │   ├── log/
│   │   ├── logicalPlant/
│   │   ├── material/
│   │   ├── monitor/
│   │   ├── mq/
│   │   ├── sparepart/
│   │   ├── storageLocation/
│   │   ├── transit/
│   │   └── warehouse/
│   ├── entity/                # 领域实体（id 为值对象类型）
│   │   ├── inventory/         # 库存子包
│   │   ├── material/          # 物料凭证子包
│   │   └── (33 个实体类，见根 INDEX 聚合表)
│   ├── enums/                 # 领域枚举
│   └── types/                 # 值对象 / ID 类型（27 个）
│       ├── InventoryAlertId.java
│       ├── InventorySnapshotId.java
│       ├── InventorySupplyId.java
│       ├── LogicalPlantId.java
│       ├── StorageLocationId.java
│       └── ... (见下)
├── plan/                      # 计划相关
├── repository/                # 仓库接口（38 个）
├── service/                   # 领域服务
│   ├── impl/                  # 服务实现（13 个）
│   ├── convertor/             # MapStruct Convertor（entity↔BO，6 个）
│   ├── converter/             # 业务转换器（2 个）
│   ├── external/              # 外部服务接口
│   ├── lock/                  # 锁相关
│   ├── map/                   # 库存地图
│   ├── material/              # 物料凭证
│   │   ├── builder/           # 构建链
│   │   │   ├── SnMaterialDocCancelBuilderHandleChain.java
│   │   │   └── SnMaterialDocInBuilderHandleChain.java
│   │   ├── convertor/         # 取消转换器（6 个 CancelConvertor）
│   │   ├── ihandle/           # 处理器接口
│   │   └── model/             # 物料模型
│   ├── monitor/               # 监控
│   ├── mq/                    # 消息队列
│   ├── outbound/              # 出库
│   ├── validator/             # 验证器
│   └── (各 *DomainService.java / *CoreService.java)
├── specification/             # 规约
└── validator/                 # 验证器
```

---

## 值对象 ID 类型全量 (`model/types/`)

| 值对象 | 对应实体 |
|---|---|
| `InventoryAlertId` | InventoryAlert |
| `InventoryAlertNotificationId` | InventoryAlertNotification |
| `InventoryDemandId` | InventoryDemand |
| `InventoryMapId` | InventoryMap |
| `InventoryMapHisId` | InventoryMapHis |
| `InventoryMonitorRuleId` | InventoryMonitorRule |
| `InventoryMonitorRuleLineId` | InventoryMonitorRuleLine |
| `InventoryPlanId` | InventoryPlan |
| `InventorySnapshotId` | InventorySnapshot |
| `InventorySupplyId` | InventorySupply |
| `InventoryTransitId` | InventoryTransit |
| `LogicalPlantId` | LogicalPlant |
| `MaterialDocItemId` | MaterialDocItem |
| `MaterialDocMainId` | MaterialDocMain |
| `MdocSubExtId` | MdocSubExt |
| `MdocSubFinanceId` | MdocSubFinance |
| `MdocSubInOutId` | MdocSubInOut |
| `MdocSubMapId` | MdocSubMap |
| `MdocSubMaterialId` | MdocSubMaterial |
| `MdocSubQuantityId` | MdocSubQuantity |
| `ShipmentId` | Shipment |
| `ShipmentLineId` | ShipmentLine |
| `StorageLocationId` | StorageLocation |
| `WarehouseId` | Warehouse |
| `BatchNo` | — |
| `CodeGeneratorCfgId` | CodeGeneratorCfg |
| `MaterialCode` | — |

---

## Domain Service 一览

### DomainService（聚合根服务，Command 路径）

| 类 | 职责 |
|---|---|
| `InventoryDomainService` | 库存核心聚合 |
| `InventoryAlertDomainService` | 库存预警 |
| `InventoryLogDomainService` | 库存日志 |
| `InventoryMaterialDomainService` | 库存物料 |
| `InventoryMonitorRuleDomainService` | 监控规则 |
| `InventoryMonitorRuleLineDomainService` | 监控规则行 |
| `InventorySnapshotDomainService` | 库存快照 |
| `LogicalPlantDomainService` | 逻辑工厂 |
| `MaterialDocDomainService` | 物料凭证 |
| `MaterialLogicalPlantRefDomainService` | 物料逻辑工厂关系 |
| `CodeDomainService` | 编码 |

### CoreService（核心业务逻辑，被 DomainService 调用）

| 类 | 职责 |
|---|---|
| `InventoryCoreService` | 库存核心 |
| `InventoryAlertCoreService` | 预警核心 |
| `InventoryAlertNotificationCoreService` | 预警通知核心 |
| `InventoryDemandCoreService` | 需求核心 |
| `InventoryLogCoreService` | 日志核心 |
| `InventoryMonitorRuleCoreService` | 监控规则核心 |
| `InventoryMonitorRuleLineCoreService` | 监控规则行核心 |
| `InventorySupplyCoreService` | 供应核心 |
| `LogicalPlantCoreService` | 逻辑工厂核心 |
| `MaterialDocCoreService` | 物料凭证核心 |
| `MaterialDocItemCoreService` | 物料凭证行核心 |
| `MaterialDocSubCoreService` | 物料凭证子核心 |
| `MaterialLogicalPlantRefCoreService` | 物料逻辑工厂关系核心 |
| `StorageLocationCoreService` | 库位核心 |
| `InventoryMasterDataSourceDomainService` | 主数据源 |

---

## MapStruct Convertor（domain 层，`componentModel="default"`）

### `service/convertor/` — Entity ↔ BO

| Convertor | 源 → 目标 | 备注 |
|---|---|---|
| `InventoryAlertConvertor` | Entity ↔ BO | `id` ignore + 手动设值 |
| `InventorySnapshotConvertor` | Entity ↔ BO | `deleted` Integer↔Boolean expression |
| `InventorySupplyConvertor` | Entity ↔ BO | `id` ignore |
| `LogicalPlantConvertor` | Entity ↔ BO | `id` ignore |
| `StorageLocationConvertor` | Entity ↔ BO | `id` ignore |
| `CreateInTransitStockRequestConvertor` | CreateInTransitStockRequest → InventorySupply | 复杂字段重映射 |

### `service/material/convertor/` — BO → DTO（取消场景）

| Convertor | 备注 |
|---|---|
| `MaterialDocumentCancelConvertor` | MaterialDocument BO → DTO |
| `MaterialDataCancelConvertor` | MaterialData BO → DTO |
| `WarehouseDataCancelConvertor` | WarehouseData BO → DTO（字段一致） |
| `FinancialDataCancelConvertor` | FinancialData BO → DTO（字段一致） |
| `QuantityAndAmountDataCancelConvertor` | QuantityAndAmountData BO → DTO |
| `MaterialExtDataCancelConvertor` | MaterialExtData BO → DTO |

### `service/converter/` — 业务转换器（非 MapStruct）

| Converter | 职责 |
|---|---|
| `InventoryAlertBizConverter` | 预警业务转换 |
| `InventoryMonitorRuleBizConverter` | 监控规则业务转换 |

---

## 关键约束

- **禁止 Spring 注解** — 使用 `@Mapper(componentModel = "default")` + `Mappers.getMapper()`
- Entity `id` 字段是值对象（如 `InventorySnapshotId`），BO `id` 是 `Long` — Convertor 必须 `@Mapping(target="id", ignore=true)` + 手动设值
- Repository 接口在此定义，实现在 infra 层

---

*Last updated: 2026-07-02*
