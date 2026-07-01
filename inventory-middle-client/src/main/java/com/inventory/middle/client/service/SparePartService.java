package com.inventory.middle.client.service;


import com.inventory.middle.client.dto.sparepart.PageSparePartReqDTO;
import com.inventory.middle.client.dto.sparepart.PageSparePartResDTO;

/**
 * @description 备品备件
 * @author peisheng.wang
 * @date 2021-06-16
 */
public interface SparePartService {

    /**
     * 备品备件入库信息查询
     * @param reqDto PageSparePartReqDTO
     * @return top.kdla.framework.dto.PageResponse<PageSparePartResDTO>
     */
    top.kdla.framework.dto.PageResponse<PageSparePartResDTO> pageList(PageSparePartReqDTO reqDto);

}
