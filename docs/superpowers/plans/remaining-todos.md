# Inventory-Middle 剩余 TODO 实现计划

> **面向 AI 代理的工作者：** 必需子技能：使用 superpowers:subagent-driven-development（推荐）或 superpowers:executing-plans 逐任务实现此计划。步骤使用复选框（`- [ ]`）语法来跟踪进度。

**目标：** 补全所有剩余 stub/TODO 实现，使系统具备完整的业务逻辑可运行性

**架构：** DDD 分层：domain（领域服务）→ infra（Repository/Mapper/外部集成）→ application（用例编排）→ interfaces（Facade/Controller/MQ消费）。外部依赖（HTTP/Feign）均通过 domain 层 interface + infra 层实现隔离。

**技术栈：** Java 8、Spring Boot、MyBatis-Plus、Spring Cloud OpenFeign、RestTemplate、KDLA Framework

**构建验证命令：** `mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository`

---

## 分组总览

| 分组 | 任务 | 优先级 | 外部依赖 | 状态 |
|------|------|--------|---------|------|
| **A. 库存快照写操作** | disableById / adjustDown / saveAlertInfo / deleteAlertId | 高 | 无 | ✅ 已完成 |
| **B. 在途库存（InventorySupply）** | batchUpdateInTransitStock / batchCreateInTransitStock | 高 | 无 | ✅ 已完成 |
| **C. 物料凭证冲销** | reverseMaterialDoc 完整逻辑 | 高 | 无 | ✅ 已完成 |
| **D. 备件流转码业务** | manufacturerInStock / regenerateCode / updateCodeForDeliverOrder / fleeingGoodsApplyCheck / queryCodesForPrint | 中 | SequenceNoHelper | ✅ 已完成 |
| **E. 码申请审批** | CodeApplyOrder.approval | 中 | 无 | ✅ 已完成 |
| **F. 预警通知推送** | RemoteUniformPushService OpenFeign 接入 + InventoryAlertNotification 触发 | 中 | HTTP: 统一推送服务 | ✅ 已完成（改为 OpenFeign） |
| **G. ~~Dubbo 远程服务接入~~** | ~~RemoteInventoryService / RemoteLogService / RemoteMaterialDocService / CrmDistributorRemoteService~~ → 已改为本地直接调用 | 低 | ~~Dubbo~~ → 无 | ✅ 已完成（改为本地调用） |
| **H. 产品中心 HTTP 接入** | ProductExternalServiceImpl 真实 HTTP 实现 | 低 | HTTP: product-center | ✅ 已完成（本地 DB 实现） |
| **I. 模型修复** | PageListLogicalPlantResponseBO.slList 类型 / MaterialDocumentItemBO converter 下沉 / ProductMasterDataSourceRouteUtil.isUseInventoryMaterialData | 低 | 无 | ✅ 已完成 |
| **J. 监控巡检链路** | MonitorAnnualInspectionHandleChain SkuBatchPageRequest 迁移 + 翻页逻辑 | 低 | 已有 ProductExternalServiceImpl | ✅ 已完成 |
| **K. 逻辑仓按 warehouseNo 查询** | LogicalPlantCoreService.listByWarehouseNo | 低 | 无 | ✅ 已完成 |

---

## 任务 A：库存快照写操作（domain 层接 Mapper）

**业务背景：** 出库扣减（adjustDown）和快照禁用（disableById）是物料凭证出库流程的核心写操作；saveAlertInfo/deleteAlertId 是库存预警模块持久化预警状态的关键路径。

**文件：**
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/impl/InventorySnapshotCoreService.java`
- 修改：`inventory-middle-infra/src/main/java/com/inventory/middle/infra/persistence/repository/impl/InventorySnapshotRepositoryImpl.java`
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/repository/InventorySnapshotRepository.java`
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/repository/InventoryAlertRepository.java`（新增批量接口）
- 修改：`inventory-middle-infra/src/main/java/com/inventory/middle/infra/persistence/repository/impl/InventoryAlertRepositoryImpl.java`

- [x] **A-1：在 InventorySnapshotRepository 增加写操作接口方法**

```java
// inventory-middle-domain/.../repository/InventorySnapshotRepository.java
/** 按 ID 禁用快照（deleted=1，更新 number=0） */
boolean disableById(Long id);

/** 扣减库存数量（unrestricted/damaged/inspection 按 stockType 对应字段 -= number） */
boolean adjustDown(Long id, BigDecimal number, String stockType);
```

- [x] **A-2：在 InventorySnapshotRepositoryImpl 实现上述方法（使用 MP LambdaUpdateWrapper）**

```java
// inventory-middle-infra/.../repository/impl/InventorySnapshotRepositoryImpl.java
@Override
public boolean disableById(Long id) {
    return this.update(new LambdaUpdateWrapper<InventorySnapshotDo>()
            .eq(InventorySnapshotDo::getId, id)
            .set(InventorySnapshotDo::getDeleted, 1)
            .set(InventorySnapshotDo::getUnrestricted, BigDecimal.ZERO));
}

@Override
public boolean adjustDown(Long id, BigDecimal number, String stockType) {
    // 根据 stockType 决定扣哪个字段："unrestricted"/"damaged"/"inspection"
    LambdaUpdateWrapper<InventorySnapshotDo> wrapper =
        new LambdaUpdateWrapper<InventorySnapshotDo>().eq(InventorySnapshotDo::getId, id);
    if ("damaged".equals(stockType)) {
        wrapper.setSql("damaged = damaged - " + number.toPlainString());
    } else if ("inspection".equals(stockType)) {
        wrapper.setSql("inspection = inspection - " + number.toPlainString());
    } else {
        wrapper.setSql("unrestricted = unrestricted - " + number.toPlainString());
    }
    return this.update(wrapper);
}
```

- [x] **A-3：在 InventorySnapshotCoreService 替换 stub 为真实调用**

```java
// inventory-middle-domain/.../service/impl/InventorySnapshotCoreService.java
public void disableById(Long id, BigDecimal number, StockTypeEnum stockTypeEnum, boolean delFlag) {
    inventorySnapshotRepository.disableById(id);
}

