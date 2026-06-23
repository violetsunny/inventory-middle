package com.inventory.middle.client.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 基础类
 *
 * @author vincent.li
 * @date 2021/12/20 11:30
 */
@Data
public class BaseCmd implements Serializable {
    /**
     * 来源系统
     */
    @NotBlank(message = "appKey不能为空")
    private String appKey;
}
