package com.inventory.middle.client.plan.config.dto;

import com.inventory.middle.client.plan.PageRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 物料计划执行查询请求
 *
 * @author Danny.Lee
 * @date 2021/9/29
 */
@Data
public class MaterialPlanInstanceQueryRequest extends PageRequest {

    private static final long serialVersionUID = 2870072116017468390L;

    /**
     * 计划类型
     */
    private Integer planType;

    /**
     * 计划方案号
     */
    private String planCode;

    /**
     * 计划方案版本号
     */
    private String planVersion;

    /**
     * 计算开始时间查询起始时间
     */
    private Date calStartTimeStart;

    /**
     * 计算开始时间查询结束时间
     */
    private Date calStartTimeEnd;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料编码集合
     */
    private List<String> materialCodes;

    /**
     * 逻辑仓编码集合
     */
    private List<String> logicalPlantNos;

}
