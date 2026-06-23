package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author kll
 */
@Data
public class ReverseMaterialDocumentBO implements Serializable {
    /**
     * 物料凭证编号
     */
    @NotBlank(message = "物料凭证编号不能为空")
    private String materialDocNo;
    /**
     * 操作人
     */
    @NotBlank(message = "operator 操作人不能为空")
    private String operator;
    /**
     * 租户
     */
    @NotBlank(message = "tenantId 租户不能为空")
    private String tenantId;
    /**
     * 头备注
     */
    private String remark;
    /**
     * 业务唯一号 幂等
     */
    @NotBlank(message = "uniqueNo 业务唯一号不能为空")
    private String uniqueNo;

    /**
     * 操作应用服务标识
     */
    private String appKey;
}
