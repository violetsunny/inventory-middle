package com.inventory.middle.client.code.dto.request;


import com.inventory.middle.client.code.enums.CodeApprovalStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderApprovalRequest implements Serializable {

    private static final long serialVersionUID = -5110146375225302830L;
    /**
     * 租户id （厂商）
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    /**
     * 申请单id
     */
    @NotNull(message = "申请单id不能为空")
    private Long applyOrderId;

    /**
     * 审批状态 2-审批通过；3-审批已拒绝
     * @see CodeApprovalStatusEnum
     */
    @NotNull(message = "审批状态不能为空")
    private Integer approvalStatus;

    /**
     * 审批原因
     */
    @Size(max = 256, message = "审批原因过长，最大256")
    private String approvalReason;

    /**
     * 操作人id
     */
    @NotEmpty(message = "操作人id不能为空")
    private String operatorId;

    @NotBlank(message = "来源系统不能为空")
    @Size(max = 20, message = "来源系统过长，最大20")
    private String sourceSystem;


}
