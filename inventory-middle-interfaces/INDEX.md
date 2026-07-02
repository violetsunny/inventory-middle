# INDEX.md — inventory-middle-interfaces 模块索引

> Interfaces 层：Controller、Facade 实现、MQ Consumer、定时任务。

---

## 包结构

```
com.inventory.middle.interfaces
├── consumer/                  # RocketMQ 消费者
│   ├── plan/                  # 计划相关消费者
│   └── validator/             # 消息验证器
├── facade/                    # Facade 接口实现（28 个）
├── schedule/                  # 定时任务（5 个）
├── support/                   # 支撑工具
├── task/                      # 异步任务
└── web/                       # REST Controller
    ├── bff/                   # BFF 聚合接口
    ├── common/                # 通用
    ├── file/                  # 文件上传
    ├── plan/                  # 计划接口
    └── sparepart/             # 备件接口
```

---

## Controller 一览 (`web/`)

| Controller | 路由推测 | 聚合 |
|---|---|---|
| `InventoryAlertController` | /inventory-alert | 库存预警 |
| `InventoryAlertNotificationController` | /inventory-alert-notification | 预警通知 |
| `InventoryDemandController` | /inventory-demand | 库存需求 |
| `InventoryMapController` | /inventory-map | 库存地图 |
| `InventoryMapHisController` | /inventory-map-his | 地图历史 |
| `InventoryMaterialController` | /inventory-material | 库存物料 |
| `InventoryMonitorRuleController` | /inventory-monitor-rule | 监控规则 |
| `InventoryPlanController` | /inventory-plan | 库存计划 |
| `InventorySnapshotController` | /inventory-snapshot | 库存快照 |
| `InventorySupplyController` | /inventory-supply | 库存供应 |
| `InventoryTransitController` | /inventory-transit | 在途库存 |
| `LogicalPlantController` | /logical-plant | 逻辑工厂 |
| `MaterialDocController` | /material-doc | 物料凭证（组合） |
| `MaterialDocItemController` | /material-doc-item | 物料凭证行 |
| `MaterialDocMainController` | /material-doc-main | 物料凭证头 |
| `MdocSubExtController` | /mdoc-sub-ext | 凭证子-扩展 |
| `MdocSubFinanceController` | /mdoc-sub-finance | 凭证子-财务 |
| `MdocSubInOutController` | /mdoc-sub-in-out | 凭证子-出入 |
| `MdocSubMapController` | /mdoc-sub-map | 凭证子-映射 |
| `MdocSubMaterialController` | /mdoc-sub-material | 凭证子-物料 |
| `MdocSubQuantityController` | /mdoc-sub-quantity | 凭证子-数量 |
| `ShipmentController` | /shipment | 发货 |
| `ShipmentLineController` | /shipment-line | 发货行 |
| `StorageLocationController` | /storage-location | 库位 |
| `WarehouseController` | /warehouse | 仓库 |
| `AccessoriesFlowCodeController` | /accessories-flow-code | 编码流水 |
| `CodeApplyOrderController` | /code-apply-order | 编码申请 |
| `FileImportController` | /file-import | 文件导入 |

---

## Facade 实现一览 (`facade/`, 28 个)

每个 FacadeImpl 实现自 client 层定义的 ServiceFacade 接口，注入 application 层服务。

