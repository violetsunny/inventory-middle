package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.transit.CreateInTransitStockRequest;
import com.inventory.middle.client.dto.transit.InTransitStockDTO;
import com.inventory.middle.client.dto.transit.InTransitStockPageRequest;
import com.inventory.middle.client.dto.transit.TransferTransitStockRequest;

import java.util.List;

/**
 * @author dongguo.tao
 * @description 在途库存相关的服务
 * @date 2021-09-27 17:55:14
 */
public interface InTransitStockService {


    /**
     * 创建在途库存
     * @param requestList
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> batchCreate(List<CreateInTransitStockRequest> requestList);


    /**
     * 在途库存转在库库存
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> transferInStock(TransferTransitStockRequest request);


    /**
     * 分页查询在途库存
     * @param pageRequest
     * @return
     */
    top.kdla.framework.dto.PageResponse<InTransitStockDTO> pagedInTransitStock(InTransitStockPageRequest pageRequest);
}
