# INDEX.md — inventory-middle-infra 模块索引

> Infra 层：持久化（MyBatis+）、外部服务（Feign）、基础设施适配器。

---

## 包结构

```
com.inventory.middle.infra
├── external/                  # 外部服务实现（domain 层接口的实现）
│   ├── InventoryMapExternalServiceImpl.java
│   ├── InventoryMaterialExternalServiceImpl.java
│   ├── ProductExternalServiceImpl.java
│   └── SequenceNoHelperImpl.java
├── feign/                     # OpenFeign 客户端
│   ├── CrmDistributorFeignClient.java
│   ├── SpDeliveryOrderFeignClient.java
│   └── impl/                  # Feign 降级/实现
├── integration/               # 集成适配器
│   ├── oss/                   # 对象存储
│   ├── participant/           # 参与者服务
│   └── push/                  # 推送服务
├── lock/                      # 分布式锁
├── mq/                        # MQ 生产者
│   └── InventoryMqProducerImpl.java
├── persistence/               # 持久化（核心）
│   ├── convertor/             # MapStruct Convertor（Do ↔ Entity，36 个）
│   ├── entity/                # 持久化对象 (PO/Do)（56 个）
│   ├── mapper/                # MyBatis Mapper 接口（35 个）
│   └── repository/
│       └── impl/              # Repository 实现（33 个）
├── plan/                      # 计划相关 infra
└── produce/                   # 消息生产模型
```

---

## 持久化对象 (PO/Do) 一览 (`persistence/entity/`)

### 基类

| 类 | 用途 |
|---|---|
| `BasePO` | 公共字段：`creatorId`, `createTime`, `updatorId`(拼写如此), `updateTime`, `deleted(int)` |
| `BaseExtendPO` | 扩展基类 |

### 实体 Do 与查询 Param PO

| 聚合 | 主 Do | 查询/参数 PO |
|---|---|---|
| InventoryAlert | `InventoryAlertDo` | — |
| InventoryAlertNotification | `InventoryAlertNotificationDo` | — |
| InventoryDemand | `InventoryDemandDo` | `InventoryDemandPo` |
| InventoryLog | `InventoryLogDo` | `InventoryLogQueryDo` |
| InventoryMap | `InventoryMapDo` | — |
| InventoryMapHis | `InventoryMapHisDo` | — |
| InventoryMaterial | `InventoryMaterialDo` | `InventoryMaterialQueryPO` |
| InventoryMonitorRule | `InventoryMonitorRuleDo` | — |
| InventoryMonitorRuleLine | `InventoryMonitorRuleLineDo` | — |
| InventoryPlan | `InventoryPlanDo` | — |
| InventorySnapshot | `InventorySnapshotDo` | — |
| InventorySupply | `InventorySupplyDo` | — |
| InventoryTransit | `InventoryTransitDo` | `TransferTransitStockDo` |
| LogicalPlant | `LogicalPlantDo` | — |
| StorageLocation | `StorageLocationDo` | — |
| Warehouse | `WarehouseDo` | — |
| MaterialDocMain | `MaterialDocMainDo` | — |
| MaterialDocItem | `MaterialDocItemDo` | — |
| MaterialLogicalPlantRef | `MaterialLogicalPlantRefDo` | `QueryMaterialLogicalPlantRefPO`, `PageQueryMaterialPlantRefRequestPO` |
| MdocSubExt | `MdocSubExtDo` | — |
| MdocSubFinance | `MdocSubFinanceDo` | — |
| MdocSubInOut | `MdocSubInOutDo` | — |
| MdocSubMap | `MdocSubMapDo` | — |
| MdocSubMaterial | `MdocSubMaterialDo` | — |
| MdocSubQuantity | `MdocSubQuantityDo` | — |
| Shipment | `ShipmentDo` | — |
| ShipmentLine | `ShipmentLineDo` | — |
| Code | `CodeDo` | `ListCodeParamPO`, `SelectOneCodeParamPO`, `SelectOneCodeParam2PO`, `CountCodeParamPO` |
| CodeApplyOrder | `CodeApplyOrderDo` | `CodeApplyOrderParamPO`, `CodeApplyOrderStatisticsPO` |
| CodeApplyOrderDetail | `CodeApplyOrderDetailDo` | `CodeApplyOrderDetailParamPO` |
| CodeApplyApprovalRecord | `CodeApplyApprovalRecordDo` | — |
| CodeGeneratorCfg | `CodeGeneratorCfgDo` | — |
| FileImportRecord | `FileImportRecordDo` | `FileImportLineRecordDo`, `ListFileImportRecordParam` |
| SkuBatch | `SkuBatchDo` | `SkuBatchQueryPO` |

