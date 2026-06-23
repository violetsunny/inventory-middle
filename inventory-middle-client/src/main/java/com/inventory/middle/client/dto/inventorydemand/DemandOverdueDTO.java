package com.inventory.middle.client.dto.inventorydemand;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库逾期基础数据
 *
 * @author vincent.li
 * @date 2021/9/30
 */
@Data
public class DemandOverdueDTO implements Serializable {
    /**
     * 出库过期日期
     */
    private Date deadlineDate;

    /**
     * 良品数量
     */
    private BigDecimal unrestricted;
}
