package com.inventory.middle.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.service.InventoryMaterialDomainService;
import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.domain.service.external.RemoteInventoryMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存物料biz服务实现
 * @author vincent.li
 * @date 2022/5/11 11:06
 */
@Slf4j
@Service
public class InventoryMaterialDomainServiceImpl implements InventoryMaterialDomainService {

    @Resource
    private RemoteInventoryMaterialService remoteInventoryMaterialService;

    @Override
    public ArrayList<InventoryMaterialDTO> listByMaterialCodeList(String tenantId, List<String> materialCodeList, List<String> outMaterialCodeList) {
        log.info("InventoryMaterialDomainServiceImpl.listByMaterialCodeList#tenantId:{},materialCodeList:{},outMaterialCodeList:{}",
            tenantId, JSON.toJSONString(materialCodeList), JSON.toJSONString(outMaterialCodeList));
        return remoteInventoryMaterialService.listByMaterialCodeList(tenantId, materialCodeList, outMaterialCodeList);
    }
}
