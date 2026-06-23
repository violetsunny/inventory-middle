package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划物料清单导入入参
 * @date 2021/10/2 9:51
 */
@Data
public class PlanMaterialImportReqDTO implements Serializable {

    private static final long serialVersionUID = 8187576016704001436L;

    @Schema(description = "上传文件")
    private MultipartFile file;

    @Schema(description = "计划Id")
    private Long planId;

}