package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 删除计划物料清单入参
 * @date 2021/10/15 16:36
 */
@Data
public class PlanMaterialDeleteReqDTO implements Serializable {

    private static final long serialVersionUID = 8187576016704001436L;

    @Schema(description = "上传文件")
    private MultipartFile[] files;

    @Schema(description = "计划Id")
    private Long planId;
}