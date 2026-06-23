package com.inventory.middle.client.dto.transit;

import com.inventory.middle.client.dto.BaseRequest;
import com.inventory.middle.client.enums.MaterialDocRefOrderTypeEnum;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 在途库存创建
 * @author dongguo.tao
 * @date 2021-09-27 17:58:09
 */

@Data
public class CreateInTransitStockRequest extends BaseRequest {


    private static final long serialVersionUID = -9196333488652357419L;
    /**
     * 租户id
     */
    @NotBlank(message = "租户ID不能为空")
    @Size(max = 32, message = "租户ID过长，最大32")
    private String tenantId;

    @NotNull(message = "产生在途的出库物料凭证id不能为空")
    private Long materialDocId;

    /**
     * 物料编码
     */
    @NotBlank(message = "物料编码不能为空")
    @Size(max = 32, message = "物料编码过长，最大32")
    private String materialCode;

    /**
     * 逻辑仓id
     */
    @NotNull(message = "逻辑仓id不能为空")
    private Long logicalPlantId;

    /**
     * 存储地点id， 可以为空，为空时系选择使用默认存储地点
     */
    private Long locationId;

    /**
     * 在途数量
     */
    @NotNull(message = "在途数量不能为空")
    @Digits(integer= 16, fraction= 4, message = "在途数量最大支持16位整数4位小数")
    private BigDecimal quantity;

    /**
     * 库存类型
     */
    @NotNull(message = "库存类型不能为空")
    private Integer stockType;

    /**
     * 计量单位
     */
    @NotBlank(message = "计量单位不能为空")
    @Size(max = 16, message = "计量单位过长，最大16")
    private String uom;

    /**
     * 货币单位
     */
    @NotBlank(message = "货币单位不能为空")
    @Size(max = 16, message = "计量单位过长，最大16")
    private String currency;


    /**
     * 批次价格
     */
    @Digits(integer= 16, fraction= 4, message = "批次价格最大支持16位整数4位小数")
    private BigDecimal batchPrice;

    /**
     * 生产日期 非必填
     * 为空默认为创建日期
     */
    private Date eta;

    /**
     * 过期日期 非必填
     * 为空默认为：9999-12-30 00:00:00
     */
    private Date dueDate;

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
    @Size(max = 64, message = "来源单据编号过长，最大64")
    private String sourceOrderNo;

    /**
     * 交运单编号
     *
     * 来源单据编号 和 交运单编号 不能同时为空
     */
    @Size(max = 64, message = "交运单编号过长，最大64")
    private String deliveryNo;

    /**
     * 操作人id
     */
    @NotNull(message = "操作人ID不能为空")
    @Size(max = 32, message = "操作人ID过长，最大32")
    private String operatorId;

}
