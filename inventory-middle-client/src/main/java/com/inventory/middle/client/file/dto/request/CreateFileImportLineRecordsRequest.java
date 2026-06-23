package com.inventory.middle.client.file.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.file.enums.ImplTypeEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjs
 * @date 2022/5/5
 */
@Data
public class CreateFileImportLineRecordsRequest implements Serializable {


    @NotBlank(message = "appKey不能为空")
    private String appKey;

    /**
     * 租户id
     */
    @NotBlank(message = "tenantId不能为空")
    private String tenantId;

    /**
     * 实现类型
     */
    @EnumValidator(enumClass = ImplTypeEnum.class,checkMethod = "isValidCode",message = "implType不正确")
    @NotBlank(message = "implType不能为空")
    private String implType;

    /**
     * 源导入文件表的记录id
     */
    @NotNull(message = "fileImportRecordId不能为空")
    private Long fileImportRecordId;
    /**
     * 文件详情记录列表
     */
    @NotEmpty(message = "recordList不能为空")
    @Valid
    private List<CreateFileImportLineRecordRequest> recordList;

    @NotBlank(message = "operatorId不能为空")
    private String operatorId;

    @NotBlank(message = "operator不能为空")
    private String operator;
}
