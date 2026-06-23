package com.inventory.middle.domain.model.bo.mq.sub;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.inventory.middle.client.dto.BaseRequest;
import com.inventory.middle.domain.common.annotation.EnumValidator;
import com.inventory.middle.domain.model.enums.*;

import lombok.Data;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-08-30 10:21:06
 */
@Data
public class MaterialDocInMessage extends BaseRequest {

    private static final long serialVersionUID = -1910585838109766395L;

    /**
     * 渠道
     */
    @NotNull(message = "渠道 不能为空")
    @EnumValidator(enumClass = MaterialDocChannelEnum.class, checkMethod = "checkByCode", message="渠道取值不正确")
    private Integer channel;

    /**
     * 物料凭证组 0QC
     */
    @NotEmpty(message = "物料凭证组 不能为空")
    @EnumValidator(enumClass = MaterialDocGroupEnum.class, checkMethod = "checkByCode", message="物料凭证组取值不正确")
    private String materialDocGroup;

    /**
     * 物料凭证类型 库存管理类
     */
    @NotNull(message = "物料凭证类型 不能为空")
    @EnumValidator(enumClass = MaterialDocTypeEnum.class, checkMethod = "checkByCode", message="物料凭证类型取值不正确")
    private Integer materialDocType;


    /**
     * 出入库类型 出库 入库 出入库 取消
     * @see MaterialDocCategoryEnum
     */
    @NotNull(message = "出入库类型 不能为空")
    @EnumValidator(enumClass = MaterialDocCategoryEnum.class, checkMethod = "checkByCode", message="出入库类型取值不正确")
    private Integer materialDocCategory;

    /**
     * 业务事项 - 期初导入，调拨
     *
     * @see BusinessTypeEnum
     */
    @NotEmpty(message = "业务事项 不能为空")
    @EnumValidator(enumClass = BusinessTypeEnum.class, checkMethod = "checkByCode", message="业务事项取值不正确")
    private String businessType;

    /**
     * 移动类型 eg:CG101
     * @see MaterialAdjustTypeEnum
     */
    @NotEmpty(message = "移动类型 不能为空")
    @EnumValidator(enumClass = MaterialAdjustTypeEnum.class, checkMethod = "checkByCode", message="移动类型取值不正确")
    private String adjustType;

    /**
     * token
     */
    @NotEmpty(message = "token 不能为空")
    private String token;
    /**
     * 操作人
     */
    @NotEmpty(message = "操作人 不能为空")
    private String operator;
    /**
     * 租户
     */
    @NotEmpty(message = "租户 不能为空")
    private String tenantId;

    /**
     * 内容
     */
    @NotEmpty(message = "内容 不能为空")
    private String content;
}