---

## MapStruct Convertor (`persistence/convertor/`, 36 个)

全部使用 `@Mapper(componentModel = "spring")`，由 Spring 注入。

| Convertor | 源 ↔ 目标 |
|---|---|
| `InventoryAlertConvertor` | InventoryAlertDo ↔ InventoryAlert Entity |
| `InventoryAlertNotificationConvertor` | InventoryAlertNotificationDo ↔ Entity |
| `InventoryDemandConvertor` | InventoryDemandDo ↔ Entity |
| `InventoryLogConvertor` | InventoryLogDo ↔ Entity |
| `InventoryMapConvertor` | InventoryMapDo ↔ Entity |
| `InventoryMapExternalConvertor` | 外部服务专用 |
| `InventoryMapHisConvertor` | InventoryMapHisDo ↔ Entity |
| `InventoryMaterialConvertor` | InventoryMaterialDo ↔ Entity |
| `InventoryMaterialExternalConvertor` | 外部服务专用 |
| `InventoryMonitorRuleConvertor` | InventoryMonitorRuleDo ↔ Entity |
| `InventoryMonitorRuleLineConvertor` | InventoryMonitorRuleLineDo ↔ Entity |
| `InventoryPlanConvertor` | InventoryPlanDo ↔ Entity |
| `InventorySnapshotConvertor` | InventorySnapshotDo ↔ Entity |
| `InventorySupplyConvertor` | InventorySupplyDo ↔ Entity |
| `InventoryTransitConvertor` | InventoryTransitDo ↔ Entity |
| `LogicalPlantConvertor` | LogicalPlantDo ↔ Entity |
| `StorageLocationConvertor` | StorageLocationDo ↔ Entity |
| `WarehouseConvertor` | WarehouseDo ↔ Entity |
| `MaterialDocMainConvertor` | MaterialDocMainDo ↔ Entity |
| `MaterialDocItemConvertor` | MaterialDocItemDo ↔ Entity |
| `MaterialLogicalPlantRefConvertor` | MaterialLogicalPlantRefDo ↔ Entity |
| `MdocSubExtConvertor` | MdocSubExtDo ↔ Entity |
| `MdocSubFinanceConvertor` | MdocSubFinanceDo ↔ Entity |
| `MdocSubInOutConvertor` | MdocSubInOutDo ↔ Entity |
| `MdocSubMapConvertor` | MdocSubMapDo ↔ Entity |
| `MdocSubMaterialConvertor` | MdocSubMaterialDo ↔ Entity |
| `MdocSubQuantityConvertor` | MdocSubQuantityDo ↔ Entity |
| `ShipmentConvertor` | ShipmentDo ↔ Entity |
| `ShipmentLineConvertor` | ShipmentLineDo ↔ Entity |
| `CodeConvertor` | CodeDo ↔ Entity |
| `CodeApplyOrderConvertor` | CodeApplyOrderDo ↔ Entity |
| `CodeApplyOrderDetailConvertor` | CodeApplyOrderDetailDo ↔ Entity |
| `CodeApplyApprovalRecordConvertor` | CodeApplyApprovalRecordDo ↔ Entity |
| `CodeGeneratorCfgConvertor` | CodeGeneratorCfgDo ↔ Entity |
| `FileImportRecordConvertor` | FileImportRecordDo ↔ Entity |
| `ProductSkuBatchConvertor` | SkuBatchDo ↔ Entity |

---

## Repository 实现全量 (`persistence/repository/impl/`, 33 个)

每个 RepositoryImpl 注入对应 Mapper + Convertor，实现 domain 层定义的 Repository 接口。

