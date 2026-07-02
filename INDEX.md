# INDEX.md — inventory-middle 项目索引

> Agent 快速检索文件，减少全局 grep 和上下文消耗。

---

## 项目概览

| 项 | 值 |
|---|---|
| GroupId | `com.inventory.middle` |
| 版本 | 1.0.0 |
| Java | 1.8 target |
| Spring Boot | 2.7.7 |
| Spring Cloud | 2021.0.5 |
| MyBatis Plus | 3.5.3 |
| MapStruct | 1.4.2.Final |
| 架构 | DDD 六模块单体 |

---

## 模块依赖关系

```
starter → interfaces → application → domain ← infra
                                                ↑
                       client (独立 artifact)
```

- domain 无 Spring 依赖（纯 Java 业务层）
- Query: ApplicationService → Repository
- Command: ApplicationService → DomainService → Repository

---

## 模块索引

| 模块 | 路径 | INDEX |
|---|---|---|
| **domain** | `inventory-middle-domain/` | [INDEX.md](inventory-middle-domain/INDEX.md) |
| **infra** | `inventory-middle-infra/` | [INDEX.md](inventory-middle-infra/INDEX.md) |
| **application** | `inventory-middle-application/` | [INDEX.md](inventory-middle-application/INDEX.md) |
| **interfaces** | `inventory-middle-interfaces/` | [INDEX.md](inventory-middle-interfaces/INDEX.md) |
| **client** | `inventory-middle-client/` | [INDEX.md](inventory-middle-client/INDEX.md) |
| **starter** | `inventory-middle-starter/` | [INDEX.md](inventory-middle-starter/INDEX.md) |

---

## 业务聚合（Aggregate）一览

