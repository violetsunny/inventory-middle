package com.inventory.middle.client.dto.material.qry;

import com.inventory.middle.client.dto.base.Query;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 物料和逻辑仓查询
 * @author vincent.li
 * @date 2022/5/7 10:59
 */
@Data
public class MaterialLogicalPlantQry extends Query implements Serializable {

    /**
     * 租户id
     *
     * 必填
     */
    @Size(max = 40, message = "租户id过长，最大40")
    @NotEmpty(message = "租户id不能为空")
    private String tenantId;

    /**
     * 逻辑仓编码
     * 必填
     */
    @NotEmpty(message = "逻辑仓编码不能为空")
    private String logicalPlantNo;

}
