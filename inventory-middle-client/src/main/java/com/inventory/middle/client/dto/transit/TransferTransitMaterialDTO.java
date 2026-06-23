package com.inventory.middle.client.dto.transit;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.*;

import com.inventory.middle.client.enums.StockTypeEnum;
import com.inventory.middle.client.enums.TransferTransitTypeEnum;
import lombok.Data;

/**
 * @description 在途库存 -> 在库库存请求
 * @author dongguo.tao
 * @date 2021-09-27 17:58:09
 */

@Data
public class TransferTransitMaterialDTO implements Serializable {


    private static final long serialVersionUID = -4807008253089940916L;
    /**
     * 物料编码
     */
    @NotBlank(message = "物料编码不能为空")
    private String materialCode;

    /**
     * 逻辑仓id
     */
    @NotNull(message = "逻辑仓id不能为空")
    private Long logicalPlantId;

    /**
     * 存储地点id
     */
    private Long locationId;

    /**
     * @see TransferTransitTypeEnum
     */
    @NotNull(message = "在途转换类型不能为空")
    private Integer transferType;

    /**
     * 入库数量
     */
    @NotNull(message = "在途数量不能为空")
    @Digits(integer= 16, fraction= 4, message = "在途数量最大支持12位整数4位小数")
    @DecimalMin(value= "0", inclusive= false, message = "在途数量必须大于0")
    private BigDecimal inBoundQuantity;

    /**
     * @see StockTypeEnum
     */
    @NotNull(message = "库存类型不能为空")
    private Integer stockType;

}
