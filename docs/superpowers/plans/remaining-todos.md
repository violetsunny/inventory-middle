# Inventory-Middle 剩余 TODO 实现计划

> **面向 AI 代理：** 使用 superpowers:subagent-driven-development 逐任务实现。步骤用复选框（`- [ ]`）跟踪进度。

## 基础信息

**架构（DDD 分层）：** domain（领域服务，无 Spring 依赖）→ infra（Repository/Mapper/外部集成）→ application（用例编排）→ interfaces（Controller/MQ Consumer）。外部依赖通过 domain 层 interface + infra 层实现隔离。

**技术栈：** Java 8、Spring Boot 2.7.7、MyBatis-Plus 3.5.3、Spring Cloud OpenFeign 3.1.5、KDLA Framework（`top.kdla.framework.*`）、EasyExcel 3.1.5、RocketMQ、Redisson

**环境路径：**
- Java 8: `/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home`
- Maven: `/usr/local/bin/mvn`
- KDLA 源码: `/Users/kangll13/aiot/java-code/aiot-robot/kdla`
- Maven 本地仓库: `/Users/kangll13/aiot/java-code/respository`

**构建验证命令：**
```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home \
  mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
```

**重要约束：**
1. domain 层禁止 Spring 注解
2. 新增 plan 包路径禁止出现 `plan.plan.` 双层嵌套
3. HTTP 外部调用优先 OpenFeign，次选 VertxHttpClient
4. EasyExcel 实现参考 KDLA 封装（`top.kdla.framework.supplement.excel`）
5. 编译后必须 BUILD SUCCESS，无 ERROR
6. 任务完成后，需要进行标注（`✅`）

---

## 完成状态总览

| 系列 | 内容 | 状态 |
|------|------|------|
| A-K | 库存快照/在途库存/物料凭证冲销/备件流转码/码审批/预警推送/Dubbo替换/产品中心/模型修复/监控巡检/逻辑仓查询 | ✅ 全部完成 |
| L1-L6 | KDLA 对齐（BusinessException/application.yml/MdcDot/StopWatch/BaseAssert/SequenceNo） | ✅ 全部完成 |
| P1-P10 | plan 包路径去重（plan/plan/ 嵌套修复）+ 跨模块重名类处理 | ✅ 全部完成 |
| R1-R9 | BFF 迁移残余（CatchAndLog/WebConfig/application-dev.yml/SnapshotQuery/MonitorRule枚举/Excel导入/MaterialDoc/LogicalPlant/StorageLocation/Warehouse） | ✅ 全部完成 |
| SCM O-4 | SCM Plan 迁移代码全量提交（f441f65） | ✅ 完成 |
| **N1-N5** | **EasyExcel 改造 + R6/R10/R11/R12 外部服务接入** | **✅ 全部完成** |

---

## N 系列：待完成任务

> **背景：** EasyExcel 改用 KDLA 封装；R6/R10/R11/R12 接入外部服务（本地 DB 或 OpenFeign stub）。
>
> **KDLA Excel 参考路径：** `/Users/kangll13/aiot/java-code/aiot-robot/kdla/kdla-component-supplement/src/main/java/top/kdla/framework/supplement/excel/`
> - `BaseExcel` — Excel BO 基类（implements Serializable）
> - `imp/KdlaExcelReadListener<T extends BaseExcel, E extends BaseExcel>` — 导入监听器（abstract，含 `checkData(T t): E` 行级校验回调）
> - `exp/KdlaExcelWrite<T extends BaseExcel>` — 导出工具（`writeWeb(response, list, fileName)` 写出到 HTTP 响应）

| 任务 | 说明 | 优先级 | 状态 |
|------|------|--------|------|
| N1 | EasyExcel 改造：`importByExcel` 改用 `KdlaExcelReadListener`，补 `exportTemplate` 端点 | 高 | ✅ 已完成（2026-06-23） |
| N2 | R6-productExternal：`RemoteProductCenterRestService` 扩展 2 个方法，接入 `MaterialDocController` | 中 | ✅ 已完成（2026-06-23） |
| N3 | R10：`InventoryTransitController` 分页结果填充 `uomName`（本地 DB 降级） | 低 | ✅ 已完成（2026-06-23） |
| N4 | R11：新建 `SpDeliveryOrderFeignClient`（6 接口），接入 `DeliveryOrderMgntController` | 低 | ✅ 已完成（2026-06-23） |
| N5 | R12：新建 `CrmDistributorFeignClient`（2 接口），接入 `DistributorController` | 低 | ✅ 已完成（2026-06-23） |

