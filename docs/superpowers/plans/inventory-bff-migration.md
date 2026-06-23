# inventory-center-bff 迁移计划

> **状态：✅ 主体全部完成**（2026-06-23 前）

## 迁移目标

将 `inventory-center-bff` 全部 HTTP Controller 迁移至 `inventory-middle-interfaces/web/`，本地 ApplicationService 替代原 Dubbo/RDFA 远程调用。

## 全局替换规则（参考）

| 原 BFF | 替换方案 |
|--------|---------|
| `RdfaResult<T>` / `PagedRdfaResult<T>` | `SingleResponse<T>` / `PageResponse<T>` |
| `RemoteParticipantCenterService.getCurrentTenantId()` | `UserContextHolder.getTenantId()` |
| `RemoteXxxService`（Dubbo） | 本地 `XxxApplicationService` 直注入 |
| `@Authorize`（RDFA 鉴权） | 删除，由 Spring Security 拦截器处理 |
| `@RequestHeader token` | 删除 |
| Controller 裸抛异常 | 类上加 `@CatchAndLog` |

## 完成摘要

| 任务 | 内容 | 状态 |
|------|------|------|
| P0-1~4 | UserContextHolder / UserContextInterceptor / WebConfig | ✅ |
| A | MaterialDocController（18 接口）含 checkParam/updateAnnualDate/cityGasImport/ProductExternal | ✅ |
| B | InventorySnapshotController（6 接口）含 5 个 QueryService 方法 | ✅ |
| C | LogicalPlantController / StorageLocationController / WarehouseController | ✅ |
| D | InventoryMonitorRuleController（14 接口）含枚举/updateWithLines/importExcel/importResult/templateExport | ✅ |
| E | InventoryTransitController（pageList + uomName 回填） | ✅ |
| F | InventoryMapController（/map/queryMap） | ✅ |
| G | InventoryMaterialController（城燃分页） | ✅ |
| H | AccessoriesFlowCodeController / CodeApplyOrderController / DeliveryOrderMgntController / DistributorController | ✅ |
| I | SparePartController / ShipmentQueryController / FileController / CommonController（部分 stub） | ✅ |
| R1 | 全部 Controller 加 @CatchAndLog | ✅ |
| R2/R3 | LogInterceptor 注册 + application-dev.yml | ✅ |
| R4 | InventorySnapshotQueryService 补全 5 个方法 | ✅ |
| R5 | MonitorRule 4 枚举 + updateWithLines + importExcel + importResult + templateExport | ✅ |
| R6 | MaterialDoc checkParam/updateAnnualDate/cityGasImport/ProductExternal 接入 | ✅ |
| R7 | LogicalPlant types/list/listByIdList/detail | ✅ |
| R8 | StorageLocation listByAdjust/detail/types | ✅ |
| R9 | Warehouse list/detail | ✅ |
| R10 | InventoryTransit uomName 回填（getUnitNameByCode 降级） | ✅ |
| R11 | DeliveryOrderMgntController 6 TODO → SpDeliveryOrderFeignClient | ✅ |
| R12 | DistributorController 2 TODO → CrmDistributorFeignClient | ✅ |

## 已知缺口（待后续跟进）

详见 `remaining-todos.md`：

- **P-1**：interfaces/pom.xml 依赖检查（easyexcel/fastjson）
- **P0-5~9**：RemoteParticipantCenterService 替换确认、LogInterceptor 验证、CatchAndLog 验证
- **Issue-1**：participant-center 接通后用 `FieldValueFindHelp` 批量回填 userName/companyName
- **V1-V4**：全量验收（编译 + CatchAndLog 覆盖率 + TODO 统计 + RDFA 残留确认）
- **CommonController.listCompany**：participant-center HTTP 接入（当前返回空 stub）
- **FullAddressHelper**：省市区名称注入（当前返回编码）
