package com.inventory.middle.application.plan.order.service.impl;

import top.kdla.framework.validator.BaseAssert;
import com.github.pagehelper.PageInfo;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamQueryReqBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.config.service.PlanConfigService;
import com.inventory.middle.application.plan.order.convertor.PlanOrderConvertor;
import com.inventory.middle.application.plan.order.service.PlanOrderApplicationService;
import com.inventory.middle.client.plan.config.dto.ManualPlanOrderCreateDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderIssueDetailDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderIssueDetailPageReqDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderIssueReqDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderPageReqDTO;
import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.plan.mq.DefaultMqProducer;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.domain.plan.common.enums.MqTagEnum;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanOrderStatusEnum;
import com.inventory.middle.infra.plan.aop.anno.ExternalMaterialProcess;
import com.inventory.middle.infra.plan.persistence.condition.PlanOrderIssueDetailPageCondition;
import com.inventory.middle.infra.plan.persistence.condition.PlanOrderPageCondition;
import com.inventory.middle.infra.plan.persistence.dao.PlanOrderDao;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderIssueDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * 计划订单应用服务。
 */
@Service
@Slf4j
public class PlanOrderApplicationServiceImpl implements PlanOrderApplicationService {

    private static final String PLAN_ORDER_PREFIX = "PO";
    private static final String PLAN_ORDER_ISSUE_PREFIX = "PIO";

    @Resource
    private PlanOrderDao planOrderDao;

    @Resource
    private DefaultMqProducer defaultMqProducer;

    @Resource
    private PlanConfigService planConfigService;

    @Override
    public SingleResponse<Boolean> createManualPlanOrder(ManualPlanOrderCreateDTO dto) {
        BaseAssert.notNull(dto, "计划订单参数不能为空");
        PlanMaterialParamQueryReqBO request = new PlanMaterialParamQueryReqBO();
        request.setTenantId(dto.getTenantId());
        request.setMaterialCode(dto.getMaterialCode());
        request.setLogicalPlantNo(dto.getLogicalPlantNo());
        PlanMaterialParameterBO planMaterialParameter = planConfigService.queryByMaterialCodeAndLogicalPlantNo(request);
        BaseAssert.notNull(planMaterialParameter, "物料计划参数不存在，无法创建计划订单");
        PlanOrderPO po = PlanOrderConvertor.toManualCreatePO(dto, generateNo(PLAN_ORDER_PREFIX), planMaterialParameter.getPlanTypeCode());
        return SingleResponse.of(planOrderDao.createManualPlanOrder(po));
    }

    @Override
    @ExternalMaterialProcess(patterns = "data")
    public SingleResponse<PlanOrderDTO> queryPlanOrderById(Long id, String tenantId) {
        return SingleResponse.of(PlanOrderConvertor.toDTO(planOrderDao.queryPlanOrderById(id, tenantId)));
    }

    @Override
    public SingleResponse<Boolean> updatePlanOrder(PlanOrderDTO dto) {
        BaseAssert.notNull(dto, "计划订单参数不能为空");
        BaseAssert.notNull(dto.getId(), "计划订单ID不能为空");
        PlanOrderPO current = planOrderDao.queryPlanOrderById(dto.getId(), dto.getTenantId());
        BaseAssert.notNull(current, "计划订单不存在");
        PlanOrderConvertor.mergeForUpdate(dto, current);
        return SingleResponse.of(planOrderDao.updatePlanOrder(current));
    }

    @Override
    @ExternalMaterialProcess(patterns = "data")
    public PageResponse<PlanOrderDTO> pageQueryPlanOrder(PlanOrderPageReqDTO dto) {
        PlanOrderPageCondition condition = PlanOrderConvertor.toPageCondition(dto);
        PageInfo<PlanOrderPO> pageInfo = planOrderDao.pageQueryPlanOrder(condition);
        List<PlanOrderDTO> data = pageInfo.getList().stream().map(PlanOrderConvertor::toDTO).collect(Collectors.toList());
        return PageResponse.of(data, pageInfo.getTotal(), dto.getPageSize(), dto.getPageNum());
    }

