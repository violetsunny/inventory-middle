package com.inventory.middle.domain.service.impl;

import com.inventory.middle.domain.service.InventoryMasterDataSourceDomainService;
import com.inventory.middle.client.enums.ProductMasterDataSourceEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.common.util.ProductMasterDataSourceRouteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


/**
 * 库存主数据源获取biz服务实现
 *
 * @author vincent.li
 * @date 2022/4/29 15:42
 */
@Slf4j
@Service
public class InventoryMasterDataSourceDomainServiceImpl implements InventoryMasterDataSourceDomainService {

    @Resource
    private ProductMasterDataSourceRouteUtil productMasterDataSourceRouteUtil;

    /**
     * 根据租户ID获取产品物料主数据源
     *
     * @param tenantId 租户ID
     * @return
     */
    @Override
    public ProductMasterDataSourceEnum getProductMasterDataSource(String tenantId) {
        log.info("InventoryMasterDataSourceDomainServiceImpl.getProductMasterDataSource#tenantId:{}", tenantId);
        if (StringUtils.isEmpty(tenantId)) {
            throw new BusinessException(ResponseCodeEnum.PARAM_IS_NULL.getCode(), "tenantId不能为空");
        }
        if (productMasterDataSourceRouteUtil.isUseInventoryMaterialData(tenantId)) {
            return ProductMasterDataSourceEnum.SCM_INVENTORY_CENTER_M_DATA;
        }
        return ProductMasterDataSourceEnum.PRODUCT_CENTER;
    }
}
