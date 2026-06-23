package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.service.material.model.MaterialDocInvRes;

/**
 * 物料凭证（出入库）领域服务
 * 迁移自: com.enn.inventory.center.biz.service.MaterialDocDomainService
 */
public interface MaterialDocDomainService {
    /** 物料凭证入库 */
    MaterialDocInvRes createMaterialDoc(MaterialDocumentBO materialDocument);
    /** 物料凭证入库（在途转在库） */
    MaterialDocInvRes inboundMaterialDoc(MaterialDocumentBO materialDocument);
    /** 物料凭证出库 */
    MaterialDocInvRes outboundMaterialDoc(MaterialDocumentBO materialDocument);
    /** 物料凭证出入库（同单） */
    MaterialDocInvRes outInboundMaterialDoc(MaterialDocumentBO materialDocument);
    /** 撤销物料凭证 */
    MaterialDocInvRes reverseMaterialDoc(MaterialDocumentBO reverseMaterialDocument);
}
