package com.inventory.middle.client.dto.inventorysupply;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 入库逾期数据
 *
 * @author vincent.li
 * @date 2021/9/30
 */
@Data
public class SupplyOverdueDTO implements Serializable {
    /**
     * 库存过期日期
     */
    private Date dueDate;

    /**
     * 良品数量
     */
    private BigDecimal unrestricted;
}
