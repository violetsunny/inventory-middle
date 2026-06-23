package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.FileImportRecord;
import com.inventory.middle.domain.repository.FileImportRecordRepository;
import com.inventory.middle.infra.persistence.entity.FileImportRecordDo;
import com.inventory.middle.infra.persistence.entity.ListFileImportRecordParam;
import com.inventory.middle.infra.persistence.mapper.FileImportRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件导入记录Repository实现类
 */
@Repository
public class FileImportRecordRepositoryImpl implements FileImportRecordRepository {

    @Resource
    private FileImportRecordMapper fileImportRecordMapper;

    @Override
    public FileImportRecord findById(Long id) {
        FileImportRecordDo doObj = fileImportRecordMapper.selectByIdWithNoBlob(id);
        return toEntity(doObj);
    }

    @Override
    public boolean store(FileImportRecord entity) {
        FileImportRecordDo doObj = toDoObject(entity);
        return fileImportRecordMapper.insert(doObj) > 0;
    }

    @Override
    public boolean update(FileImportRecord entity) {
        FileImportRecordDo doObj = toDoObject(entity);
        return fileImportRecordMapper.updateByIdAndTenantIdSelective(doObj) > 0;
    }

    @Override
    public List<FileImportRecord> findByIds(List<Long> ids) {
        return Collections.emptyList();
    }

    public List<FileImportRecord> listWithNoBlob(ListFileImportRecordParam param) {
        return fileImportRecordMapper.listWithNoBlob(param)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    private FileImportRecordDo toDoObject(FileImportRecord entity) {
        FileImportRecordDo doObj = new FileImportRecordDo();
        BeanUtils.copyProperties(entity, doObj);
        return doObj;
    }

    private FileImportRecord toEntity(FileImportRecordDo doObj) {
        if (doObj == null) return null;
        FileImportRecord entity = new FileImportRecord();
        BeanUtils.copyProperties(doObj, entity);
        return entity;
    }
}
