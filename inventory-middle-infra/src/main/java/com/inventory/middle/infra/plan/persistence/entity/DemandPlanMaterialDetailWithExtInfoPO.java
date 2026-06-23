package com.inventory.middle.infra.plan.persistence.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouxinzhong
 * @date 2022/05/13 10:57
 */
@Getter
@Setter
public class DemandPlanMaterialDetailWithExtInfoPO extends DemandPlanMaterialDetailPO {

    /**
     * 拓展信息
     */
    private String extInfo;
}
