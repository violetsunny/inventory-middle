package com.inventory.middle.client.file.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
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
public class PageQueryFileImportRecordRequest implements Serializable {



    @NotBlank(message = "appKey不能为空")
    private String appKey;

    @NotBlank(message = "tenantId不能为空")
    private String tenantId;

    @EnumValidator(enumClass = ImplTypeEnum.class,checkMethod = "isValidCode",message = "implType不正确")
    @NotBlank(message = "implType不能为空")
    private String implType;

    @EnumValidator(enumClass = FileImportProcessStatusEnum.class,checkMethod = "isValidCode",message = "processStatus不正确", trueIfNull = true)
    private String processStatus;


    private int pageSize;

    private int pageNum;

}
