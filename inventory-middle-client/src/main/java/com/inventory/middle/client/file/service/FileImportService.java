package com.inventory.middle.client.file.service;


import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import com.inventory.middle.client.file.dto.request.CreateFileImportLineRecordsRequest;
import com.inventory.middle.client.file.dto.request.CreateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.PageQueryFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.UpdateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.response.FileImportRecord;

/**
 * @author hjs
 * @date 2022/5/5
 */
public interface FileImportService {

    /**
     * 创建
     * @param request
     */
    SingleResponse<FileImportRecord> createFileImportRecord(CreateFileImportRecordRequest request);

    /**
     * 更新
     * @param request
     */
    SingleResponse<Object> updateFileImportRecord(UpdateFileImportRecordRequest request);

    /**
     * 分页查询
     * @param request
     * @return
     */
    PageResponse<FileImportRecord> pageQuery(PageQueryFileImportRecordRequest request);

    /**
     * 插入创建详情
     * @param request
     */
    SingleResponse<Object> createFileLineRecords(CreateFileImportLineRecordsRequest request);


}
