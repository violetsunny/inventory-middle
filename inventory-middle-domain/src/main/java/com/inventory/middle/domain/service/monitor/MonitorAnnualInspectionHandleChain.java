/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.monitor;

import com.inventory.middle.client.dto.sku.Pager;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.client.dto.sku.SkuBatchPageRequest;
import com.inventory.middle.client.dto.sku.SkuBatchResponse;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.service.InventoryMasterDataSourceDomainService;
import com.inventory.middle.domain.service.InventoryMonitorRuleLineDomainService;
import com.inventory.middle.domain.service.InventorySnapshotDomainService;
import com.inventory.middle.client.enums.ProductMasterDataSourceEnum;
import com.inventory.middle.client.enums.monitor.MonitorRuleDimensionEnum;
import com.inventory.middle.domain.common.constants.CommonConstant;
import com.inventory.middle.domain.model.enums.InventoryTagEnum;
import com.inventory.middle.domain.model.bo.inventorysnapshot.MonitorInventorySnapshotPageBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLineBO;
import com.inventory.middle.domain.model.bo.mq.MonitorAnnualInspectionMessageBO;
import com.inventory.middle.domain.service.mq.InventoryMqProducer;
import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.service.external.RemoteProductCenterRestService;
import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 库存年检预警规则
 * @author dongguo.tao
 * @version
 */
@Slf4j
@Service("monitorAnnualInspectionHandleChain")
public class MonitorAnnualInspectionHandleChain implements IHandleChain<List<InventoryMonitorRuleBO>, Boolean> {
    @Resource
    private InventoryMonitorRuleLineDomainService ruleLineDomainService;

    @Resource
    private RemoteProductCenterRestService productCenterRestService;

    @Resource
    private InventoryMqProducer monitorAnnualInspectionMqProduce;

    @Resource
    private InventorySnapshotDomainService snapshotDomainService;

    @Resource
    private InventoryMasterDataSourceDomainService inventoryMasterDataSourceDomainService;

    @Value("${inventory.product.special.token:}")
    private String productSpecialToken;

