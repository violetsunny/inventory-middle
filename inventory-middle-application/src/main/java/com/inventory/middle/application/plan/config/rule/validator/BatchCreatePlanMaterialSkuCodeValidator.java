package com.inventory.middle.application.plan.config.rule.validator;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateDetailBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateReqBO;
import com.inventory.middle.client.plan.dto.product.ProductBO;
import com.inventory.middle.application.plan.support.ProductSupportService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


import javax.annotation.Resource;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划物料清单 校验物料存在性 考虑去掉
 * @date 2021/10/2 17:38
 */
@Component
public class BatchCreatePlanMaterialSkuCodeValidator implements IValidator {

    @Resource
    private ProductSupportService productSupportService;


    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        //校验入参
        PlanMaterialBatchCreateReqBO reqBO = (PlanMaterialBatchCreateReqBO) message.getT();
        // 校验结果
        List<PlanMaterialBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialList())) {
            return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
        }
        // 查询产品中心存在的物料code
        List<String> codes = new ArrayList<>();
        List<String> materialCodes = reqBO.getPlanMaterialList().stream().map(PlanMaterialBO::getMaterialCode).collect(Collectors.toList());
        List<ProductBO> products = productSupportService.listProducts(materialCodes,reqBO.getTenantId());
        if (!CollectionUtils.isEmpty(products)) {
            codes = products.stream().filter(e -> Objects.nonNull(e) && StringUtils.isNotBlank(e.getCode()))
                    .map(ProductBO::getCode).distinct().collect(Collectors.toList());
        }
        // 2.3 对比发现如果在产品中心不存在 则加入失败列表
        Iterator<PlanMaterialBO> iterator = reqBO.getPlanMaterialList().iterator();
        while (iterator.hasNext()) {
            PlanMaterialBO planMaterialBO = iterator.next();
            if (!codes.contains(planMaterialBO.getMaterialCode())) {
                PlanMaterialBatchCreateDetailBO failedBO = new PlanMaterialBatchCreateDetailBO();
                failedBO.setMaterialCode(planMaterialBO.getMaterialCode());
                failedBO.setLogicalPlantNo(planMaterialBO.getLogicalPlantNo());
                failedBO.setCreateMessage(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_MATERIAL_CODE_IS_NOT_EXIST.getDesc());
                failedBO.setIndex(planMaterialBO.getIndex());
                failedDetails.add(failedBO);
                iterator.remove();
            }
        }
        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
    }
}