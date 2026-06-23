package com.inventory.middle.client.dto.inventorysupply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 逾期接收库存按天统计查询响应
 *
 * @author vincent.li
 * @date 2021/9/28
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanInventorySupplyOverdueResp implements Serializable {

    /**
     * 入库逾期数据
     */
    List<SupplyOverdueDTO> supplyOverdues;
}
