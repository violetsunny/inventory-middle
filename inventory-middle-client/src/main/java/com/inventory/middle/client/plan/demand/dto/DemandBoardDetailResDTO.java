package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.dto.ExternalMaterialSupport;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/9/29 9:54
 */

@Data
public class DemandBoardDetailResDTO implements ExternalMaterialSupport, Serializable {

    private static final long serialVersionUID = 8124039835103823702L;

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
     * 需求列表
     */
    private List<SingleDemandResultDTO> demandList;

    @Override
    public String getTenantId() {
        return null;
    }
}