**执行顺序：** N1 → N2 → N3 → N4/N5（可并行）

---

## 任务 N1：EasyExcel 改造（KdlaExcelReadListener + KdlaExcelWrite）

**当前问题：**
- `importByExcel` 用 `EasyExcel.read(...).doReadSync()` 直接读取，未用 KDLA 封装
- `ImportMonitorRuleLineExcelBO` 用 `implements Serializable` 而非继承 `BaseExcel`
- 缺少 `/exportTemplate` 模板下载端点

**涉及文件：**
- `inventory-middle-application/src/main/java/com/inventory/middle/application/bo/monitor/ImportMonitorRuleLineExcelBO.java`
- 新建：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/monitor/MonitorRuleLineExcelReadListener.java`
- `inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/InventoryMonitorRuleLineApplicationServiceImpl.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/InventoryMonitorRuleController.java`

**步骤：**

- [x] **N1-1：`ImportMonitorRuleLineExcelBO` 继承 `BaseExcel`**

```java
import top.kdla.framework.supplement.excel.BaseExcel;

@Data
public class ImportMonitorRuleLineExcelBO extends BaseExcel {
    @ExcelProperty("预警规则ID")
    private Long monitorRuleId;
    @ExcelProperty("预警维度")
    private String monitorDimension;
    @ExcelProperty("预警对象")
    private String monitorObject;
    @ExcelProperty("上限")
    private BigDecimal monitorCeil;
    @ExcelProperty("下限")
    private BigDecimal monitorFloor;
    @ExcelProperty("租户ID")
    private String tenantId;
}
// 去掉 implements Serializable 和 serialVersionUID（BaseExcel 已实现）
```

- [x] **N1-2：新建 `MonitorRuleLineExcelReadListener`**

```java
package com.inventory.middle.application.service.monitor;

import com.inventory.middle.application.bo.monitor.ImportMonitorRuleLineExcelBO;
import top.kdla.framework.supplement.excel.imp.KdlaExcelReadListener;
import java.util.List;
import java.util.function.Predicate;

/**
 * 库存预警规则明细 Excel 导入监听器
 * T = 原始行数据，E = 校验通过后的结果行（此处复用同一类型）
 */
