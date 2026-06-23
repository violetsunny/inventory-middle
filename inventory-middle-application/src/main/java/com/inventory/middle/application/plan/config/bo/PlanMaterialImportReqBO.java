package com.inventory.middle.application.plan.config.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class PlanMaterialImportReqBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MultipartFile file;
    private Long planId;
}
