package com.inventory.middle.client.plan.dto.inventory;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 库存查询请求
 *
 * @author Danny.Lee
 * @date 2021/9/30
 */
@Data
public class InventoryQueryRequest {

    /** 物料编码 */
    private String materialCode;

    /** 逻辑仓编码 */
    private String logicalPlantNo;

    /** 查询开始时间(左右闭合) */
    private LocalDateTime startTime;

    /** 查询结束时间(左右闭合) */
    private LocalDateTime endTime;

    /** 租户id */
    private String tenantId;
}
