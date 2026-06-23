package com.inventory.middle.application.plan.config.rule.validator;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamBatchCreateDetailBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamBatchCreateReqBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.config.convertor.PlanConfigConverter;
import com.inventory.middle.client.plan.dto.product.ProductBO;
import com.inventory.middle.application.plan.support.ProductSupportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量新增物料计划参数 校验物料是否存在 校验器
 * @date 2021/10/8 16:00
 */
@Slf4j
@Component
public class BatchCreatePlanMaterialParamExistValidator implements IValidator {

    @Resource
    private ProductSupportService productSupportService;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        //校验入参
        PlanMaterialParamBatchCreateReqBO reqBO = (PlanMaterialParamBatchCreateReqBO) message.getT();
        log.info("BatchCreatePlanMaterialParamExistValidator: " + JSON.toJSONString(reqBO));
        // 校验结果
        List<PlanMaterialParamBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialParamList())) {
            return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
        }
        // 查询物料编码中存在产品中心的物料
        Map<String, ProductBO> skuResponseMap = this.querySkuResponseByPlanMaterialParamList(reqBO.getPlanMaterialParamList(), reqBO.getTenantId());
        // 2.3 对比发现如果在产品中心不存在 则加入失败列表
        Iterator<PlanMaterialParameterBO> iterator = reqBO.getPlanMaterialParamList().iterator();
        // 转换为了获取到物料名称
        List<PlanMaterialParameterBO> boListTemp = new ArrayList<>();
        while (iterator.hasNext()) {
            // 检查物料编码是否存在产品中心
            PlanMaterialParameterBO planMaterialParameterBO = iterator.next();
            if (!skuResponseMap.containsKey(planMaterialParameterBO.getMaterialCode())) {
                PlanMaterialParamBatchCreateDetailBO failedBO = new PlanMaterialParamBatchCreateDetailBO();
                failedBO.setMaterialCode(planMaterialParameterBO.getMaterialCode());
                failedBO.setLogicalPlantNo(planMaterialParameterBO.getLogicalPlantNo());
                failedBO.setCreateMessage(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_MATERIAL_CODE_IS_NOT_EXIST.getDesc());
                failedBO.setIndex(planMaterialParameterBO.getIndex());
                failedDetails.add(failedBO);
                iterator.remove();
            } else {
                PlanMaterialParameterBO boTemp = PlanConfigConverter.convertPlanMaterialParameterBO2BO(planMaterialParameterBO, null, skuResponseMap);
                boListTemp.add(boTemp);
            }
            reqBO.setPlanMaterialParamList(boListTemp);
        }
        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
    }

    private Map<String, ProductBO> querySkuResponseByPlanMaterialParamList(List<PlanMaterialParameterBO> planMaterialParamList, String tenant) {
        Map<String, ProductBO> productMap = new HashMap<>(planMaterialParamList.size());
        List<String> materialCodes = planMaterialParamList.stream().map(PlanMaterialParameterBO::getMaterialCode).collect(Collectors.toList());
        List<ProductBO> products = productSupportService.listProducts(materialCodes, tenant);
        if (!CollectionUtils.isEmpty(products)) {
            productMap = products.stream().collect(Collectors.toMap(ProductBO::getCode, a -> a,(k1,k2)->k2));
        }
        return productMap;
    }
}