| 聚合 | 领域实体 (domain/model/entity) | 仓库接口 (domain/repository) | 仓库实现 (infra/persistence/repository/impl) | 持久化对象 (infra/persistence/entity) | MyBatis Mapper (infra/persistence/mapper) | 基础设施转换器 (infra/persistence/convertor) |
|---|---|---|---|---|---|---|
| InventoryAlert | `InventoryAlert.java` | `InventoryAlertRepository.java` | `InventoryAlertRepositoryImpl.java` | `InventoryAlertDo.java` | `InventoryAlertMapper.java` | `InventoryAlertConvertor.java` |
| InventoryAlertNotification | `InventoryAlertNotification.java` | `InventoryAlertNotificationRepository.java` | `InventoryAlertNotificationRepositoryImpl.java` | `InventoryAlertNotificationDo.java` | `InventoryAlertNotificationMapper.java` | `InventoryAlertNotificationConvertor.java` |
| InventoryDemand | `InventoryDemand.java` | `InventoryDemandRepository.java` | `InventoryDemandRepositoryImpl.java` | `InventoryDemandDo.java` / `InventoryDemandPo.java` | `InventoryDemandMapper.java` | `InventoryDemandConvertor.java` |
| InventoryLog | `InventoryLog.java` | `InventoryLogRepository.java` | `InventoryLogRepositoryImpl.java` | `InventoryLogDo.java` / `InventoryLogQueryDo.java` | `InventoryLogMapper.java` | `InventoryLogConvertor.java` |
| InventoryMap | `InventoryMap.java` | `InventoryMapRepository.java` | `InventoryMapRepositoryImpl.java` | `InventoryMapDo.java` | `InventoryMapMapper.java` | `InventoryMapConvertor.java` + `InventoryMapExternalConvertor.java` |
| InventoryMapHis | `InventoryMapHis.java` | `InventoryMapHisRepository.java` | `InventoryMapHisRepositoryImpl.java` | `InventoryMapHisDo.java` | `InventoryMapHisMapper.java` | `InventoryMapHisConvertor.java` |
| InventoryMaterial | `InventoryMaterial.java` | `InventoryMaterialRepository.java` | `InventoryMaterialRepositoryImpl.java` | `InventoryMaterialDo.java` / `InventoryMaterialQueryPO.java` | `InventoryMaterialMapper.java` | `InventoryMaterialConvertor.java` + `InventoryMaterialExternalConvertor.java` |
| InventoryMonitorRule | `InventoryMonitorRule.java` | `InventoryMonitorRuleRepository.java` | `InventoryMonitorRuleRepositoryImpl.java` | `InventoryMonitorRuleDo.java` | `InventoryMonitorRuleMapper.java` | `InventoryMonitorRuleConvertor.java` |
| InventoryMonitorRuleLine | `InventoryMonitorRuleLine.java` | `InventoryMonitorRuleLineRepository.java` | `InventoryMonitorRuleLineRepositoryImpl.java` | `InventoryMonitorRuleLineDo.java` | `InventoryMonitorRuleLineMapper.java` | `InventoryMonitorRuleLineConvertor.java` |
| InventoryPlan | `InventoryPlan.java` | `InventoryPlanRepository.java` | `InventoryPlanRepositoryImpl.java` | `InventoryPlanDo.java` | `InventoryPlanMapper.java` | `InventoryPlanConvertor.java` |
| InventorySnapshot | `InventorySnapshot.java` | `InventorySnapshotRepository.java` | `InventorySnapshotRepositoryImpl.java` | `InventorySnapshotDo.java` | `InventorySnapshotMapper.java` | `InventorySnapshotConvertor.java` |
| InventorySupply | `InventorySupply.java` | `InventorySupplyRepository.java` | `InventorySupplyRepositoryImpl.java` | `InventorySupplyDo.java` | `InventorySupplyMapper.java` | `InventorySupplyConvertor.java` |
| InventoryTransit | `InventoryTransit.java` | `InventoryTransitRepository.java` | `InventoryTransitRepositoryImpl.java` | `InventoryTransitDo.java` | `InventoryTransitMapper.java` | `InventoryTransitConvertor.java` |
| LogicalPlant | `LogicalPlant.java` | `LogicalPlantRepository.java` | `LogicalPlantRepositoryImpl.java` | `LogicalPlantDo.java` | `LogicalPlantMapper.java` | `LogicalPlantConvertor.java` |
| StorageLocation | `StorageLocation.java` | `StorageLocationRepository.java` | `StorageLocationRepositoryImpl.java` | `StorageLocationDo.java` | `StorageLocationMapper.java` | `StorageLocationConvertor.java` |
| Warehouse | `Warehouse.java` | `WarehouseRepository.java` | `WarehouseRepositoryImpl.java` | `WarehouseDo.java` | `WarehouseMapper.java` | `WarehouseConvertor.java` |
| MaterialDocMain | `MaterialDocMain.java` | `MaterialDocMainRepository.java` | `MaterialDocMainRepositoryImpl.java` | `MaterialDocMainDo.java` | `MaterialDocMainMapper.java` | `MaterialDocMainConvertor.java` |
| MaterialDocItem | `MaterialDocItem.java` | `MaterialDocItemRepository.java` | `MaterialDocItemRepositoryImpl.java` | `MaterialDocItemDo.java` | `MaterialDocItemMapper.java` | `MaterialDocItemConvertor.java` |
| MaterialLogicalPlantRef | `MaterialLogicalPlantRef.java` | `MaterialLogicalPlantRefRepository.java` | `MaterialLogicalPlantRefRepositoryImpl.java` | `MaterialLogicalPlantRefDo.java` | `MaterialLogicalPlantRefMapper.java` | `MaterialLogicalPlantRefConvertor.java` |
| MdocSubExt | `MdocSubExt.java` | `MdocSubExtRepository.java` | `MdocSubExtRepositoryImpl.java` | `MdocSubExtDo.java` | `MdocSubExtMapper.java` | `MdocSubExtConvertor.java` |
| MdocSubFinance | `MdocSubFinance.java` | `MdocSubFinanceRepository.java` | `MdocSubFinanceRepositoryImpl.java` | `MdocSubFinanceDo.java` | `MdocSubFinanceMapper.java` | `MdocSubFinanceConvertor.java` |
| MdocSubInOut | `MdocSubInOut.java` | `MdocSubInOutRepository.java` | `MdocSubInOutRepositoryImpl.java` | `MdocSubInOutDo.java` | `MdocSubInOutMapper.java` | `MdocSubInOutConvertor.java` |
| MdocSubMap | `MdocSubMap.java` | `MdocSubMapRepository.java` | `MdocSubMapRepositoryImpl.java` | `MdocSubMapDo.java` | `MdocSubMapMapper.java` | `MdocSubMapConvertor.java` |
| MdocSubMaterial | `MdocSubMaterial.java` | `MdocSubMaterialRepository.java` | `MdocSubMaterialRepositoryImpl.java` | `MdocSubMaterialDo.java` | `MdocSubMaterialMapper.java` | `MdocSubMaterialConvertor.java` |
| MdocSubQuantity | `MdocSubQuantity.java` | `MdocSubQuantityRepository.java` | `MdocSubQuantityRepositoryImpl.java` | `MdocSubQuantityDo.java` | `MdocSubQuantityMapper.java` | `MdocSubQuantityConvertor.java` |
| Shipment | `Shipment.java` | `ShipmentRepository.java` | `ShipmentRepositoryImpl.java` | `ShipmentDo.java` | `ShipmentMapper.java` | `ShipmentConvertor.java` |
| ShipmentLine | `ShipmentLine.java` | `ShipmentLineRepository.java` | `ShipmentLineRepositoryImpl.java` | `ShipmentLineDo.java` | `ShipmentLineMapper.java` | `ShipmentLineConvertor.java` |
| Code | `Code.java` | `CodeRepository.java` | `CodeRepositoryImpl.java` | `CodeDo.java` | `CodeMapper.java` | `CodeConvertor.java` |
| CodeApplyOrder | `CodeApplyOrder.java` | `CodeApplyOrderRepository.java` | `CodeApplyOrderRepositoryImpl.java` | `CodeApplyOrderDo.java` | `CodeApplyOrderMapper.java` | `CodeApplyOrderConvertor.java` |
| CodeApplyOrderDetail | `CodeApplyOrderDetail.java` | `CodeApplyOrderDetailRepository.java` | `CodeApplyOrderDetailRepositoryImpl.java` | `CodeApplyOrderDetailDo.java` | `CodeApplyOrderDetailMapper.java` | `CodeApplyOrderDetailConvertor.java` |
| CodeApplyApprovalRecord | `CodeApplyApprovalRecord.java` | `CodeApplyApprovalRecordRepository.java` | `CodeApplyApprovalRecordRepositoryImpl.java` | `CodeApplyApprovalRecordDo.java` | `CodeApplyApprovalRecordMapper.java` | `CodeApplyApprovalRecordConvertor.java` |
| CodeGeneratorCfg | `CodeGeneratorCfg.java` | `CodeGeneratorCfgRepository.java` | `CodeGeneratorCfgRepositoryImpl.java` | `CodeGeneratorCfgDo.java` | `CodeGeneratorCfgMapper.java` | `CodeGeneratorCfgConvertor.java` |
| FileImportRecord | `FileImportRecord.java` | `FileImportRecordRepository.java` | `FileImportRecordRepositoryImpl.java` | `FileImportRecordDo.java` | `FileImportRecordMapper.java` | `FileImportRecordConvertor.java` |

