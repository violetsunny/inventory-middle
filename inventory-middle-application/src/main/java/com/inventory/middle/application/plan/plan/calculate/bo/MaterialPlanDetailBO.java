package com.inventory.middle.application.plan.plan.calculate.bo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * 计划执行物料实例
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanDetailBO extends BaseBo {

    private static final long serialVersionUID = -7903726655544634716L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 计划实例详情id
     */
    private Long materialInstanceId;

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
     * 计划日期
     */
    private Date planDate;

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

    private String creatorId;

    private int deleted;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 指标数据
     */
    private Map<String, BigDecimal> indicators;

    /**
     * 指标过程数据
     */
    private Map<String, JSONObject> indicatorExtAttrs;

    /**
     * 业务扩展数据
     */
    private Map<String, String> businessExtAttrs;

    public void appendIndicator(String name, BigDecimal value) {
        if (null == indicators) {
            indicators = Maps.newHashMap();
        }
        indicators.put(name, value);
    }

    public MaterialPlanDetailBO mergeIndicators(Map<String, BigDecimal> indicators) {
        if (null == this.indicators) {
            this.indicators = Maps.newHashMap();
        }
        for (Map.Entry<String, BigDecimal> indicator : indicators.entrySet()) {
            if(null != indicator.getValue()){
                this.indicators.merge(indicator.getKey(), indicator.getValue(), BigDecimal::add);
            }
        }
        return this;
    }

    public void appendIndicatorExtAttrs(String name, JSONObject extAttrs) {
        if (null == indicatorExtAttrs) {
            indicatorExtAttrs = Maps.newHashMap();
        }
        indicatorExtAttrs.put(name, extAttrs);
    }

    public void addBusinessExt(String name, String value) {
        if (null == businessExtAttrs) {
            businessExtAttrs = Maps.newHashMap();
        }
        businessExtAttrs.put(name, value);
    }

    public String getBusinessExt(String name) {
        return Optional.ofNullable(businessExtAttrs)
                .map(extAttrs -> extAttrs.get(name))
                .orElse(null);
    }

    @Override
    public String toLog() {
        return this.toString();
    }

    public MaterialPlanDetailBO copyBasic() {
        MaterialPlanDetailBO copyThat = new MaterialPlanDetailBO();
        // ignore id
        copyThat.setTenantId(this.getTenantId());
        copyThat.setPlanDate(this.getPlanDate());
        copyThat.setMaterialCode(this.getMaterialCode());
        copyThat.setMaterialDesc(this.getMaterialDesc());
        copyThat.setLogicalPlantNo(this.getLogicalPlantNo());
        copyThat.setMaterialInstanceId(this.getMaterialInstanceId());

        copyThat.setCreateTime(this.getCreateTime());
        copyThat.setCreatorId(this.getCreatorId());
        return copyThat;
    }
}
