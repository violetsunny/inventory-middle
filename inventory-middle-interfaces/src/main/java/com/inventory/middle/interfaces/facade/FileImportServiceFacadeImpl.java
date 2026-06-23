package com.inventory.middle.interfaces.facade;

import com.inventory.middle.application.service.FileImportApplicationService;
import com.inventory.middle.client.facade.FileImportServiceFacade;
import com.inventory.middle.client.file.dto.request.CreateFileImportLineRecordsRequest;
import com.inventory.middle.client.file.dto.request.CreateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.PageQueryFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.UpdateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.response.FileImportRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;

/**
 * 文件导入FacadeImpl
 */
@Component
@Slf4j
@CatchAndLog
public class FileImportServiceFacadeImpl implements FileImportServiceFacade {

    @Resource
    private FileImportApplicationService fileImportApplicationService;

    @Override
    public SingleResponse<FileImportRecord> createFileImportRecord(CreateFileImportRecordRequest request) {
        return fileImportApplicationService.createFileImportRecord(request);
    }

    @Override
    public SingleResponse<Boolean> updateFileImportRecord(UpdateFileImportRecordRequest request) {
        return fileImportApplicationService.updateFileImportRecord(request);
    }

    @Override
    public PageResponse<FileImportRecord> pageQuery(PageQueryFileImportRecordRequest request) {
        return fileImportApplicationService.pageQuery(request);
    }

    @Override
    public SingleResponse<Boolean> createFileLineRecords(CreateFileImportLineRecordsRequest request) {
        return fileImportApplicationService.createFileLineRecords(request);
    }
}
