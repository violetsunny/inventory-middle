package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.entity.Code;
import java.util.List;

/**
 * 备件流转码DomainService
 */
public interface CodeDomainService {

    /**
     * 厂商入库：批量生成码记录
     */
    void manufacturerInStock(String businessNo, List<Code> codes);

    /**
     * 重新生成码：废弃旧码 + 生成新码
     */
    Code regenerateCode(String innerCode, String operatorId);
}
