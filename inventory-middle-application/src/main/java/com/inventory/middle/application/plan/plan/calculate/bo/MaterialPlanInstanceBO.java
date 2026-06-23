package com.inventory.middle.application.plan.plan.calculate.bo;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * 物料计划实例
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanInstanceBO extends BaseBo {

    private static final long serialVersionUID = -8145147713993880595L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 实例id
     */
    private Long instanceId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 物料层级
     */
    private String materialLevel;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 需求响应策略编码
     */
    private String demandStrategyCode;

    /**
     * 根节点id
     */
    private Long rootId;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 物料计划实例状态
     */
    private Integer status;

    /**
     * 产出类型
     */
    private Integer geneType;

    /**
     * 物料计划初始参数
     */
    private JSONObject extAttrs;

    /**
     * 是否删除（0-未删除/1-已删除，默认0）
     */
    private Integer isDelete;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 创建人（0-系统）
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    private String creatorId;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料计划详情
     */
    private Map<LocalDate, MaterialPlanDetailBO> materialPlanDetails;

    public MaterialPlanInstanceBO copyBasic() {
        MaterialPlanInstanceBO copyThat = new MaterialPlanInstanceBO();
        //ignore id
        copyThat.setStatus(this.getStatus());
        copyThat.setTenantId(this.getTenantId());
        copyThat.setInstanceId(this.getInstanceId());
        copyThat.setMaterialCode(this.getMaterialCode());
        copyThat.setMaterialDesc(this.getMaterialDesc());
        copyThat.setMaterialLevel(this.getMaterialLevel());
        copyThat.setLogicalPlantNo(this.getLogicalPlantNo());
        copyThat.setLogicalPlantName(this.getLogicalPlantNo());
        copyThat.setDemandStrategyCode(this.getDemandStrategyCode());

        copyThat.setRootId(this.getRootId());
        copyThat.setParentId(this.getParentId());

        copyThat.setCreateTime(this.getCreateTime());
        copyThat.setCreatorId(this.getCreatorId());
        copyThat.setCreateUserName(this.getCreateUserName());

        return copyThat;
    }

    @Override
    public String toLog() {
        return this.toString();
    }
}
