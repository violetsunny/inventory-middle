package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.dto.ExternalMaterialSupport;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/9 15:42
 * @description 物料信息以及需求数量
 */
@Data
public class DemandPlanDetailResultDTO implements ExternalMaterialSupport, Serializable {

    private static final long serialVersionUID = -880898144457319061L;

    /**
     * 需求计划物料表id
     */
    private Long planMaterialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * erp物料编码
     */
    private String externalMaterialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 单位
     */
    private String materialUnit;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 物料需求列表
     */
    private List<PeriodDemandResultDTO> demandList;

    @Override
    public String getTenantId() {
        return null;
    }
}
