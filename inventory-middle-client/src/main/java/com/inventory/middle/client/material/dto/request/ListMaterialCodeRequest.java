package com.inventory.middle.client.material.dto.request;

import com.inventory.middle.client.dto.cmd.BaseCmd;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author dongguo.tao
 * @description
 * @date 2022-05-05 17:26:17
 */
@Data
public class ListMaterialCodeRequest extends BaseCmd implements Serializable {


    private static final long serialVersionUID = -3753670397374505805L;
    /**
     * 租户id【必填】
     */
    @NotEmpty(message = "租户id不能为空")
    private String tenantId;

    /**
     * 物料编码集合
     */
    private List<String> materialCodeList;

    /**
     * 外部物料编码集合
     */
    private List<String> outMaterialCodeList;

}
