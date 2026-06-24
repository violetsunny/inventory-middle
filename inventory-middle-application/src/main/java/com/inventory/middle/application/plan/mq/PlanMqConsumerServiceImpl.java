package com.inventory.middle.application.plan.mq;

import com.inventory.middle.application.plan.demand.bo.OrderDemandBO;
import com.inventory.middle.application.plan.demand.service.ProjectOrderDemandService;
import com.inventory.middle.application.plan.mq.message.DemandSupplyMessage;
import com.inventory.middle.application.plan.mq.message.MaterialPlanMessage;
import com.inventory.middle.application.plan.mq.message.ProjectOrderMessage;
import com.inventory.middle.application.plan.mq.message.PurchaseFinishMessage;
import com.inventory.middle.application.plan.mq.message.SystemPlanOrderCreateMessage;
import com.inventory.middle.infra.plan.persistence.dao.DemandSupplySourceDao;
import com.inventory.middle.infra.plan.persistence.entity.DemandSupplySourcePO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanMqConsumerServiceImpl implements PlanMqConsumerService {

    @Resource
    private DemandSupplySourceDao demandSupplySourceDao;

    @Resource
    private ProjectOrderDemandService projectOrderDemandService;

    @Override
    public void handleMaterialPlanGenerate(List<MaterialPlanMessage> messages) {
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }
        for (MaterialPlanMessage msg : messages) {
            log.info("handleMaterialPlanGenerate materialCode={}, logicalPlantNo={}, tenantId={}",
                    msg.getMaterialCode(), msg.getLogicalPlantNo(), msg.getTenantId());
        }
    }

    @Override
    public void handleDemandSupplySource(DemandSupplyMessage message) {
        DemandSupplySourcePO po = toDemandSupplySourcePO(message);
        demandSupplySourceDao.insert(po);
    }

    @Override
    public void handleDemandSupplySourceBatch(List<DemandSupplyMessage> messages) {
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }
        List<DemandSupplySourcePO> poList = messages.stream()
                .map(this::toDemandSupplySourcePO)
                .collect(Collectors.toList());
        demandSupplySourceDao.batchInsert(poList);
    }

    @Override
    public void handleProjectOrder(List<ProjectOrderMessage> messages) {
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }
        List<OrderDemandBO> orderDemandBOList = messages.stream()
                .map(this::toOrderDemandBO)
                .collect(Collectors.toList());
        projectOrderDemandService.insertProjectOrderPlan(orderDemandBOList);
    }

    @Override
    public void handlePurchaseFinish(PurchaseFinishMessage message) {
        log.info("handlePurchaseFinish bizNo={}, sourceNo={}", message.getBizNo(), message.getSourceNo());
    }

    @Override
    public void handleSystemPlanOrderCreate(SystemPlanOrderCreateMessage message) {
        log.info("handleSystemPlanOrderCreate planId={}, materialCode={}", message.getPlanId(), message.getMaterialCode());
    }

    private DemandSupplySourcePO toDemandSupplySourcePO(DemandSupplyMessage msg) {
        DemandSupplySourcePO po = new DemandSupplySourcePO();
        po.setMaterialCode(msg.getMaterialCode());
        po.setLogicalPlantNo(msg.getLogicalPlantNo());
        po.setTenantId(msg.getTenantId());
        po.setPlanDate(msg.getPlanDate());
        po.setSourceType(msg.getSourceType());
        po.setBizType(msg.getBizType());
        po.setAmount(msg.getAmount());
        po.setVoucherNo(msg.getVoucherNo());
        po.setDocumentNo(msg.getDocumentNo());
        po.setBusinessIdRef(msg.getBusinessIdRef());
        po.setIsDelete(0);
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        return po;
    }

    private OrderDemandBO toOrderDemandBO(ProjectOrderMessage msg) {
        OrderDemandBO bo = new OrderDemandBO();
        bo.setOrderNo(msg.getOrderNo());
        bo.setOrderType(1);
        bo.setMaterialCode(msg.getMaterialCode());
        bo.setLogicalPlantNo(msg.getLogicalPlantNo());
        bo.setTenantId(msg.getTenantId());
        bo.setPlanDate(msg.getPlanDate());
        bo.setAmount(msg.getAmount());
        bo.setCreateTime(msg.getCreateTime());
        return bo;
    }
}
