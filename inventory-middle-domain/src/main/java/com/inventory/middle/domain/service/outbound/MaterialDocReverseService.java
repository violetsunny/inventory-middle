package com.inventory.middle.domain.service.outbound;

import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;

/** 物料凭证反转服务接口 */
public interface MaterialDocReverseService {
    void doReverse(MaterialDocumentBO materialDocumentBO);
}
