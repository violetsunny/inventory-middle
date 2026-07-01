package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.FileImportApplicationService;
import com.inventory.middle.client.file.dto.request.CreateFileImportLineRecordsRequest;
import com.inventory.middle.client.file.dto.request.CreateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.PageQueryFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.UpdateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.response.FileImportRecord;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;

/**
 * 文件导入Controller
 * 迁移自: FileImportService
 */
@Tag(name = "文件导入管理")
@RestController
@RequestMapping("/file-import")
@Slf4j
@CatchAndLog
public class FileImportController {

    @Resource
    private FileImportApplicationService fileImportApplicationService;

    @Operation(summary = "创建文件导入记录")
    @PostMapping("/create")
    public SingleResponse<FileImportRecord> createFileImportRecord(@RequestBody CreateFileImportRecordRequest request) {
        request.setTenantId(UserContextHolder.getTenantId());
        request.setOperatorId(UserContextHolder.getUserId());
        request.setOperator(UserContextHolder.getUsername());
        return fileImportApplicationService.createFileImportRecord(request);
    }

    @Operation(summary = "更新文件导入记录")
    @PostMapping("/update")
    public SingleResponse<Boolean> updateFileImportRecord(@RequestBody UpdateFileImportRecordRequest request) {
        request.setTenantId(UserContextHolder.getTenantId());
        request.setOperatorId(UserContextHolder.getUserId());
        request.setOperator(UserContextHolder.getUsername());
        return fileImportApplicationService.updateFileImportRecord(request);
    }

    @Operation(summary = "分页查询文件导入记录")
    @PostMapping("/page")
    public PageResponse<FileImportRecord> pageQuery(@RequestBody PageQueryFileImportRecordRequest request) {
        return fileImportApplicationService.pageQuery(request);
    }

    @Operation(summary = "创建文件导入详情行")
    @PostMapping("/create-line-records")
    public SingleResponse<Boolean> createFileLineRecords(@RequestBody CreateFileImportLineRecordsRequest request) {
        request.setTenantId(UserContextHolder.getTenantId());
        request.setOperatorId(UserContextHolder.getUserId());
        request.setOperator(UserContextHolder.getUsername());
        return fileImportApplicationService.createFileLineRecords(request);
    }
}
