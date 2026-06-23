package com.inventory.middle.application.plan.calculate.bo;

import com.inventory.middle.domain.plan.common.enums.InstanceStatusEnum;
import com.inventory.middle.domain.plan.common.ex.ErrorCode;
import com.inventory.middle.domain.plan.common.ex.PlanGeneError;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.google.common.collect.Lists;
import lombok.Data;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

import java.util.List;
import java.util.Optional;

/**
 * Created on 2021/12/20.
 *
 * @author Danny.Lee
 */
@Data
public class MaterialPlanInstanceBomNodeBO extends BaseBo {

    private static final long serialVersionUID = -958743427699635623L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 状态信息
     */
    private Integer status;

    /**
     * @see PlanGeneError#errCode()
     */
    private String errCode;

    /**
     * @see PlanGeneError#userTip()
     */
    private String errMessage;

    /**
     * 下层节点
     */
    private List<MaterialPlanInstanceBomNodeBO> children;

    public void addChildNode(MaterialPlanInstanceBomNodeBO child) {
        if (null == child) {
            return;
        }
        if (null == children) {
            children = Lists.newArrayList();
        }
        children.add(child);
    }

    public static MaterialPlanInstanceBomNodeBO success(MaterialBO material, String materialDesc, Long id) {
        MaterialPlanInstanceBomNodeBO bomNode = new MaterialPlanInstanceBomNodeBO();
        bomNode.setId(id);
        bomNode.setMaterialDesc(materialDesc);
        bomNode.setTenantId(material.getTenantId());
        bomNode.setMaterialCode(material.getMaterialCode());
        bomNode.setLogicalPlantNo(material.getLogicalPlantNo());
        bomNode.setStatus(InstanceStatusEnum.SUCCESS.getCode());
        return bomNode;
    }

    public static MaterialPlanInstanceBomNodeBO fail(MaterialBO material, String materialDesc, PlanGeneError error) {
        MaterialPlanInstanceBomNodeBO bomNode = new MaterialPlanInstanceBomNodeBO();
        bomNode.setMaterialDesc(materialDesc);
        bomNode.setTenantId(material.getTenantId());
        bomNode.setMaterialCode(material.getMaterialCode());
        bomNode.setLogicalPlantNo(material.getLogicalPlantNo());
        bomNode.setStatus(InstanceStatusEnum.FAIL.getCode());
        bomNode.setErrCode(error.errCode());
        // 优先使用用户提示
        bomNode.setErrMessage(Optional.ofNullable(error.userTip()).orElse(error.errMessage()));
        return bomNode;
    }

    public static MaterialPlanInstanceBomNodeBO of(MaterialPlanInstanceBO materialPlan, String desc) {
        MaterialPlanInstanceBomNodeBO bomNode = new MaterialPlanInstanceBomNodeBO();
        bomNode.setTenantId(materialPlan.getTenantId());
        bomNode.setMaterialCode(materialPlan.getMaterialCode());
        bomNode.setMaterialDesc(desc);
        bomNode.setLogicalPlantNo(materialPlan.getLogicalPlantNo());
        if (InstanceStatusEnum.isFail(materialPlan.getStatus())) {
            final ErrorCode error = PlanGeneError.CALCULATE_ERROR;
            bomNode.setStatus(InstanceStatusEnum.FAIL.getCode());
            bomNode.setErrCode(error.errCode());
            // 优先使用用户提示
            bomNode.setErrMessage(Optional.ofNullable(error.userTip()).orElse(error.errMessage()));
        } else {
            bomNode.setId(materialPlan.getId());
            bomNode.setStatus(InstanceStatusEnum.SUCCESS.getCode());
        }
        return bomNode;
    }

    @Override
    public String toLog() {
        return toString();
    }
}
