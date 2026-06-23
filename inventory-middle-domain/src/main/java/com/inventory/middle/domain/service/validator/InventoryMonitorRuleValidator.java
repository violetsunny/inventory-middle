package com.inventory.middle.domain.service.validator;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 库存监控规则校验器
 * 迁移自: com.enn.inventory.center.biz.service.validator.InventoryMonitorRuleValidator
 */
@Slf4j
@Component
public class InventoryMonitorRuleValidator {

    /**
     * 校验创建请求
     */
    public void validateMonitorRuleCreateRequest(Object createRequest) {
        BaseAssert.notNull(createRequest, ResponseCodeEnum.PARAM_IS_NULL.getCode(), "预警规则创建请求不能为空");
        if (createRequest instanceof InventoryMonitorRuleBO) {
            validateRuleBO((InventoryMonitorRuleBO) createRequest);
        }
    }

    /**
     * 校验更新请求
     */
    public void validateMonitorUpdate(InventoryMonitorRuleBO ruleBO) {
        BaseAssert.notNull(ruleBO, ResponseCodeEnum.PARAM_IS_NULL.getCode(), "预警规则不能为空");
        BaseAssert.notNull(ruleBO.getId(), ResponseCodeEnum.PARAM_IS_NULL.getCode(), "预警规则ID不能为空");
        validateRuleBO(ruleBO);
    }

    private void validateRuleBO(InventoryMonitorRuleBO ruleBO) {
        BaseAssert.isTrue(!StringUtils.isBlank(ruleBO.getTenantId()), ResponseCodeEnum.PARAM_IS_NULL.getCode(), "租户ID不能为空");
        BaseAssert.isTrue(!StringUtils.isBlank(ruleBO.getMonitorType()), ResponseCodeEnum.PARAM_IS_NULL.getCode(), "监控类型不能为空");
        BaseAssert.isTrue(!StringUtils.isBlank(ruleBO.getMonitorDimension()), ResponseCodeEnum.PARAM_IS_NULL.getCode(), "监控维度不能为空");
        BaseAssert.isTrue(ruleBO.getMonitorInterval() != null && ruleBO.getMonitorInterval() > 0,
                ResponseCodeEnum.PARAM_IS_NULL.getCode(), "监控周期必须大于0");
    }
}
