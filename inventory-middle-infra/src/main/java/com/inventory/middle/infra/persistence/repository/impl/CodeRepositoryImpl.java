package com.inventory.middle.infra.persistence.repository.impl;

import com.inventory.middle.domain.model.entity.Code;
import com.inventory.middle.domain.repository.CodeRepository;
import com.inventory.middle.infra.persistence.entity.CodeDo;
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
        // CodeMapper没有selectById；通过code字段查询请使用findByCode
        return null;
    }

    public Code findByCode(String code) {
        SelectOneCodeParamPO param = new SelectOneCodeParamPO();
        param.setCode(code);
        CodeDo doObj = codeMapper.selectOne(param);
        return toEntity(doObj);
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
}
