package com.inventory.middle.domain.model.bo.mq.sub;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.*;

import lombok.Data;

/**
 * @author dongguo.tao
 * @description 数能批量创建
 * @date 2021-08-30 13:56:03
 */

@Data
public class SnMaterialDocInBO implements Serializable {

    private static final long serialVersionUID = 6882891856269402796L;

    /**
     * 同一个Excel导入序列号
     */
    @NotEmpty(message = "同一个Excel导入序列号 不能为空")
    private String batchNumber;

    /**
     * 入库仓库编码
     */
    @NotEmpty(message = "入库仓库编码 不能为空")
    private String storeCode;

    /**
     * 物料编码
     */
    @NotEmpty(message = "物料编码 不能为空")
    private String goodCode;

    /**
     * 入库数量
     */
    @NotNull(message = "库数量 不能为空")
    @Digits(integer= 14, fraction= 6, message = "库存上限最大支持14位整数6位小数")
    @DecimalMin(value= "0", inclusive= false, message = "库数量 必须大于0")
    private BigDecimal quantity;

    /**
     * 入库时间
     */
    private Date inStoreTime;

    /**
     * 生产日期
     */
    private Date productionTime;

    /**
     * 年检周期（天）
     */
    private Integer annualCycleDays;

    /**
     * 年检日期
     */
    private Date annualTime;

    /**
     * 供应商CODE
     */
    private String supplierCode;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 描述
     */
    private String goodDescribe;

    /**
     * Excel中的序号
     */
    @NotNull(message = "Excel中的序号 不能为空")
    private Integer batchIndex;
}