public class MonitorRuleLineExcelReadListener
        extends KdlaExcelReadListener<ImportMonitorRuleLineExcelBO, ImportMonitorRuleLineExcelBO> {

    public MonitorRuleLineExcelReadListener(Predicate<List<ImportMonitorRuleLineExcelBO>> predicate) {
        super(predicate);
    }

    @Override
    public ImportMonitorRuleLineExcelBO checkData(ImportMonitorRuleLineExcelBO row) {
        // 行级校验：预警维度和预警对象必填，否则跳过
        if (row.getMonitorDimension() == null || row.getMonitorObject() == null) {
            return null;
        }
        return row;
    }
}
```

- [x] **N1-3：改造 `importByExcel` 使用 Listener 模式**

```java
// InventoryMonitorRuleLineApplicationServiceImpl.java
@Override
@Transactional(rollbackFor = Exception.class)
public String importByExcel(Long monitorRuleId, String monitorType, MultipartFile file, String tenantId) {
    String taskKey = UUID.randomUUID().toString().replace("-", "");
    log.info("importByExcel start: monitorRuleId={}, monitorType={}, tenantId={}, taskKey={}",
            monitorRuleId, monitorType, tenantId, taskKey);
    try {
        MonitorRuleLineExcelReadListener listener = new MonitorRuleLineExcelReadListener(rows -> {
            List<InventoryMonitorRuleLine> lines = rows.stream().map(row -> {
                InventoryMonitorRuleLine line = new InventoryMonitorRuleLine();
                line.setMonitorRuleId(monitorRuleId != null ? monitorRuleId : row.getMonitorRuleId());
                line.setMonitorDimension(row.getMonitorDimension());
                line.setMonitorObject(row.getMonitorObject());
                line.setMonitorCeil(row.getMonitorCeil() != null ? row.getMonitorCeil() : BigDecimal.ZERO);
                line.setMonitorFloor(row.getMonitorFloor() != null ? row.getMonitorFloor() : BigDecimal.ZERO);
                line.setTenantId(tenantId != null ? tenantId : row.getTenantId());
                return line;
            }).collect(Collectors.toList());
            if (!lines.isEmpty()) {
                inventorymonitorrulelineRepository.batchStore(lines);
                log.info("importByExcel done: count={}, taskKey={}", lines.size(), taskKey);
            }
            return true;
        });
        EasyExcel.read(file.getInputStream(), ImportMonitorRuleLineExcelBO.class, listener).sheet().doRead();
        if (!listener.isSuccess()) {
            throw new RuntimeException("Excel 行数据校验失败");
        }
    } catch (Exception e) {
        log.error("importByExcel failed, taskKey={}", taskKey, e);
        throw new RuntimeException("Excel 导入失败: " + e.getMessage(), e);
    }
    return taskKey;
}
```

- [x] **N1-4：在 `InventoryMonitorRuleController` 补充 `/exportTemplate` 端点**

```java
import top.kdla.framework.supplement.excel.exp.KdlaExcelWrite;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Operation(summary = "下载导入模板")
@GetMapping("/exportTemplate")
public void exportTemplate(HttpServletResponse response) throws IOException {
    List<ImportMonitorRuleLineExcelBO> templateList =
        Collections.singletonList(new ImportMonitorRuleLineExcelBO());
    new KdlaExcelWrite<ImportMonitorRuleLineExcelBO>()
        .writeWeb(response, templateList, "库存预警规则明细导入模板.xlsx");
}
```

- [x] **N1-5：编译验证**
- [x] **N1-6：Commit** `feat(application): refactor Excel import to KdlaExcelReadListener, add exportTemplate`

---

## 任务 N2：R6-productExternal（扩展 ProductExternalService，接入 MaterialDocController）

**当前问题：** `MaterialDocController` 中 `/queryBuildMaterialInfo`、`/queryMaterialInfoByName` 有 TODO stub，未调用任何服务。

**方案：** 扩展 `RemoteProductCenterRestService` 接口，在 `ProductExternalServiceImpl`（本地 SkuBatch DB）实现，接入 Controller。

**涉及文件：**
- `inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/external/RemoteProductCenterRestService.java`
- `inventory-middle-infra/src/main/java/com/inventory/middle/infra/external/ProductExternalServiceImpl.java`
- `inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/web/MaterialDocController.java`

**步骤：**

- [x] **N2-1：`RemoteProductCenterRestService` 新增 2 个方法**

```java
/** 查询组装物料信息（按 skuCode 精确查本地 SkuBatch） */
SingleResponse<Object> queryBuildMaterialInfo(String skuCode, String tenantId);

/** 按名称模糊查询物料信息（分页，当前降级返回全量分页）*/
SingleResponse<Object> fuzzyQueryByName(String skuName, int pageNum, int pageSize, String tenantId);
```

- [x] **N2-2：`ProductExternalServiceImpl` 实现上述方法（SkuBatchMapper 本地查询）**

```java
@Override
public SingleResponse<Object> queryBuildMaterialInfo(String skuCode, String tenantId) {
    SkuBatchQueryPO param = new SkuBatchQueryPO();
    param.setSkuCode(skuCode);
    param.setTenantId(tenantId);
    return SingleResponse.buildSuccess(skuBatchMapper.list(param));
}

@Override
public SingleResponse<Object> fuzzyQueryByName(String skuName, int pageNum, int pageSize, String tenantId) {
    // SkuBatchQueryPO 暂无 skuName 字段，降级返回分页全量数据
    SkuBatchQueryPO param = new SkuBatchQueryPO();
    param.setTenantId(tenantId);
    param.setPageNum((pageNum - 1) * pageSize);
    param.setPageSize(pageSize);
    return SingleResponse.buildSuccess(skuBatchMapper.list(param));
}
```

- [x] **N2-3：`MaterialDocController` 注入服务，替换 2 个 TODO**

```java
@Resource
private RemoteProductCenterRestService productCenterRestService;

// 替换 /queryBuildMaterialInfo
@GetMapping("/queryBuildMaterialInfo")
public SingleResponse<Object> queryBuildMaterialInfo(@RequestParam String skuCode) {
    return productCenterRestService.queryBuildMaterialInfo(skuCode, UserContextHolder.getTenantId());
}

