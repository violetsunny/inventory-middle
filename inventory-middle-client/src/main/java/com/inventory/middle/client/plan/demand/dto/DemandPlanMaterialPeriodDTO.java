package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author:xinao
 * @date:2021/9/28 17:24
 */
@Data
public class DemandPlanMaterialPeriodDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -6793813136446341594L;


    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 具体的分配信息
     */
    private List<DemandPlanPeriodInfoDTO> planPeriodInfoList;




}
