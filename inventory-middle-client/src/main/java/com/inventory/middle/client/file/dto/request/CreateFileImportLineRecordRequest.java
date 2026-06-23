package com.inventory.middle.client.file.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.file.enums.FileImportProcessStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hjs
 * @date 2022/5/5
 */
@Data
public class CreateFileImportLineRecordRequest implements Serializable {

    @EnumValidator(enumClass = FileImportProcessStatusEnum.class,checkMethod = "isValidCode",message = "processStatus不正确", trueIfNull = true)
    private String processStatus;

    private String processMessage;

    @NotBlank(message = "lineDetail不能为空")
    private String lineDetail;

}
