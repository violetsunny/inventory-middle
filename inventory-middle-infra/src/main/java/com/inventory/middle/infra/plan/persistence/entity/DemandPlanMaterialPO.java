package com.inventory.middle.infra.plan.persistence.entity;

import java.util.Date;

/**
 * pl_demand_plan_material
 *
 * @date 2021-09-28
 */
public class DemandPlanMaterialPO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 需求计划主键,关联pl_demand_plan表
     */
    private Long demandPlanId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
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
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 状态（0-已失效/1-已生效/2-已剔除，默认为1）
     */
    private Integer status;

    /**
     * 是否已删除（0-未删除/1-已删除，默认为0）
     */
    private Integer isDelete;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 更新时间（默认当前时间）
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUserId;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 主键
     * 
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * 
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 需求计划主键,关联pl_demand_plan表
     * 
     * @return demand_plan_id 需求计划主键,关联pl_demand_plan表
     */
    public Long getDemandPlanId() {
        return demandPlanId;
    }

    /**
     * 需求计划主键,关联pl_demand_plan表
     * 
     * @param demandPlanId 需求计划主键,关联pl_demand_plan表
     */
    public void setDemandPlanId(Long demandPlanId) {
        this.demandPlanId = demandPlanId;
    }

    /**
     * 物料编码
     * 
     * @return material_code 物料编码
     */
    public String getMaterialCode() {
        return materialCode;
    }

    /**
     * 物料编码
     * 
     * @param materialCode 物料编码
     */
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    /**
     * 物料名称
     * 
     * @return material_desc 物料名称
     */
    public String getMaterialDesc() {
        return materialDesc;
    }

    /**
     * 物料名称
     * 
     * @param materialDesc 物料名称
     */
    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    /**
     * 单位
     * 
     * @return material_unit 单位
     */
    public String getMaterialUnit() {
        return materialUnit;
    }

    /**
     * 单位
     * 
     * @param materialUnit 单位
     */
    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    /**
     * 逻辑仓编码
     * 
     * @return logical_plant_no 逻辑仓编码
     */
    public String getLogicalPlantNo() {
        return logicalPlantNo;
    }

    /**
     * 逻辑仓编码
     * 
     * @param logicalPlantNo 逻辑仓编码
     */
    public void setLogicalPlantNo(String logicalPlantNo) {
        this.logicalPlantNo = logicalPlantNo;
    }

    /**
     * 逻辑仓名称
     * 
     * @return logical_plant_name 逻辑仓名称
     */
    public String getLogicalPlantName() {
        return logicalPlantName;
    }

    /**
     * 逻辑仓名称
     * 
     * @param logicalPlantName 逻辑仓名称
     */
    public void setLogicalPlantName(String logicalPlantName) {
        this.logicalPlantName = logicalPlantName;
    }

    /**
     * 状态（0-已失效/1-已生效/2-已剔除，默认为1）
     *
     * @return status 状态（0-已失效/1-已生效/2-已剔除，默认为1）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态（0-已失效/1-已生效/2-已剔除，默认为1）
     *
     * @param status 状态（0-已失效/1-已生效/2-已剔除，默认为1）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 是否已删除（0-未删除/1-已删除，默认为0）
     * 
     * @return is_delete 是否已删除（0-未删除/1-已删除，默认为0）
     */
    public Integer getDeleted() {
        return isDelete;
    }

    /**
     * 是否已删除（0-未删除/1-已删除，默认为0）
     * 
     * @param isDelete 是否已删除（0-未删除/1-已删除，默认为0）
     */
    public void setDeleted(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 创建时间（默认当前时间）
     * 
     * @return create_time 创建时间（默认当前时间）
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间（默认当前时间）
     * 
     * @param createTime 创建时间（默认当前时间）
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人
     * 
     * @return create_user_id 创建人
     */
    public String getCreatorId() {
        return createUserId;
    }

    /**
     * 创建人
     * 
     * @param createUserId 创建人
     */
    public void setCreatorId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 更新时间（默认当前时间）
     * 
     * @return update_time 更新时间（默认当前时间）
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间（默认当前时间）
     * 
     * @param updateTime 更新时间（默认当前时间）
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 更新人
     * 
     * @return update_user_id 更新人
     */
    public String getUpdatorId() {
        return updateUserId;
    }

    /**
     * 更新人
     * 
     * @param updateUserId 更新人
     */
    public void setUpdatorId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 租户id
     * 
     * @return tenant_id 租户id
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户id
     * 
     * @param tenantId 租户id
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
