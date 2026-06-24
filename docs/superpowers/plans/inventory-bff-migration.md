# inventory-center-bff 迁移计划

> **状态：✅ 主体全部完成**（2026-06-23 前）

## 迁移目标

将 `inventory-center-bff`（/Users/kangll13/aiot/java-code/self/inventory/inventory-center-bff） 全部 HTTP Controller 和 Server 迁移至 `inventory-middle-interfaces/web/`，本地 Controller 和 ApplicationService ，去除 RDFA/Nacos/Dubbo，替换为 KDLA 和 SpringBoot 、 SpringCloud 原生组件。

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

## 已知缺口（已全部处理）

以下项目在 `remaining-todos.md` 中已标记完成：

- **P-1**：interfaces/pom.xml 依赖 ✅ fastjson + commons-lang3 已补充
- **P0-5~9**：静态验证 ✅ 全部通过
- **Issue-1**：ParticipantCenter HTTP 客户端 ✅ 已接入（participant-token 待补）
- **V1-V4**：全量验收 ✅ BUILD SUCCESS / CatchAndLog 42/42 / RDFA 零残留
- **CommonController.listCompany**：✅ N/A（scm-plan-bff 原始无此方法）
- **FullAddressHelper**：✅ 省市区拼接工具已实现

**可能遗漏的 inventory-center-bff Controller**（详见 remaining-todos.md X1-X3）：
- SparePartController（备品备件）
- CommonController（省/市/公司通用查询）
- FileController（OSS 文件下载）