// 替换 /queryMaterialInfoByName
@PostMapping("/queryMaterialInfoByName")
public SingleResponse<Object> queryMaterialInfoByName(@RequestBody MaterialFuzzyQueryReq req) {
    int pageNum = req.getPageNum() != null ? req.getPageNum() : 1;
    int pageSize = req.getPageSize() != null ? req.getPageSize() : 20;
    return productCenterRestService.fuzzyQueryByName(
        req.getSkuName(), pageNum, pageSize, UserContextHolder.getTenantId());
}
```

- [x] **N2-4：编译验证 + Commit** `feat(infra/interfaces): wire ProductExternalService to MaterialDocController (local DB)`

---

## 任务 N3：R10（InventoryTransitController 单位名称填充）

**当前问题：** `queryInTransitStockPage` 有 TODO，`uom` 字段仅有单位编码，未填充单位名称。

**方案：** 扩展 `RemoteProductCenterRestService.getUnitNameByCode`，本地 DB 无专属 unit 表，降级返回编码本身。

**涉及文件：**
- `inventory-middle-domain/.../service/external/RemoteProductCenterRestService.java`
- `inventory-middle-infra/.../external/ProductExternalServiceImpl.java`
- `inventory-middle-interfaces/.../web/InventoryTransitController.java`
- `inventory-middle-client/.../dto/InventoryTransitDto.java`（确认是否有 `uomName` 字段）

**步骤：**

- [x] **N3-1：确认 `InventoryTransitDto` 是否有 `uomName` 字段**

```bash
grep -n "uomName\|uom" inventory-middle-client/src/main/java/com/inventory/middle/client/dto/InventoryTransitDto.java
```

若无，新增 `private String uomName;`。

- [x] **N3-2：`RemoteProductCenterRestService` 新增方法**

```java
/** 根据单位编码查询单位名称（本地 DB 无表时降级返回编码） */
String getUnitNameByCode(String uomCode, String tenantId);
```

- [x] **N3-3：`ProductExternalServiceImpl` 实现（降级）**

```java
@Override
public String getUnitNameByCode(String uomCode, String tenantId) {
    // 本地无专属 uom 表，降级返回编码本身
    return uomCode;
}
```

- [x] **N3-4：`InventoryTransitController` 替换 TODO，填充单位名称**

```java
@Resource
private RemoteProductCenterRestService productCenterRestService;

@PostMapping("/page/query")
public PageResponse<InventoryTransitDto> queryInTransitStockPage(
        @RequestBody InventoryTransitPageQuery pageQuery) {
    pageQuery.setTenantId(UserContextHolder.getTenantId());
    PageResponse<InventoryTransitDto> result = inventoryTransitQueryService.queryPage(pageQuery);
    String tenantId = UserContextHolder.getTenantId();
    if (result != null && result.getData() != null) {
        result.getData().forEach(dto -> {
            if (dto.getUom() != null) {
                dto.setUomName(productCenterRestService.getUnitNameByCode(dto.getUom(), tenantId));
            }
        });
    }
    return result;
}
```

- [x] **N3-5：编译验证 + Commit** `feat(interfaces): fill uomName in InventoryTransit page query`

---

## 任务 N4：R11（SpDeliveryOrderRemoteService OpenFeign Stub）

**当前问题：** `DeliveryOrderMgntController` 6 个方法全是 TODO stub，依赖外部备件发货单服务（原 Dubbo）。

**方案：** domain 层定义接口，infra 层建 `@FeignClient` 实现，URL 通过 `remote.spDelivery.url` 配置（空时降级）。

**涉及文件：**
- 新建：`inventory-middle-domain/.../service/external/SpDeliveryOrderRemoteService.java`
- 新建：`inventory-middle-infra/.../feign/SpDeliveryOrderFeignClient.java`
- 新建：`inventory-middle-infra/.../feign/impl/SpDeliveryOrderRemoteServiceImpl.java`
- `inventory-middle-interfaces/.../web/sparepart/DeliveryOrderMgntController.java`
- `inventory-middle-starter/src/main/resources/application.yml`

**步骤：**

- [x] **N4-1：新建 domain 层接口 `SpDeliveryOrderRemoteService`（6 个方法）**

```java
package com.inventory.middle.domain.service.external;

