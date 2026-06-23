package com.inventory.middle.client.facade;

import com.inventory.middle.client.file.dto.request.CreateFileImportLineRecordsRequest;
import com.inventory.middle.client.file.dto.request.CreateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.PageQueryFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.UpdateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.response.FileImportRecord;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

/**
 * 文件导入Facade接口
 */
public interface FileImportServiceFacade {

    SingleResponse<FileImportRecord> createFileImportRecord(CreateFileImportRecordRequest request);

    SingleResponse<Boolean> updateFileImportRecord(UpdateFileImportRecordRequest request);

    PageResponse<FileImportRecord> pageQuery(PageQueryFileImportRecordRequest request);

    SingleResponse<Boolean> createFileLineRecords(CreateFileImportLineRecordsRequest request);
}
