package com.inventory.middle.domain.model.bo.inventorysnapshot;

import com.inventory.middle.client.dto.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description 库存物料合计
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorInventorySnapshotPageBO extends PageRequest implements Serializable {

    /**
     * 物料code集合
     */
    private List<String> materialCodeList;

    /**
     * 逻辑仓ID集合
     */
    private List<Long> logicalPlantIdList;


    /**
     * 租户Id
     */
    private String tenantId;

    /**
     * id
     */
    private Long id;


}
