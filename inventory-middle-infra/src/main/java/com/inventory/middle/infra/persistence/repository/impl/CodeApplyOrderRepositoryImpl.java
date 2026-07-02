package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.CodeApplyOrder;
import com.inventory.middle.domain.repository.CodeApplyOrderQueryParam;
import com.inventory.middle.domain.repository.CodeApplyOrderRepository;
import com.inventory.middle.infra.persistence.convertor.CodeApplyOrderConvertor;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDo;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderParamPO;
import com.inventory.middle.infra.persistence.mapper.CodeApplyOrderMapper;
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

    @Resource
    private CodeApplyOrderConvertor codeApplyOrderConvertor;

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

    @Override
    public List<CodeApplyOrder> listByCondition(CodeApplyOrderQueryParam queryParam) {
        CodeApplyOrderParamPO param = toParamPO(queryParam);
        List<CodeApplyOrderDo> doList = codeApplyOrderMapper.listByCondition(param);
        return doList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    private CodeApplyOrderParamPO toParamPO(CodeApplyOrderQueryParam queryParam) {
        return codeApplyOrderConvertor.toParamPO(queryParam);
    }

    private CodeApplyOrderDo toDoObject(CodeApplyOrder entity) {
        return codeApplyOrderConvertor.toDo(entity);
    }

    private CodeApplyOrder toEntity(CodeApplyOrderDo doObj) {
        if (doObj == null) return null;
        return codeApplyOrderConvertor.toEntity(doObj);
    }
}
