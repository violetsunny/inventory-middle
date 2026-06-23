package com.inventory.middle.infra.plan.aop.extension;

import com.inventory.middle.client.plan.dto.ExternalMaterialSupport;
import org.springframework.stereotype.Component;

/**
 * 默认(新奥)外部物料编码填充扩展点实现
 *
 * @author Danny.Lee
 * @date 2022/5/12
 */
@Component
@Extension("1369923265280311297")
public class DefaultExternalMaterialFillingProcessor implements ExternalMaterialFillingProcessor {

    @Override
    public void apply(ExternalMaterialSupport model) {
        model.setExternalMaterialCode(model.getMaterialCode());
    }
}
