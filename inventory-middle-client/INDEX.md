# INDEX.md — inventory-middle-client 模块索引

> Client 层：SDK/消费者客户端，独立 artifact（`inventory-middle-client:1.0.0-SNAPSHOT`）。

---

## 包结构

```
com.inventory.middle.client
├── code/                      # 编码相关
│   ├── annotation/            # 注解
│   ├── dto/                   # 编码 DTO
│   ├── enums/                 # 编码枚举
│   └── service/               # 编码服务接口
├── dto/                       # 通用 DTO
│   ├── base/                  # 基础 DTO
│   ├── cmd/                   # 命令 DTO
│   ├── command/               # 命令 DTO
│   ├── query/                 # 查询 DTO
│   ├── inventorydemand/       # 需求子包
│   ├── inventorysnapshot/     # 快照子包
│   ├── inventorysupply/       # 供应子包
│   ├── log/                   # 日志子包
│   ├── logicalPlant/          # 逻辑工厂子包
│   ├── map/                   # 地图子包
│   ├── material/              # 物料子包
│   ├── mdata/                 # 主数据子包
│   ├── monitory/              # 监控子包
│   ├── sku/                   # SKU子包
│   ├── sparepart/             # 备件子包
│   ├── storageLocation/       # 库位子包
│   ├── transit/               # 在途子包
│   └── warehouse/             # 仓库子包
├── enums/                     # 全局枚举（23 个）
│   └── monitor/               # 监控枚举
├── facade/                    # Facade 接口（28 个）
├── file/                      # 文件相关
├── material/                  # 物料相关
├── plan/                      # 计划相关
└── service/                   # Feign 服务接口（19 个）
```

---

## DTO 一览 (`dto/`)

### 顶层 DTO

| DTO | 聚合 |
|---|---|
| `InventoryAlertDto` | 库存预警 |
| `InventoryAlertNotificationDto` | 预警通知 |
| `InventoryDemandDto` | 库存需求 |
| `InventoryMapDto` | 库存地图 |
| `InventoryMapHisDto` | 地图历史 |
| `InventoryMonitorRuleDto` | 监控规则 |
| `InventoryMonitorRuleLineDto` | 监控规则行 |
| `InventoryPlanDto` | 库存计划 |
| `InventorySnapshotDto` | 库存快照 |
| `InventorySupplyDto` | 库存供应 |
| `InventoryTransitDto` | 在途库存 |
| `LogicalPlantDto` | 逻辑工厂 |
| `MaterialDocItemDto` | 物料凭证行 |
| `MaterialDocMainDto` | 物料凭证头 |
| `MdocSubExtDto` | 凭证子-扩展 |
| `MdocSubFinanceDto` | 凭证子-财务 |
| `MdocSubInOutDto` | 凭证子-出入 |
| `MdocSubMapDto` | 凭证子-映射 |
| `MdocSubMaterialDto` | 凭证子-物料 |
| `MdocSubQuantityDto` | 凭证子-数量 |
| `ShipmentDto` | 发货 |
| `ShipmentLineDto` | 发货行 |
| `StorageLocationDto` | 库位 |
| `WarehouseDto` | 仓库 |
| `UnitDto` | 单位 |
| `EnumResponse` | 枚举响应 |
| `KeyValueDTO` | 键值对 |
| `BaseRequest` | 请求基类 |
| `PageRequest` | 分页请求 |

---

## Facade 接口一览 (`facade/`, 28 个)

