package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import com.inventory.middle.client.dto.base.Query;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 现有期末库存查询请求
 * @author vincent.li
 * @version 1.0
 * @date 2021-09-27
 */
@Data
public class CurrentInventoryBalanceQry extends Query implements Serializable {
    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 外部物料编码
     */
    private String outMaterialCode;

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
}
