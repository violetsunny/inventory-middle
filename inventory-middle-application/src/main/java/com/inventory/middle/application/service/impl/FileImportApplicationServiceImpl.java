package com.inventory.middle.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.FileImportApplicationService;
import com.inventory.middle.client.file.dto.request.CreateFileImportLineRecordsRequest;
import com.inventory.middle.client.file.dto.request.CreateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.PageQueryFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.UpdateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.response.FileImportRecord;
import com.inventory.middle.client.file.enums.FileImportBusinessTypeEnum;
import com.inventory.middle.client.file.enums.FileImportProcessStatusEnum;
import com.inventory.middle.client.file.enums.ImplTypeEnum;
import com.inventory.middle.infra.persistence.entity.ListFileImportRecordParam;
import com.inventory.middle.infra.persistence.repository.impl.FileImportRecordRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件导入ApplicationService实现
 * 迁移自: com.enn.inventory.center.ext.biz.file.FileImportDomainService
 */
@Slf4j
@Service
public class FileImportApplicationServiceImpl implements FileImportApplicationService {

    @Resource
    private FileImportRecordRepositoryImpl fileImportRecordRepository;

    @Override
    public SingleResponse<FileImportRecord> createFileImportRecord(CreateFileImportRecordRequest request) {
        log.info("FileImportApplicationServiceImpl.createFileImportRecord request={}", JSON.toJSONString(request));
        com.inventory.middle.domain.model.entity.FileImportRecord entity =
                new com.inventory.middle.domain.model.entity.FileImportRecord();
        BeanUtils.copyProperties(request, entity);
        fileImportRecordRepository.store(entity);
        FileImportRecord resp = new FileImportRecord();
        BeanUtils.copyProperties(entity, resp);
        return SingleResponse.of(resp);
    }

    @Override
    public SingleResponse<Boolean> updateFileImportRecord(UpdateFileImportRecordRequest request) {
        log.info("FileImportApplicationServiceImpl.updateFileImportRecord request={}", JSON.toJSONString(request));
        com.inventory.middle.domain.model.entity.FileImportRecord entity =
                new com.inventory.middle.domain.model.entity.FileImportRecord();
        BeanUtils.copyProperties(request, entity);
        boolean result = fileImportRecordRepository.update(entity);
        return SingleResponse.of(result);
    }

    @Override
    public PageResponse<FileImportRecord> pageQuery(PageQueryFileImportRecordRequest request) {
        log.info("FileImportApplicationServiceImpl.pageQuery request={}", JSON.toJSONString(request));
        ListFileImportRecordParam param = new ListFileImportRecordParam();
        BeanUtils.copyProperties(request, param);
        List<com.inventory.middle.domain.model.entity.FileImportRecord> list =
                fileImportRecordRepository.listWithNoBlob(param);
        List<FileImportRecord> respList = list.stream().map(e -> {
            FileImportRecord dto = new FileImportRecord();
            BeanUtils.copyProperties(e, dto);
            return dto;
        }).collect(Collectors.toList());
        return PageResponse.of(respList, (long) respList.size(),
                (long) request.getPageSize(), (long) request.getPageNum());
    }

    @Override
    public SingleResponse<Boolean> createFileLineRecords(CreateFileImportLineRecordsRequest request) {
        log.info("FileImportApplicationServiceImpl.createFileLineRecords recordId={}", request == null ? null : request.getFileImportRecordId());
        if (request == null || request.getFileImportRecordId() == null) {
            return SingleResponse.of(false);
        }
        // 委托 fileImportRecordRepository 更新主记录状态
        com.inventory.middle.domain.model.entity.FileImportRecord entity =
                new com.inventory.middle.domain.model.entity.FileImportRecord();
        entity.setId(request.getFileImportRecordId());
        boolean result = fileImportRecordRepository.update(entity);
        return SingleResponse.of(result);
    }

    @Override
    public SingleResponse<Boolean> cityGasImport(MultipartFile file, String tenantId) {
        log.info("FileImportApplicationServiceImpl.cityGasImport tenantId={}, fileName={}",
                tenantId, file != null ? file.getOriginalFilename() : null);
        if (file == null || file.isEmpty()) {
            return SingleResponse.of(false);
        }
        com.inventory.middle.domain.model.entity.FileImportRecord entity =
                new com.inventory.middle.domain.model.entity.FileImportRecord();
        entity.setTenantId(tenantId);
        entity.setFileName(file.getOriginalFilename());
        entity.setBusinessType(FileImportBusinessTypeEnum.CITY_GAS_INVENTORY_IMPORT.getCode());
        entity.setImplType(ImplTypeEnum.CITY_GAS.getCode());
        entity.setProcessStatus(FileImportProcessStatusEnum.PROCESSING.getCode());
        fileImportRecordRepository.store(entity);
        log.info("FileImportApplicationServiceImpl.cityGasImport created record id={}", entity.getId());
        return SingleResponse.of(true);
    }
}