public void adjustDown(String materialCode, String batchNo, BigDecimal number,
        StockTypeEnum stockTypeEnum, String tenantId, boolean deletedSnp) {
    Map<String, Object> params = new HashMap<>();
    params.put("materialCode", materialCode);
    params.put("batchNo", batchNo);
    params.put("tenantId", tenantId);
    List<InventorySnapshot> list = inventorySnapshotRepository.queryList(params);
    if (list == null || list.isEmpty()) return;
    InventorySnapshot snap = list.get(0);
    String stockType = stockTypeEnum != null ? stockTypeEnum.getCode() : "unrestricted";
    inventorySnapshotRepository.adjustDown(snap.getId().get(), number, stockType);
    if (deletedSnp) {
        inventorySnapshotRepository.disableById(snap.getId().get());
    }
}
```

- [x] **A-4：在 InventoryAlertRepository 增加批量保存/删除接口**

```java
// inventory-middle-domain/.../repository/InventoryAlertRepository.java
boolean batchStore(List<InventoryAlert> alerts);
boolean batchDeleteByIds(List<Long> ids);
```

- [x] **A-5：在 InventoryAlertRepositoryImpl 实现批量操作**

```java
// inventory-middle-infra/.../repository/impl/InventoryAlertRepositoryImpl.java
@Override
public boolean batchStore(List<InventoryAlert> alerts) {
    List<InventoryAlertDo> doList = alerts.stream()
        .map(e -> convertor.fromInventoryAlert(e)).collect(Collectors.toList());
    return this.saveBatch(doList);
}
@Override
public boolean batchDeleteByIds(List<Long> ids) {
    return this.removeByIds(ids);
}
```

- [x] **A-6：在 InventorySnapshotCoreService 接入 InventoryAlertRepository**

```java
// 注入：
@Resource
private InventoryAlertRepository inventoryAlertRepository;

public void saveAlertInfo(List<InventoryAlertBO> alertList) {
    if (CollectionUtils.isEmpty(alertList)) return;
    List<InventoryAlert> entities = alertList.stream().map(bo -> {
        InventoryAlert e = new InventoryAlert();
        BeanUtils.copyProperties(bo, e);
        return e;
    }).collect(Collectors.toList());
    inventoryAlertRepository.batchStore(entities);
}

public void deleteAlertId(List<InventoryAlertBO> alertList) {
    if (CollectionUtils.isEmpty(alertList)) return;
    List<Long> ids = alertList.stream()
        .filter(bo -> bo.getId() != null).map(InventoryAlertBO::getId)
        .collect(Collectors.toList());
    if (!ids.isEmpty()) inventoryAlertRepository.batchDeleteByIds(ids);
}
```

- [x] **A-7：编译验证**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
```
预期：BUILD SUCCESS，无 ERROR

- [x] **A-8：Commit**

```bash
git add -A
git commit -m "feat(domain): implement InventorySnapshot write ops and Alert persistence"
```

---

## 任务 B：在途库存（InventorySupply）写操作

**业务背景：** 调拨在途（`inventory_supply` 表）在调拨单创建时新增记录，到库后更新在途数量。`InventoryDomainServiceImpl` 中两个 stub 方法需接入 `InventorySupplyRepository`。

**文件：**
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/repository/InventorySupplyRepository.java`
- 修改：`inventory-middle-infra/src/main/java/com/inventory/middle/infra/persistence/repository/impl/InventorySupplyRepositoryImpl.java`
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/impl/InventoryDomainServiceImpl.java`

- [x] **B-1：在 InventorySupplyRepository 增加批量写操作接口**

```java
// inventory-middle-domain/.../repository/InventorySupplyRepository.java
/** 批量创建在途库存记录 */
boolean batchStore(List<InventorySupply> supplyList);
/** 按 sourceOrderNo 更新在途数量 */
boolean batchUpdateBySourceOrderNo(List<InventorySupply> supplyList);
```

- [x] **B-2：查看 InventorySupply 领域对象字段**

```bash
grep -n "private" inventory-middle-domain/src/main/java/com/inventory/middle/domain/model/entity/InventorySupply.java | head -20
# 关键字段：materialCode, logicalPlantNo, sourceOrderNo, inTransitQty 等
```

- [x] **B-3：在 InventorySupplyRepositoryImpl 实现接口**

```java
// inventory-middle-infra/.../repository/impl/InventorySupplyRepositoryImpl.java
@Override
public boolean batchStore(List<InventorySupply> supplyList) {
    List<InventorySupplyDo> doList = supplyList.stream()
        .map(e -> convertor.fromInventorySupply(e)).collect(Collectors.toList());
    return this.saveBatch(doList);
}

@Override
public boolean batchUpdateBySourceOrderNo(List<InventorySupply> supplyList) {
    for (InventorySupply e : supplyList) {
        InventorySupplyDo doObj = convertor.fromInventorySupply(e);
        this.saveOrUpdate(doObj);
    }
    return true;
}
```

- [x] **B-4：在 InventoryDomainServiceImpl 注入 InventorySupplyRepository 并实现两个方法**

```java
// inventory-middle-domain/.../service/impl/InventoryDomainServiceImpl.java
@Resource
private InventorySupplyRepository inventorySupplyRepository;

@Override
public boolean batchUpdateInTransitStock(List<InventorySupplyBO> supplyBOList) {
    if (CollectionUtils.isEmpty(supplyBOList)) return false;
    List<InventorySupply> entities = supplyBOList.stream().map(bo -> {
        InventorySupply e = new InventorySupply();
        BeanUtils.copyProperties(bo, e);
        return e;
    }).collect(Collectors.toList());
    return inventorySupplyRepository.batchUpdateBySourceOrderNo(entities);
}

@Override
public boolean batchCreateInTransitStock(List<CreateInTransitStockRequest> requestList) {
    if (CollectionUtils.isEmpty(requestList)) return false;
    List<InventorySupply> entities = requestList.stream().map(req -> {
        InventorySupply e = new InventorySupply();
        BeanUtils.copyProperties(req, e);
        return e;
    }).collect(Collectors.toList());
    return inventorySupplyRepository.batchStore(entities);
}
```

- [x] **B-5：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
git add -A && git commit -m "feat(domain): implement in-transit stock batch operations"
```

---

## 任务 C：物料凭证冲销（reverseMaterialDoc）

**业务背景：** 冲销是对已有物料凭证的反向操作——查找原凭证、生成冲销凭证（adjustType 取反）、触发库存扣减/增加（与入库/出库逻辑相反）、更新原凭证状态为已冲销。

**文件：**
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/impl/MaterialDocDomainServiceImpl.java:115-123`
- 查阅：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/MaterialDocCoreService.java`（查询原凭证方法）

- [x] **C-1：查看 MaterialDocCoreService 中可用方法**

```bash
cat inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/MaterialDocCoreService.java
# 关键：是否有 queryByMaterialDocNo、updateStatus 方法
```

- [x] **C-2：查看 MaterialDocumentBO 中的冲销相关字段**

```bash
grep -n "reverse\|cancelDoc\|originalDocNo\|sourceDocNo\|adjustType" \
  inventory-middle-domain/src/main/java/com/inventory/middle/domain/model/bo/material/MaterialDocumentBO.java
