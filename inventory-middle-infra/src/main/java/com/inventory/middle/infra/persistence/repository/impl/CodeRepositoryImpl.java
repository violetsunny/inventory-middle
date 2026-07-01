package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.Code;
import com.inventory.middle.domain.repository.CodeQueryParam;
import com.inventory.middle.domain.repository.CodeRepository;
import com.inventory.middle.infra.persistence.entity.CodeDo;
import com.inventory.middle.infra.persistence.entity.ListCodeParamPO;
import com.inventory.middle.infra.persistence.entity.SelectOneCodeParam2PO;
import com.inventory.middle.infra.persistence.entity.SelectOneCodeParamPO;
import com.inventory.middle.infra.persistence.mapper.CodeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 备件流转码Repository实现类
 */
@Repository
public class CodeRepositoryImpl implements CodeRepository {

    @Resource
    private CodeMapper codeMapper;

    @Override
    public Code findById(Long id) {
        return null;
    }

    @Override
    public Code findByCode(String code) {
        SelectOneCodeParamPO param = new SelectOneCodeParamPO();
        param.setCode(code);
        CodeDo doObj = codeMapper.selectOne(param);
        return toEntity(doObj);
    }

    @Override
    public Code findByInnerCode(String innerCode) {
        SelectOneCodeParam2PO param = new SelectOneCodeParam2PO();
        param.setInnerCode(innerCode);
        CodeDo doObj = codeMapper.selectByInnerCode(param);
        return toEntity(doObj);
    }

    @Override
    public List<Code> listByInnerCodes(List<String> innerCodes) {
        if (innerCodes == null || innerCodes.isEmpty()) {
            return Collections.emptyList();
        }
        ListCodeParamPO param = new ListCodeParamPO();
        param.setInnerCodeList(innerCodes);
        return codeMapper.list(param).stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Code> listBySourceAndBusiness(String sourceNo, String businessNo) {
        ListCodeParamPO param = new ListCodeParamPO();
        param.setSourceNo(sourceNo);
        param.setBusinessNo(businessNo);
        return codeMapper.list(param).stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Code> list(CodeQueryParam param) {
        if (param == null) {
            return Collections.emptyList();
        }
        ListCodeParamPO po = toListCodeParamPO(param);
        return codeMapper.list(po).stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public boolean store(Code entity) {
        CodeDo doObj = toDoObject(entity);
        return codeMapper.insert(doObj) > 0;
    }

    @Override
    public boolean update(Code entity) {
        CodeDo doObj = toDoObject(entity);
        return codeMapper.updateByIdSelective(doObj) > 0;
    }

    @Override
    public List<Code> findByIds(List<Long> ids) {
        return Collections.emptyList();
    }

    @Override
    public void batchInsert(List<Code> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        List<CodeDo> doList = entities.stream().map(this::toDoObject).collect(Collectors.toList());
        codeMapper.batchInsert(doList);
    }

    @Override
    public void batchUpdate(List<Code> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        List<CodeDo> doList = entities.stream().map(this::toDoObject).collect(Collectors.toList());
        codeMapper.batchUpdate(doList);
    }

    private CodeDo toDoObject(Code entity) {
        CodeDo doObj = new CodeDo();
        BeanUtils.copyProperties(entity, doObj);
        return doObj;
    }

    private Code toEntity(CodeDo doObj) {
        if (doObj == null) return null;
        Code entity = new Code();
        BeanUtils.copyProperties(doObj, entity);
        return entity;
    }

    private ListCodeParamPO toListCodeParamPO(CodeQueryParam param) {
        ListCodeParamPO po = new ListCodeParamPO();
        BeanUtils.copyProperties(param, po);
        return po;
    }
}
