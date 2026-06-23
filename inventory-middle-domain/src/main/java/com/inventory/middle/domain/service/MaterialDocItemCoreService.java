package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;

import java.util.List;

/**
 * @author holmes
 * @Classname MaterialDocItemCoreService
 * @Date 2021/6/21 22:14
 */
public interface MaterialDocItemCoreService {

     List<MaterialDocumentItemBO> findSkuBatchNosByMdocId(Long mdocId);

}
