package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import com.inventory.middle.client.enums.BaseStatusEnum;
import lombok.Data;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-10-13 14:16:44
 */
@Data
public class QueryMaterialLogicalPlantRefReqDTO extends BaseRequest {


    private static final long serialVersionUID = -1147268342996570131L;

    /**
     * 主键id
     *
     * 非必填
     */
    private Long id;

    /**
     * 租户id
     *
     * 必填
     */
    private String tenantId;

    /**
     * 物料编码
     *
     * 非必填
     */
    private String materialCode;

    /**
     * 逻辑仓id
     *
     * 非必填
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     *
     * 非必填
     */
    private String logicalPlantNo;

    /**
     * 是否删除 0-否；1-是
     *
     * 非必填 为空默认查询未被删除的数据
     * @see BaseStatusEnum
     */
    private Integer deleted;
}