    @Override
    public boolean doProcess(HandleMessage<List<InventoryMonitorRuleBO>, Boolean> msg) {
        log.info("MonitorAnnualInspectionHandleChain.doProcess msg={}", JSON.toJSONString(msg));
        if (Objects.isNull(msg.getT())) {
            msg.setE(true);
            return true;
        }

        if (StringUtils.isBlank(productSpecialToken)){
            log.error("MonitorAnnualInspectionHandleChain.doProcess productSpecialToken is blank.");
            return false;
        }
        Map<String, String> tokenMap = JSON.parseObject(productSpecialToken, HashMap.class);


        List<InventoryMonitorRuleBO> ruleBOList = msg.getT();
        if (CollectionUtils.isEmpty(ruleBOList)){
            msg.setE(true);
            return true;
        }
        //使用库存中心的主数据，不需要进行年检预警告警
        ruleBOList = ruleBOList.stream().filter(this::filterByInventoryMasterData).collect(Collectors.toList());
        List<Long> ruleIds = ruleBOList.stream().map(InventoryMonitorRuleBO::getId).collect(Collectors.toList());
        List<InventoryMonitorRuleLineBO> ruleLineList = ruleLineDomainService.queryByMonitorRuleIds(ruleIds);
        if (CollectionUtils.isEmpty(ruleLineList)){
            msg.setE(true);
            return true;
        }

        Map<Boolean, List<InventoryMonitorRuleLineBO>> dimensionMap = ruleLineList.stream()
                .filter(e -> StringUtils.isNotBlank(e.getMonitorDimension()))
                .collect(Collectors.groupingBy(e -> MonitorRuleDimensionEnum.ASSIGN_MATERIAL.name().equals(e.getMonitorDimension())));

        //物料维度
        Map<String, List<String>> materialMap = Maps.newHashMap();
        //逻辑仓维度
        Map<String, List<String>> logicalMap = Maps.newHashMap();
        Map<String, BigDecimal> ojMap = Maps.newHashMap();
        Map<String, BigDecimal> lgMap = Maps.newHashMap();

        Map<String, List<InventoryMonitorRuleBO>> listMap = ruleBOList.stream().collect(Collectors.groupingBy(InventoryMonitorRuleBO::getTenantId));
        listMap.forEach((k,v) -> {
            if (CollectionUtils.isEmpty(v)){
                return;
            }
            List<String> materialList = Lists.newArrayList();
            List<String> logicalList = Lists.newArrayList();
            List<Long> ruleIdList = v.stream().map(InventoryMonitorRuleBO::getId).collect(Collectors.toList());
            dimensionMap.forEach((type,ruleLineBOS) -> {
                if (CollectionUtils.isEmpty(ruleLineBOS)){
                    return;
                }
                ruleLineBOS.stream().filter(e -> ruleIdList.contains(e.getMonitorRuleId())).forEach(e -> {
                    if (type){
                        materialList.add(e.getMonitorObject());
                        ojMap.put(e.getMonitorObject(), e.getMonitorCeil());
                    }else {
                        logicalList.add(e.getMonitorObject());
                        lgMap.put(e.getMonitorObject(), e.getMonitorCeil());
                    }
                });
            });
            materialMap.put(k, materialList);
            logicalMap.put(k, logicalList);
        });

        List<InventorySnapshotBO> finalList = getInventorySnapshotBOS(materialMap, logicalMap, ojMap, lgMap);
        if (CollectionUtils.isEmpty(finalList)){
            log.info("MonitorAnnualInspectionHandleChain.doProcess finalList is empty.");
            return true;
        }
        Stopwatch sw = Stopwatch.createStarted();
        Map<String, List<InventorySnapshotBO>> stringListMap = finalList.stream().collect(Collectors.groupingBy(InventorySnapshotBO::getTenantId));
        log.info("MonitorAnnualInspectionHandleChain.doProcess stringListMap size={}" , stringListMap.size());
        stringListMap.forEach((tenantId, snapshotList) -> {
            String token = tokenMap.get(tenantId);
            if (token == null){
                log.warn("MonitorAnnualInspectionHandleChain.doProcess auth error");
                return ;
            }
            List<SkuBatchResponse> skuBatchReqList = getSkuList(snapshotList, token);
            //3.发送年检消息提醒
            List<MonitorAnnualInspectionMessageBO> monitorAnnualInspectionMessageBOS = assembleMonitorAnnualInspectionMessage(snapshotList, skuBatchReqList, ojMap);
            if (!CollectionUtils.isEmpty(monitorAnnualInspectionMessageBOS)){
                monitorAnnualInspectionMqProduce.sendAnnualInspection(monitorAnnualInspectionMessageBOS, InventoryTagEnum.MONITOR_ANNUAL_INSPECTION);
            }
        });
        sw.stop();
        log.info("MonitorAnnualInspectionHandleChain.doProcess calculate 耗时：{}", sw.toString());
        return true;
    }

