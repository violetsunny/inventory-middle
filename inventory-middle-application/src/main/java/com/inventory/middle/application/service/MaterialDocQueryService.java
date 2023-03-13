/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package com.inventory.middle.application.service;

import com.inventory.middle.client.dto.material.MaterialBatchNoResDto;
import com.inventory.middle.client.dto.material.MaterialMappingDto;
import com.inventory.middle.client.dto.query.MaterialBatchNoQuery;
import com.inventory.middle.client.dto.query.MaterialMappingQuery;

/**
 * @author kanglele
 * @version $Id: MaterialDocQueryService, v 0.1 2023/3/13 16:55 kanglele Exp $
 */
public interface MaterialDocQueryService {

    /**
     * 获取移动类型映射关系
     * @param req
     * @return
     */
    MaterialMappingDto queryMaterialTypeMapping(MaterialMappingQuery req);

    /**
     * 查询物料批次
     * @param query
     * @return
     */
    MaterialBatchNoResDto queryMaterialBatchNo(MaterialBatchNoQuery query);

}
