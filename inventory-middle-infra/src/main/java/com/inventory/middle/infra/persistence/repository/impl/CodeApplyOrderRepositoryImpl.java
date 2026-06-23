package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.CodeApplyOrder;
import com.inventory.middle.domain.repository.CodeApplyOrderRepository;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDo;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderParamPO;
import com.inventory.middle.infra.persistence.mapper.CodeApplyOrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 码申请单Repository实现类
 */
@Repository
public class CodeApplyOrderRepositoryImpl implements CodeApplyOrderRepository {

    @Resource
    private CodeApplyOrderMapper codeApplyOrderMapper;

    @Override
    public CodeApplyOrder findById(Long id) {
        CodeApplyOrderDo doObj = codeApplyOrderMapper.getById(id);
        return toEntity(doObj);
    }

    @Override
    public boolean store(CodeApplyOrder entity) {
        CodeApplyOrderDo doObj = toDoObject(entity);
        return codeApplyOrderMapper.insert(doObj) > 0;
    }

    @Override
    public boolean update(CodeApplyOrder entity) {
        CodeApplyOrderDo doObj = toDoObject(entity);
        return codeApplyOrderMapper.update(doObj) > 0;
    }

    @Override
    public List<CodeApplyOrder> findByIds(List<Long> ids) {
        return Collections.emptyList();
    }

    public List<CodeApplyOrder> listByCondition(CodeApplyOrderParamPO param) {
        List<CodeApplyOrderDo> doList = codeApplyOrderMapper.listByCondition(param);
        return doList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    private CodeApplyOrderDo toDoObject(CodeApplyOrder entity) {
        CodeApplyOrderDo doObj = new CodeApplyOrderDo();
        BeanUtils.copyProperties(entity, doObj);
        return doObj;
    }

    private CodeApplyOrder toEntity(CodeApplyOrderDo doObj) {
        if (doObj == null) return null;
        CodeApplyOrder entity = new CodeApplyOrder();
        BeanUtils.copyProperties(doObj, entity);
        return entity;
    }
}