    private List<SkuBatchResponse> getSkuList(List<InventorySnapshotBO> snapshotList, String token){
        if (CollectionUtils.isEmpty(snapshotList)){
            log.info("MonitorAnnualInspectionHandleChain.getSkuList snapshotList is empty.");
            return Lists.newArrayList();
        }
        log.info("MonitorAnnualInspectionHandleChain.getSkuList snapshotList size={}", snapshotList.size());
        //调用产品中心获取年检时间
        List<String> materialCodeList = snapshotList.stream().filter(e -> StringUtils.isNotBlank(e.getMaterialCode()))
                .map(InventorySnapshotBO::getMaterialCode).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(materialCodeList)){
            log.info("MonitorAnnualInspectionHandleChain.getSkuList materialCodeList is empty.");
            return Lists.newArrayList();
        }
        Stopwatch sw = Stopwatch.createStarted();
        InventorySnapshotBO snapshotPO = snapshotList.get(0);
        List<SkuBatchResponse> list = Lists.newArrayList();
        for (String skuCode : materialCodeList) {
            Pager pager = new Pager();
            pager.setSize(50);
            pager.setPage(1);
            SkuBatchPageRequest reqDTO = buildSkuBatchPageRequest(skuCode, pager);
            MutablePair<List<SkuBatchResponse>, Long> mutablePair = getSkuFromProduct(reqDTO, token, snapshotPO);
            if (CollectionUtils.isEmpty(mutablePair.left)){
                log.info("MonitorAnnualInspectionHandleChain.getSkuList mutablePair list is empty. reqDTO={}", JSON.toJSONString(reqDTO));
                continue;
            }
            list.addAll(mutablePair.left);
            long totalPages = mutablePair.right;
            log.info("MonitorAnnualInspectionHandleChain.getSkuList skuCode={}, totalPages={}, size={}", skuCode, totalPages, list.size());
            if (totalPages <= 1){
                continue;
            }
            for (long page = 2; page <= totalPages; page++) {
                pager.setPage((int) page);
                // pager 是引用对象，reqDTO 内部已持有同一 pager 引用，page 更新自动生效
                MutablePair<List<SkuBatchResponse>, Long> pair = getSkuFromProduct(reqDTO, token, snapshotPO);
                if (CollectionUtils.isEmpty(pair.left)){
                    log.info("MonitorAnnualInspectionHandleChain.getSkuList data is empty. reqDTO={}", JSON.toJSONString(reqDTO));
                    break;
                }
                list.addAll(pair.left);
            }
        }
        sw.stop();
        log.info("MonitorAnnualInspectionHandleChain.getSkuList 耗时：{}", sw.toString());
        return list;
    }

    private MutablePair<List<SkuBatchResponse>, Long> getSkuFromProduct(SkuBatchPageRequest reqDTO, String token, InventorySnapshotBO snapshotPO){
        log.info("MonitorAnnualInspectionHandleChain.getSkuFromProduct reqDTO={}", JSON.toJSONString(reqDTO));
        Stopwatch sw = Stopwatch.createStarted();
        java.util.List<SkuBatchResponse> rdfaResult = productCenterRestService.skuBatchListByRequestWithSpecialToken(reqDTO, token, snapshotPO.getUpdatorId(), snapshotPO.getTenantId());
        if (Objects.isNull(rdfaResult) || CollectionUtils.isEmpty(rdfaResult)){
            log.info("MonitorAnnualInspectionHandleChain.getSkuFromProduct failed. reqDTO={}", JSON.toJSONString(reqDTO));
            return MutablePair.of(Lists.newArrayList(), 1L);
        }
        sw.stop();
        log.info("MonitorAnnualInspectionHandleChain.getSkuFromProduct 耗时：{}, reqDTO={}", sw.toString(), JSON.toJSONString(reqDTO));
        return MutablePair.of(rdfaResult, 1L);
    }

    private List<InventorySnapshotBO> getInventorySnapshotBOS(Map<String, List<String>> materialMap, Map<String, List<String>> logicalMap,
                                                              Map<String, BigDecimal> ojMap, Map<String, BigDecimal> lgMap) {
        List<InventorySnapshotBO> snapshotPOS = Lists.newArrayList();
        materialMap.forEach((tenantId, monitorObjects) -> {
            if (CollectionUtils.isEmpty(monitorObjects)){
                return;
            }
            MonitorInventorySnapshotPageBO bo = new MonitorInventorySnapshotPageBO();
            bo.setTenantId(tenantId);
            bo.setMaterialCodeList(monitorObjects);
            List<InventorySnapshotBO> snapshotList = snapshotDomainService.queryByMaterialAndLogical(bo);
            if (CollectionUtils.isEmpty(snapshotList)){
                return;
            }
            snapshotPOS.addAll(snapshotList);
        });
        logicalMap.forEach((tenantId, monitorObjects) -> {
            if (CollectionUtils.isEmpty(monitorObjects)){
                return;
            }
            MonitorInventorySnapshotPageBO bo = new MonitorInventorySnapshotPageBO();
            bo.setTenantId(tenantId);
            bo.setLogicalPlantIdList(monitorObjects.stream().filter(Objects::nonNull).map(Long::parseLong).collect(Collectors.toList()));
            List<InventorySnapshotBO> snapshotList = snapshotDomainService.queryByMaterialAndLogical(bo);
            if (CollectionUtils.isEmpty(snapshotList)){
                return;
            }
            snapshotList.forEach(e -> {
                BigDecimal decimal = lgMap.get(e.getLogicalPlantNo());
                if (null != decimal){
                    ojMap.put(e.getMaterialCode(), decimal);
                }
            });
            snapshotPOS.addAll(snapshotList);
        });
        return snapshotPOS.stream()
                .filter(e -> Objects.nonNull(e) && BigDecimal.ZERO.compareTo(e.getUnrestricted()) < 0)
                .collect(Collectors.toMap(InventorySnapshotBO::getId, Function.identity(), (k1, k2) -> k2))
                .values().stream().collect(Collectors.toList());
    }

    public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private String builderKey(String bachNo, String skuCode){
        return bachNo+"&"+skuCode;
    }

    private List<MonitorAnnualInspectionMessageBO> assembleMonitorAnnualInspectionMessage(List<InventorySnapshotBO> finalList,
                                                                                          List<SkuBatchResponse> skuBatchReqList,
                                                                                          Map<String, BigDecimal> ojMap) {

        List<MonitorAnnualInspectionMessageBO> result = new ArrayList<>();
        //list转成map key为materialCode value为attrList
        Map<String, SkuBatchResponse> skuBatchMap = Maps.newHashMap();
        skuBatchReqList.stream().filter(e -> Objects.nonNull(e) && StringUtils.isNotBlank(e.getBatchCode())).forEach(e -> {
            skuBatchMap.put(builderKey(e.getBatchCode(),e.getSkuCode()), e);
        });

        for (InventorySnapshotBO inventorySnapshotPO : finalList){
            // 根据物料code在map中找是否有对应的值 没有说明产品中心没有配置 跳过
            SkuBatchResponse reqDTO = skuBatchMap.get(builderKey(inventorySnapshotPO.getBatchNo(), inventorySnapshotPO.getMaterialCode()));
            if (null == reqDTO || StringUtils.isBlank(reqDTO.getExt())){
                log.info("MonitorAnnualInspectionHandleChain.assembleMonitorAnnualInspectionMessage reqDTO={}", JSON.toJSONString(reqDTO));
                continue;
            }
            Map<String, String> map = JSONObject.parseObject(reqDTO.getExt(), HashMap.class);
            String annualDate =  map.get(CommonConstant.ANNUAL_DATE);
            BigDecimal decimal = ojMap.get(inventorySnapshotPO.getMaterialCode());
            Date tempDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
            if (null != decimal){
                tempDate = DateUtil.parseDate(DateUtil.formatDate(DateUtil.offsetDay(new Date(), decimal.intValue())));
            }
            if (null == annualDate || DateUtil.compare(DateUtil.parseDate(annualDate), tempDate) > 0){
                log.info("MonitorAnnualInspectionHandleChain.assembleMonitorAnnualInspectionMessage annualDate={},tempDate={},reqDTO={}"
                        , annualDate, tempDate, JSON.toJSONString(reqDTO));
                continue;
            }
            // 封装mq消息
            MonitorAnnualInspectionMessageBO messageBO = MonitorAnnualInspectionMessageBO.builder()
                    .tenantId(inventorySnapshotPO.getTenantId())
                    .batchNo(inventorySnapshotPO.getBatchNo())
                    .materialCode(inventorySnapshotPO.getMaterialCode())
                    .annualDate(annualDate).build();
            result.add(messageBO);
        }
        return result;
    }

    private boolean filterByInventoryMasterData(InventoryMonitorRuleBO ruleBO){
        if (Objects.isNull(ruleBO) || StringUtils.isBlank(ruleBO.getTenantId())){
            return false;
        }
        //使用库存中心的主数据，不需要进行年检预警告警
        ProductMasterDataSourceEnum sourceEnum = inventoryMasterDataSourceDomainService.getProductMasterDataSource(ruleBO.getTenantId());
        if (ProductMasterDataSourceEnum.isUseScmInventoryCenterMData(sourceEnum)){
            log.info("MonitorAnnualInspectionHandleChain.filterByInventoryMasterData 库存中心主数据租户。ruleBO={}", JSON.toJSONString(ruleBO));
            return false;
        }
        return true;
    }


    private SkuBatchPageRequest buildSkuBatchPageRequest(String skuCode, Pager pager) {
        SkuBatchPageRequest req = new SkuBatchPageRequest();
        req.setSkuCode(skuCode);
        req.setPager(pager);
        return req;
    }

}