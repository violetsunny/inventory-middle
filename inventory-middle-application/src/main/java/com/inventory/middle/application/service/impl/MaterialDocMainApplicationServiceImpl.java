package com.inventory.middle.application.service.impl;

import com.inventory.middle.application.convertor.MaterialDocMainDtoConvertor;
import com.inventory.middle.application.convertor.MaterialDocumentConvertor;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.client.dto.command.MaterialDocMainCommand;
import com.inventory.middle.client.dto.material.MaterialDocumentDTO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocCancelMessage;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocInMessage;
import com.inventory.middle.domain.repository.MaterialDocMainRepository;
import com.inventory.middle.domain.repository.MdocSubExtRepository;
import com.inventory.middle.domain.service.MaterialDocDomainService;
import com.inventory.middle.domain.service.material.model.MaterialDocInvRes;
import com.inventory.middle.domain.specification.MaterialDocMainUpdateSpecification;
import com.inventory.middle.domain.model.entity.MaterialDocMain;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.model.types.MaterialDocMainId;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import top.kdla.framework.domain.ApplicationContextHelp;
import top.kdla.framework.dto.SingleResponse;

/**
 * 物料凭证主表ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MaterialDocMainApplicationServiceImpl implements MaterialDocMainApplicationService {

	@Resource
	private MaterialDocMainRepository materialdocmainRepository;

	@Resource
	private MaterialDocMainDtoConvertor dtoConvertor;
	@Resource
	private MaterialDocDomainService materialDocDomainService;
	@Resource
	private com.inventory.middle.domain.repository.MdocSubExtRepository mdocSubExtRepository;
	@Resource
	private MaterialDocumentConvertor materialDocumentConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MaterialDocMainCommand materialdocmainCommand) {
		MaterialDocMain materialdocmain = dtoConvertor.toMaterialDocMain(materialdocmainCommand);
		return materialdocmainRepository.store(materialdocmain);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MaterialDocMainCommand materialdocmainCommand) {
		MaterialDocMain materialdocmain = dtoConvertor.toMaterialDocMain(materialdocmainCommand);
		MaterialDocMainUpdateSpecification materialdocmainUpdateSpecification = new MaterialDocMainUpdateSpecification();
		materialdocmainUpdateSpecification.isSatisfiedBy(materialdocmain);
		return  materialdocmainRepository.store(materialdocmain );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MaterialDocMainId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MaterialDocMainId(id));
		});
		return  materialdocmainRepository.delete(tempIds);
	}

        @Override
        @StopWatchWrapper(logHead = "MaterialDocApp", msg = "createMaterialDocIn")
        public SingleResponse<MaterialDocInvRes> createMaterialDocIn(MaterialDocumentBO materialDocument) {
                log.info("MaterialDocMainApplicationServiceImpl.createMaterialDocIn cmd={}", JSON.toJSONString(materialDocument));
                MaterialDocInvRes res = materialDocDomainService.createMaterialDoc(materialDocument);
                return SingleResponse.of(res);
        }

        @Override
        @StopWatchWrapper(logHead = "MaterialDocApp", msg = "createMaterialDocOut")
        public SingleResponse<MaterialDocInvRes> createMaterialDocOut(MaterialDocumentBO materialDocument) {
                log.info("MaterialDocMainApplicationServiceImpl.createMaterialDocOut cmd={}", JSON.toJSONString(materialDocument));
                MaterialDocInvRes res = materialDocDomainService.outboundMaterialDoc(materialDocument);
                return SingleResponse.of(res);
        }

        @Override
        public SingleResponse<MaterialDocInvRes> createMaterialDocOutIn(MaterialDocumentBO materialDocument) {
                log.info("MaterialDocMainApplicationServiceImpl.createMaterialDocOutIn cmd={}", JSON.toJSONString(materialDocument));
                MaterialDocInvRes res = materialDocDomainService.outInboundMaterialDoc(materialDocument);
                return SingleResponse.of(res);
        }

        @Override
        @StopWatchWrapper(logHead = "MaterialDocApp", msg = "reverseMaterialDoc")
        public SingleResponse<MaterialDocInvRes> reverseMaterialDoc(MaterialDocumentBO materialDocument) {
                log.info("MaterialDocMainApplicationServiceImpl.reverseMaterialDoc cmd={}", JSON.toJSONString(materialDocument));
                MaterialDocInvRes res = materialDocDomainService.reverseMaterialDoc(materialDocument);
                return SingleResponse.of(res);
        }

        @Override
        public SingleResponse<MaterialDocInvRes> createMaterialDocFromMessage(MaterialDocInMessage message) {
                log.info("MaterialDocMainApplicationServiceImpl.createMaterialDocFromMessage channel={}", message.getChannel());
                try {
                        IHandleChain handleChain = ApplicationContextHelp.getBean("snMaterialDocInBuilderHandleChain", IHandleChain.class);
                        HandleMessage msg = new HandleMessage();
                        msg.setT(message);
                        handleChain.doProcess(msg);
                        MaterialDocumentDTO documentDTO = (MaterialDocumentDTO) msg.getE();
                        MaterialDocumentBO documentBO = materialDocumentConvertor.toBO(documentDTO);
                        MaterialDocInvRes res = materialDocDomainService.createMaterialDoc(documentBO);
                        return SingleResponse.of(res);
                } catch (Exception e) {
                        log.error("createMaterialDocFromMessage failed", e);
                        throw e;
                }
        }

        @Override
        public SingleResponse<MaterialDocInvRes> reverseMaterialDocFromMessage(MaterialDocCancelMessage message) {
                log.info("MaterialDocMainApplicationServiceImpl.reverseMaterialDocFromMessage materialDocNo={}", message.getMaterialDocNo());
                try {
                        IHandleChain handleChain = ApplicationContextHelp.getBean("snMaterialDocCancelBuilderHandleChain", IHandleChain.class);
                        HandleMessage msg = new HandleMessage();
                        msg.setT(message);
                        handleChain.doProcess(msg);
                        MaterialDocumentDTO documentDTO = (MaterialDocumentDTO) msg.getE();
                        MaterialDocumentBO documentBO = materialDocumentConvertor.toBO(documentDTO);
                        MaterialDocInvRes res = materialDocDomainService.reverseMaterialDoc(documentBO);
                        return SingleResponse.of(res);
                } catch (Exception e) {
                        log.error("reverseMaterialDocFromMessage failed", e);
                        throw e;
                }
        }

        @Override
        public String getMaterialDocId() {
                return java.util.UUID.randomUUID().toString().replace("-", "");
        }

        @Override
        public boolean checkMaterialDoc(com.inventory.middle.domain.model.bo.material.MaterialDocumentBO bo) {
                if (bo == null) {
                        throw new com.inventory.middle.domain.common.exception.BusinessException("物料凭证请求体不能为空");
                }
                if (org.apache.commons.lang3.StringUtils.isBlank(bo.getTenantId())) {
                        throw new com.inventory.middle.domain.common.exception.BusinessException("租户ID不能为空");
                }
                if (bo.getMaterialDocumentItems() == null || bo.getMaterialDocumentItems().isEmpty()) {
                        throw new com.inventory.middle.domain.common.exception.BusinessException("物料凭证明细不能为空");
                }
                return true;
        }

        @Override
        public boolean updateAnnualDate(com.inventory.middle.domain.model.bo.material.UpdateMaterialAnnualDateReqBO bo) {
                if (bo == null || org.apache.commons.lang3.StringUtils.isBlank(bo.getMaterialDocNo())) {
                        throw new com.inventory.middle.domain.common.exception.BusinessException("物料凭证编号不能为空");
                }
                com.inventory.middle.domain.model.entity.MaterialDocMain main =
                        materialdocmainRepository.findByMaterialDocNo(bo.getMaterialDocNo());
                if (main == null || main.getId() == null) {
                        throw new com.inventory.middle.domain.common.exception.BusinessException(
                                "物料凭证不存在: " + bo.getMaterialDocNo());
                }
                Long materialDocId = main.getId().get();
                java.util.List<com.inventory.middle.domain.model.entity.MdocSubExt> extList =
                        mdocSubExtRepository.findByMaterialDocId(materialDocId);
                if (extList == null || extList.isEmpty()) {
                        log.warn("updateAnnualDate: no MdocSubExt found for materialDocId={}", materialDocId);
                        return false;
                }
                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                for (com.inventory.middle.domain.model.entity.MdocSubExt ext : extList) {
                        ext.setAnnualDate(now);
                        mdocSubExtRepository.update(ext);
                }
                return true;
        }

}