import top.kdla.framework.dto.SingleResponse;

/** 备件发货单远程服务（domain 层接口，infra OpenFeign 实现） */
public interface SpDeliveryOrderRemoteService {
    SingleResponse<Object> listSales(Object query);
    SingleResponse<Object> listPrint(Object query);
    SingleResponse<Object> doDeliverPrint(Object request);
    SingleResponse<Object> queryPrintInfos(Object request);
    SingleResponse<Object> listDeliveryState(Object query);
    SingleResponse<Object> doConfirmExtraPrint(Object request);
}
```

- [x] **N4-2：新建 `SpDeliveryOrderFeignClient`（infra 层，OpenFeign）**

```java
package com.inventory.middle.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.kdla.framework.dto.SingleResponse;

/**
 * 备件发货单外部服务 Feign Client
 * URL 通过 application.yml 配置 remote.spDelivery.url（空串时 Feign 不初始化）
 */
@FeignClient(name = "spDeliveryOrderClient", url = "${remote.spDelivery.url:}")
public interface SpDeliveryOrderFeignClient {
    @PostMapping("/delivery-order/list-sales")
    SingleResponse<Object> listSales(@RequestBody Object query);

    @PostMapping("/delivery-order/list-print")
    SingleResponse<Object> listPrint(@RequestBody Object query);

    @PostMapping("/delivery-order/do-deliver-print")
    SingleResponse<Object> doDeliverPrint(@RequestBody Object request);

    @PostMapping("/delivery-order/query-print-infos")
    SingleResponse<Object> queryPrintInfos(@RequestBody Object request);

    @PostMapping("/delivery-order/list-delivery-state")
    SingleResponse<Object> listDeliveryState(@RequestBody Object query);

    @PostMapping("/delivery-order/do-confirm-extra-print")
    SingleResponse<Object> doConfirmExtraPrint(@RequestBody Object request);
}
```

> **注意：** url 空串时 `@FeignClient` 无法注入，需要在 `SpDeliveryOrderRemoteServiceImpl` 中用 `@Autowired(required = false)` 注入 Feign Client，URL 未配置时降级返回错误响应。

- [x] **N4-3：新建 `SpDeliveryOrderRemoteServiceImpl`（委托 Feign，URL 空时降级）**

```java
package com.inventory.middle.infra.feign.impl;

import com.inventory.middle.domain.service.external.SpDeliveryOrderRemoteService;
import com.inventory.middle.infra.feign.SpDeliveryOrderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

@Service
@Slf4j
public class SpDeliveryOrderRemoteServiceImpl implements SpDeliveryOrderRemoteService {

    @Autowired(required = false)
    private SpDeliveryOrderFeignClient feignClient;

    private boolean isAvailable() {
        return feignClient != null;
    }

    @Override
    public SingleResponse<Object> listSales(Object query) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.listSales(query);
    }

    @Override
    public SingleResponse<Object> listPrint(Object query) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.listPrint(query);
    }

    @Override
    public SingleResponse<Object> doDeliverPrint(Object request) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.doDeliverPrint(request);
    }

    @Override
    public SingleResponse<Object> queryPrintInfos(Object request) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.queryPrintInfos(request);
    }

    @Override
    public SingleResponse<Object> listDeliveryState(Object query) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.listDeliveryState(query);
    }

    @Override
    public SingleResponse<Object> doConfirmExtraPrint(Object request) {
        if (!isAvailable()) return SingleResponse.buildFailure("NOT_CONFIGURED", "备件发货单服务未配置");
        return feignClient.doConfirmExtraPrint(request);
    }
}
```

- [x] **N4-4：`DeliveryOrderMgntController` 注入 `SpDeliveryOrderRemoteService`，替换 6 个 TODO**

```java
@Resource
private SpDeliveryOrderRemoteService spDeliveryOrderRemoteService;

// 6 个方法分别替换 log.warn + return null 为：
public SingleResponse<Object> listSales(@RequestBody Object query) {
    return spDeliveryOrderRemoteService.listSales(query);
}
// 其余同理...
```

- [x] **N4-5：`application.yml` 新增配置**

```yaml
remote:
  spDelivery:
    url: ${SP_DELIVERY_URL:}
