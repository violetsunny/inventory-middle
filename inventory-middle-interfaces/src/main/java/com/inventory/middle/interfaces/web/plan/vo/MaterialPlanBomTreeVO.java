package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "物料报表计划bom树")
public class MaterialPlanBomTreeVO implements Serializable {

    private static final long serialVersionUID = 6511468491336967303L;


    /**
     * 主键
     */
    @Schema(description = "实例ID")
    private Long id;

    /**
     * 物料编码
     */
    @Schema(description = "物料编码")
    private String materialCode;

    /**
     * 物料描述
     */
    @Schema(description = "物料描述")
    private String materialDesc;

    /**
     * 状态信息
     */
    @Schema(description = "状态信息")
    private Integer status;

    /**
     * 异常编码
     */
    @Schema(description = "异常编码")
    private String errCode;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String errMessage;

    /**
     * 子件list集合
     */
    @Schema(description = "子件集合")
    private List<MaterialPlanBomTreeVO> list;

    public List<MaterialPlanBomTreeVO> getList() {
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        return list;
    }
}
