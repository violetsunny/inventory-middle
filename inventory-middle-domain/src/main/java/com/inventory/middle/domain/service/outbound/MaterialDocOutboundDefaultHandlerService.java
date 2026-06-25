package com.inventory.middle.domain.service.outbound;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.service.InventoryDomainService;
import com.inventory.middle.domain.service.outbound.MaterialDocBaseService;
import com.inventory.middle.domain.service.outbound.MaterialDocReverseService;
import com.inventory.middle.client.dto.transit.CreateInTransitStockRequest;
import com.inventory.middle.domain.model.enums.*;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyBO;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandBO;
import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.service.external.SequenceNoHelper;
import com.inventory.middle.domain.service.mq.InventoryMqProducer;
import com.inventory.middle.domain.service.*;
import com.inventory.middle.domain.service.impl.InventorySnapshotCoreService;
import com.inventory.middle.domain.service.impl.LogicalPlantCoreService;
import com.inventory.middle.domain.service.impl.StorageLocationCoreService;
// dal entity imports removed - use BO classes
import com.inventory.middle.domain.service.external.MaterialDocOutboundService;
import com.inventory.middle.domain.service.external.RemoteInventoryMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.inventory.middle.domain.service.lock.DistributedLockService;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.DEC;
import static com.inventory.middle.domain.common.constants.RedisConstant.MATERIAL_NO_OUTBOUND_KEY;
import static com.inventory.middle.domain.common.constants.ResponseCodeEnum.MDOC_OUTBOUND_REPEAT_SAMETIME;

/**
 * @author holmes
 * @Classname MaterialDocOutboundDefaultHandlerService
 * @Description 出库公共类 默认实现出库流程
 * @Date 2021/6/23 18:45
 */