```

- [x] **N4-6：确认 `@EnableFeignClients` 扫描路径覆盖 `infra.feign` 包**

```bash
grep -n "EnableFeignClients\|basePackages" \
  inventory-middle-starter/src/main/java/com/inventory/middle/starter/ProviderApplication.java
```
若 `basePackages` 未包含 `com.inventory.middle.infra.feign`，需添加。

- [x] **N4-7：编译验证 + Commit** `feat(infra/interfaces): add SpDeliveryOrderFeignClient, wire DeliveryOrderMgntController`

---

## 任务 N5：R12（CrmDistributorRemoteService OpenFeign Stub）

**当前问题：** `DistributorController` 2 个方法全是 TODO stub，依赖外部 CRM 经销商服务。

**方案：** 同 N4，domain 层定义接口，infra 层 `@FeignClient` 实现，URL 通过 `remote.crm.url` 配置。

**涉及文件：**
- 新建：`inventory-middle-domain/.../service/external/CrmDistributorRemoteService.java`
- 新建：`inventory-middle-infra/.../feign/CrmDistributorFeignClient.java`
- 新建：`inventory-middle-infra/.../feign/impl/CrmDistributorRemoteServiceImpl.java`
- `inventory-middle-interfaces/.../web/sparepart/DistributorController.java`
- `inventory-middle-starter/src/main/resources/application.yml`

**步骤：**

- [x] **N5-1：新建 domain 层接口 `CrmDistributorRemoteService`（2 个方法）**

```java
package com.inventory.middle.domain.service.external;

import top.kdla.framework.dto.SingleResponse;

/** CRM 经销商远程服务（domain 层接口，infra OpenFeign 实现） */
public interface CrmDistributorRemoteService {
    /** 厂商端模糊查询经销商 */
    SingleResponse<Object> fuzzyQueryByManufacturer(String distributorName, String appKey);
    /** 经销商端模糊查询 */
    SingleResponse<Object> fuzzyQueryByDistributor(String distributorName);
}
```

- [x] **N5-2：新建 `CrmDistributorFeignClient`**

```java
package com.inventory.middle.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.kdla.framework.dto.SingleResponse;

@FeignClient(name = "crmDistributorClient", url = "${remote.crm.url:}")
public interface CrmDistributorFeignClient {
    @GetMapping("/distributor/fuzzy-query")
    SingleResponse<Object> fuzzyQueryByManufacturer(
        @RequestParam String distributorName, @RequestParam String appKey);

    @GetMapping("/distributor/fuzzy-query-by-distributor")
    SingleResponse<Object> fuzzyQueryByDistributor(
        @RequestParam(required = false) String distributorName);
}
```

- [x] **N5-3：新建 `CrmDistributorRemoteServiceImpl`（委托 Feign，URL 空时降级）**

```java
package com.inventory.middle.infra.feign.impl;

import com.inventory.middle.domain.service.external.CrmDistributorRemoteService;
import com.inventory.middle.infra.feign.CrmDistributorFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

@Service
@Slf4j
public class CrmDistributorRemoteServiceImpl implements CrmDistributorRemoteService {

    @Autowired(required = false)
    private CrmDistributorFeignClient feignClient;

    @Override
    public SingleResponse<Object> fuzzyQueryByManufacturer(String distributorName, String appKey) {
        if (feignClient == null) return SingleResponse.buildFailure("NOT_CONFIGURED", "CRM 服务未配置");
        return feignClient.fuzzyQueryByManufacturer(distributorName, appKey);
    }

    @Override
    public SingleResponse<Object> fuzzyQueryByDistributor(String distributorName) {
        if (feignClient == null) return SingleResponse.buildFailure("NOT_CONFIGURED", "CRM 服务未配置");
        return feignClient.fuzzyQueryByDistributor(distributorName);
    }
}
```

- [x] **N5-4：`DistributorController` 注入 `CrmDistributorRemoteService`，替换 2 个 TODO**

```java
@Resource
private CrmDistributorRemoteService crmDistributorRemoteService;

@GetMapping("/fuzzy-query")
public SingleResponse<Object> fuzzyQueryByManufacturer(
        @RequestParam String distributorName, @RequestParam String appKey) {
    return crmDistributorRemoteService.fuzzyQueryByManufacturer(distributorName, appKey);
}

