package com.inventory.middle.infra.plan.stub;

import com.inventory.middle.domain.plan.common.bo.PlanProductBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PlanProductStub {

    public Object queryMaterialByCode(String materialCode, String tenantId) {
        log.warn("TODO: PlanProductStub.queryMaterialByCode not implemented");
        return null;
    }

    public Map<String, PlanProductBO> queryProductMap(List<String> materialCodes, String tenant) {
        log.warn("TODO: PlanProductStub.queryProductMap not implemented");
        return Collections.emptyMap();
    }
}
