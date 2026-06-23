package com.inventory.middle.domain.service.validator;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.model.bo.log.InventoryLogBO;
import com.inventory.middle.domain.model.bo.log.InventoryLogQueryBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/** 库存日志校验器（迁移自 InventoryLogValidator） */
@Slf4j
@Component
public class InventoryLogValidator {
    public void validateLogBos(List<InventoryLogBO> logBOs) {
        BaseAssert.notEmpty(logBOs, ResponseCodeEnum.PARAM_IS_NULL.getCode(), "logBOs不能为空");
    }
    public <T extends InventoryLogQueryBO> void validateLogQueryBo(T queryBo) {
        BaseAssert.notNull(queryBo, ResponseCodeEnum.PARAM_IS_NULL.getCode(), "queryBO不能为空");
    }
}
