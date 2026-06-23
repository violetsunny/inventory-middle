package com.inventory.middle.application.plan.plan.config.rule.chain;

import cn.hutool.core.collection.CollectionUtil;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParamBatchCreateDetailBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParamBatchCreateReqBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParamBatchCreateResBO;
import com.inventory.middle.domain.plan.common.rule.AbstractValidatorChain;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.plan.config.rule.validator.BatchCreatePlanMaterialParamExistValidator;
import com.inventory.middle.application.plan.plan.config.rule.validator.BatchCreatePlanMaterialParamFormatValidator;
import com.inventory.middle.application.plan.plan.config.rule.validator.BatchCreatePlanMaterialParamMaterialPlantRefValidator;
import com.inventory.middle.application.plan.plan.config.rule.validator.BatchCreatePlanMaterialParamPlantNoExistValidator;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量创建物料参数 校验器链
 * @date 2021/10/8 15:49
 */
@Slf4j
@Component
public class BatchCreatePlanMaterialParamValidatorChain extends AbstractValidatorChain {

    @Resource
    private BatchCreatePlanMaterialParamFormatValidator batchCreatePlanMaterialParamFormatValidator;

    @Resource
    private BatchCreatePlanMaterialParamPlantNoExistValidator batchCreatePlanMaterialParamPlantNoExistValidator;

    @Resource
    private BatchCreatePlanMaterialParamExistValidator batchCreatePlanMaterialParamExistValidator;

    @Resource
    private BatchCreatePlanMaterialParamMaterialPlantRefValidator batchCreatePlanMaterialParamMaterialPlantRefValidator;

    @Override
    public ValidateMessage doProcess(ValidateMessage originalMessage){
        // 校验入参
        PlanMaterialParamBatchCreateReqBO reqBO = (PlanMaterialParamBatchCreateReqBO)originalMessage.getT();
        // 所有校验器校验失败的集合
        List<PlanMaterialParamBatchCreateDetailBO> totalFailedDetails = Lists.newArrayList();
        // 参与校验总数
        int totalCount = reqBO.getPlanMaterialParamList().size();
        // 循环校验
        if (CollectionUtil.isNotEmpty(validators)) {
            for (IValidator validator : validators) {
                // 开始校验
                ValidateMessage resultMessage = validator.doValidate(originalMessage);
                // 处理校验失败的集合
                List<PlanMaterialParamBatchCreateDetailBO> failedDetails = (List<PlanMaterialParamBatchCreateDetailBO>)resultMessage.getE();
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
        PlanMaterialParamBatchCreateResBO resBO = PlanMaterialParamBatchCreateResBO.builder()
                .totalCount(totalCount)
                .failedCount(totalFailedDetails.size())
                .failedDetails(totalFailedDetails).build();
        return ValidateMessage.builder().e(resBO).build();
    }


    @Override
    protected void initChain() {
        validators.add(batchCreatePlanMaterialParamFormatValidator);
        validators.add(batchCreatePlanMaterialParamPlantNoExistValidator);
        validators.add(batchCreatePlanMaterialParamExistValidator);
        validators.add(batchCreatePlanMaterialParamMaterialPlantRefValidator);
    }

}