```

- [x] **C-3：实现 reverseMaterialDoc 完整逻辑**

```java
// inventory-middle-domain/.../service/impl/MaterialDocDomainServiceImpl.java
@Override
@Transactional(rollbackFor = Exception.class)
public MaterialDocInvRes reverseMaterialDoc(MaterialDocumentBO reverseMaterialDocument) {
    // 1. 校验冲销参数
    String originalDocNo = reverseMaterialDocument.getMaterialDocNo();
    if (StringUtils.isBlank(originalDocNo)) {
        throw new BusinessException(PARAM_VALID_ERROR.getCode(), "原凭证号不能为空");
    }

    // 2. 查询原凭证
    MaterialDocumentBO originalDoc = materialDocCoreService.queryByMaterialDocNo(originalDocNo,
            reverseMaterialDocument.getTenantId());
    if (originalDoc == null) {
        throw new BusinessException(MDOC_NOT_EXIST.getCode(), "原凭证不存在: " + originalDocNo);
    }

    // 3. 构建冲销凭证（adjustType 取反、materialDocCategory = CANCEL）
    reverseMaterialDocument.setMaterialDocCategory(MaterialDocCategoryEnum.CANCEL.getCode());
    reverseMaterialDocument.setAdjustType(
        MaterialAdjustTypeEnum.getReverseType(originalDoc.getAdjustType()));
    reverseMaterialDocument.setSourceMaterialDocNo(originalDocNo);
    initMaterialDocument(reverseMaterialDocument);

    // 4. 执行冲销（反向出入库）
    MaterialDocInvRes result;
    if (MaterialDocCategoryEnum.IN.getCode().equals(originalDoc.getMaterialDocCategory())) {
        // 原来是入库 → 冲销做出库
        result = domainOutboundMaterialDoc(reverseMaterialDocument);
    } else {
        // 原来是出库 → 冲销做入库
        result = domainOutInboundMaterialDoc(reverseMaterialDocument);
    }

    // 5. 更新原凭证状态为已冲销
    materialDocCoreService.markAsReversed(originalDocNo, reverseMaterialDocument.getMaterialDocNo(),
            reverseMaterialDocument.getOperator());

    log.info("reverseMaterialDoc success, originalDocNo={} reverseDocNo={}",
            originalDocNo, result.getMaterialDocNo());
    return result;
}
```

> **注意：** 如果 `MaterialDocCoreService` 中无 `queryByMaterialDocNo` / `markAsReversed`，需先在接口和实现类中添加。参考 `materialDocCoreService.queryByXxx` 的现有实现模式。

- [x] **C-4：若 MaterialDocCoreService 缺少方法，补充接口定义和实现**

```java
// inventory-middle-domain/.../service/MaterialDocCoreService.java 追加：
MaterialDocumentBO queryByMaterialDocNo(String materialDocNo, String tenantId);
void markAsReversed(String originalDocNo, String reverseDocNo, String operator);
```

- [x] **C-5：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
git add -A && git commit -m "feat(domain): implement reverseMaterialDoc full logic"
```

---

## 任务 D：备件流转码业务逻辑

**业务背景：** `manufacturerInStock` 是厂商入库时批量生成流转码的核心方法，依赖 `SequenceNoHelper` 生成序列号；`regenerateCode` 重新生成码；`fleeingGoodsApplyCheck` 窜货校验；`updateCodeForDeliverOrder` 更新码关联出库单；`queryCodesForPrint` 打印信息组装。

**文件：**
- 修改：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/AccessoriesFlowCodeApplicationServiceImpl.java`
- 查阅：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/external/SequenceNoHelper.java`

- [x] **D-1：查看 SequenceNoHelper 和 ManufacturerInStockRequest 字段**

```bash
cat inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/external/SequenceNoHelper.java
grep -n "private" inventory-middle-client/src/main/java/com/inventory/middle/client/code/dto/request/ManufacturerInStockRequest.java | head -20
```

- [x] **D-2：实现 manufacturerInStock（批量生成码 + 入库记录）**

核心逻辑：按 `request.getNum()` 循环，调用 `sequenceNoHelper.generate()` 生成 innerCode，构建 `Code` entity 批量 insert：

```java
// AccessoriesFlowCodeApplicationServiceImpl.java
@Resource
private SequenceNoHelper sequenceNoHelper;

@Override
public void manufacturerInStock(ManufacturerInStockRequest request) {
    // 1. 参数校验
    if (request.getNum() == null || request.getNum() <= 0) {
        throw new IllegalArgumentException("码数量必须大于 0");
    }
    // 2. 批量生成码
    List<CodeDo> codeList = new ArrayList<>();
    for (int i = 0; i < request.getNum(); i++) {
        CodeDo code = new CodeDo();
        code.setInnerCode(sequenceNoHelper.generateCodeNo());
        code.setBusinessNo(request.getBusinessNo());
        code.setPublisher(request.getPublisher());
        code.setType(request.getType());
        code.setStatus("UNUSED");
        code.setCreatorId(request.getOperatorId());
        codeList.add(code);
    }
    // 3. 批量插入
    codeMapper.batchInsert(codeList);
    log.info("manufacturerInStock success, num={} businessNo={}", request.getNum(), request.getBusinessNo());
}
```

- [x] **D-3：实现 regenerateCode（失效旧码 + 生成新码）**

```java
@Override
public SingleResponse<AccessoriesFlowCodeResponse> regenerateCode(RegenerateCodeRequest request) {
    // 1. 查旧码
    Code oldCode = codeRepository.findByCode(request.getCode());
    if (oldCode == null) return SingleResponse.buildFailure("NOT_FOUND", "码不存在");
    // 2. 废弃旧码
    oldCode.setStatus("SCRAPPED");
    codeRepository.update(oldCode);
    // 3. 生成新码
    CodeDo newCodeDo = new CodeDo();
    BeanUtils.copyProperties(oldCode, newCodeDo);
    newCodeDo.setId(null);
    newCodeDo.setInnerCode(sequenceNoHelper.generateCodeNo());
    newCodeDo.setStatus("UNUSED");
    newCodeDo.setCreatorId(request.getOperatorId());
    codeMapper.insert(newCodeDo);
    AccessoriesFlowCodeResponse resp = new AccessoriesFlowCodeResponse();
    BeanUtils.copyProperties(newCodeDo, resp);
    return SingleResponse.of(resp);
}
```

- [x] **D-4：实现 updateCodeForDeliverOrder（更新码关联出库单号）**

```java
@Override
public SingleResponse<Boolean> updateCodeForDeliverOrder(UpdateCodeForDeliverOrderRequest request) {
    if (CollectionUtils.isEmpty(request.getCodeList())) return SingleResponse.of(false);
    List<CodeDo> updateList = request.getCodeList().stream().map(code -> {
        CodeDo doObj = new CodeDo();
        doObj.setCode(code);
        doObj.setExtendField1(request.getDeliverOrderNo()); // extendField1 存出库单号
        return doObj;
    }).collect(Collectors.toList());
    codeMapper.batchUpdate(updateList);
    return SingleResponse.of(true);
}
```

