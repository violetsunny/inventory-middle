package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/13 16:05
 */
@Data
public class DemandPlanVersionSelectResDTO implements Serializable {

    private static final long serialVersionUID = 5961722574134544897L;

    /**
     * 需求版本列表
     */
    private List<DemandPlanVersionDTO> demandPlanVersionList;
}
