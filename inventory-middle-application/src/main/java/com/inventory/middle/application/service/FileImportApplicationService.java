package com.inventory.middle.application.service;

import com.inventory.middle.client.file.dto.request.CreateFileImportLineRecordsRequest;
import com.inventory.middle.client.file.dto.request.CreateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.PageQueryFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.UpdateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.response.FileImportRecord;
import org.springframework.web.multipart.MultipartFile;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

/**
 * 文件导入ApplicationService
 */
public interface FileImportApplicationService {

    SingleResponse<FileImportRecord> createFileImportRecord(CreateFileImportRecordRequest request);

    SingleResponse<Boolean> updateFileImportRecord(UpdateFileImportRecordRequest request);

    PageResponse<FileImportRecord> pageQuery(PageQueryFileImportRecordRequest request);

    SingleResponse<Boolean> createFileLineRecords(CreateFileImportLineRecordsRequest request);

    /**
     * 城燃项目库存 Excel 批量导入
     * 创建一条 FileImportRecord 记录，异步处理行数据
     */
    SingleResponse<Boolean> cityGasImport(MultipartFile file, String tenantId);
}
