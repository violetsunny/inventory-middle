package com.inventory.middle.application.plan.config.rule.chain;

import cn.hutool.core.collection.CollectionUtil;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateDetailBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateReqBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateResBO;
import com.inventory.middle.domain.plan.common.rule.AbstractValidatorChain;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.rule.validator.*;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量创建计划方案物料验证器链
 * @date 2021/10/2 20:42
 */
@Component
public class BatchCreatePlanMaterialValidatorChain extends AbstractValidatorChain {


    @Resource
    private BatchCreatePlanMaterialPlanValidator planValidator;

    @Resource
    private BatchCreatePlanMaterialLogicalPlantValidator logicalPlantValidator;

    @Resource
    private BatchCreatePlanMaterialSkuCodeValidator skuCodeValidator;

    @Resource
    private BatchCreatePlanMaterialPlantRefValidator materialPlantRefValidator;

    @Resource
    private BatchCreatePlanMaterialInExecValidator materialInExecValidator;


    @Override
    public ValidateMessage doProcess(ValidateMessage  originalMessage){
        // 校验入参
        PlanMaterialBatchCreateReqBO reqBO = (PlanMaterialBatchCreateReqBO)originalMessage.getT();
        // 所有校验器校验失败的集合
        List<PlanMaterialBatchCreateDetailBO> totalFailedDetails = Lists.newArrayList();
        // 参与校验总数
        int totalCount = reqBO.getPlanMaterialList().size();
        // 循环校验
        if (CollectionUtil.isNotEmpty(validators)) {
            for (IValidator validator : validators) {
                // 开始校验
                ValidateMessage resultMessage = validator.doValidate(originalMessage);
                // 处理校验失败的集合
                List<PlanMaterialBatchCreateDetailBO> failedDetails = (List<PlanMaterialBatchCreateDetailBO>)resultMessage.getE();
                totalFailedDetails.addAll(failedDetails);
                // 设置校验成功的数据 继续往下校验
                originalMessage.setT(resultMessage.getT());
                // 如果所有的数据都校验失败则退出循环
                if (totalFailedDetails.size() == totalCount){
                    break;
                }
            }
        }
        // 封装校验结果
        PlanMaterialBatchCreateResBO resBO = new PlanMaterialBatchCreateResBO();
        resBO.setTotalCount(totalCount);
        resBO.setFailedDetails(totalFailedDetails);
        resBO.setFailedCount(totalFailedDetails.size());
        return ValidateMessage.builder().e(resBO).build();
    }

    @Override
    protected void initChain() {
        validators.add(planValidator);
        validators.add(logicalPlantValidator);
        validators.add(skuCodeValidator);
        validators.add(materialPlantRefValidator);
        validators.add(materialInExecValidator);
    }
}