@Service
@Slf4j
public class MaterialDocOutboundDefaultHandlerService extends MaterialDocBaseService
        implements MaterialDocOutboundService {

    @Resource
    protected InventoryMqProducer inventoryMqProducer;
    @Resource
    protected MaterialDocCoreService materialDocCoreService;
    @Resource
    protected MaterialDocSubCoreService materialDocSubCoreService;
    @Resource
    protected InventoryCoreService inventoryCoreService;
    @Resource
    protected LogicalPlantCoreService logicalPlantCoreService;
    @Resource
    protected StorageLocationCoreService storageLocationCoreService;
    @Resource
    protected MaterialDocItemCoreService materialDocItemCoreService;
    @Resource
    protected InventoryDemandCoreService inventoryDemandCoreService;
    @Resource
    protected InventorySupplyCoreService inventorySupplyCoreService;
    @Resource
    protected InventorySnapshotCoreService inventorySnapshotCoreService;
    @Resource
    protected MaterialDocReverseService materialDocReverseCommonService;
    @Resource
    protected SequenceNoHelper sequenceNoHelper;
    @Resource
    protected InventoryMqProducer inventoryChangeMqProduce;
    @Resource
    protected RemoteInventoryMapService remoteInventoryMapService;
    @Resource
    private DistributedLockService redissonDistributedLockService;
    @Resource
    private InventoryDomainService inventoryDomainService;

    /**
     * 执行出库
     *
     * @param materialDocumentBO
     */
    public void doOutbound(MaterialDocumentBO materialDocumentBO) {
        doOutbound(materialDocumentBO, false);

    }

    public void doOutbound(MaterialDocumentBO materialDocumentBO, Boolean outOnly) {
        //check
        checkAdjustNumber(materialDocumentBO, false);
        //move
        generateDemandData(materialDocumentBO, outOnly);

    }

    /**
     * 计算可用库存
     *
     * @param materialDocumentBO
     * @param stockTypeEnum
     * @param materialDocumentItemBO
     * @return
     */
    protected BigDecimal countAvailable(MaterialDocumentBO materialDocumentBO, StockTypeEnum stockTypeEnum, MaterialDocumentItemBO materialDocumentItemBO, boolean needBatchNo) {
        String batchNo = null;
        String storageLocationNo = materialDocumentItemBO.getWarehouseData().getDemandStorageLocationNo();
        if (needBatchNo) {
            batchNo = materialDocumentItemBO.getWarehouseData().getDemandBatchNo();
        }
        return inventorySnapshotCoreService.countAvailable(materialDocumentItemBO.getMaterialData().getMaterialCode(), materialDocumentBO.getDemandLogicalPlantNo(), stockTypeEnum,
                materialDocumentBO.getTenantId(), batchNo, storageLocationNo);
    }


    /**
     * 业务逻辑处理
     *
     * @param operator
     * @param itemBO
     * @param inventorySnapshotPOS
     * @param stockTypeEnum
     * @param newMaterialDocId
     * @param target
     * @param batchNoOnly
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void doBiz(String operator, MaterialDocumentItemBO itemBO, List<InventorySnapshotBO> inventorySnapshotPOS, StockTypeEnum stockTypeEnum, Long newMaterialDocId, List<MaterialDocumentItemBO> target, Boolean batchNoOnly) {
        String chooseBatchNo = itemBO.getWarehouseData().getDemandBatchNo();
        List<InventorySnapshotBO> sortedList = new ArrayList<>(inventorySnapshotPOS.size());
        if (batchNoOnly) {
            if (StringUtils.isBlank(chooseBatchNo)) {
                throw new BusinessException("批次号不能为空");
            }
            //找到选中的批次
            Optional<InventorySnapshotBO> chooseInventorySnapshotOp = inventorySnapshotPOS.stream().filter(x -> x.getBatchNo().equals(itemBO.getWarehouseData().getDemandBatchNo())).findFirst();
            //无效的批次
            if (!chooseInventorySnapshotOp.isPresent()) {
                throw new BusinessException("没有查询到对应批次号的库存数据，不能完成出库 ,batchNo=" + chooseBatchNo + " materialCode=" + itemBO.getMaterialData().getMaterialCode());
            }
            InventorySnapshotBO chooseInventorySnapshotBO = chooseInventorySnapshotOp.get();
            sortedList.add(chooseInventorySnapshotBO);
            List<MaterialDocumentItemBO> skuBuildItems = processCountOutboundBatch(operator, stockTypeEnum, sortedList, itemBO, newMaterialDocId);
            target.addAll(skuBuildItems);
        } else {
            if (!StringUtils.isBlank(chooseBatchNo)) {
                //找到选中的批次
                Optional<InventorySnapshotBO> chooseInventorySnapshotOp = inventorySnapshotPOS.stream().filter(x -> x.getBatchNo().equals(itemBO.getWarehouseData().getDemandBatchNo())).findFirst();
                //无效的批次
                if (!chooseInventorySnapshotOp.isPresent()) {
                    throw new BusinessException("没有查询到对应批次号的库存数据，不能完成出库 ,batchNo=" + chooseBatchNo + " materialCode=" + itemBO.getMaterialData().getMaterialCode());
                }
                //去掉选中的批次
                InventorySnapshotBO chooseInventorySnapshotBO = chooseInventorySnapshotOp.get();
                inventorySnapshotPOS.removeIf(y -> chooseInventorySnapshotBO.getBatchNo().equals(y.getBatchNo()));
                //把选中的批次放到头部
                sortedList.add(chooseInventorySnapshotBO);
            }
            sortedList.addAll(inventorySnapshotPOS);
            //计算需要多少批次满足出库
            List<MaterialDocumentItemBO> skuBuildItems = processCountOutboundBatch(operator, stockTypeEnum, sortedList, itemBO, newMaterialDocId);
            target.addAll(skuBuildItems);
        }
    }

    /**
     * 计算需要多少个批次出库能满足需求数量
     *
     * @param stockTypeEnum
     * @param sortedList
     * @param materialDocumentItemBO
     * @param newMaterialDocId
     * @return
     */
    protected List<MaterialDocumentItemBO> processCountOutboundBatch(String operator, StockTypeEnum stockTypeEnum, List<InventorySnapshotBO> sortedList, MaterialDocumentItemBO materialDocumentItemBO, Long newMaterialDocId) {
        BigDecimal tempSum = BigDecimal.ZERO;
        BigDecimal adjustNumber = materialDocumentItemBO.getQuantityData().getAdjustQuantity();
        List<MaterialDocumentItemBO> currentSkuList = new ArrayList<>();
        log.info("processCountOutboundBatch adjustNumber ={},materialCode ={}", adjustNumber, materialDocumentItemBO.getMaterialData().getMaterialCode());
        for (InventorySnapshotBO snp : sortedList) {
            BigDecimal tempDemand = findColumn(snp, stockTypeEnum);
            tempSum = tempDemand.add(tempSum);
            BigDecimal gap = tempSum.subtract(adjustNumber);
            log.info("processCountOutboundBatch tempDemand ={},tempSum ={},gap ={}", tempDemand, tempSum, gap);
            MaterialDocumentItemBO afterBuild;
            if (gap.compareTo(BigDecimal.ZERO) == 0) {
                afterBuild = doProcessCountOutboundBatch(operator, materialDocumentItemBO, snp, stockTypeEnum, newMaterialDocId, tempDemand, true, true);
                currentSkuList.add(afterBuild);
                //累加数量正好满足出库、盘亏数量 结束
                break;

            } else if (gap.compareTo(BigDecimal.ZERO) < 0) {
                afterBuild = doProcessCountOutboundBatch(operator, materialDocumentItemBO, snp, stockTypeEnum, newMaterialDocId, tempDemand, true, true);
                currentSkuList.add(afterBuild);
                //累加数量不满足出库、盘亏数量 继续
                continue;
            } else {
                //累加数量大于出库、盘亏数量，需要用gap
                //差额调整
                //记录当前的批次ID， 生成demand数据 、调整快照表、
                afterBuild = doProcessCountOutboundBatch(operator, materialDocumentItemBO, snp, stockTypeEnum, newMaterialDocId, tempDemand.subtract(gap), false, false);
                currentSkuList.add(afterBuild);
                //结束
                break;
            }
        }

        return currentSkuList;
    }


    /**
     * @param materialDocumentBO
     * @param needBatchNo        批次内出库
     */
    public void checkAdjustNumber(MaterialDocumentBO materialDocumentBO, Boolean needBatchNo) {
        List<MaterialDocumentItemBO> materialDocumentItems = materialDocumentBO.getMaterialDocumentItems();
        BaseAssert.notEmpty(materialDocumentItems, "物料凭证ITEM不能为空");
        materialDocumentItems.forEach(e -> {
            //判断数量
            BigDecimal adjustNumber = e.getQuantityData().getAdjustQuantity();
            Integer stockType = e.getWarehouseData().getDemandStockType();
            StockTypeEnum stockTypeEnum = StockTypeEnum.getByCode(stockType);
            if (stockTypeEnum == null) {
                stockTypeEnum = StockTypeEnum.UNRESTRICTED;
            }
            BigDecimal availableStock = countAvailable(materialDocumentBO, stockTypeEnum, e, needBatchNo);
            BigDecimal availableStockFormat = availableStock == null ? BigDecimal.ZERO : availableStock;
            if (adjustNumber.compareTo(availableStockFormat) > 0) {
                log.warn("checkAdjustNumber fail,物料编码 ={} ,  出库/盘亏超过可用库存. needBatchNo= {},adjustNumber:{},availableStock:{},availableStockFormat:{}", e.getMaterialData().getMaterialCode(), needBatchNo, adjustNumber, availableStock, availableStockFormat);
                throw new BusinessException("物料编码：" + e.getMaterialData().getMaterialCode() + " 超过可用库存");
            }
        });
    }

    /**
     * @param materialDocumentBO
     */
    protected void doSendMq(MaterialDocumentBO materialDocumentBO) {
        materialDocCoreService.sendInventoryInMQ(materialDocumentBO, InventoryTagEnum.OUTBOUND);
    }


    /**
     * 生成
     *
     * @param materialDocumentBO
     * @param outOnly            true：  只执行出库,不新增凭证，不发MQ
     */
    @Transactional(rollbackFor = Exception.class)
    public void generateDemandData(MaterialDocumentBO materialDocumentBO, boolean outOnly) {
        List<MaterialDocumentItemBO> materialDocumentItems = materialDocumentBO.getMaterialDocumentItems();

        final Long newMaterialDocId = materialDocumentBO.getMaterialDocId();
        final List<MaterialDocumentItemBO> list = new ArrayList<>();
        final MaterialDocumentBO finalDocumentBO = materialDocumentBO;
        for (MaterialDocumentItemBO itemBO : materialDocumentItems) {
            String locKkey = MATERIAL_NO_OUTBOUND_KEY + itemBO.getMaterialData().getMaterialCode() + "::" + finalDocumentBO.getDemandLogicalPlantNo();
            final MaterialDocumentItemBO finalItemBO = itemBO;
            redissonDistributedLockService.executeWithLock(1000L, java.util.concurrent.TimeUnit.MILLISECONDS, locKkey, () -> {
                //判断数量
                Integer stockType = finalItemBO.getWarehouseData().getDemandStockType();
                StockTypeEnum stockTypeEnum = StockTypeEnum.getByCode(stockType);
                List<InventorySnapshotBO> inventorySnapshotPOS = inventorySnapshotCoreService.allAvailableSortList(finalItemBO.getMaterialData().getMaterialCode(),
                        finalDocumentBO.getDemandLogicalPlantNo(), stockTypeEnum, finalDocumentBO.getTenantId(), finalItemBO.getWarehouseData().getDemandStorageLocationNo());
                if (CollectionUtils.isEmpty(inventorySnapshotPOS)) {
                    throw new BusinessException("没有查询到可用用库存,批次号=" + finalItemBO.getWarehouseData().getDemandBatchNo()
                            + ",物料编码=" + finalItemBO.getMaterialData().getMaterialCode());
                }
                doBiz(finalDocumentBO.getOperator(), finalItemBO, inventorySnapshotPOS, stockTypeEnum, newMaterialDocId, list, false);
                return null;
            });

        }
        if (!outOnly) {
            //生成物料凭证 重新构建BO
            materialDocumentBO.setMaterialDocumentItems(list);
            //是否计算MAP
            materialDocCoreService.initMap(materialDocumentBO);
            if (org.apache.commons.lang3.StringUtils.isBlank(materialDocumentBO.getMaterialDocNo())) {
                materialDocumentBO.setMaterialDocNo(sequenceNoHelper.generateMaterialDocNo());
            }
            materialDocumentBO = materialDocCoreService.createMaterialDoc(materialDocumentBO);
            materialDocSubCoreService.supplementMaterialDoc(materialDocumentBO);

            //发送MQ
            doSendMq(materialDocumentBO);
        }

    }


    /**
     * 执行批次出库操作
     *
     * @param operator
     * @param source
     * @param snp
     * @param stockTypeEnum
     * @param newMaterialDocId
     * @param demandNumber
     * @param deletedSnp
     * @param disabledSupply
     * @return
     */
    protected MaterialDocumentItemBO doProcessCountOutboundBatch(String operator, MaterialDocumentItemBO source, InventorySnapshotBO snp,
                                                                 StockTypeEnum stockTypeEnum, Long newMaterialDocId,
                                                                 BigDecimal demandNumber, boolean deletedSnp,
                                                                 boolean disabledSupply) {

        //有可能查出来多个 比如盘盈 同一批次
        List<InventorySupplyBO> inventorySupplyPOs = inventorySupplyCoreService.find(source.getMaterialDocId(), snp.getMaterialCode(),
                snp.getBatchNo(), snp.getTenantId(), snp.getLocationId(), snp.getLogicalPlantNo());
        if (CollectionUtils.isEmpty(inventorySupplyPOs)) {
            throw new BusinessException("没有查询到对应的入库数据，不能完成出库 ,batchNo=" + snp.getBatchNo() + " materialCode=" + snp.getMaterialCode());
        }
        /*if (disabledSupply) {
            inventorySupplyPOs.forEach(e -> inventorySupplyCoreService.disableById(e.getId()));
        }*/
        //取ID最小的对象 使用其属性
        log.info("doProcessCountOutboundBatch demandNumber ={},snp ={}", demandNumber, snp);
        InventorySupplyBO inventorySupplyPO = inventorySupplyPOs.stream().min(Comparator.comparingLong(InventorySupplyBO::getId)).orElse(null);
        //InventorySupplyBO inventorySupplyPO = inventorySupplyPOs.get(0);
        String jsonString = JSON.toJSONString(source);
        MaterialDocumentItemBO target = JSON.parseObject(jsonString, MaterialDocumentItemBO.class);
        target.setBatchNo(snp.getBatchNo());
        target.setItemCategory(MaterialDocCategoryEnum.OUT.getCode());
        target.getWarehouseData().setDemandStockType(stockTypeEnum.getCode());
        target.getWarehouseData().setDemandStorageLocationNo(snp.getLocationNo());
        target.getWarehouseData().setIo(StringUtils.isBlank(source.getWarehouseData().getIo()) ? DEC : source.getWarehouseData().getIo());
        target.getWarehouseData().setDemandBatchNo(snp.getBatchNo());
        target.getQuantityData().setAdjustQuantity(demandNumber);
        BigDecimal outPrice = findBatchPrice(target, snp);
        target.setMaterialCode(snp.getMaterialCode());
        target.getQuantityData().setPrice(outPrice);
        if (target.getMapJournalData() != null) {
            String mapCode = sequenceNoHelper.generateMapCode();
            target.getMapJournalData().setMapCode(mapCode);
        }

        /* inventorySupplyPOs.forEach(inventorySupplyPO -> {*/
        //， 生成demand数据
        InventoryDemandBO inventoryDemand = new InventoryDemandBO();
        setColumn(stockTypeEnum, inventoryDemand, demandNumber);
        inventoryDemand.setBatchNo(snp.getBatchNo());
        inventoryDemand.setMaterialCode(snp.getMaterialCode());
        inventoryDemand.setMaterialDocId(newMaterialDocId);
        inventoryDemand.setCancelDate(new Date());
        inventoryDemand.setCreateTime(LocalDateTime.now());


        inventoryDemand.setCreatorId(operator);
        inventoryDemand.setCurrency(inventorySupplyPO.getCurrency());
        inventoryDemand.setUom(inventorySupplyPO.getUom());
        inventoryDemand.setStockType(stockTypeEnum.getCode());
        inventoryDemand.setOwnerId(inventorySupplyPO.getOwnerId());
        inventoryDemand.setTenantId(inventorySupplyPO.getTenantId());
        inventoryDemand.setLocationId(snp.getLocationId());
        inventoryDemand.setLogicalPlantId(snp.getLogicalPlantId());
        inventoryDemand.setWarehousePrice(outPrice);
        inventoryDemand.setDeleted(0);
        inventoryDemand.setUpdateTime(LocalDateTime.now());
        inventoryDemand.setDeadlineDate(new Date());
        inventoryDemand.setUpdatorId(inventorySupplyPO.getUpdatorId());
        inventoryDemandCoreService.create(inventoryDemand);

        //、调整快照表、-
        //inventorySnapshotCoreService.adjustDown(snp.getMaterialCode(), snp.getBatchNo(), demandNumber, stockTypeEnum, snp.getTenantId(), deletedSnp);

        boolean delFlag = checkDisabled(snp, demandNumber, stockTypeEnum, deletedSnp);
        inventorySnapshotCoreService.disableById(snp.getId(), demandNumber, stockTypeEnum, delFlag);
        //
        return target;
    }

    /**
     * 2021.8.25 14:25 产品闵羽要求不能用页面输入的价格，直接用批次价格
     *
     * @param target
     * @param snp
     * @return
     */
    protected BigDecimal findBatchPrice(MaterialDocumentItemBO target, InventorySnapshotBO snp) {
        //BigDecimal outPrice = Optional.ofNullable(target.getQuantityData().getPrice()).filter(d -> !StringUtils.isEmpty(d)).orElse(snp.getBatchPrice());
        //BigDecimal outPrice = Optional.ofNullable(target.getQuantityData().getPrice()).filter(d -> !StringUtils.isEmpty(d)).orElse(snp.getBatchPrice());
        return snp.getBatchPrice();
    }

    /**
     * @param snp
     * @param demandNumber
     * @param stockTypeEnum
     * @param deletedSnp
     * @return
     */
    protected boolean checkDisabled(InventorySnapshotBO snp, BigDecimal demandNumber, StockTypeEnum stockTypeEnum, boolean deletedSnp) {
        if (StockTypeEnum.UNRESTRICTED.equals(stockTypeEnum)) {
            return snp.getUnrestricted().compareTo(demandNumber) == 0 && snp.getDamaged().compareTo(BigDecimal.ZERO) == 0 && snp.getInspection().compareTo(BigDecimal.ZERO) == 0;
        }
        if (StockTypeEnum.DAMAGED.equals(stockTypeEnum)) {
            return snp.getDamaged().compareTo(demandNumber) == 0 && snp.getUnrestricted().compareTo(BigDecimal.ZERO) == 0 && snp.getInspection().compareTo(BigDecimal.ZERO) == 0;
        } else {
            return snp.getInspection().compareTo(demandNumber) == 0 && snp.getDamaged().compareTo(BigDecimal.ZERO) == 0 && snp.getUnrestricted().compareTo(BigDecimal.ZERO) == 0;
        }
    }


    /**
     * 根据库存类型操作不同的字段
     *
     * @param stockTypeEnum
     * @param inventoryDemand
     * @param tempDemand
     */
    protected void setColumn(StockTypeEnum stockTypeEnum, InventoryDemandBO inventoryDemand, BigDecimal tempDemand) {
        if (StockTypeEnum.UNRESTRICTED.equals(stockTypeEnum)) {
            inventoryDemand.setUnrestricted(tempDemand);
            inventoryDemand.setDamaged(BigDecimal.ZERO);
            inventoryDemand.setInspection(BigDecimal.ZERO);
        } else if (StockTypeEnum.DAMAGED.equals(stockTypeEnum)) {
            inventoryDemand.setDamaged(tempDemand);

            inventoryDemand.setUnrestricted(BigDecimal.ZERO);
            inventoryDemand.setInspection(BigDecimal.ZERO);
        } else {
            inventoryDemand.setInspection(tempDemand);

            inventoryDemand.setUnrestricted(BigDecimal.ZERO);
            inventoryDemand.setDamaged(BigDecimal.ZERO);
        }
    }


    /**
     * 根据库存类型查询不同的字段
     *
     * @param snp
     * @param stockTypeEnum
     * @return
     */
    protected BigDecimal findColumn(InventorySnapshotBO snp, StockTypeEnum stockTypeEnum) {
        if (StockTypeEnum.UNRESTRICTED.equals(stockTypeEnum)) {
            return snp.getUnrestricted();
        }
        if (StockTypeEnum.DAMAGED.equals(stockTypeEnum)) {
            return snp.getDamaged();
        } else {
            return snp.getInspection();
        }
    }


    /**
     * 校验参数，调拨出库必须包含入库信息，生成在途
     *
     * @param materialDocumentBO
     * @return
     */
    protected void checkDbParam(MaterialDocumentBO materialDocumentBO) {
        List<MaterialDocumentItemBO> materialDocumentItems = materialDocumentBO.getMaterialDocumentItems();
        BaseAssert.notEmpty(materialDocumentItems, "物料凭证行项目不能为空");
        if (StringUtils.isBlank(materialDocumentBO.getDemandLogicalPlantNo()) || StringUtils.isBlank(materialDocumentBO.getSupplyLogicalPlantNo())) {
            log.error("校验参数失败, 收货仓和发货仓信息缺失");
            throw new BusinessException(ResponseCodeEnum.PARAM_VALID_ERROR.getCode(),"收货仓和发货仓信息缺失");
        }

    }

    /**
     * 生成在途库存
     *
     * @param materialDocumentBO
     */
    protected void generateTransitStock(MaterialDocumentBO materialDocumentBO) {
        //如果来源单号和交运单号都为空，或来源单类型为物料凭证。则不生成在途
        if ((StringUtils.isBlank(materialDocumentBO.getOriginalNo()) && StringUtils.isBlank(materialDocumentBO.getDeliverNo()))
            || MaterialDocRefOrderTypeEnum.MATERIAL_DOC.getCode().equals(materialDocumentBO.getOriginalNoType())) {
            return;
        }

        LogicalPlantBO logicalPlantPO = logicalPlantCoreService.getByLogicalPlantNo(materialDocumentBO.getSupplyLogicalPlantNo());
        if (logicalPlantPO == null) {
            log.error("找不到对应的逻辑仓，逻辑仓编码={}", materialDocumentBO.getSupplyLogicalPlantNo());
            throw new BusinessException("找不到对应的逻辑仓，逻辑仓编码=" + materialDocumentBO.getSupplyLogicalPlantNo());
        }

        List<MaterialDocumentItemBO> materialDocumentItems = materialDocumentBO.getMaterialDocumentItems();
        List<CreateInTransitStockRequest> requestList = new ArrayList<>();
        materialDocumentItems.forEach(e -> {
            CreateInTransitStockRequest request = new CreateInTransitStockRequest();


            request.setTenantId(logicalPlantPO.getTenantId());
            request.setMaterialDocId(materialDocumentBO.getMaterialDocId());
            request.setLogicalPlantId(logicalPlantPO.getId());
            request.setCurrency(CurrencyEnum.CNY.getCode());
            request.setBatchPrice(e.getQuantityData().getPrice());
            request.setMaterialCode(e.getMaterialData().getMaterialCode());

            StorageLocationBO storageLocationPO = findStorageLocationBO(e.getWarehouseData().getSupplyStorageLocationNo(), logicalPlantPO.getId());
            if (storageLocationPO == null) {
                log.error("找不到对应的存储地点，逻辑仓编码={},存储地点编码={}", materialDocumentBO.getSupplyLogicalPlantNo(), e.getWarehouseData().getSupplyStorageLocationNo());
                throw new BusinessException("找不到对应的存储地点，逻辑仓编码=" + materialDocumentBO.getSupplyLogicalPlantNo());
            }
            request.setLocationId(storageLocationPO.getId());
            request.setOperatorId(materialDocumentBO.getOperator());
            request.setUom(e.getQuantityData().getUom());
            request.setQuantity(e.getQuantityData().getAdjustQuantity());
            request.setSourceOrderType(materialDocumentBO.getOriginalNoType());
            request.setSourceOrderNo(materialDocumentBO.getOriginalNo());
            request.setDeliveryNo(Optional.ofNullable(materialDocumentBO.getDeliverNo()).filter(
                StringUtils::isNotBlank).orElse(materialDocumentBO.getOriginalNo()));
            request.setStockType(StockTypeEnum.UNRESTRICTED.getCode());//默认良品
            requestList.add(request);
        });


        inventoryDomainService.batchCreateInTransitStock(requestList);
    }

    /**
     * 优先通过存储地点编码查询，找不到再通过逻辑仓编码查询
     *
     * @param storageNo
     * @param logicalPlantId
     * @return
     */
    protected StorageLocationBO findStorageLocationBO(String storageNo, Long logicalPlantId) {
        if (StringUtils.isNotBlank(storageNo)) {
            return storageLocationCoreService.getByStorageLocationNo(storageNo);
        }
        List<StorageLocationBO> storageLocationPOS = storageLocationCoreService.listByPlantId(logicalPlantId);
        if (CollectionUtils.isEmpty(storageLocationPOS)) {
            return null;
        }
        storageLocationPOS = storageLocationPOS.stream().filter(e -> e.getLocationType().equals(StorageLocationTypeEnum.NORMAL.getCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(storageLocationPOS)) {
            return null;
        }
        return storageLocationPOS.get(0);
    }

}

