package com.inventory.middle.application.plan.config.rule.validator;

import com.inventory.middle.domain.plan.common.enums.PlanCoverMaterialTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanStatusEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateDetailBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateReqBO;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.persistence.dao.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划物料清单 校验计划方案
 * @date 2021/10/2 21:50
 */
@Component
public class BatchCreatePlanMaterialPlanValidator implements IValidator {

    @Resource
    private PlanConfigDao planConfigDao;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        //校验入参
        PlanMaterialBatchCreateReqBO reqBO = (PlanMaterialBatchCreateReqBO) message.getT();
        // 校验结果
        List<PlanMaterialBatchCreateDetailBO> failedDetails = new ArrayList<>();
        // 查询计划方案
        PlanPO plan = planConfigDao.queryPlanById(reqBO.getPlanId());
        // 计划方案不存在
        if (Objects.isNull(plan)) {
            throw Ex.of(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_PLAN_IS_NOT_EXIST);
        }
        // 计划方案处于开启状态
        if (plan.getStatus().equals(PlanStatusEnum.ON.getCode())) {
            throw Ex.of(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_PLAN_STATUS_IS_ON);
        }
        // 覆盖物料类型不是指定物料 不让上传
        if (plan.getCoverMaterialType().equals(PlanCoverMaterialTypeEnum.ALL.getCode())) {
            throw Ex.of(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_MATERIAL_TYPE_ERROR);
        }

        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
    }
}