package com.inventory.middle.client.code.dto.request;


import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.code.enums.CodeApplyOrderChannelEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderCreateRequest implements Serializable {


    private static final long serialVersionUID = -6267539181115304849L;

    @NotNull(message = "流转码申请单创建渠道不能为空")
    @EnumValidator(enumClass = CodeApplyOrderChannelEnum.class, checkMethod = "checkByCode", message = "流转码申请单创建渠道错误")
    private Integer channel;

    /**
     * 租户id （经销商）
     */
    @NotBlank(message = "租户id不能为空")
    @Size(max = 32, message = "租户id过长，最大32")
    private String tenantId;

    /**
     * 所有者-厂商
     */
    @NotBlank(message = "所有者不能为空")
    @Size(max = 32, message = "所有者过长，最大32")
    private String ownerId;

    /**
     * 受邀者-接收经销商
     */
    @NotBlank(message = "受邀者不能为空")
    @Size(max = 32, message = "受邀者过长，最大32")
    private String inviteeId;

    @NotBlank(message = "受邀者逻辑仓不能为空")
    @Size(max = 40, message = "受邀者逻辑仓编码过长，最大40")
    private String inviteeLogicalPlantNo;

    @Size(max = 256, message = "申请原因过长，最大256")
    private String applyReason;

    @NotBlank(message = "操作人id不能为空")
    @Size(max = 32, message = "操作人id过长，最大32")
    private String operatorId;

    @NotBlank(message = "来源系统不能为空")
    @Size(max = 20, message = "来源系统过长，最大20")
    private String sourceSystem;

    @Valid
    @NotNull(message = "申请明细不能为空")
    @Size(min = 1, message = "申请明细至少有一条")
    private List<CodeApplyOrderDetailCreateRequest> detailCreateBoList;

}
