package com.inventory.middle.client.dto.material;

import lombok.Data;
import top.kdla.framework.dto.EnumResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 大类映射
 * @author kanglele
 * @version $Id: MaterialTypeMapping, v 0.1 2019-11-04 18:31 Exp $
 */
@Data
public class MaterialDocCategoryMappingDto implements Serializable {

    private EnumResponse<String> materialDocCategory;

    private List<BusinessTypeMappingDto> businessTypes;

}