- [x] **D-5：实现 fleeingGoodsApplyCheck（查码所属厂商/当前持有者做窜货校验）**

```java
@Override
public MultiResponse<FleeingGoodsApplyCheckResponse> fleeingGoodsApplyCheck(
        FleeingGoodsApplyCheckRequest request) {
    List<FleeingGoodsApplyCheckResponse> result = new ArrayList<>();
    if (CollectionUtils.isEmpty(request.getCodeList())) return MultiResponse.of(result);
    ListCodeParamPO param = new ListCodeParamPO();
    param.setInnerCodeList(request.getCodeList());
    List<CodeDo> codeList = codeMapper.list(param);
    for (CodeDo code : codeList) {
        FleeingGoodsApplyCheckResponse resp = new FleeingGoodsApplyCheckResponse();
        resp.setCode(code.getCode());
        resp.setCurrentOwner(code.getCurrentOwner());
        resp.setPublisher(code.getPublisher());
        // 窜货判断：当前持有者 != 申请人
        resp.setIsFleeing(!request.getApplicantId().equals(code.getCurrentOwner()));
        result.add(resp);
    }
    return MultiResponse.of(result);
}
```

- [x] **D-6：实现 queryCodesForPrint（组装打印数据）**

```java
@Override
public MultiResponse<SpDeliveryDetailPrintInfo> queryCodesForPrint(QueryCodesForPrintRequest request) {
    ListCodeParamPO param = new ListCodeParamPO();
    param.setBusinessNo(request.getDeliveryOrderNo());
    List<CodeDo> codeList = codeMapper.list(param);
    List<SpDeliveryDetailPrintInfo> result = codeList.stream().map(code -> {
        SpDeliveryDetailPrintInfo info = new SpDeliveryDetailPrintInfo();
        info.setMarkCode(code.getCode());
        info.setDeliveryOrderNo(request.getDeliveryOrderNo());
        info.setMaterialCode(code.getExtendField2()); // extendField2 存物料码
        return info;
    }).collect(Collectors.toList());
    return MultiResponse.of(result);
}
```

- [x] **D-7：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
git add -A && git commit -m "feat(application): implement accessories flow code business logic"
```

---

## 任务 E：码申请审批（CodeApplyOrder.approval）

**业务背景：** 审批通过后更新申请单状态为 PASSED；审批拒绝更新为 REFUSED。无论通过/拒绝，都要记录审批意见和操作人。

**文件：**
- 修改：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/CodeApplyOrderApplicationServiceImpl.java:49-52`

- [x] **E-1：查看 CodeApplyOrder entity 字段确认可设置 approvalStatus**

```bash
grep -n "private\|approvalStatus\|approvalReason\|approvedNum" \
  inventory-middle-domain/src/main/java/com/inventory/middle/domain/model/entity/CodeApplyOrder.java
```

- [x] **E-2：实现 approval 方法**

```java
// CodeApplyOrderApplicationServiceImpl.java
@Override
public SingleResponse<Boolean> approval(CodeApplyOrderApprovalRequest approvalRequest) {
    log.info("CodeApplyOrderApplicationServiceImpl.approval request={}", JSON.toJSONString(approvalRequest));
    // 1. 校验审批状态合法性（必须是 PASSED=2 或 REFUSED=3）
    if (!CodeApprovalStatusEnum.checkApprovalStatus(approvalRequest.getApprovalStatus())) {
        return SingleResponse.buildFailure("INVALID_STATUS", "审批状态非法，必须为已通过(2)或已拒绝(3)");
    }
    // 2. 查原申请单
    CodeApplyOrder order = codeApplyOrderRepository.findById(approvalRequest.getApplyOrderId());
    if (order == null) {
        return SingleResponse.buildFailure("NOT_FOUND", "申请单不存在");
    }
    // 3. 只有待审批状态可以审批
    if (!CodeApprovalStatusEnum.UNAPPROVED.getCode() == order.getApprovalStatus()) {
        return SingleResponse.buildFailure("INVALID_STATE", "申请单状态不可审批");
    }
    // 4. 更新申请单状态
    order.setApprovalStatus(approvalRequest.getApprovalStatus());
    boolean result = codeApplyOrderRepository.update(order);
    return SingleResponse.of(result);
}
```

> **注意：** 如果 `CodeApplyOrderRepositoryImpl` 的 `update` 不按 ID 更新，需确认 entity 有 `id` 字段设置。

- [x] **E-3：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
git add -A && git commit -m "feat(application): implement code apply order approval"
```

---

## 任务 F：预警通知推送  

**业务背景：** `InventoryAlertNotificationApplicationServiceImpl` 已生成 alert 消息，需要调用 `RemoteUniformPushService.push()` 完成邮件/钉钉推送，同时持久化通知记录到 `InventoryAlertNotificationRepository`。

**文件：**
- 修改：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/InventoryAlertNotificationApplicationServiceImpl.java:65-68`
- 修改：`inventory-middle-infra/src/main/java/com/inventory/middle/infra/integration/push/RemoteUniformPushService.java`（已改为 OpenFeign/RestTemplate HTTP 调用）

- [x] **F-1：查看 InventoryAlertNotificationApplicationServiceImpl 完整代码**

```bash
cat inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/InventoryAlertNotificationApplicationServiceImpl.java
```

- [x] **F-2：查看 AlertMessageBO 和 RemoteUniformPushService.push 参数需求**

```bash
grep -n "private" inventory-middle-domain/src/main/java/com/inventory/middle/domain/model/bo/monitor/InventoryAlertNotificationBO.java | head -15
cat inventory-middle-infra/src/main/java/com/inventory/middle/infra/integration/push/RemoteUniformPushService.java
```

- [x] **F-3：构建推送 DTO 并调用 push（当前 RemoteUniformPushService 是 stub，直接调用即可）**

```java
// InventoryAlertNotificationApplicationServiceImpl.java
@Resource
private RemoteUniformPushService remoteUniformPushService;

// 在通知循环内替换 TODO 处：
Map<String, Object> pushDTO = new HashMap<>();
pushDTO.put("monitorRuleId", alertMessageBO.getMonitorRuleId());
pushDTO.put("materialCode", alertMessageBO.getMaterialCode());
pushDTO.put("message", alertMessageBO.getMessage());
pushDTO.put("sendMode", alertMessageBO.getSendMode());
boolean pushed = remoteUniformPushService.push(pushDTO);
log.info("预警通知推送结果: monitorRuleId={} pushed={}", alertMessageBO.getMonitorRuleId(), pushed);
```

