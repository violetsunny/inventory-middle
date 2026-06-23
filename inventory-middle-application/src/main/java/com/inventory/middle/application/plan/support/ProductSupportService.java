package com.inventory.middle.application.plan.support;

import com.inventory.middle.client.plan.dto.product.ProductBO;

import java.util.List;
import java.util.Map;

/**
 * 支撑域-产品域服务接口
 * TODO: 待实现，依赖 ProductCenter 外部服务（通过 PlanProductStub）
 *
 * @author Danny.Lee (migrated from scm-plan-management)
 * @date 2022/4/15
 */
public interface ProductSupportService {

    /**
     * 根据编码集合查询产品集合
     *
     * @param productCodes 产品编码集合
     * @param tenant       租户
     * @return 产品集合
     */
    List<ProductBO> listProducts(List<String> productCodes, String tenant);

    /**
     * 根据编码集合查询产品集合，并做映射
     *
     * @param productCodes 产品编码集合
     * @param tenant       租户
     * @return code → ProductBO 映射
     */
    Map<String, ProductBO> queryProductMap(List<String> productCodes, String tenant);

    /**
     * 根据产品编码查询产品
     *
     * @param productCode 产品编码
     * @param tenant      租户
     * @return 产品
     */
    ProductBO findProduct(String productCode, String tenant);
}
