package com.inventory.middle.application.plan.support;

import com.inventory.middle.client.plan.dto.product.ProductBO;
import com.inventory.middle.domain.plan.common.bo.PlanProductBO;
import com.inventory.middle.infra.plan.stub.PlanProductStub;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSupportServiceImpl implements ProductSupportService {

    @Resource
    private PlanProductStub planProductStub;

    @Override
    public List<ProductBO> listProducts(List<String> productCodes, String tenant) {
        if (CollectionUtils.isEmpty(productCodes)) {
            return Collections.emptyList();
        }
        Map<String, PlanProductBO> productMap = planProductStub.queryProductMap(productCodes, tenant);
        return productMap.values().stream()
                .map(this::toProductBO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, ProductBO> queryProductMap(List<String> productCodes, String tenant) {
        if (CollectionUtils.isEmpty(productCodes)) {
            return Collections.emptyMap();
        }
        Map<String, PlanProductBO> productMap = planProductStub.queryProductMap(productCodes, tenant);
        return productMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> toProductBO(e.getValue()),
                        (v1, v2) -> v1
                ));
    }

    @Override
    public ProductBO findProduct(String productCode, String tenant) {
        PlanProductBO planProductBO = planProductStub.queryMaterialByCode(productCode, tenant);
        if (planProductBO == null) {
            return null;
        }
        return toProductBO(planProductBO);
    }

    private ProductBO toProductBO(PlanProductBO src) {
        ProductBO target = new ProductBO();
        target.setId(src.getId());
        target.setName(src.getName());
        target.setCode(src.getCode());
        target.setOutMaterialCode(src.getOutMaterialCode());
        target.setDesc(src.getDesc());
        target.setUnit(src.getUnit());
        return target;
    }
}
