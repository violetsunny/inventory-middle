package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author kll
 */
@Data
public class ReverseMaterialDocumentDTO extends BaseRequest {
    /**
     * 物料凭证编号
     */
    @NotBlank(message = "materialDocNo 物料凭证编号不能为空")
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
     * 是否计算map
     */
    private Boolean calMap;

}
