package com.inventory.middle.client.dto.cmd;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页基础类
 *
 * @author vincent.li
 * @date 2021/12/20 11:30
 */
@Data
public class BasePageCmd implements Serializable {
    /**
     * 来源系统
     */
    @NotBlank(message = "appKey不能为空")
    private String appKey;

    @NotNull(message = "页面size不能为空")
    @Min(value = 1)
    private Integer pageSize;

    @NotNull(message = "页码不能为空")
    @Min(value = 1)
    private Integer pageNum;
}
