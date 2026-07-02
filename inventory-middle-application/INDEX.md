# INDEX.md — inventory-middle-application 模块索引

> Application 层：用例编排、服务 Bean、DTO 转换。

---

## 包结构

```
com.inventory.middle.application
├── bo/                        # Application 层 BO
│   └── monitor/
├── convertor/                 # MapStruct Convertor（BO/Entity ↔ DTO，31 个）
├── helper/                    # 辅助工具
├── plan/                      # 计划相关
└── service/                   # 应用服务（61 个）
    ├── impl/                  # 服务实现
    └── monitor/               # 监控相关
```

---

## 应用服务一览 (`service/`, 61 个)

### Command 服务（写操作）

| 服务 | 聚合 |
|---|---|
| `AccessoriesFlowCodeApplicationService` | 编码-辅料流水 |
| `CodeApplyOrderApplicationService` | 编码申请单 |
| `DeliveryOrderApplicationService` | 交货单 |
| `DistributorApplicationService` | 经销商 |
| `FileApplicationService` | 文件 |
| `FileImportApplicationService` | 文件导入 |
| `InventoryAlertApplicationService` | 库存预警 |
| `InventoryAlertNotificationApplicationService` | 预警通知 |
| `InventoryCommonApplicationService` | 库存通用 |
| `InventoryExternalApplicationService` | 库存外部 |
| `InventoryMapApplicationService` | 库存地图 |
| `InventoryMaterialApplicationService` | 库存物料 |
| `InventoryMonitorRuleApplicationService` | 监控规则 |
| `InventoryMonitorRuleLineApplicationService` | 监控规则行 |
| `InventoryPlanApplicationService` | 库存计划 |
| `InventorySnapshotApplicationService` | 库存快照 |
| `InventorySupplyApplicationService` | 库存供应 |
| `InventoryTransitApplicationService` | 在途库存 |
| `LogicalPlantApplicationService` | 逻辑工厂 |
| `MaterialDocMainApplicationService` | 物料凭证头 |
| `MaterialDocItemApplicationService` | 物料凭证行 |
| `MdocSubExtApplicationService` | 凭证子-扩展 |
| `MdocSubFinanceApplicationService` | 凭证子-财务 |
| `MdocSubInOutApplicationService` | 凭证子-出入 |
| `MdocSubMapApplicationService` | 凭证子-映射 |
| `MdocSubMaterialApplicationService` | 凭证子-物料 |
| `MdocSubQuantityApplicationService` | 凭证子-数量 |
| `ShipmentApplicationService` | 发货 |
| `ShipmentLineApplicationService` | 发货行 |
| `SparePartApplicationService` | 备件 |
| `StorageLocationApplicationService` | 库位 |
| `WarehouseApplicationService` | 仓库 |

### Query 服务（读操作）

| 服务 | 聚合 |
|---|---|
| `InventoryAlertQueryService` | 库存预警 |
| `InventoryAlertNotificationQueryService` | 预警通知 |
| `InventoryDemandQueryService` | 库存需求 |
| `InventoryMapQueryService` | 库存地图 |
| `InventoryMapHisQueryService` | 库存地图历史 |
| `InventoryMonitorRuleQueryService` | 监控规则 |
| `InventoryMonitorRuleLineQueryService` | 监控规则行 |
| `InventoryPlanQueryService` | 库存计划 |
| `InventorySnapshotQueryService` | 库存快照 |
| `InventorySupplyQueryService` | 库存供应 |
| `InventoryTransitQueryService` | 在途库存 |
| `LogicalPlantQueryService` | 逻辑工厂 |
| `MaterialDocItemQueryService` | 物料凭证行 |
| `MaterialDocMainQueryService` | 物料凭证头 |
| `MaterialDocQueryService` | 物料凭证（组合） |
| `MdocSubExtQueryService` | 凭证子-扩展 |
| `MdocSubFinanceQueryService` | 凭证子-财务 |
| `MdocSubInOutQueryService` | 凭证子-出入 |
| `MdocSubMapQueryService` | 凭证子-映射 |
| `MdocSubMaterialQueryService` | 凭证子-物料 |
| `MdocSubQuantityQueryService` | 凭证子-数量 |
| `ShipmentQueryService` | 发货 |
| `ShipmentLineQueryService` | 发货行 |
| `StorageLocationQueryService` | 库位 |
| `WarehouseQueryService` | 仓库 |

---

## MapStruct Convertor (`convertor/`, 31 个)

全部使用 `@Mapper(componentModel = "spring")`，负责 BO/Entity ↔ Client DTO 转换。

| Convertor | 转换方向 |
|---|---|
| `AccessoriesFlowCodeConvertor` | BO ↔ DTO |
| `CodeApplyOrderConvertor` | BO ↔ DTO |
| `DemandPlanConvertor` | BO ↔ DTO |
| `FileImportRecordConvertor` | BO ↔ DTO |
| `InventoryAlertDtoConvertor` | BO ↔ InventoryAlertDto |
| `InventoryAlertNotificationDtoConvertor` | BO ↔ DTO |
| `InventoryDemandDtoConvertor` | BO ↔ DTO |
| `InventoryMapDtoConvertor` | BO ↔ DTO |
| `InventoryMapHisDtoConvertor` | BO ↔ DTO |
| `InventoryMaterialConvertor` | BO ↔ DTO |
| `InventoryMonitorRuleDtoConvertor` | BO ↔ DTO |
| `InventoryMonitorRuleLineDtoConvertor` | BO ↔ DTO |
| `InventoryPlanDtoConvertor` | BO ↔ DTO |
| `InventorySnapshotDtoConvertor` | BO ↔ DTO |
| `InventorySupplyDtoConvertor` | BO ↔ DTO |
| `InventoryTransitDtoConvertor` | BO ↔ DTO |
| `LogicalPlantDtoConvertor` | BO ↔ DTO |
| `MaterialDocItemDtoConvertor` | BO ↔ DTO |
| `MaterialDocMainDtoConvertor` | BO ↔ DTO |
| `MaterialDocumentConvertor` | BO ↔ DTO |
| `MdocSubExtDtoConvertor` | BO ↔ DTO |
| `MdocSubFinanceDtoConvertor` | BO ↔ DTO |
| `MdocSubInOutDtoConvertor` | BO ↔ DTO |
| `MdocSubMapDtoConvertor` | BO ↔ DTO |
| `MdocSubMaterialDtoConvertor` | BO ↔ DTO |
| `MdocSubQuantityDtoConvertor` | BO ↔ DTO |
| `ProjectTaskConvertor` | BO ↔ DTO |
| `ShipmentDtoConvertor` | BO ↔ DTO |
| `ShipmentLineDtoConvertor` | BO ↔ DTO |
| `StorageLocationDtoConvertor` | BO ↔ DTO |
| `WarehouseDtoConvertor` | BO ↔ DTO |

---

## 关键约束

- CQRS：Query 直接调用 domain Repository，Command 通过 domain DomainService
- Convertor 使用 `@Mapper(componentModel = "spring")`
- 禁止注入 infra 层的 Mapper/DAO/Do/PO

---

*Last updated: 2026-07-02*
