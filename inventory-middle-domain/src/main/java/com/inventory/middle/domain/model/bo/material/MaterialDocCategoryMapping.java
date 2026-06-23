package com.inventory.middle.domain.model.bo.material;

import com.inventory.middle.domain.model.bo.EnumMapping;
import lombok.Data;

import java.util.List;

/**
 * @author kanglele
 * @version $Id: MaterialTypeMapping, v 0.1 2019-11-04 18:31 Exp $
 */
@Data
public class MaterialDocCategoryMapping {

    private EnumMapping materialDocCategory;

    private List<BusinessTypeMapping> businessTypes;

    /**
     * @deprecated 弃用 使用businessTypes.originalNoNeed
     */
    @Deprecated
    private Boolean originalNoRequired = false;
    /**
     * @deprecated 弃用 使用businessTypes.updateData
     */
    @Deprecated
    private Boolean operateData = true;
}
