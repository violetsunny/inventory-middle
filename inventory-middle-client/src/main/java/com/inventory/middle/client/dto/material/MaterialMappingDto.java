package com.inventory.middle.client.dto.material;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 映射关系
 * @author kll
 * @date 2021/6/11
 */
@Data
public class MaterialMappingDto implements Serializable {

    private List<MaterialDocCategoryMappingDto> mappings;

}
