package com.inventory.middle.domain.model.bo.mq.sub;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.inventory.middle.domain.common.annotation.EnumValidator;
import com.inventory.middle.domain.model.enums.*;

import lombok.Data;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-08-30 10:21:06
 */
@Data
public class MaterialDocCancelMessage implements Serializable {

    private static final long serialVersionUID = 4175703206101605941L;

    /**
     * 渠道
     */
    @NotNull(message = "渠道 不能为空")
    @EnumValidator(enumClass = MaterialDocChannelEnum.class, checkMethod = "checkByCode", message="渠道取值不正确")
    private Integer channel;

    /**
     * 出入库类型 出库 入库 出入库 取消
     * @see MaterialDocCategoryEnum
     */
    @NotNull(message = "出入库类型 不能为空")
    @EnumValidator(enumClass = MaterialDocCategoryEnum.class, checkMethod = "checkByCode", message="出入库类型取值不正确")
    private Integer materialDocCategory;

    /**
     * 物料凭证编码
     */
    @NotEmpty(message = "物料凭证编码 不能为空")
    private String materialDocNo;

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
}
