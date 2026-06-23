package com.inventory.middle.domain.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/** 产品物料主数据路由工具类（迁移自 ProductMasterDataSourceRouteUtil） */
@Slf4j
@Component
public class ProductMasterDataSourceRouteUtil {
    @Value("${inventory.use.inventory.center.m.data.tenantIds:}")
    public List<String> useInventoryCenterMDataTenantIds;

    public Boolean isUseInventoryMaterialData(String tenantId) {
        if (!StringUtils.hasText(tenantId) || CollectionUtils.isEmpty(useInventoryCenterMDataTenantIds)) {
            return Boolean.FALSE;
        }
        return useInventoryCenterMDataTenantIds.contains(tenantId);
    }
}
