package com.inventory.middle.domain.service.outbound;

import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import top.kdla.framework.domain.ApplicationContextHelp;
import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author holmes
 * @Classname MaterialDocOutboundCommonService
 * @Description 出库相关操作的入口
 * @Date 2021/6/23 18:47
 */
@Service
@Slf4j
public class MaterialDocOutboundService extends MaterialDocBaseService {

    private static final String servicePrefix = "material_Doc_Outbound_";
    public static final String serviceSuffix = "_handler_Service";


    @Resource
    MaterialDocOutboundDefaultHandlerService materialDocOutboundDefaultHandlerService;

    /**
     * @param materialDocumentBO
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleOutbound(MaterialDocumentBO materialDocumentBO) {

        //校验
        BaseAssert.notNull(materialDocumentBO, "物料凭证信息为空");
        MaterialAdjustTypeEnum materialAdjustTypeEnum = MaterialAdjustTypeEnum.tansfer(materialDocumentBO.getAdjustType());
        BaseAssert.notNull(materialAdjustTypeEnum, "物料凭证-移动类型为空");
        MaterialDocOutboundDefaultHandlerService handlerService = null;
        try {
            handlerService = ApplicationContextHelp.getBean(buildClassNameOutBound(materialAdjustTypeEnum, servicePrefix, serviceSuffix), MaterialDocOutboundDefaultHandlerService.class);
        } catch (Exception e) {
            log.warn("MaterialDocOutboundDefaultHandlerService bean available",e);
        }

        if (handlerService != null) {
            handlerService.doOutbound(materialDocumentBO);
        } else {
            log.warn("handleOutbound materialDocOutboundAbstractService can not find , default process handler, materialAdjustTypeEnum ={}", materialAdjustTypeEnum);
            materialDocOutboundDefaultHandlerService.doOutbound(materialDocumentBO);
        }

    }
}
