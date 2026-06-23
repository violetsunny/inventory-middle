package com.inventory.middle.client.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 6193784870034428654L;

    @NotNull(message = "页面size不能为空")
    @Min(value = 1)
    private Integer pageSize;

    @NotNull(message = "页码不能为空")
    @Min(value = 1)
    private Integer pageNum;

}
