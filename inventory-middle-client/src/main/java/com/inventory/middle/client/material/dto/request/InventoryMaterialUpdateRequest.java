package com.inventory.middle.client.material.dto.request;

import com.inventory.middle.client.dto.cmd.BaseCmd;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dongguo.tao
 * @description
 * @date 2022-05-05 16:54:35
 */
@Data
public class InventoryMaterialUpdateRequest extends BaseCmd implements Serializable {

    private static final long serialVersionUID = 2667578571814038949L;
    /**
     * 租户id【必填】
     */
    @NotEmpty(message = "租户id不能为空")
    private String tenantId;

    /**
     * 物料编码【必填】
     */
    @NotEmpty(message = "物料编码不能为空")
    private String materialCode;

    /**
     * 物料名称【非必填】
     */
    @Size(max = 128, message = "物料名称过长，最大128")
    private String materialName;

    /**
     * 外部物料编码【非必填】
     */
    @Size(max = 64, message = "外部物料编码过长，最大64")
    private String outMaterialCode;

    /**
     * 单位id【非必填】
     */
    private Long unitId;

    /**
     * 操作人id【必填】
     */
    @NotEmpty(message = "操作人id不能为空")
    @Size(max = 32, message = "操作人id过长，最大32")
    private String operatorId;

}