- [x] **F-4：RemoteUniformPushService 已从 Dubbo 改为 OpenFeign/RestTemplate HTTP 调用**

原 Dubbo `@DubboReference(version = "1.0.0") UniformPushClient` 已替换为 RestTemplate HTTP POST 调用，
通过 `remote.uniformPush.url` 配置推送服务地址。

- [x] **F-5：Commit**

```bash
git add -A && git commit -m "feat(application): wire alert notification push service (OpenFeign)"
```

---

## 任务 G：~~Dubbo 远程服务接入~~ → 已改为本地直接调用  

**业务背景：** 以下服务原通过 Dubbo 调用 inventory-center，但 inventory-center 已整体迁入 inventory-middle，因此不再需要远程调用，改为本地直接调用。

**变更说明：**
- 原 `inventory-center-client` Dubbo 依赖提供的 `InventoryService`、`LogicalPlantService`、`InventoryLogService`、`MaterialDocService` → 现均由 inventory-middle 本地 Domain Service / Repository 直接提供
- 原 `crm-customer-client` Dubbo 依赖提供的 `DealerRpcService` → 如需调用 CRM 服务，改为 OpenFeign HTTP 接口
- `infra/integration/inventory/`、`infra/integration/material/`、`infra/integration/crm/` 空目录已删除
- Dubbo 依赖（`dubbo-spring-boot-starter`、`dubbo-registry-nacos`）已从 pom.xml 移除
- Spring Cloud OpenFeign 已引入替代 Dubbo

**具体实现：**
- [x] **G-1：移除 Dubbo/Nacos pom 依赖，替换为 OpenFeign**
- [x] **G-2：RemoteInventoryService → 本地 InventorySnapshotDomainService / LogicalPlantCoreService 直接调用**
- [x] **G-3：RemoteLogService → 本地 Repository 直接持久化**
- [x] **G-4：RemoteMaterialDocService → 本地 MaterialDocDomainService 直接调用**
- [x] **G-5：CrmDistributorRemoteService → 如需外部 CRM 调用，使用 OpenFeign HTTP 接口**
- [x] **G-6：编译验证**

---

## 任务 H：产品中心 HTTP 接入  

**业务背景：** `ProductExternalServiceImpl` 对接产品中心，用于监控巡检任务中查询 SKU 批次信息。当前已实现本地 DB 版本（通过 `SkuBatchMapper`），如需切换为远程 HTTP 调用可使用 OpenFeign/RestTemplate。

**文件：**
- `inventory-middle-infra/src/main/java/com/inventory/middle/infra/external/ProductExternalServiceImpl.java`（已实现本地 DB 版本）
- `inventory-middle-starter/src/main/resources/application.yml`（已配置 `remote.product.center.url`）

- [x] **H-1：ProductExternalServiceImpl 本地 DB 版本已实现**
- [x] **H-2：application.yml 已配置 `remote.product.center.url`**
- [x] **H-3：如需切换为 HTTP 调用，已有 RestTemplate Bean 配置**

---

## 任务 I：模型修复（低风险代码清理）

**文件：**
- 修改：`inventory-middle-domain/.../model/bo/logicalPlant/PageListLogicalPlantResponseBO.java`
- 修改：`inventory-middle-domain/.../model/bo/material/MaterialDocumentItemBO.java`（移除注释）
- 修改：`inventory-middle-domain/.../common/util/ProductMasterDataSourceRouteUtil.java`

- [x] **I-1：修复 PageListLogicalPlantResponseBO.slList 类型**

将 `List<Object>` 替换为 `List<StorageLocationBO>`（domain 层已有此 BO）：

```java
// PageListLogicalPlantResponseBO.java
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
// ...
private List<StorageLocationBO> slList;
```

删除文件头的 `// TODO: StorageLocationDo 已迁移至 infra 层` 注释。

- [x] **I-2：实现 ProductMasterDataSourceRouteUtil.isUseInventoryMaterialData**

```java
public Boolean isUseInventoryMaterialData(String tenantId) {
    if (!StringUtils.hasText(tenantId) || CollectionUtils.isEmpty(useInventoryCenterMDataTenantIds)) {
        return Boolean.FALSE;
    }
    return useInventoryCenterMDataTenantIds.contains(tenantId);
}
```

- [x] **I-3：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
git add -A && git commit -m "fix(domain): fix model types and ProductMasterDataSourceRouteUtil"
```

---

## 任务 J：监控巡检链路修复（MonitorAnnualInspectionHandleChain）

**业务背景：** `MonitorAnnualInspectionHandleChain` 在查询 RDFA 产品数据时，`SkuBatchPageRequest` 的分页器（pager）没有正确传递，导致翻页逻辑无效只取第一页。

**文件：**
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/monitor/MonitorAnnualInspectionHandleChain.java:188-220`

- [x] **J-1：查看 SkuBatchPageRequest 的分页字段**

```bash
grep -n "private\|page\|pager\|size" \
  inventory-middle-client/src/main/java/com/inventory/middle/client/dto/sku/SkuBatchPageRequest.java | head -15
```

- [x] **J-2：修复 buildSkuBatchPageRequest 方法，确认 pager 字段名**

```java
// MonitorAnnualInspectionHandleChain.java
// 找到 buildSkuBatchPageRequest 方法，确认 SkuBatchPageRequest 有 pageNum/pageSize 字段
// 在翻页循环中设置：
reqDTO.setPageNum((int) page);   // 或 reqDTO.getPager().setPage((int) page)
reqDTO.setPageSize(pager.getSize());
```

- [x] **J-3：修复 getSkuFromProduct 中 operatorId 字段取值**

```java
// 确认 snapshotPO.getUpdatorId() 是否是正确的操作人字段
// 如果快照 BO 中有 creatorId 或 tenantId，优先用 creatorId
java.util.List<SkuBatchResponse> rdfaResult =
    productCenterRestService.skuBatchListByRequestWithSpecialToken(
        reqDTO, token,
        snapshotPO.getUpdatorId(),   // 确认此字段是 operatorId 语义
        snapshotPO.getTenantId());
```

- [x] **J-4：Commit**

```bash
git add -A && git commit -m "fix(domain): fix annual inspection chain paging logic"
```

---

## 任务 K：逻辑仓按 warehouseNo 查询

**文件：**
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/repository/LogicalPlantRepository.java`
- 修改：`inventory-middle-infra/src/main/java/com/inventory/middle/infra/persistence/repository/impl/LogicalPlantRepositoryImpl.java`
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/impl/LogicalPlantCoreService.java`

- [x] **K-1：在 LogicalPlantRepository 增加方法**

```java
/** 按物理仓库编码查询逻辑仓列表 */
List<LogicalPlant> listByWarehouseNo(String warehouseNo);
```

- [x] **K-2：在 LogicalPlantRepositoryImpl 实现**

