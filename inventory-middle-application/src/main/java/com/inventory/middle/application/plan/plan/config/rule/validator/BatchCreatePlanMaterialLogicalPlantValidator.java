package com.inventory.middle.application.plan.plan.config.rule.validator;

import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialBatchCreateDetailBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialBatchCreateReqBO;
import com.inventory.middle.application.plan.plan.convertor.PlanConfigConverter;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.persistence.dao.plan.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案批量上传物料清单 逻辑仓校验
 * @date 2021/11/5 15:12
 */
@Component
public class BatchCreatePlanMaterialLogicalPlantValidator implements IValidator {

    @Resource
    private PlanConfigDao planConfigDao;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        //校验入参
        PlanMaterialBatchCreateReqBO reqBO = (PlanMaterialBatchCreateReqBO) message.getT();
        // 校验结果
        List<PlanMaterialBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialList())) {
            return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
        }
        // 校验上传的物料清单是否在当前计划覆盖的逻辑仓范围内 优化下 已经查过了
        PlanPO plan = planConfigDao.queryPlanById(reqBO.getPlanId());

        List<String> logicalPlantNos = Arrays.asList(plan.getCoverLogicalPlant().split(CommonConstants.DELIMITER_COMMA));
        Iterator<PlanMaterialBO> iterator = reqBO.getPlanMaterialList().iterator();
        while (iterator.hasNext()) {
            PlanMaterialBO planMaterialBO = iterator.next();
            if (!logicalPlantNos.contains(planMaterialBO.getLogicalPlantNo())){
                String errorMsg = ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_ERROR_LOGICAL_PLANT.getDesc();
                PlanMaterialBatchCreateDetailBO failedBO = PlanConfigConverter.convertPlanMaterialBO2CreateDetail(planMaterialBO, errorMsg);
                failedDetails.add(failedBO);
                iterator.remove();
            }
        }
        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
    }
}