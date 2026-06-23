package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/30 10:34
 */
@Data
public class CancelDemandPlanMaterialReqDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -2631658051314221085L;

    /**
     * 需求计划物料表id
     */
    private Long demandPlanMaterialId;


}
