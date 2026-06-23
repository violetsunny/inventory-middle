package com.inventory.middle.application.plan.calculate.bo;

import com.inventory.middle.domain.plan.common.bo.BaseBo;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.calculate.support.generate.PlanGeneType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 计划产出业务对象
 *
 * @author Danny.Lee
 * @date 2021/10/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlanGenerateRequestBO extends BaseBo {

    private static final long serialVersionUID = -845078714768010942L;
    /**
     * 计划id
     */
    private Long planId;

    /**
     * 产出类型
     */
    private PlanGeneType generateType = PlanGeneType.MANUAL;

    /**
     * 覆盖物料范围<br/>
     * 计划方案计算优先使用覆盖范围的物料数据,若不存在,则使用计划方案的覆盖的物料计算范围
     */
    private List<MaterialBO> coverMaterials;

    @Override
    public String toLog() {
        return this.toString();
    }
}