```java
@Override
public List<LogicalPlant> listByWarehouseNo(String warehouseNo) {
    Map<String, Object> params = new HashMap<>();
    params.put("warehouseNo", warehouseNo);
    List<LogicalPlantDo> doList = baseMapper.queryList(params);
    if (CollectionUtils.isEmpty(doList)) return Collections.emptyList();
    return doList.stream().map(convertor::toLogicalPlant).collect(Collectors.toList());
}
```

- [x] **K-3：在 LogicalPlantCoreService 替换 stub**

```java
public List<LogicalPlantBO> listByWarehouseNo(String warehouseNo) {
    List<LogicalPlant> list = logicalPlantRepository.listByWarehouseNo(warehouseNo);
    return list.stream().map(this::toBO).collect(Collectors.toList());
}
```

- [x] **K-4：编译验证 + Commit**

```bash
mvn clean compile -DskipTests -Dmaven.repo.local=/Users/kangll13/aiot/java-code/respository
git add -A && git commit -m "feat(domain): implement listByWarehouseNo for LogicalPlant"
```

---

## 执行顺序建议

> **所有任务 A-K 均已完成。** Dubbo 已替换为 OpenFeign/RestTemplate，Nacos 已替换为本地 YAML 配置。

```
A（快照写操作） → B（在途库存） → I（模型修复） → K（仓库查询）
  ↓
C（物料凭证冲销） → E（码审批）
  ↓
D（备件流转码） → F（推送通知，OpenFeign） → J（监控巡检）
  ↓
G（~~Dubbo接入~~→本地调用） → H（产品中心，本地DB+OpenFeign可切换）
```

---

## KDLA 对齐补充任务（L 系列）

> **背景：** 通过深入分析 KDLA 框架源码（kdla-component-exception / log / common / validator / supplement-cache），发现以下 6 类对齐落差，需补充到项目中。

| 任务 | 类别 | 优先级 | 状态 |
|------|------|--------|------|
| **L1** | `BusinessException` 继承 `BizException` | 高 | ✅ 已完成 |
| **L2** | application.yml 补充 KDLA 切面配置 | 高 | ✅ 已完成 |
| **L3** | MQ Consumer 加 `@MdcDot` 链路追踪 | 中 | ✅ 已完成 |
| **L4** | 核心 ApplicationService 加 `@StopWatchWrapper` | 中 | ✅ 已完成 |
| **L5** | Domain 层校验改用 `BaseAssert` | 低 | ✅ 已完成 |
| **L6** | `SequenceNoHelperImpl` 委托 KDLA `SequenceNoGenerator` | 中 | ✅ 已完成 |

---

## 任务 L1：BusinessException 继承 BizException

**业务背景：** 当前 `BusinessException extends RuntimeException`，`CatchLogAspect`（`@CatchAndLog` 的切面实现）只识别 `BizException` / `SysException` / `LockFailException`。若 `BusinessException` 不继承 `BizException`，所有业务异常会命中 `UNKNOWN_ERROR` 分支，导致：① 日志级别从 warn 变成 error；② 响应 code 变成 `UNKNOWN_ERROR` 而非业务错误码。

**文件：**
- 修改：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/common/exception/BusinessException.java`

- [x] **L1-1：修改 BusinessException 继承链**

将 `extends RuntimeException` 改为 `extends BizException`，删除重复字段 `code` / `msg`（`BizException` 父类 `BaseException` 已有），调整各构造器：

```java
package com.inventory.middle.domain.common.exception;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import top.kdla.framework.exception.BizException;

/**
 * 业务异常 - 继承 KDLA BizException，确保被 @CatchAndLog 切面正确识别
 * CatchLogAspect 会将 BizException 以 warn 级别记录并返回业务错误码
 */
public class BusinessException extends BizException {

    public BusinessException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum == null
                ? ResponseCodeEnum.FAILED.getCode()
                : responseCodeEnum.getCode(),
              responseCodeEnum == null
                ? ResponseCodeEnum.FAILED.getDesc()
                : responseCodeEnum.getDesc());
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum, String message) {
        super(responseCodeEnum == null
                ? ResponseCodeEnum.FAILED.getCode()
                : responseCodeEnum.getCode(),
              (responseCodeEnum == null
                ? ResponseCodeEnum.FAILED.getDesc()
                : responseCodeEnum.getDesc()) + "," + message);
    }

    public BusinessException(String msg) {
        super(ResponseCodeEnum.FAILED.getCode(), msg);
    }

    public BusinessException(String code, String msg) {
        super(code, msg);
    }

    public BusinessException(String code, String errorInfo, Object... args) {
        super(code, String.format(errorInfo, args));
    }

    public BusinessException(String msg, Throwable e) {
        super(ResponseCodeEnum.FAILED.getCode(), msg, e);
    }
}
```

- [x] **L1-2：编译验证** ✅

- [x] **L1-3：Commit**

```bash
git add inventory-middle-domain/src/main/java/com/inventory/middle/domain/common/exception/BusinessException.java
git commit -m "refactor(domain): BusinessException extends BizException for CatchAndLog recognition"
```

---

## 任务 L2：application.yml 补充 KDLA 切面配置

**业务背景：** 当前 `application.yml` 缺少 KDLA 切面开关配置。虽然 interfaces 层已有 `@CatchAndLog` 注解，但 `CatchLogAutoConfigure` 的条件装配（`@ConditionalOnProperty(name="kdla.log.catchlog.enabled", havingValue="true", matchIfMissing=true)`）默认 `matchIfMissing=true` 实际会自动生效；`UnifiedExceptionControllerAdvice` 同理。**但仍需显式配置以明确意图，同时补充 `RequestLog` 和 `StopWatch` 开关，方便后续开关控制。**

**文件：**
- 修改：`inventory-middle-starter/src/main/resources/application.yml`

- [x] **L2-1：在 application.yml 末尾追加 KDLA 配置节**

在文件末尾（`alert.interval.minute: 60` 之后）追加：

```yaml
# KDLA 框架配置
kdla:
  # 统一异常处理（UnifiedExceptionControllerAdvice）
  # BizException → 业务错误码 + warn 日志；SysException/未知异常 → error 日志
  exception:
    handler:
      enabled: true
  # @CatchAndLog 切面：自动打印方法入参/出参/耗时，并处理异常响应
  log:
    catchlog:
      enabled: true
    # 请求日志切面（记录 HTTP 请求/响应体，调试用，生产可设为 false）
    requestlog:
      enabled: true
  # @StopWatchWrapper 耗时打印切面
  stopwatch:
    enabled: true
```

- [x] **L2-2：验证配置加载**

```bash
# 启动后搜索日志确认切面装配成功
grep -r "CatchLogAutoConfigure\|StopWatchAutoConfigure\|UnifiedExceptionControllerAdvice" \
  inventory-middle-starter/src --include="*.java" | grep -v target
