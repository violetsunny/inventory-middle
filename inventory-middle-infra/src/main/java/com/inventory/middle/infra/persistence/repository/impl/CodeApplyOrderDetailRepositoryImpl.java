package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.CodeApplyOrderDetail;
import com.inventory.middle.domain.repository.CodeApplyOrderDetailRepository;
import com.inventory.middle.infra.persistence.convertor.CodeApplyOrderDetailConvertor;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDetailDo;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDetailParamPO;
import com.inventory.middle.infra.persistence.mapper.CodeApplyOrderDetailMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 码申请单明细Repository实现类
 */
@Repository
public class CodeApplyOrderDetailRepositoryImpl implements CodeApplyOrderDetailRepository {

    @Resource
    private CodeApplyOrderDetailMapper codeApplyOrderDetailMapper;

    @Resource
    private CodeApplyOrderDetailConvertor codeApplyOrderDetailConvertor;

    @Override
    public CodeApplyOrderDetail findById(Long id) {
        return null;
    }

    @Override
    public boolean store(CodeApplyOrderDetail entity) {
        CodeApplyOrderDetailDo doObj = toDoObject(entity);
        return codeApplyOrderDetailMapper.insert(doObj) > 0;
    }

    @Override
    public boolean update(CodeApplyOrderDetail entity) {
        return false;
    }

    @Override
    public List<CodeApplyOrderDetail> findByIds(List<Long> ids) {
        return Collections.emptyList();
    }

    public List<CodeApplyOrderDetail> listByApplyOrderId(Long applyOrderId) {
        return codeApplyOrderDetailMapper.listByApplyOrderId(applyOrderId)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<CodeApplyOrderDetail> queryByCondition(CodeApplyOrderDetailParamPO param) {
        return codeApplyOrderDetailMapper.queryByCondition(param)
            .stream().map(this::toEntity).collect(Collectors.toList());
    }

    private CodeApplyOrderDetailDo toDoObject(CodeApplyOrderDetail entity) {
        return codeApplyOrderDetailConvertor.toDo(entity);
    }

    private CodeApplyOrderDetail toEntity(CodeApplyOrderDetailDo doObj) {
        if (doObj == null) return null;
        return codeApplyOrderDetailConvertor.toEntity(doObj);
    }
}
