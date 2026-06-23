package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/30 14:53
 */

@Data
public class ModifyDemandPlanMaterialAmountReqDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 5511968405492406761L;


    /**
     * 需求计划物料表id
     */
    private Long demandPlanMaterialId;


    /**
     * 数量
     */
    private List<DemandPlanMaterialAmountDTO> amountList;
}
