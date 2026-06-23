package com.inventory.middle.client.dto.mdata.qry;

import com.inventory.middle.client.dto.base.Query;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 产品主数据来源查询请求
 *
 * @author vincent.li
 * @date 2022/4/28 17:00
 */
@Data
public class GetProductMasterDataSourceQry extends Query {

    /**
     * 租户ID
     */
    @NotEmpty(message = "[租户ID]不能为空")
    private String tenantId;
}