| FacadeImpl | 对应 Facade 接口 (client) |
|---|---|
| `AccessoriesFlowCodeServiceFacadeImpl` | `AccessoriesFlowCodeServiceFacade` |
| `CodeApplyOrderServiceFacadeImpl` | `CodeApplyOrderServiceFacade` |
| `FileImportServiceFacadeImpl` | `FileImportServiceFacade` |
| `InventoryAlertServiceFacadeImpl` | `InventoryAlertServiceFacade` |
| `InventoryAlertNotificationServiceFacadeImpl` | `InventoryAlertNotificationServiceFacade` |
| `InventoryDemandServiceFacadeImpl` | `InventoryDemandServiceFacade` |
| `InventoryMapServiceFacadeImpl` | `InventoryMapServiceFacade` |
| `InventoryMapHisServiceFacadeImpl` | `InventoryMapHisServiceFacade` |
| `InventoryMaterialServiceFacadeImpl` | `InventoryMaterialServiceFacade` |
| `InventoryMonitorRuleServiceFacadeImpl` | `InventoryMonitorRuleServiceFacade` |
| `InventoryMonitorRuleLineServiceFacadeImpl` | `InventoryMonitorRuleLineServiceFacade` |
| `InventoryPlanServiceFacadeImpl` | `InventoryPlanServiceFacade` |
| `InventorySnapshotServiceFacadeImpl` | `InventorySnapshotServiceFacade` |
| `InventorySupplyServiceFacadeImpl` | `InventorySupplyServiceFacade` |
| `InventoryTransitServiceFacadeImpl` | `InventoryTransitServiceFacade` |
| `LogicalPlantServiceFacadeImpl` | `LogicalPlantServiceFacade` |
| `MaterialDocItemServiceFacadeImpl` | `MaterialDocItemServiceFacade` |
| `MaterialDocMainServiceFacadeImpl` | `MaterialDocMainServiceFacade` |
| `MdocSubExtServiceFacadeImpl` | `MdocSubExtServiceFacade` |
| `MdocSubFinanceServiceFacadeImpl` | `MdocSubFinanceServiceFacade` |
| `MdocSubInOutServiceFacadeImpl` | `MdocSubInOutServiceFacade` |
| `MdocSubMapServiceFacadeImpl` | `MdocSubMapServiceFacade` |
| `MdocSubMaterialServiceFacadeImpl` | `MdocSubMaterialServiceFacade` |
| `MdocSubQuantityServiceFacadeImpl` | `MdocSubQuantityServiceFacade` |
| `ShipmentServiceFacadeImpl` | `ShipmentServiceFacade` |
| `ShipmentLineServiceFacadeImpl` | `ShipmentLineServiceFacade` |
| `StorageLocationServiceFacadeImpl` | `StorageLocationServiceFacade` |
| `WarehouseServiceFacadeImpl` | `WarehouseServiceFacade` |

---

## MQ Consumer 一览 (`consumer/`)

| Consumer | Topic（配置） | 说明 |
|---|---|---|
| `InventoryChangeMqConsumer` | inventory-change | 库存变更（⚠️ 字段缺失问题） |
| `SnMaterialDocInConsumer` | sn-material-doc-in | SN 物料凭证入库 |
| `SnMaterialDocCancelConsumer` | — | SN 物料凭证取消 |
| `InventoryAlertConsumer` | — | 库存预警 |
| `InventoryBffMqConsumer` | — | BFF 消息 |
| `MonitorRuleConsumer` | ⚠️ topic 配置错误 | 监控规则（永久空转） |
| `MonitorRuleInOutBoundConsumer` | — | 监控规则出入库 |
| `MonitorRuleLineConsumer` | — | 监控规则行 |

---

## 定时任务 (`schedule/`)

| Job | 说明 | 分布式锁 |
|---|---|---|
| `PlanGenerateJob` | 计划生成（stub/log-only） | ❌ 无 |
| `PlanDemandSupplyStockGenerateJob` | 需求供应库存生成（stub） | ❌ 无 |
| `PlanRedisOperateJob` | Redis 操作（stub） | ❌ 无 |
| `PlanOrderOverdueCheckJob` | 计划单逾期检查 | ❌ 无 |
| `DemandPlanDetailGenerateJob` | 需求计划明细生成 | ❌ 无 |

> **注意：** 所有定时任务均无分布式锁，多实例部署会重复执行。

---

## 关键约束

- Controller 必须加 `@CatchAndLog` 注解
- `tenantId`/`userId`/`operatorId` 等字段从 `UserContextHolder` 覆盖，不信任请求体
- 定时任务需要加 Redisson 分布式锁

---

*Last updated: 2026-07-02*
