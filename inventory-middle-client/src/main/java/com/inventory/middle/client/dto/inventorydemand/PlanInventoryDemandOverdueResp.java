package com.inventory.middle.client.dto.inventorydemand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 逾期出库按天统计查询响应
 *
 * @author vincent.li
 * @date 2021/9/28
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanInventoryDemandOverdueResp implements Serializable {

    /**
     * 逾期出库数据
     */
    List<DemandOverdueDTO> demandOverdues;
}