@GetMapping("/fuzzy-query-by-distributor")
public SingleResponse<Object> fuzzyQueryByDistributor(
        @RequestParam(required = false) String distributorName) {
    return crmDistributorRemoteService.fuzzyQueryByDistributor(distributorName);
}
```

- [x] **N5-5：`application.yml` 新增配置**

```yaml
remote:
  crm:
    url: ${CRM_URL:}
```

- [x] **N5-6：编译验证 + Commit** `feat(infra/interfaces): add CrmDistributorFeignClient, wire DistributorController`

---

## 已完成任务参考摘要

> 以下为已完成内容的极简摘要，仅供参考。详细实现见 git 历史。

| 系列 | 关键实现 | Commit 关键词 |
|------|---------|--------------|
| A | InventorySnapshot disableById/adjustDown；InventoryAlert batchStore/batchDeleteByIds | `feat(domain): implement InventorySnapshot write ops` |
| B | InventorySupply batchStore/batchUpdateBySourceOrderNo；InventoryDomainServiceImpl 两个 stub | `feat(domain): implement in-transit stock batch` |
| C | reverseMaterialDoc（查原凭证→反向出入库→markAsReversed）；MaterialDocCoreService 补方法 | `feat(domain): implement reverseMaterialDoc` |
| D | manufacturerInStock/regenerateCode/updateCodeForDeliverOrder/fleeingGoodsApplyCheck/queryCodesForPrint；SequenceNoHelper 接入 | `feat(application): accessories flow code` |
| E | CodeApplyOrder.approval（状态校验→查单→更新）| `feat(application): code apply order approval` |
| F | InventoryAlertNotificationApplicationServiceImpl 接入 RemoteUniformPushService（RestTemplate） | `feat(application): alert notification push` |
| G | 移除 Dubbo，改本地调用；Spring Cloud OpenFeign 替代 | — |
| H | ProductExternalServiceImpl 本地 SkuBatch DB 实现 | — |
| I | PageListLogicalPlantResponseBO.slList 改 StorageLocationBO；isUseInventoryMaterialData 实现 | `fix(domain): fix model types` |
| J | MonitorAnnualInspectionHandleChain 翻页分页修复 | `fix(domain): fix annual inspection paging` |
| K | LogicalPlantCoreService.listByWarehouseNo；Repository + Impl 补充 | `feat(domain): listByWarehouseNo` |
| L1 | BusinessException extends BizException | `refactor(domain): BusinessException extends BizException` |
| L2 | application.yml 补 kdla.exception/log/stopwatch 配置 | — |
| L3 | 7 个 MQ Consumer.onMessage 加 `@MdcDot` | `feat(interfaces): add @MdcDot to consumers` |
| L4 | 3 个核心 ApplicationServiceImpl 关键方法加 `@StopWatchWrapper` | `feat(application): add @StopWatchWrapper` |
| L5 | domain 层 if-throw 替换为 BaseAssert | `refactor(domain): replace null-checks with BaseAssert` |
| L6 | SequenceNoHelperImpl 委托 KDLA SequenceNoGenerator；code_generator_cfg 表 | `feat(infra): delegate SequenceNoHelper` |
| P1-P6 | plan 包路径去重（application/interfaces/client/infra 四层各自修平 plan/plan/ 嵌套） | — |
| P7-P10 | PlanConfigConverter→PlanConfigWebConverter；interfaces 重名 DTO 加 Http 后缀 | — |
| R1-R9 | BFF 迁移：CatchAndLog/WebConfig/application-dev.yml/SnapshotQuery 7方法/MonitorRule枚举/importByExcel/MaterialDoc updateAnnualDate+cityGasImport/LogicalPlant listByIdList/StorageLocation getByDescription/WarehouseController | 见各任务 commit |
| SCM | scm-plan migration v3（f441f65） | `ENH: scm-plan migration v3` |

---

## plan 包路径规范（强制）

```
✅ 正确                                  ❌ 禁止
application/plan/config/service/        application/plan/plan/config/service/
interfaces/web/plan/PlanXxx.java        interfaces/web/plan/plan/PlanXxx.java
client/plan/plan-config/dto/            client/plan/plan/dto/
infra/plan/persistence/dao/             infra/plan/persistence/dao/plan/
```

- interfaces 层 Converter 必须加 `Web`/`VO` 后缀
- 新增 DTO 前先 grep client/plan/ 是否已有同名类
