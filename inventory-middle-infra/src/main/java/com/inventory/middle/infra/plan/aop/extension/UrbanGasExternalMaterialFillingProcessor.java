package com.inventory.middle.infra.plan.aop.extension;

import com.inventory.middle.client.plan.dto.ExternalMaterialSupport;
import org.springframework.stereotype.Component;

/**
 * 城燃外部物料编码填充扩展点实现
 *
 * @author Danny.Lee
 * @date 2022/5/12
 */
@Component
@Extension({"1458969781147373569", "2735622725099421696"})
public class UrbanGasExternalMaterialFillingProcessor implements ExternalMaterialFillingProcessor {

    @Override
    public void apply(ExternalMaterialSupport model) {
        model.setExternalMaterialCode(model.getMaterialCode());
        model.setMaterialCode(null);
    }
}
