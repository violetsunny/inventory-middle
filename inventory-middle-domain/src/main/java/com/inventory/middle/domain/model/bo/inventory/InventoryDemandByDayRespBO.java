package com.inventory.middle.domain.model.bo.inventory;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 按天统计查询计划出库查询结果
 *
 * @author vincent.li
 * @date 2021/9/29
 */
@Data
public class InventoryDemandByDayRespBO implements Serializable {

    /**
     * 需求过期日期
     */
    private Date deadlineDate;

    /**
     * 良品数量
     */
    private BigDecimal unrestricted;
}
