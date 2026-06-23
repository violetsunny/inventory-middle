package com.inventory.middle.infra.plan.persistence.entity;

import java.util.Date;

/**
 * 计划物料清单表
 */
public class PlanMaterialPO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 计划方案ID
     */
    private Long sourceId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 计划物料的使用场景标识
     */
    private Integer type;

    /**
     * 是否已删除（0:未删除，1:已删除，默认为0）
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
     * 租户Id
     */
    private String tenantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getLogicalPlantNo() {
        return logicalPlantNo;
    }

    public void setLogicalPlantNo(String logicalPlantNo) {
        this.logicalPlantNo = logicalPlantNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDeleted() {
        return isDelete;
    }

    public void setDeleted(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {
        return createUserId;
    }

    public void setCreatorId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatorId() {
        return updateUserId;
    }

    public void setUpdatorId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
