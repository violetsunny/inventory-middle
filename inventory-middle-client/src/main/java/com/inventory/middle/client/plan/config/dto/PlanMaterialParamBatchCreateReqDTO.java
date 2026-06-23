package com.inventory.middle.client.plan.config.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/9/27 14:59
 */
@Data
public class PlanMaterialParamBatchCreateReqDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -2364564581801756328L;
    /**
     * 批量新增集合
     */
    private List<PlanMaterialParameterDTO> planMaterialParamList;
}