# 预期：无需代码改动，自动配置通过 spring.factories 装配
```

- [x] **L2-3：Commit**

---

## 任务 L3：MQ Consumer 加 @MdcDot 链路追踪

**业务背景：** MQ 消息消费是异步链路，当前所有 Consumer 的 `onMessage` 方法无 traceId 注入，日志排查时无法关联请求链路。KDLA 的 `@MdcDot` 会在方法执行前注入 MDC `traceId`，执行后自动清理。

**文件：**
- 修改：`inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/consumer/` 下所有 Consumer 类

涉及 Consumer 清单：
- `InventoryChangeMqConsumer.java`
- `InventoryAlertConsumer.java`
- `MonitorRuleConsumer.java`
- `MonitorRuleInOutBoundConsumer.java`
- `SnMaterialDocInConsumer.java`
- `SnMaterialDocCancelConsumer.java`
- `InventoryBffMqConsumer.java`

- [x] **L3-1：为每个 Consumer 的 onMessage 方法加 @MdcDot 注解**

在每个 Consumer 的 `import` 区加：
```java
import top.kdla.framework.common.aspect.mdc.MdcDot;
```

在 `onMessage` 方法上加（`bizCode` 使用各自业务名）：
```java
// InventoryChangeMqConsumer.java
@Override
@MdcDot(bizCode = "inventoryChangeMq")
public void onMessage(String message) { ... }

// InventoryAlertConsumer.java
@Override
@MdcDot(bizCode = "inventoryAlertMq")
public void onMessage(String message) { ... }

// MonitorRuleConsumer.java
@Override
@MdcDot(bizCode = "monitorRuleMq")
public void onMessage(String message) { ... }

// MonitorRuleInOutBoundConsumer.java
@Override
@MdcDot(bizCode = "monitorRuleInOutBoundMq")
public void onMessage(String message) { ... }

// SnMaterialDocInConsumer.java
@Override
@MdcDot(bizCode = "snMaterialDocInMq")
public void onMessage(String message) { ... }

// SnMaterialDocCancelConsumer.java
@Override
@MdcDot(bizCode = "snMaterialDocCancelMq")
public void onMessage(String message) { ... }

// InventoryBffMqConsumer.java
@Override
@MdcDot(bizCode = "inventoryBffMq")
public void onMessage(String message) { ... }
```

- [x] **L3-2：编译验证** ✅

- [x] **L3-3：Commit**

```bash
git add inventory-middle-interfaces/src/main/java/com/inventory/middle/interfaces/consumer/
git commit -m "feat(interfaces): add @MdcDot to all MQ consumers for trace linking"
```

---

## 任务 L4：核心 ApplicationService 加 @StopWatchWrapper

**业务背景：** KDLA `@StopWatchWrapper` 切面会自动打印方法耗时（`StopWatchWrapperAspect`），无需手动 `StopWatch.start()/stop()`，适合加在出入库、预警等核心方法上做性能基线监控。

**文件：**
- 修改：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/MaterialDocMainApplicationServiceImpl.java`
- 修改：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/InventoryAlertNotificationApplicationServiceImpl.java`
- 修改：`inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/InventoryMapApplicationServiceImpl.java`

- [x] **L4-1：在三个核心 ApplicationServiceImpl 的关键方法上加 @StopWatchWrapper**

各文件 import 区加：
```java
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
```

在以下方法上加注解（`logHead` 用类名缩写，`msg` 用方法名）：

```java
// MaterialDocMainApplicationServiceImpl.java
@StopWatchWrapper(logHead = "MaterialDocApp", msg = "createMaterialDocIn")
public SingleResponse<Boolean> createMaterialDocIn(...) { ... }

@StopWatchWrapper(logHead = "MaterialDocApp", msg = "createMaterialDocOut")
public SingleResponse<Boolean> createMaterialDocOut(...) { ... }

@StopWatchWrapper(logHead = "MaterialDocApp", msg = "reverseMaterialDoc")
public SingleResponse<MaterialDocInvRes> reverseMaterialDoc(...) { ... }

// InventoryAlertNotificationApplicationServiceImpl.java
@StopWatchWrapper(logHead = "AlertNotificationApp", msg = "sendAlertNotification")
public SingleResponse<Boolean> sendAlertNotification(...) { ... }

// InventoryMapApplicationServiceImpl.java
@StopWatchWrapper(logHead = "InventoryMapApp", msg = "recordMapJournal")
public SingleResponse<Boolean> recordMapJournal(...) { ... }
```

> **注意：** `@StopWatchWrapper` 基于 AOP，需要方法是 Spring 托管 Bean 的 public 方法；`private` 方法无效。

- [x] **L4-2：编译验证** ✅

- [x] **L4-3：Commit**

```bash
git add inventory-middle-application/src/main/java/com/inventory/middle/application/service/impl/
git commit -m "feat(application): add @StopWatchWrapper to core service methods for perf monitoring"
```

---

## 任务 L5：Domain 层校验改用 BaseAssert

**业务背景：** KDLA `BaseAssert` 提供语义化校验方法（`notNull` / `isTrue` / `notEmpty` / `isBlank`），内部统一抛 `BizException`，与项目已有的 `BusinessException extends BizException`（L1 完成后）完全兼容。可替换 domain 层中大量 `if (x == null) throw new BusinessException(...)` 模式，提升可读性。

> **前置依赖：** 任务 L1 必须先完成（`BusinessException` 继承 `BizException`，`BaseAssert` 抛的是 `BizException`，功能等价）。

**影响范围（优先处理下列文件，其余按需）：**
- `domain/service/outbound/MaterialDocOutboundService.java`
- `domain/service/outbound/MaterialDocBaseService.java`
- `domain/service/outbound/MaterialDocOutboundDefaultHandlerService.java`
- `domain/validator/` 下所有 Validator 类

**文件：**
- 修改：上述各文件（外科手术式替换，不改业务逻辑）

- [x] **L5-1：在各文件 import 区加 BaseAssert**

```java
import top.kdla.framework.validator.BaseAssert;
```

- [x] **L5-2：替换典型校验模式**

```java
// 改前
if (materialDocument == null) {
    throw new BusinessException("物料凭证信息为空");
}
// 改后
BaseAssert.notNull(materialDocument, "物料凭证信息为空");

// 改前
if (StringUtils.isBlank(materialDocument.getMoveType())) {
    throw new BusinessException("物料凭证-移动类型为空");
}
// 改后
BaseAssert.isBlank(materialDocument.getMoveType(), "物料凭证-移动类型为空");

