package com.inventory.middle.application.plan.demand.validator;

import com.inventory.middle.application.plan.demand.bo.DemandPlanUpdateStatusReqBO;
import com.inventory.middle.domain.plan.common.enums.DemandPlanStatusEnum;
import com.inventory.middle.domain.plan.common.enums.DemandStatusEnum;
import com.inventory.middle.domain.plan.common.enums.IsDeleteEnum;
import com.inventory.middle.domain.plan.common.enums.ValidatorResultEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.persistence.dao.DemandPlanDao;
import com.inventory.middle.infra.plan.persistence.dao.DemandPlanMaterialDao;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 开启时，校验物料当前日期是否重合
 * <p>
 * 迁移自 com.enn.plan.management.core.demand.rule.validator.PlanStatusMaterialValidator
 * </p>
 */
@Component
@Order(2)
public class PlanStatusMaterialValidator implements IValidator {

    @Autowired
    private DemandPlanDao planDao;

    @Autowired
    private DemandPlanMaterialDao materialDao;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        DemandPlanUpdateStatusReqBO req = (DemandPlanUpdateStatusReqBO) message.getT();
        if (Objects.equals(req.getStatus(), DemandPlanStatusEnum.OFF.getCode())) {
            return ValidateMessage.builder().t(req).success(true).build();
        } else {
            DemandPlanPO planPO = planDao.selectById(req.getDemandPlanId());
            if (Objects.nonNull(planPO)) {
                DemandPlanMaterialPO condition = new DemandPlanMaterialPO();
                condition.setStatus(DemandStatusEnum.OFF.getCode());
                condition.setDemandPlanId(planPO.getId());
                condition.setDeleted(IsDeleteEnum.NO.getValue());
                condition.setTenantId(planPO.getTenantId());
                List<DemandPlanMaterialPO> materialPOList = materialDao.selectByCondition(condition);
                List<String> materialList = materialPOList.stream()
                        .map(DemandPlanMaterialPO::getMaterialCode)
                        .collect(Collectors.toList());
                if (CollectionUtils.isEmpty(materialList)) {
                    return ValidateMessage.builder()
                            .t(req)
                            .e(null)
                            .code(ValidatorResultEnum.DEMAND_PLAN_NO_MATERIAL.getCode())
                            .message(ValidatorResultEnum.DEMAND_PLAN_NO_MATERIAL.getDesc())
                            .build();
                }
                boolean result = planDao.checkDuplicate(req.getTenantId(), planPO.getDemandType(),
                        planPO.getLogicalPlantNo(), materialList);
                if (result) {
                    return ValidateMessage.builder()
                            .t(req)
                            .e(null)
                            .code(ValidatorResultEnum.DEMAND_PLAN_MATERIAL_DUPLICATE.getCode())
                            .message(ValidatorResultEnum.DEMAND_PLAN_MATERIAL_DUPLICATE.getDesc())
                            .build();
                }
            }
            return ValidateMessage.builder().t(req).success(true).build();
        }
    }
}
