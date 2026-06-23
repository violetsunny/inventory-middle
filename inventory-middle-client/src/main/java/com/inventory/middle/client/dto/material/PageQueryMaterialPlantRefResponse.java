package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 分页查询  物料-逻辑仓  关系 的结果返回对象
 *
 * @author hjs
 * @date 2021/12/10
 */
@Data
public class PageQueryMaterialPlantRefResponse implements Serializable {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;
}
