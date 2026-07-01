package com.inventory.middle.domain.service.impl;

import com.inventory.middle.domain.model.entity.Code;
import com.inventory.middle.domain.repository.CodeRepository;
import com.inventory.middle.domain.service.CodeDomainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 备件流转码DomainService实现
 */
@Service
public class CodeDomainServiceImpl implements CodeDomainService {

    @Resource
    private CodeRepository codeRepository;

    @Override
    public void manufacturerInStock(String businessNo, List<Code> codes) {
        for (Code code : codes) {
            code.setBusinessNo(businessNo);
            code.setType("MANUFACTURER");
            code.setStatus("UNUSED");
            code.setInnerCode(UUID.randomUUID().toString().replace("-", ""));
        }
        codeRepository.batchInsert(codes);
    }

    @Override
    public Code regenerateCode(String innerCode, String operatorId) {
        Code oldCode = codeRepository.findByInnerCode(innerCode);
        if (oldCode == null) {
            return null;
        }
        oldCode.setStatus("SCRAPPED");
        oldCode.setUpdatorId(operatorId);
        codeRepository.update(oldCode);

        Code newCode = new Code();
        newCode.setBusinessNo(oldCode.getBusinessNo());
        newCode.setSourceNo(oldCode.getSourceNo());
        newCode.setType(oldCode.getType());
        newCode.setCode(oldCode.getCode());
        newCode.setPublisher(oldCode.getPublisher());
        newCode.setPreOwner(oldCode.getPreOwner());
        newCode.setCurrentOwner(oldCode.getCurrentOwner());
        newCode.setExtendField1(oldCode.getExtendField1());
        newCode.setExtendField2(oldCode.getExtendField2());
        newCode.setExtendParam(oldCode.getExtendParam());
        newCode.setStatus("UNUSED");
        newCode.setInnerCode(UUID.randomUUID().toString().replace("-", ""));
        newCode.setCreatorId(operatorId);
        codeRepository.store(newCode);
        return newCode;
    }
}
