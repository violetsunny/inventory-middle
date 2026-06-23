package com.inventory.middle.client.dto.inventorysnapshot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 现有期末库存查询响应
 * @author vincent.li
 * @version 1.0
 * @date 2021-09-27
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentInventoryBalanceResp implements Serializable {

    /**
     * 良品库存
     */
    private BigDecimal unrestricted;

    /**
     * 次品库存
     */
    private BigDecimal damaged;

}
