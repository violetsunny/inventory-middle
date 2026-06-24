package com.inventory.middle.infra.plan.stub;

import com.inventory.middle.domain.plan.common.bo.PlanProductBO;
import com.inventory.middle.domain.service.external.RemoteProductCenterRestService;
import com.inventory.middle.domain.service.external.dto.SkuResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanProductStub {

    @Resource
    private RemoteProductCenterRestService remoteProductCenterRestService;

    public PlanProductBO queryMaterialByCode(String materialCode, String tenantId) {
        try {
            List<SkuResponse> skuList = remoteProductCenterRestService.skuListByRequest(
                    Collections.singletonList(materialCode), null, tenantId);
            if (CollectionUtils.isNotEmpty(skuList)) {
                return toPlanProductBO(skuList.get(0));
            }
        } catch (Exception e) {
            log.warn("queryMaterialByCode failed, materialCode={}", materialCode, e);
        }
        return null;
    }

    public Map<String, PlanProductBO> queryProductMap(List<String> materialCodes, String tenant) {
        try {
            List<SkuResponse> skuList = remoteProductCenterRestService.skuListByRequest(
                    materialCodes, null, tenant);
            if (CollectionUtils.isNotEmpty(skuList)) {
                return skuList.stream()
                        .map(this::toPlanProductBO)
                        .collect(Collectors.toMap(PlanProductBO::getCode, bo -> bo, (v1, v2) -> v1));
            }
        } catch (Exception e) {
            log.warn("queryProductMap failed, materialCodes={}", materialCodes, e);
        }
        return Collections.emptyMap();
    }

    private PlanProductBO toPlanProductBO(SkuResponse sku) {
        PlanProductBO bo = new PlanProductBO();
        bo.setId(sku.getId());
        bo.setName(sku.getName());
        bo.setCode(sku.getCode());
        bo.setDesc(sku.getDescription());
        bo.setUnit(sku.getUnitName() != null ? sku.getUnitName() : sku.getUnit());
        return bo;
    }
}
