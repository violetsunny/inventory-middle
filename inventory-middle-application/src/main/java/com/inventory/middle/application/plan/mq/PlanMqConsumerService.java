package com.inventory.middle.application.plan.mq;

import com.inventory.middle.application.plan.mq.message.DemandSupplyMessage;
import com.inventory.middle.application.plan.mq.message.MaterialPlanMessage;
import com.inventory.middle.application.plan.mq.message.ProjectOrderMessage;
import com.inventory.middle.application.plan.mq.message.PurchaseFinishMessage;
import com.inventory.middle.application.plan.mq.message.SystemPlanOrderCreateMessage;

import java.util.List;

public interface PlanMqConsumerService {

    void handleMaterialPlanGenerate(List<MaterialPlanMessage> messages);

    void handleDemandSupplySource(DemandSupplyMessage message);

    void handleDemandSupplySourceBatch(List<DemandSupplyMessage> messages);

    void handleProjectOrder(List<ProjectOrderMessage> messages);

    void handlePurchaseFinish(PurchaseFinishMessage message);

    void handleSystemPlanOrderCreate(SystemPlanOrderCreateMessage message);
}
