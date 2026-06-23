package com.inventory.middle.client.dto.inventorysupply;

import com.inventory.middle.client.dto.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 远期计划入supply查询请求
 *
 * @author vincent.li
 * @date 2021/9/28
 */
@Data
public class PlanInventorySupplyQry extends PageRequest {

    /**
     * 物料编码
     */
    @NotEmpty(message = "[物料编码]不能为空")
    private String materialCode;

    /**
     * 逻辑仓库编码
     */
    @NotEmpty(message = "[逻辑仓库编码]不能为空")
    private String logicalPlantNo;
    /**
     * 租户ID
     */
    @Size(max = 40, message = "租户id过长，最大40")
    @NotEmpty(message = "[租户ID]不能为空")
    private String tenantId;

    /**
     * 开始时间
     */
    @NotNull(message = "[开始时间]不能为空")
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "[结束时间]不能为空")
    private Date endTime;
}
