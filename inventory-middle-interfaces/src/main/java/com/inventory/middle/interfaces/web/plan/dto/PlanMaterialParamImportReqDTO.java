package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划物料参数导入 入参
 * @date 2021/10/9 14:39
 */
@Data
public class PlanMaterialParamImportReqDTO implements Serializable {

    private static final long serialVersionUID = 7330936889672240927L;

    @Schema(description = "上传文件", required = true)
    private MultipartFile file;

}