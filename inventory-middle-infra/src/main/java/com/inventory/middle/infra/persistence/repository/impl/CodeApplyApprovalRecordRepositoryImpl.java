package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.CodeApplyApprovalRecord;
import com.inventory.middle.domain.repository.CodeApplyApprovalRecordRepository;
import com.inventory.middle.infra.persistence.entity.CodeApplyApprovalRecordDo;
import com.inventory.middle.infra.persistence.mapper.CodeApplyApprovalRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 码申请审批记录Repository实现类
 */
@Repository
public class CodeApplyApprovalRecordRepositoryImpl implements CodeApplyApprovalRecordRepository {

    @Resource
    private CodeApplyApprovalRecordMapper codeApplyApprovalRecordMapper;

    @Override
    public CodeApplyApprovalRecord findById(Long id) {
        CodeApplyApprovalRecordDo doObj = codeApplyApprovalRecordMapper.getById(id);
        return toEntity(doObj);
    }

    @Override
    public boolean store(CodeApplyApprovalRecord entity) {
        CodeApplyApprovalRecordDo doObj = toDoObject(entity);
        return codeApplyApprovalRecordMapper.insert(doObj) > 0;
    }

    @Override
    public boolean update(CodeApplyApprovalRecord entity) {
        return false;
    }

    @Override
    public List<CodeApplyApprovalRecord> findByIds(List<Long> ids) {
        return Collections.emptyList();
    }

    private CodeApplyApprovalRecordDo toDoObject(CodeApplyApprovalRecord entity) {
        CodeApplyApprovalRecordDo doObj = new CodeApplyApprovalRecordDo();
        BeanUtils.copyProperties(entity, doObj);
        return doObj;
    }

    private CodeApplyApprovalRecord toEntity(CodeApplyApprovalRecordDo doObj) {
        if (doObj == null) return null;
        CodeApplyApprovalRecord entity = new CodeApplyApprovalRecord();
        BeanUtils.copyProperties(doObj, entity);
        return entity;
    }
}
