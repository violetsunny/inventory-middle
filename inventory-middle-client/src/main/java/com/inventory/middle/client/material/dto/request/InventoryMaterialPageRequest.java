package com.inventory.middle.client.material.dto.request;

import com.inventory.middle.client.dto.cmd.BasePageCmd;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author dongguo.tao
 * @description
 * @date 2022-05-05 17:26:17
 */
@Data
public class InventoryMaterialPageRequest extends BasePageCmd implements Serializable {


    private static final long serialVersionUID = -1319697650268154037L;
    /**
     * 租户id
     */
    @NotEmpty(message = "租户id不能为空")
    private String tenantId;

    /**
     * 物料编码 精确查询
     *
     * 优先级比materialCodeFuzzy高
     */
    private String materialCode;

    /**
     * 物料编码 模糊查询
     *
     * 当materialCode 不为空时，优先使用materialCode查询
     */
    private String materialCodeFuzzy;

    /**
     * 外部物料编码 精确查询
     *
     * 优先级比outMaterialCodeFuzzy高
     */
    private String outMaterialCode;

    /**
     * 外部物料编码 模糊查询
     *
     * 当outMaterialCode 不为空时，优先使用当outMaterialCode查询
     */
    private String outMaterialCodeFuzzy;


    /**
     * 物料名称 模糊查询
     */
    private String materialNameFuzzy;



}