    @Override
    public SingleResponse<Boolean> confirmPlanOrder(Long id, String tenantId) {
        PlanOrderPO current = planOrderDao.queryPlanOrderById(id, tenantId);
        BaseAssert.notNull(current, "计划订单不存在");
        return SingleResponse.of(planOrderDao.confirmPlanOrder(id));
    }

    @Override
    public SingleResponse<Boolean> issuePlanOrder(PlanOrderIssueReqDTO dto) {
        BaseAssert.notNull(dto, "下发参数不能为空");
        BaseAssert.notNull(dto.getPlanOrderId(), "计划订单ID不能为空");
        BaseAssert.notNull(dto.getIssueQuantity(), "下发数量不能为空");
        PlanOrderPO current = planOrderDao.queryPlanOrderById(dto.getPlanOrderId(), dto.getTenantId());
        BaseAssert.notNull(current, "计划订单不存在");

        int confirmQuantity = current.getConfirmQuantity() == null ? 0 : current.getConfirmQuantity();
        int currentIssueQuantity = current.getIssueQuantity() == null ? 0 : current.getIssueQuantity();
        int targetIssueQuantity = currentIssueQuantity + dto.getIssueQuantity();
        if (targetIssueQuantity > confirmQuantity) {
            throw new BusinessException("下发数量不能大于确认数量");
        }

        current.setIssueQuantity(targetIssueQuantity);
        current.setRealIssueTime(new Date());
        current.setUpdateTime(new Date());
        current.setUpdatorId(dto.getUserId());
        current.setOperatorName(dto.getUserName());
        current.setStatus(targetIssueQuantity == confirmQuantity
                ? PlanOrderStatusEnum.COMPLETE_ISSUE.getCode()
                : PlanOrderStatusEnum.INCOMPLETE_ISSUE.getCode());

        PlanOrderIssueDetailPO issueDetailPO = PlanOrderConvertor.toIssueDetailPO(current, dto, generateNo(PLAN_ORDER_ISSUE_PREFIX));
        Boolean result = planOrderDao.issuePlanOrder(current, issueDetailPO);
        if (Boolean.TRUE.equals(result)) {
            try {
                String tag = getIssueTagByPlanType(current.getPlanType());
                defaultMqProducer.doSend(JSON.toJSONString(issueDetailPO), tag);
            } catch (Exception e) {
                log.error("下发计划订单 MQ 发送失败, planOrderId={}", current.getId(), e);
            }
        }
        return SingleResponse.of(result);
    }

    @Override
    public PageResponse<PlanOrderIssueDetailDTO> pageQueryPlanOrderIssueDetail(PlanOrderIssueDetailPageReqDTO dto) {
        PlanOrderIssueDetailPageCondition condition = PlanOrderConvertor.toIssueDetailPageCondition(dto);
        PageInfo<PlanOrderIssueDetailPO> pageInfo = planOrderDao.pageQueryPlanOrderIssueDetail(condition);
        List<PlanOrderIssueDetailDTO> data = pageInfo.getList().stream().map(PlanOrderConvertor::toDTO).collect(Collectors.toList());
        return PageResponse.of(data, pageInfo.getTotal(), dto.getPageSize(), dto.getPageNum());
    }

    private String getIssueTagByPlanType(Integer planType) {
        PlanMaterialParamPlanTypeEnum typeEnum = PlanMaterialParamPlanTypeEnum.getByCode(planType);
        if (typeEnum == null) {
            return MqTagEnum.ISSUE_PURCHASE_PLAN_ORDER_TAG.getCode();
        }
        switch (typeEnum) {
            case TRANSFER:
                return MqTagEnum.ISSUE_TRANSFER_PLAN_ORDER_TAG.getCode();
            case PRODUCER:
                return MqTagEnum.ISSUE_PRODUCE_PLAN_ORDER_TAG.getCode();
            default:
                return MqTagEnum.ISSUE_PURCHASE_PLAN_ORDER_TAG.getCode();
        }
    }

    private String generateNo(String prefix) {
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = ThreadLocalRandom.current().nextInt(10000000, 99999999);
        return prefix + time + random;
    }
}
