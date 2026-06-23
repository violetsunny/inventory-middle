package com.inventory.middle.domain.model.bo.material;

import java.io.Serializable;

import com.inventory.middle.client.enums.BaseEnableStatusEnum;
import com.inventory.middle.client.enums.BaseStatusEnum;
import com.inventory.middle.domain.common.annotation.EnumValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-10-13 14:16:44
 */
@Data
public class QueryMaterialLogicalPlantRefBO implements Serializable {

    private static final long serialVersionUID = -3158575569513269469L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 是否删除 0-否；1-是
     */
    @NotNull()
    @EnumValidator(enumClass = BaseStatusEnum.class, checkMethod = "checkByCode", message = "是否删除错误")
    private Integer deleted;
}
