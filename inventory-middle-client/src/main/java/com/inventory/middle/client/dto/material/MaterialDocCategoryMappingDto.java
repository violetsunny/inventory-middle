package com.inventory.middle.client.dto.material;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 大类映射
 * @author kanglele
 * @version $Id: MaterialTypeMapping, v 0.1 2019-11-04 18:31 Exp $
 */
@Data
public class MaterialDocCategoryMappingDTO implements Serializable {

    private EnumMappingDTO materialDocCategory;

    private List<BusinessTypeMappingDTO> businessTypes;

    /**
     * @deprecated 弃用 使用 businessTypes.originalNoNeed
     */
    @Deprecated
    private Boolean originalNoRequired = false;

    /**
     * @deprecated 弃用 使用 businessTypes.updateData
     */
    @Deprecated
    private Boolean operateData = true;
}
