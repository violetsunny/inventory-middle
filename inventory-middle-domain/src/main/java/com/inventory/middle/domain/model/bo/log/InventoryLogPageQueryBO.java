package com.inventory.middle.domain.model.bo.log;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description 库存报警日志
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class InventoryLogPageQueryBO extends InventoryLogQueryBO implements Serializable {

    private static final long serialVersionUID = -7644694483614018863L;

    @NotNull(message = "页面size不能为空")
    @Min(value = 1, message = "页面size最小为1")
    private Integer pageSize;


    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum;
}