| RepositoryImpl | Mapper | Convertor |
|---|---|---|
| `InventoryAlertRepositoryImpl` | `InventoryAlertMapper` | `InventoryAlertConvertor` |
| `InventoryAlertNotificationRepositoryImpl` | `InventoryAlertNotificationMapper` | `InventoryAlertNotificationConvertor` |
| `InventoryDemandRepositoryImpl` | `InventoryDemandMapper` | `InventoryDemandConvertor` |
| `InventoryLogRepositoryImpl` | `InventoryLogMapper` | `InventoryLogConvertor` |
| `InventoryMapRepositoryImpl` | `InventoryMapMapper` | `InventoryMapConvertor` |
| `InventoryMapHisRepositoryImpl` | `InventoryMapHisMapper` | `InventoryMapHisConvertor` |
| `InventoryMaterialRepositoryImpl` | `InventoryMaterialMapper` | `InventoryMaterialConvertor` |
| `InventoryMonitorRuleRepositoryImpl` | `InventoryMonitorRuleMapper` | `InventoryMonitorRuleConvertor` |
| `InventoryMonitorRuleLineRepositoryImpl` | `InventoryMonitorRuleLineMapper` | `InventoryMonitorRuleLineConvertor` |
| `InventoryPlanRepositoryImpl` | `InventoryPlanMapper` | `InventoryPlanConvertor` |
| `InventorySnapshotRepositoryImpl` | `InventorySnapshotMapper` | `InventorySnapshotConvertor` |
| `InventorySupplyRepositoryImpl` | `InventorySupplyMapper` | `InventorySupplyConvertor` |
| `InventoryTransitRepositoryImpl` | `InventoryTransitMapper` | `InventoryTransitConvertor` |
| `LogicalPlantRepositoryImpl` | `LogicalPlantMapper` | `LogicalPlantConvertor` |
| `StorageLocationRepositoryImpl` | `StorageLocationMapper` | `StorageLocationConvertor` |
| `WarehouseRepositoryImpl` | `WarehouseMapper` | `WarehouseConvertor` |
| `MaterialDocMainRepositoryImpl` | `MaterialDocMainMapper` | `MaterialDocMainConvertor` |
| `MaterialDocItemRepositoryImpl` | `MaterialDocItemMapper` | `MaterialDocItemConvertor` |
| `MaterialLogicalPlantRefRepositoryImpl` | `MaterialLogicalPlantRefMapper` | `MaterialLogicalPlantRefConvertor` |
| `MdocSubExtRepositoryImpl` | `MdocSubExtMapper` | `MdocSubExtConvertor` |
| `MdocSubFinanceRepositoryImpl` | `MdocSubFinanceMapper` | `MdocSubFinanceConvertor` |
| `MdocSubInOutRepositoryImpl` | `MdocSubInOutMapper` | `MdocSubInOutConvertor` |
| `MdocSubMapRepositoryImpl` | `MdocSubMapMapper` | `MdocSubMapConvertor` |
| `MdocSubMaterialRepositoryImpl` | `MdocSubMaterialMapper` | `MdocSubMaterialConvertor` |
| `MdocSubQuantityRepositoryImpl` | `MdocSubQuantityMapper` | `MdocSubQuantityConvertor` |
| `ShipmentRepositoryImpl` | `ShipmentMapper` | `ShipmentConvertor` |
| `ShipmentLineRepositoryImpl` | `ShipmentLineMapper` | `ShipmentLineConvertor` |
| `CodeRepositoryImpl` | `CodeMapper` | `CodeConvertor` |
| `CodeApplyOrderRepositoryImpl` | `CodeApplyOrderMapper` | `CodeApplyOrderConvertor` |
| `CodeApplyOrderDetailRepositoryImpl` | `CodeApplyOrderDetailMapper` | `CodeApplyOrderDetailConvertor` |
| `CodeApplyApprovalRecordRepositoryImpl` | `CodeApplyApprovalRecordMapper` | `CodeApplyApprovalRecordConvertor` |
| `CodeGeneratorCfgRepositoryImpl` | `CodeGeneratorCfgMapper` | `CodeGeneratorCfgConvertor` |
| `FileImportRecordRepositoryImpl` | `FileImportRecordMapper` | `FileImportRecordConvertor` |

---

## 关键约束

- **MyBatis XML 活跃路径：** `src/main/resources/mapper/**/*.xml`
- **死路径（勿用）：** `src/main/resources/mybatis/mapper/` — 25 个 XML 从未被加载
- `BasePO.updatorId` 是故意拼写（非 `updaterId`），与数据库列名一致
- Plan PO 有两套字段命名风格：`creator_id`/`updator_id`/`deleted` vs `create_user_id`/`update_user_id`/`is_delete`
- Convertor 使用 `@Mapper(componentModel = "spring")`，由 Spring 注入

---

*Last updated: 2026-07-02*
