package com.inventory.middle.domain.model.bo.material;

import com.inventory.middle.domain.model.bo.EnumMapping;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author kll
 * @date 2021/6/11
 */
@Data
public class BusinessTypeMapping {

    private EnumMapping businessType;

    private EnumMapping adjustType;

    private String io;

    /**
     * 是否参考单据下拉集合
     */
    private List<EnumMapping> refTypeList = Lists.newArrayList();

    /**
     * 参考单据类型
     */
    private Integer originalNoType;

    /**
     * 是否可以修改数据
     */
    private Boolean updateData = true;
}
