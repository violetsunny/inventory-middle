package com.inventory.middle.client.dto.material;

import lombok.Data;
import top.kdla.framework.dto.EnumResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 移动类型映射
 * @author kll
 * @date 2021/6/11
 */
@Data
public class BusinessTypeMappingDto implements Serializable {

    /**
     * 业务类型
     */
    private EnumResponse businessType;

    /**
     * 移动类型
     */
    private EnumResponse adjustType;

    /**
     * 出入标识符
     */
    private String io;

    /**
     * 是否参考单据下拉集合
     */
    private List<EnumResponse> refTypeList;

    /**
     * 参考单据类型
     */
    private Integer originalNoType;

    /**
     * 是否可以修改数据
     */
    private Boolean updateData = true;
}