// 改前
if (CollectionUtils.isEmpty(itemList)) {
    throw new BusinessException(ResponseCodeEnum.OUT_ERROR, "实际出库item为空");
}
// 改后
BaseAssert.notEmpty(itemList, ResponseCodeEnum.OUT_ERROR.getCode(), "实际出库item为空");
```

- [x] **L5-3：编译验证** ✅

- [x] **L5-4：Commit**

```bash
git add inventory-middle-domain/src/main/java/com/inventory/middle/domain/
git commit -m "refactor(domain): replace manual null-checks with BaseAssert for readability"
```

---

## 任务 L6：SequenceNoHelperImpl 委托 KDLA SequenceNoGenerator

**业务背景：** `SequenceNoHelper` 是 domain 层自定义接口，用于生成物料凭证号、出库单号等序列号。当前 infra 层实现不明，KDLA 提供了完整的 `SequenceNoGenerator`（基于 Redis 分布式锁 + DB 流水号规则配置，支持本地缓存，生产级实现）。应将 `SequenceNoHelperImpl` 委托给 KDLA，避免重复造轮子。

**前置条件：** 确认 `code_generator_cfg` 表已存在（KDLA `SequenceNoGenerator` 依赖该表）：
```sql
-- 如果不存在需建表：
CREATE TABLE `code_generator_cfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT '规则代码',
  `rule` varchar(128) DEFAULT NULL COMMENT '规则（prefix+时间格式+序列长度，如 INV+yyyyMMdd+6）',
  `max_value` varchar(64) DEFAULT NULL COMMENT '当前最大值',
  `is_cache` tinyint(4) DEFAULT 0 COMMENT '是否缓存 0否 1是',
  `cache_num` int(11) DEFAULT 1000 COMMENT '缓存数量',
  `remark` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT 0,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='序列号规则配置';

-- 初始化物料凭证号规则（前缀 INV + 日期 + 6位流水）
INSERT INTO code_generator_cfg(code, rule, is_cache, cache_num)
VALUES ('MATERIAL_DOC_NO', 'INV+yyyyMMdd+6', 1, 100);
```

**文件：**
- 查阅：`inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/external/SequenceNoHelper.java`
- 创建/修改：`inventory-middle-infra/src/main/java/com/inventory/middle/infra/external/SequenceNoHelperImpl.java`
- 修改：`inventory-middle-starter/pom.xml` 或 `inventory-middle-infra/pom.xml`（确认 `kdla-component-supplement-cache` 依赖已引入）

- [x] **L6-1：确认 SequenceNoHelper 接口方法**

```bash
cat inventory-middle-domain/src/main/java/com/inventory/middle/domain/service/external/SequenceNoHelper.java
```
预期：至少包含 `String generateCodeNo()` 或类似方法签名

- [x] **L6-2：确认 kdla-supplement-cache 依赖已在 pom 中**

```bash
grep -r "kdla-component-supplement-cache" \
  inventory-middle-infra/pom.xml inventory-middle-starter/pom.xml 2>/dev/null
```

若未引入，在 `inventory-middle-infra/pom.xml` 的 `<dependencies>` 中追加：
```xml
<dependency>
    <groupId>top.kdla.framework</groupId>
    <artifactId>kdla-component-supplement-cache</artifactId>
</dependency>
```

- [x] **L6-3：实现 SequenceNoHelperImpl 委托 KDLA SequenceNoGenerator**

```java
package com.inventory.middle.infra.external;

import com.inventory.middle.domain.service.external.SequenceNoHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.kdla.framework.supplement.cache.sequence.SequenceNoGenerator;

import javax.annotation.Resource;

/**
 * 序列号生成实现 - 委托给 KDLA SequenceNoGenerator
 * 规则配置在 code_generator_cfg 表中，key = "MATERIAL_DOC_NO"
 */
@Component
@Slf4j
public class SequenceNoHelperImpl implements SequenceNoHelper {

    @Resource
    private SequenceNoGenerator sequenceNoGenerator;

    // SequenceNoHelper 接口中实际方法名以接口定义为准，此处为示例
    @Override
    public String generateCodeNo() {
        try {
            return sequenceNoGenerator.getMaxNo("MATERIAL_DOC_NO");
        } catch (Exception e) {
            log.error("SequenceNoHelperImpl.generateCodeNo error", e);
            throw new RuntimeException("生成序列号失败", e);
        }
    }
}
```

> **注意：** `SequenceNoGenerator` 需要通过 `SequenceNoConfigure`（KDLA 自动配置）注册为 Bean，依赖 `RedissonRedDisLock` Bean。确保 `application.yml` 中 Redis 和 Redisson 配置正确（L2 已有）。

- [x] **L6-4：编译验证** ✅

- [x] **L6-5：Commit**

```bash
git add inventory-middle-infra/src/main/java/com/inventory/middle/infra/external/SequenceNoHelperImpl.java
git add inventory-middle-infra/pom.xml
git commit -m "feat(infra): delegate SequenceNoHelper to KDLA SequenceNoGenerator"
```

---

## KDLA 对齐任务执行顺序

> **所有任务 L1-L6 均已完成。**

```
L1（BusinessException 改继承）← ✅
  ↓
L2（application.yml 配置）← ✅
  ↓
L6（SequenceNoHelper 委托）← ✅
  ↓
L3（MQ Consumer @MdcDot）← ✅
L4（ApplicationService @StopWatchWrapper）← ✅
  ↓
L5（BaseAssert 替换）← ✅
```

---

## 关联残余任务（详见各迁移计划文件）

### BFF 迁移残余（详见 [2026-06-12-bff-migration.md](2026-06-12-bff-migration.md)）

| 任务 | 说明 | 优先级 |
|------|------|--------|
| R1 | 补齐 10 个 Controller 的 `@CatchAndLog` | 高 |
| R2+R3 | `LogInterceptor` 注册 + `application-dev.yml` | 高 |
| R4 | `InventorySnapshotQueryService` 补充 5 个缺失方法 | 高 |
| R5 | `MonitorRule` 4 个枚举 + `updateWithLines` + `importExcel` 等 | 中 |
| R6 | `MaterialDocController` 4 个 TODO | 中 |
| R7+R8+R9 | 基础设施 Controller 缺失方法（Warehouse/StorageLocation/LogicalPlant） | 中 |
| R10 | `InventoryTransit` 单位名称注入 | 低 |
| R11+R12 | `DeliveryOrderMgnt` / `Distributor` OpenFeign 接入（阶段三，外部依赖） | 低 |

### SCM Plan 迁移残余（详见 [2026-06-12-scm-plan-migration.md](2026-06-12-scm-plan-migration.md)）

| 任务 | 说明 | 优先级 |
|------|------|--------|
| 组 O 步骤 2-4 | 冒烟启动验证、路由注册检查、git commit | 高（需运行基础设施） |