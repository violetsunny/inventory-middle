package com.inventory.middle.client.dto.transit;

import com.inventory.middle.client.dto.BaseRequest;
import com.inventory.middle.client.enums.MaterialDocRefOrderTypeEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @description 在途库存 -> 在库库存请求
 * @author dongguo.tao
 * @date 2021-09-27 17:58:09
 */

@Data
public class TransferTransitStockRequest extends BaseRequest {


    private static final long serialVersionUID = -9196333488652357419L;

    /**
     * 租户id
     */
    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

    /**
     * 来源单据类型
     * @see MaterialDocRefOrderTypeEnum
     */
    private Integer sourceOrderType;

    /**
     * 来源单据编号
     *
     * 来源单据编号 和 交运单编号 不能同时为空
     */
    private String sourceOrderNo;

    /**
     * 交运单编号
     *
     * 来源单据编号 和 交运单编号 不能同时为空
     */
    private String deliveryNo;

    /**
     * 操作人id
     */
    @NotNull(message = "操作人ID不能为空")
    private String operatorId;

    @Valid
    @NotNull(message = "物料相关信息不能为空")
    @Size(min = 1, message = "物料相关信息集合不能为空")
    private List<TransferTransitMaterialDTO> materialDTOList;

}