---

## 全量端到端调用链速查

以 InventoryAlert 为例，展示完整链路：

```
Controller (interfaces/web/InventoryAlertController.java)
  → FacadeImpl (interfaces/facade/InventoryAlertServiceFacadeImpl.java)
    → ApplicationService (application/service/InventoryAlertApplicationService.java)
      → [Query] Repository (domain/repository/InventoryAlertRepository.java)
      → [Command] DomainService (domain/service/InventoryAlertDomainService.java)
        → Repository (domain/repository/InventoryAlertRepository.java)
          → RepositoryImpl (infra/persistence/repository/impl/InventoryAlertRepositoryImpl.java)
            → Mapper (infra/persistence/mapper/InventoryAlertMapper.java)
            → Convertor (infra/persistence/convertor/InventoryAlertConvertor.java) — Do ↔ Entity
```

---

## 已知活跃问题（精简）

| # | 问题 | 位置 |
|---|---|---|
| 1 | `MaterialDocOutboundService` 无实现，Spring 注入失败 | `domain/service/external/` |
| 2 | `InventoryChangeMqConsumer` 反序列化字段缺失 (9 vs 15) | `interfaces/consumer/InventoryChangeMqConsumer.java` |
| 3 | `UserContextHolder.get()` 无 null-check | PlanConfig/PlanReport/PlanOrder Controller |
| 4 | MonitorRuleConsumer topic 配置错误，永久空转 | `application.yml` |
| 5 | 两套 `InventoryChangeMessage` 类 (9字段 vs 15字段) | `domain.model.bo.mq.sub` vs `infra.produce.model` |

详见 `docs/superpowers/plans/remaining-todos.md`。

---

*Last updated: 2026-07-02*