| Facade 接口 | 实现 (interfaces 层) |
|---|---|
| `AccessoriesFlowCodeServiceFacade` | `AccessoriesFlowCodeServiceFacadeImpl` |
| `CodeApplyOrderServiceFacade` | `CodeApplyOrderServiceFacadeImpl` |
| `FileImportServiceFacade` | `FileImportServiceFacadeImpl` |
| `InventoryAlertServiceFacade` | `InventoryAlertServiceFacadeImpl` |
| `InventoryAlertNotificationServiceFacade` | `InventoryAlertNotificationServiceFacadeImpl` |
| `InventoryDemandServiceFacade` | `InventoryDemandServiceFacadeImpl` |
| `InventoryMapServiceFacade` | `InventoryMapServiceFacadeImpl` |
| `InventoryMapHisServiceFacade` | `InventoryMapHisServiceFacadeImpl` |
| `InventoryMaterialServiceFacade` | `InventoryMaterialServiceFacadeImpl` |
| `InventoryMonitorRuleServiceFacade` | `InventoryMonitorRuleServiceFacadeImpl` |
| `InventoryMonitorRuleLineServiceFacade` | `InventoryMonitorRuleLineServiceFacadeImpl` |
| `InventoryPlanServiceFacade` | `InventoryPlanServiceFacadeImpl` |
| `InventorySnapshotServiceFacade` | `InventorySnapshotServiceFacadeImpl` |
| `InventorySupplyServiceFacade` | `InventorySupplyServiceFacadeImpl` |
| `InventoryTransitServiceFacade` | `InventoryTransitServiceFacadeImpl` |
| `LogicalPlantServiceFacade` | `LogicalPlantServiceFacadeImpl` |
| `MaterialDocItemServiceFacade` | `MaterialDocItemServiceFacadeImpl` |
| `MaterialDocMainServiceFacade` | `MaterialDocMainServiceFacadeImpl` |
| `MdocSubExtServiceFacade` | `MdocSubExtServiceFacadeImpl` |
| `MdocSubFinanceServiceFacade` | `MdocSubFinanceServiceFacadeImpl` |
| `MdocSubInOutServiceFacade` | `MdocSubInOutServiceFacadeImpl` |
| `MdocSubMapServiceFacade` | `MdocSubMapServiceFacadeImpl` |
| `MdocSubMaterialServiceFacade` | `MdocSubMaterialServiceFacadeImpl` |
| `MdocSubQuantityServiceFacade` | `MdocSubQuantityServiceFacadeImpl` |
| `ShipmentServiceFacade` | `ShipmentServiceFacadeImpl` |
| `ShipmentLineServiceFacade` | `ShipmentLineServiceFacadeImpl` |
| `StorageLocationServiceFacade` | `StorageLocationServiceFacadeImpl` |
| `WarehouseServiceFacade` | `WarehouseServiceFacadeImpl` |

---

## Feign 服务接口 (`service/`, 19 个)

| 接口 | 用途 |
|---|---|
| `InventoryService` | 库存核心 |
| `InventoryDemandService` | 库存需求 |
| `InventoryLogService` | 库存日志 |
| `InventoryMapService` | 库存地图 |
| `InventoryMasterDataSourceService` | 主数据源 |
| `InventoryMonitoryRuleService` | 监控规则 |
| `InventoryMonitoryRuleLineService` | 监控规则行 |
| `InventorySnapshotService` | 库存快照 |
| `InventorySupplyService` | 库存供应 |
| `InTransitStockService` | 在途库存 |
| `LogicalPlantService` | 逻辑工厂 |
| `MaterialDataService` | 物料数据 |
| `MaterialDocService` | 物料凭证 |
| `MaterialLogicalPlantRefService` | 物料逻辑工厂关系 |
| `SparePartService` | 备件 |
| `StorageLocationService` | 库位 |
| `UnitClientService` | 单位 |
| `WarehouseService` | 仓库 |
| `SayHiFeignService` | 健康检查 |

---

## 枚举一览 (`enums/`, 23 个)

| 枚举 | 用途 |
|---|---|
| `AppKeyEnum` | 应用标识 |
| `BaseEnableStatusEnum` | 启用/禁用 |
| `BaseStatusEnum` | 基础状态 |
| `BatchSysEnum` | 批次系统 |
| `BusinessTypeEnum` | 业务类型 |
| `CurrencyEnum` | 币种 |
| `InventorySupplyTypeEnum` | 供应类型 |
| `LogActionEnum` | 日志动作 |
| `LogModuleEnum` | 日志模块 |
| `MapStatusEnum` | 地图状态 |
| `MaterialAdjustTypeEnum` | 物料调整类型 |
| `MaterialDocCategoryEnum` | 凭证类别 |
| `MaterialDocGroupEnum` | 凭证分组 |
| `MaterialDocRefOrderTypeEnum` | 凭证参考订单类型 |
| `MaterialDocRefTypeEnum` | 凭证参考类型 |
| `MaterialDocSourceEnum` | 凭证来源 |
| `MaterialDocTypeEnum` | 凭证类型 |
| `ProductMasterDataSourceEnum` | 产品主数据源 |
| `SettlementTypeEnum` | 结算类型 |
| `StockTypeEnum` | 库存类型 |
| `StorageLocationTypeEnum` | 库位类型 |
| `TransferTransitTypeEnum` | 转移在途类型 |

---

*Last updated: 2026-07-02*
