package com.inventory.middle.client.file.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.file.enums.FileImportBusinessTypeEnum;
import com.inventory.middle.client.file.enums.FileImportProcessStatusEnum;
import com.inventory.middle.client.file.enums.ImplTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hjs
 * @date 2022/5/5
 */
@Data
public class CreateFileImportRecordRequest implements Serializable {

    @NotBlank(message = "appKey不能为空")
    private String appKey;

    @NotBlank(message = "tenantId不能为空")
    private String tenantId;

    @EnumValidator(enumClass = ImplTypeEnum.class,checkMethod = "isValidCode",message = "implType不正确")
    @NotBlank(message = "implType不能为空")
    private String implType;

    @EnumValidator(enumClass = FileImportBusinessTypeEnum.class,checkMethod = "isValidCode",message = "businessType不正确")
    @NotBlank(message = "businessType不能为空")
    private String businessType;


    @NotBlank(message = "fileName不能为空")
    private String fileName;

    @NotBlank(message = "fileUrl不能为空")
    private String fileUrl;

    @EnumValidator(enumClass = FileImportProcessStatusEnum.class,checkMethod = "isValidCode",message = "processStatus不正确", trueIfNull = true)
    private String processStatus;

    private String processMessage;

    @NotBlank(message = "operatorId不能为空")
    private String operatorId;

    @NotBlank(message = "operator不能为空")
    private String operator;

    private String extInfo;
}
