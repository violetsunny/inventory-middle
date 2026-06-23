package com.inventory.middle.client.dto.material.qry;

import com.inventory.middle.client.dto.base.Query;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 根据物料编码和租户id批量查询请求
 *
 * @author vincent.li
 * @date 2022/5/7 10:59
 */
@Data
public class MaterialCodeListQry extends Query implements Serializable {
    /**
     * 租户id【必填】
     */
    @Size(max = 40, message = "租户id过长，最大40")
    @NotEmpty(message = "租户id不能为空")
    private String tenantId;

    /**
     * 物料编码集合
     */
    private List<String> materialCodeList;

    /**
     * 外部物料编码集合
     */
    private List<String> outMaterialCodeList;

}
