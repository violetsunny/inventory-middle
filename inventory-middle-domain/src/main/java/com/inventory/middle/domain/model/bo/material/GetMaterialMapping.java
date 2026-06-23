/**
 * kanglele Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import com.inventory.middle.domain.common.annotation.EnumValidator;
import com.inventory.middle.domain.model.enums.MaterialDocSourceEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author kanglele
 * @version $Id: GetMaterialMappingDTO, v 0.1 2021/9/27 16:44 Exp $
 */
@Data
public class GetMaterialMapping implements Serializable {

    @NotEmpty(message = "materialDocSource 物料凭证操作来源不能为空")
    @EnumValidator(enumClass = MaterialDocSourceEnum.class, checkMethod = "checkByCode", message = "materialDocSource 物料凭证操作来源取值不正确")
    private String materialDocSource;

}
