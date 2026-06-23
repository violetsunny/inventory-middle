package com.inventory.middle.client.dto.transit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.*;

import com.inventory.middle.client.dto.PageRequest;

import com.inventory.middle.client.enums.MaterialDocRefOrderTypeEnum;
import lombok.Data;

/**
 * @description 在途库存查询
 * @author dongguo.tao
 * @date 2021-09-27 18:42:29
 */

@Data
public class InTransitStockPageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 590524681311623052L;
    /**
     * 物料
     */
    private String materialCode;
    /**
     * 租户ID
     */
    @NotEmpty(message = "租户ID不能为空")
    private String tenantId;
    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 存储地点id
     */
    private Long locationId;

    /**
     * 来源单据类型
     * @see MaterialDocRefOrderTypeEnum
     */
    private Integer sourceOrderType;

    /**
     * 来源单据编号
     */
    private String sourceOrderNo;

    /**
     * 交运单号
     */
    private String deliveryNo;

    /**
     * 最早可用库存开始时间（包含）
     * 格式 yyyy-MM-dd HH:mm:ss
     */
    private Date etaStartTime;

    /**
     * 最早可用库存结束时间（不包含）
     * 格式 yyyy-MM-dd HH:mm:ss
     */
    private Date etaEndTime;

}
