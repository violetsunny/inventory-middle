package com.inventory.middle.application.plan.demand.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.*;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.demand.bo.*;
import com.inventory.middle.application.plan.demand.convertor.*;
import com.inventory.middle.application.plan.demand.handler.*;
import com.inventory.middle.application.plan.demand.validator.DemandPlanValidatorChain;
import com.inventory.middle.application.plan.demand.service.DemandPlanService;
import com.inventory.middle.infra.plan.persistence.dao.*;
import com.inventory.middle.infra.plan.persistence.dao.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.condition.DemandPlanMaterialDetailReqCondition;
import com.inventory.middle.infra.plan.persistence.condition.demand.DemandPlanSelectReqCondition;
import com.inventory.middle.infra.plan.persistence.result.DemandPlanDetailResult;
import com.inventory.middle.infra.plan.persistence.entity.*;
import com.inventory.middle.domain.plan.common.enums.DemandTypeEnum;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialParameterPO;
import com.inventory.middle.infra.plan.sequence.Sequence;
import com.inventory.middle.infra.plan.sequence.SequenceFactory;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.domain.plan.common.bo.PlanProductBO;
import com.inventory.middle.infra.plan.stub.PlanParticipantStub;
import com.inventory.middle.infra.plan.stub.PlanProductStub;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.plan.common.constants.CommonConstants.PROJECT_ODER_PREFIX;

/**
 * @author zhouxinzhong
 * @date 2021/9/29 13:55
 */
@Service
@Slf4j
public class DemandPlanServiceImpl implements DemandPlanService {

    @Value("${enn.plan.demand.horizon.max.days:180}")
    private int maxHorizonDays;

    @Autowired
    private DemandPlanDao demandPlanDao;

    @Autowired
    private DemandPlanMaterialDao demandPlanMaterialDao;

    @Autowired
    private PlanConfigDao planConfigDao;

    @Autowired
    private DemandPlanMaterialDetailDao demandPlanMaterialDetailDao;

    @Autowired
    private DemandPlanMaterialPeriodDao demandPlanMaterialPeriodDao;


    @Autowired
    private DemandPlanReverseCalculateHandler demandPlanReverseCalculateHandler;

    @Autowired
    private DemandPlanAmountHandler demandPlanAmountHandler;

    @Autowired
    private PlanProductStub planProductStub;

    @Autowired
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;

    @Autowired
    private LogicalPlantQueryService logicalPlantQueryService;

    @Autowired
    private PlanParticipantStub planParticipantStub;

    @Autowired
    private DemandPlanValidatorChain demandPlanValidatorChain;

    @Resource
    private SequenceFactory sequenceFactory;

    public static final String DEMAND_PLAN_VERSION_NAME = "demandPlanVersion";

    int corePoolSize = Runtime.getRuntime().availableProcessors();
    int maxPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            5,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10000),
            new ThreadFactoryBuilder().setNameFormat("demandPlanGenerate-task-%d").build());


    @Override
    public PageResponse<DemandPlanSelectResBO> selectDemandPlanByPage(DemandPlanSelectReqBO reqBO,
                                                                         int pageNum, int pageSize) {
        //bo转condition
        DemandPlanSelectReqCondition reqCondition = new DemandPlanSelectReqCondition();
        BeanUtils.copyProperties(reqBO, reqCondition);
        //查询
        Page<DemandPlanDao> page = PageHelper.startPage(pageNum, pageSize);
        List<DemandPlanPO> poList = demandPlanDao.selectByPage(reqCondition);
        //po转bo
        List<DemandPlanSelectResBO> boList = DemandPlanSelectConverter.INSTANCE.toBOList(poList);
        //封装
        return PageResponse.of(boList, page.getTotal(), page.getPageSize(), page.getPageNum());
    }


    @Override
    public boolean createDemandPlan(DemandPlanBO demandPlanBO) {
        Checker.notNull(demandPlanBO);
        Checker.notNull(demandPlanBO.getDemandType());
        Checker.notNull(demandPlanBO.getDemandSourceType());
        Checker.notNull(demandPlanBO.getLogicalPlantNo());
        Checker.notNull(demandPlanBO.getAggregationPeriod());
        Checker.notNull(demandPlanBO.getDemandHorizonBeginTime());
        Checker.notNull(demandPlanBO.getDemandHorizonEndTime());

        LogicalPlantDto logicalPlant = logicalPlantQueryService.findByNo(demandPlanBO.getLogicalPlantNo());
        if (Objects.isNull(logicalPlant)) {
            return false;
        }
        //校验
        validateDemandPlan(demandPlanBO);
        // 租户id 用户id 公司编码
        String companyName = planParticipantStub.getCompanyName(demandPlanBO.getTenantId(), demandPlanBO.getUserId(), logicalPlant.getCompanyCode());
        DemandPlanPO planPO = new DemandPlanPO();
        planPO.setLogicalPlantNo(demandPlanBO.getLogicalPlantNo());
        planPO.setLogicalPlantName(logicalPlant.getLogicalPlantName());
        planPO.setCompanyCode(logicalPlant.getCompanyCode());
        planPO.setCompanyName(StringUtils.isEmpty(companyName)?logicalPlant.getCompanyCode():companyName);
        planPO.setDemandType(demandPlanBO.getDemandType());
        planPO.setDemandSourceType(demandPlanBO.getDemandSourceType());
        planPO.setWarehouseNo(logicalPlant.getWarehouseNo());
        planPO.setWarehouseName(logicalPlant.getWarehouseName());
        planPO.setAggregationPeriod(demandPlanBO.getAggregationPeriod());
        planPO.setPlanVersion(generatePlanVersion(planPO));
        //保证时间是当天的0点
        planPO.setDemandHorizonBeginTime(DateUtils.getStartOfDay(demandPlanBO.getDemandHorizonBeginTime()));
        planPO.setDemandHorizonEndTime(DateUtils.getStartOfDay(demandPlanBO.getDemandHorizonEndTime()));
        planPO.setCreatorId(demandPlanBO.getUserId());
        planPO.setCreateUserName(demandPlanBO.getUserName());
        planPO.setUpdatorId(demandPlanBO.getUserId());
        planPO.setTenantId(demandPlanBO.getTenantId());
        planPO.setStatus(DemandStatusEnum.OFF.getCode());
        int result = demandPlanDao.insert(planPO);
        return result > 0;
    }

    @Override
    public boolean updateDemandPlan(DemandPlanBO demandPlanBO) {
        Checker.notNull(demandPlanBO);
        Checker.notNull(demandPlanBO.getId());
        Checker.notNull(demandPlanBO.getDemandHorizonBeginTime());
        Checker.notNull(demandPlanBO.getDemandHorizonEndTime());
        DemandPlanPO planPO = demandPlanDao.selectById(demandPlanBO.getId());
        if (Objects.isNull(planPO)){
            return false;
        }
        if (!Objects.equals(demandPlanBO.getTenantId(),planPO.getTenantId())){
            throw Ex.of(ResponseCodeEnum.NO_AUTH);
        }
        planPO.setDemandHorizonBeginTime(DateUtils.getStartOfDay(demandPlanBO.getDemandHorizonBeginTime()));
        planPO.setDemandHorizonEndTime(DateUtils.getStartOfDay(demandPlanBO.getDemandHorizonEndTime()));
        //校验
        demandPlanBO.setAggregationPeriod(planPO.getAggregationPeriod());
        validateDemandPlan(demandPlanBO);
        int result = demandPlanDao.update(planPO);
        return result > 0;
    }

    public void validateDemandPlan(DemandPlanBO demandPlanBO) {

        try {

            //日期是否合法
            if (demandPlanBO.getDemandHorizonBeginTime().after(demandPlanBO.getDemandHorizonEndTime())) {
                throw Ex.of(ResponseCodeEnum.D_CREATE_DEMAND_PLAN_INVALID_HORIZON_DATE);
            }
            //如果是周，是否是周一
            if (Objects.equals(demandPlanBO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.WEEK.getCode())
                    && !(DateUtils.checkIsDayOfWeek(demandPlanBO.getDemandHorizonBeginTime(), Calendar.MONDAY))) {
                throw Ex.of(ResponseCodeEnum.D_CREATE_DEMAND_PLAN_INVALID_FIRST_DAY);
            }
            //如果是周，是否是周日
            if (Objects.equals(demandPlanBO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.WEEK.getCode())
                    && !(DateUtils.checkIsDayOfWeek(demandPlanBO.getDemandHorizonEndTime(), Calendar.SUNDAY))) {
                throw Ex.of(ResponseCodeEnum.D_CREATE_DEMAND_PLAN_INVALID_LAST_DAY);
            }

            //如果是月，是否是1号
            if (Objects.equals(demandPlanBO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.MONTH.getCode())
                    && !(DateUtils.checkFirstDayOfMonth(demandPlanBO.getDemandHorizonBeginTime()))) {
                throw Ex.of(ResponseCodeEnum.D_CREATE_DEMAND_PLAN_INVALID_FIRST_DAY);
            }
            if (Objects.equals(demandPlanBO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.MONTH.getCode())
                    && !(DateUtils.checkLastDayOfMonth(demandPlanBO.getDemandHorizonEndTime()))) {
                throw Ex.of(ResponseCodeEnum.D_CREATE_DEMAND_PLAN_INVALID_LAST_DAY);
            }

            //是否大于180天
            int days = DateUtils.daysBetween(DateUtils.toDate(DateUtils.toLocalDate(demandPlanBO.getDemandHorizonBeginTime())), DateUtils.toDate(DateUtils.toLocalDate(demandPlanBO.getDemandHorizonEndTime())));
            if (days > maxHorizonDays) {
                throw Ex.of(ResponseCodeEnum.D_CREATE_DEMAND_PLAN_INVALID_DAYS);
            }
        } catch (Exception e) {
            log.error("validateDemandPlan error", e);
            throw Ex.of(ResponseCodeEnum.D_CREATE_DEMAND_PLAN_FAIL);
        }


    }


    /**
     * 生成版本号
     *
     * @param planPO
     * @return
     */
    public String generatePlanVersion(DemandPlanPO planPO) {
      /*  DemandPlanSelectReqCondition condition = new DemandPlanSelectReqCondition();
        condition.setBeginTime(DateUtils.getStartDateOfOneDay(new Date()));
        condition.setEndTime(DateUtils.getEndOfDay(new Date()));
        condition.setAggregationPeriod(planPO.getAggregationPeriod());
        List<DemandPlanPO> demandPlanPOS = demandPlanDao.selectByPage(condition);
        Integer maxVersion = null;
        if (CollectionUtils.isNotEmpty(demandPlanPOS)) {
            //sql里已经根据id 排序过了
            String planVersion = demandPlanPOS.get(0).getPlanVersion();
            maxVersion = Integer.valueOf(planVersion.substring(9));
        }*/
        Sequence sequence = sequenceFactory.getSequence(DEMAND_PLAN_VERSION_NAME+CommonConstants.PLACEHOLDER_$+DateUtils.dateToString(new Date(), DateUtils.yyyyMMdd));
        String version = DemandPlanAggregationPeriodEnum.of(planPO.getAggregationPeriod()).getPrefix()
                + DateUtils.dateToString(new Date(), DateUtils.yyyyMMdd)
                + StringUtils.leftPad(sequence.next() + "", 4, "0");
        return version;
    }

    @Override
    public DemandPlanMaterialBatchCreateResBO createDemandPlanMaterialPeriod(DemandPlanMaterialBatchCreateReqBO createBO) {

        DemandPlanMaterialBatchCreateResBO response = new DemandPlanMaterialBatchCreateResBO();
        Long demandPlanId = createBO.getDemandPlanId();

        DemandPlanPO demandPlanPO = demandPlanDao.selectById(demandPlanId);
        //校验状态
        checkStatus(demandPlanPO,createBO.getTenantId());
        List<DemandPlanMaterialPeriodBO> periodBOList = createBO.getPeriodList();
        //校验物料
        //List<DemandPlanMaterialBatchCreateDetailBO> materialCodeFailList = validateImportInfo(demandPlanPO, periodBOList, createBO.getTenantId());
        List<DemandPlanMaterialBatchCreateDetailBO> materialCodeFailList = null;
        response.setTotalCount(periodBOList.size());
        if (!CollectionUtils.isEmpty(materialCodeFailList)) {
            response.setFailCount(materialCodeFailList.size());
            response.setFailDetails(materialCodeFailList);
            return response;
        }

        //组装物料明细
        List<DemandPlanMaterialPO> materialPOList = convertDemandPlanMaterial(demandPlanPO, periodBOList, createBO);
        //组装计划明细
        Map<String, List<DemandPlanMaterialPeriodPO>> map = convertDemandPlanMaterialPeriod(materialPOList, periodBOList);
        demandPlanMaterialDao.insertMaterialPeriod(demandPlanPO.getId(),materialPOList, map);
        response.setSuccessCount(materialPOList.size());
        return response;
    }

    @Override
    public SingleResponse<DemandPlanMaterialBatchCreateResBO> createFullDemandPlan(DemandPlanBO demandPlanBO) {
        Checker.notNull(demandPlanBO);
        Checker.notNull(demandPlanBO.getDemandType());
        Checker.notNull(demandPlanBO.getDemandSourceType());
        Checker.notNull(demandPlanBO.getLogicalPlantNo());
        Checker.notNull(demandPlanBO.getAggregationPeriod());
        Checker.notNull(demandPlanBO.getDemandHorizonBeginTime());
        Checker.notNull(demandPlanBO.getDemandHorizonEndTime());
        DemandPlanMaterialBatchCreateResBO response = new DemandPlanMaterialBatchCreateResBO();
        LogicalPlantDto logicalPlant = logicalPlantQueryService.findByNo(demandPlanBO.getLogicalPlantNo());
        if (Objects.isNull(logicalPlant)) {
            //TODO
            return SingleResponse.buildFailure("","逻辑仓不存在");
        }
        //校验
        validateDemandPlan(demandPlanBO);
        // 租户id 用户id 公司编码 TODO
        //String companyName = participantCenterService.getCompanyName(logicalPlant.getCompanyCode(), demandPlanBO.getUserId(), demandPlanBO.getTenantId());
        String companyName = "永康城燃";
        DemandPlanPO planPO = new DemandPlanPO();
        planPO.setLogicalPlantNo(demandPlanBO.getLogicalPlantNo());
        planPO.setLogicalPlantName(logicalPlant.getLogicalPlantName());
        planPO.setCompanyCode(logicalPlant.getCompanyCode());
        planPO.setCompanyName(StringUtils.isEmpty(companyName)?logicalPlant.getCompanyCode():companyName);
        planPO.setDemandType(demandPlanBO.getDemandType());
        planPO.setDemandSourceType(demandPlanBO.getDemandSourceType());
        planPO.setWarehouseNo(logicalPlant.getWarehouseNo());
        planPO.setWarehouseName(logicalPlant.getWarehouseName());
        planPO.setAggregationPeriod(demandPlanBO.getAggregationPeriod());
        planPO.setPlanVersion(generatePlanVersion(planPO));
        //保证时间是当天的0点
        planPO.setDemandHorizonBeginTime(DateUtils.getStartOfDay(demandPlanBO.getDemandHorizonBeginTime()));
        planPO.setDemandHorizonEndTime(DateUtils.getStartOfDay(demandPlanBO.getDemandHorizonEndTime()));
        planPO.setCreatorId(demandPlanBO.getUserId());
        planPO.setCreateUserName(demandPlanBO.getUserName());
        planPO.setUpdatorId(demandPlanBO.getUserId());
        planPO.setTenantId(demandPlanBO.getTenantId());
        planPO.setStatus(DemandStatusEnum.OFF.getCode());

        List<DemandPlanMaterialPeriodBO> periodBOList = demandPlanBO.getPeriodList();
        //校验物料 TODO
       //List<DemandPlanMaterialBatchCreateDetailBO> materialCodeFailList = validateImportInfo(planPO, periodBOList, demandPlanBO.getTenantId());
       List<DemandPlanMaterialBatchCreateDetailBO> materialCodeFailList =null;
        if (!CollectionUtils.isEmpty(materialCodeFailList)) {
            response.setFailCount(materialCodeFailList.size());
            response.setFailDetails(materialCodeFailList);
            return SingleResponse.buildFailure("","创建失败");
        }
        DemandPlanMaterialBatchCreateReqBO createReqBO = new DemandPlanMaterialBatchCreateReqBO();
        createReqBO.setTenantId(demandPlanBO.getTenantId());
        createReqBO.setUserId(demandPlanBO.getUserId());
        createReqBO.setUserName(demandPlanBO.getUserName());
        //这里的先这样组装一下，621版本优化掉 TODO
        //组装物料明细
        List<DemandPlanMaterialPO> materialPOList = convertDemandPlanMaterial(planPO, periodBOList, createReqBO);
        //组装计划明细
        Map<String, List<DemandPlanMaterialPeriodPO>> map = convertDemandPlanMaterialPeriod(materialPOList, periodBOList);
        demandPlanMaterialDao.insertMaterialPeriod(planPO,materialPOList, map);
        response.setSuccessCount(materialPOList.size());
        return SingleResponse.buildSuccess(response);
    }

    /**
     * 修改数据时，鉴权
     *
     * @param demandPlanPO
     */
    public void checkStatus(DemandPlanPO demandPlanPO,String tenantId) {
        if (Objects.isNull(demandPlanPO)){
            throw Ex.of(ResponseCodeEnum.D_DEMAND_PLAN_NOT_EXIST);
        }
        if (!Objects.equals(tenantId,demandPlanPO.getTenantId())){
            throw Ex.of(ResponseCodeEnum.NO_AUTH);
        }
        if (Objects.equals(demandPlanPO.getStatus(), DemandStatusEnum.ON.getCode())) {
            throw Ex.of(ResponseCodeEnum.D_COMMON_STATUS_NOT_OFF);
        }

    }


    @Override
    public boolean updateDemandPlanStatus(DemandPlanUpdateStatusBO statusBO) {

        Checker.notNull(statusBO);
        Checker.notNull(statusBO.getId());
        DemandPlanPO demandPlanPO = demandPlanDao.selectById(statusBO.getId());
        if (Objects.isNull(demandPlanPO)) {
            return false;
        }
        if (!Objects.equals(demandPlanPO.getTenantId(),statusBO.getTenantId())){
            throw Ex.of(ResponseCodeEnum.NO_AUTH);
        }
        DemandPlanUpdateStatusReqBO req = new DemandPlanUpdateStatusReqBO();
        req.setStatus(statusBO.getStatus());
        req.setDemandPlanId(statusBO.getId());
        req.setTenantId(statusBO.getTenantId());
        req.setDemandPlanPO(demandPlanPO);
        demandPlanValidatorChain.doProcess(ValidateMessage.builder().t(req).build());

        DemandPlanPO planPO = DemandPlanConverter.INSTANCE.toPO(statusBO);
        //所有的校验都通过,更新状态
        planPO.setUpdatorId(statusBO.getUserId());
        int result = demandPlanDao.update(planPO, statusBO.getStatus());
        if (result > 0) {
            //开启之后初始化数据
            List<DemandPlanMaterialPO> materialPOList = handleDemandPlanDetailByPlanId(statusBO.getId(), statusBO.getTenantId(), req.getStatus());
            if (CollectionUtils.isNotEmpty(materialPOList)) {
                ReverseCalculateRequestContext context = new ReverseCalculateRequestContext();
                List<ReverseCalculateMaterial> materialList = DemandPlanMaterialConverter.INSTANCE.toReverseCalculateMaterialList(materialPOList);
                context.setMaterialList(materialList);
                demandPlanReverseCalculateHandler.calculate(context);
            }
        }
        return result > 0;
    }

    /**
     * 初始化数据
     *
     * @param id
     * @param tenantId
     */
    public List<DemandPlanMaterialPO> handleDemandPlanDetailByPlanId(Long id, String tenantId, int status) {

        DemandPlanPO demandPlanPO = demandPlanDao.selectById(id);

        //查询物料
        DemandPlanMaterialPO materialCondition = new DemandPlanMaterialPO();
        materialCondition.setDemandPlanId(id);
        materialCondition.setTenantId(tenantId);
        materialCondition.setStatus(status);
        List<DemandPlanMaterialPO> materialPOList = demandPlanMaterialDao.selectByCondition(materialCondition);
        handleDemandPlanDetailByMaterial(demandPlanPO, tenantId, materialPOList, status);
        return materialPOList;
    }

    /**
     * @param demandPlanPO
     * @param tenantId
     * @param materialPOList
     * @param status         处理方式 1:针对 status =1的处理，处理结果累加 0：针对status=0的处理，处理结果在原值基础上扣减
     * @return
     */
    @Transactional
    public List<DemandPlanMaterialPO> handleDemandPlanDetailByMaterial(DemandPlanPO demandPlanPO, String tenantId
            , List<DemandPlanMaterialPO> materialPOList, int status) {
        //查询导入的信息
        DemandPlanMaterialPeriodPO queryCondition = new DemandPlanMaterialPeriodPO();
        queryCondition.setDemandPlanId(demandPlanPO.getId());
        queryCondition.setDeleted(IsDeleteEnum.NO.getValue());
        queryCondition.setStatus(status);
        List<DemandPlanMaterialPeriodPO> periodPOList = demandPlanMaterialPeriodDao.queryByCondition(queryCondition);
        if (CollectionUtils.isEmpty(materialPOList) || CollectionUtils.isEmpty(periodPOList)) {
            return Lists.newArrayList();
        } else {
            //实际的类型，预测？订单？
            int type = Objects.equals(demandPlanPO.getDemandType(),DemandTypeEnum.PREDICT.getCode())?
                    DemandPlanMaterialDetailTypeEnum.PLAN.getCode():DemandPlanMaterialDetailTypeEnum.ORDER.getCode();
            List<DemandPlanMaterialDetailReqCondition> conditions = new ArrayList<>();
            for (DemandPlanMaterialPO materialPO : materialPOList) {
                DemandPlanMaterialDetailReqCondition detailReqCondition = new DemandPlanMaterialDetailReqCondition();
                detailReqCondition.setMaterialCode(materialPO.getMaterialCode());
                detailReqCondition.setLogicalPlantNo(materialPO.getLogicalPlantNo());
                detailReqCondition.setType(type);
                detailReqCondition.setTenantId(tenantId);
                conditions.add(detailReqCondition);
            }
           List<DemandPlanMaterialAmount> changeAmountList = new ArrayList<>();
            for (DemandPlanMaterialPeriodPO periodPO : periodPOList) {
                //计算预测需求值
                Map<Date, Long> result = calculate(demandPlanPO.getAggregationPeriod(), periodPO);
                if (result.size() > 0) {
                    for (Date key : result.keySet()) {
                        long changeAmount = result.get(key);
                        DemandPlanMaterialAmount materialAmount = DemandPlanMaterialAmount.builder()
                                .materialCode(periodPO.getMaterialCode())
                                .logicalPlantNo(periodPO.getLogicalPlantNo())
                                .tenantId(tenantId)
                                .planDate(key)
                                .type(type)
                                .changeAmount(status == DemandStatusEnum.ON.getCode() ? changeAmount : -changeAmount)
                                .build();
                        changeAmountList.add(materialAmount);
                    }
                }
            }
            DemandPlanAmountRequestContext requestContext = new DemandPlanAmountRequestContext();
            requestContext.setChangeAmountList(changeAmountList);
            requestContext.setDemandPlanPO(demandPlanPO);
            demandPlanAmountHandler.process(requestContext);
        }
        return materialPOList;
    }


    /**
     * 计算周期内的需求计划分布
     *
     * @param po
     * @return
     */
    private Map<Date, Long> calculate(int aggregationPeriod, DemandPlanMaterialPeriodPO po) {
        Map<Date, Long> result = new HashMap<>();
        try {
            if (Objects.equals(DemandPlanAggregationPeriodEnum.DAY.getCode(), aggregationPeriod)) {
                Date begin = DateUtils.stringToDate(po.getPlanPeriod(), DateUtils.YYYY_MM_DD_SLASH);
                if (DateUtils.getStartOfDay(new Date()).getTime() > begin.getTime()) {
                    return result;
                }
                //如果是天直接返回
                result.put(begin, po.getPlanAmount());
                return result;
            }
            if (Objects.equals(DemandPlanAggregationPeriodEnum.WEEK.getCode(), aggregationPeriod)
                    || Objects.equals(DemandPlanAggregationPeriodEnum.MONTH.getCode(), aggregationPeriod)) {

                Date begin = DateUtils.stringToDate(po.getPlanPeriod().split("-")[0], DateUtils.YYYY_MM_DD_SLASH);

                Date end = DateUtils.stringToDate(po.getPlanPeriod().split("-")[1], DateUtils.YYYY_MM_DD_SLASH);
                if (DateUtils.toLocalDate(new Date()).isAfter(DateUtils.toLocalDate(end))) {
                    return result;
                }
                int days = DateUtils.daysBetween(begin, end);
                long num = po.getPlanAmount() / days;
                long remainder = po.getPlanAmount() % days;
                long i = 1L;
                while (begin.getTime() <= end.getTime()) {
                    if (i <= remainder) {
                        result.put(begin, num + 1);
                        i++;
                    } else {
                        result.put(begin, num);
                    }

                    begin = DateUtils.addDate(begin, 1);
                }
            }
        } catch (ParseException e) {
            log.error("calculate error:", e);

        }
        return result;

    }


    @Override
    public boolean cancelDemandPlanMaterial(CancelDemandPlanMaterialReqBO reqBO) {

        Checker.notNull(reqBO);
        Checker.notNull(reqBO.getDemandPlanMaterialId());
        DemandPlanMaterialPO materialPO = demandPlanMaterialDao.selectById(reqBO.getDemandPlanMaterialId());
        if (Objects.isNull(materialPO)) {
            return false;
        }
        DemandPlanPO demandPlanPO = demandPlanDao.selectById(materialPO.getDemandPlanId());
        if (Objects.isNull(demandPlanPO)) {
            return false;
        }
        //校验状态
        checkStatus(demandPlanPO, reqBO.getTenantId());
        //更新前的值
        DemandPlanMaterialPeriodPO condition = new DemandPlanMaterialPeriodPO();
        condition.setStatus(DemandStatusEnum.OFF.getCode());
        condition.setDemandPlanId(materialPO.getDemandPlanId());
        condition.setDemandPlanMaterialId(reqBO.getDemandPlanMaterialId());
        List<DemandPlanMaterialPeriodPO> periodPOList = demandPlanMaterialPeriodDao.queryByCondition(condition);
        List<DemandPlanMaterialAmount> changeAmountList = new ArrayList<>();
        for (DemandPlanMaterialPeriodPO periodPO : periodPOList) {
            Map<Date, Long> result = calculate(demandPlanPO.getAggregationPeriod(), periodPO);
            if (result.size() > 0) {
                for (Date key : result.keySet()) {
                    long changeAmount = result.get(key);
                    //剔除数据，都是扣减
                    DemandPlanMaterialAmount materialAmount = DemandPlanMaterialAmount.builder()
                            .materialCode(periodPO.getMaterialCode())
                            .logicalPlantNo(periodPO.getLogicalPlantNo())
                            .tenantId(reqBO.getTenantId())
                            .planDate(key)
                            .type(DemandPlanMaterialDetailTypeEnum.PLAN.getCode())
                            .changeAmount(-changeAmount)
                            .build();
                    changeAmountList.add(materialAmount);
                }
            }
        }

        DemandPlanMaterialPO po = new DemandPlanMaterialPO();
        po.setId(reqBO.getDemandPlanMaterialId());
        po.setUpdatorId(reqBO.getUserId());
        boolean updaterResult = demandPlanMaterialDao.cancelDemandPlanMaterial(po);
        /*if (updaterResult) {
            //更新预测值
            DemandPlanAmountRequestContext requestContext = new DemandPlanAmountRequestContext();
            requestContext.setChangeAmountList(changeAmountList);
            demandPlanAmountHandler.process(requestContext);

            //计算冲销
            ReverseCalculateRequestContext context = new ReverseCalculateRequestContext();
            ReverseCalculateMaterial material = new ReverseCalculateMaterial();
            material.setLogicalPlantNo(materialPO.getLogicalPlantNo());
            material.setMaterialCode(material.getMaterialCode());
            material.setTenantId(reqBO.getTenantId());
            context.setMaterialList(Lists.newArrayList(material));
            demandPlanReverseCalculateHandler.calculate(context);
        }*/
        return updaterResult;
    }

    @Override
    public boolean modifyDemandPlanMaterialAmount(ModifyDemandPlanMaterialAmountReqBO reqBO) {

        Checker.notNull(reqBO);
        Checker.notNull(reqBO.getDemandPlanMaterialId());

        DemandPlanMaterialPO materialPO = demandPlanMaterialDao.selectById(reqBO.getDemandPlanMaterialId());

        if (Objects.isNull(materialPO)) {
            return false;
        }
        DemandPlanPO demandPlanPO = demandPlanDao.selectById(materialPO.getDemandPlanId());
        if (Objects.isNull(demandPlanPO)) {
            return false;
        }
        //校验状态
        checkStatus(demandPlanPO, reqBO.getTenantId());

        DemandPlanMaterialPO po = new DemandPlanMaterialPO();
        po.setId(reqBO.getDemandPlanMaterialId());
        po.setUpdatorId(reqBO.getUserId());

        List<DemandPlanMaterialPeriodPO> periodPOList = DemandPlanMaterialAmountConverter.INSTANCE.toPOList(reqBO.getAmountList());

        List<OrderDemandPO> orderPlanPOList = new ArrayList<>();

        // 需求类型为项目订单 构建人工操作需求订单
        if (demandPlanPO.getDemandType().equals(DemandTypeEnum.PROJECT_ORDER.getCode())) {

            // 人工操作记录订单需求序列号生成
            Sequence sequence = sequenceFactory.getSequence(PROJECT_ODER_PREFIX + DateUtils.dateToString(new Date(), DateUtils.yyyyMMdd));
            String orderNo = PROJECT_ODER_PREFIX + StringUtils.leftPad(sequence.next() + "", 6, "0");

            // 项目订单表人工操作记录生成
            periodPOList.forEach(periodPO -> {
                // 原数据
                DemandPlanMaterialPeriodPO originPO = demandPlanMaterialPeriodDao.queryById(periodPO.getId());

                // 物料编码 逻辑仓给值
                periodPO.setMaterialCode(originPO.getMaterialCode());
                periodPO.setLogicalPlantNo(originPO.getLogicalPlantNo());
                periodPO.setPlanPeriod(originPO.getPlanPeriod());

                // 需求订单
                OrderDemandPO orderDemandPO = new OrderDemandPO();
                orderDemandPO.setOrderNo(orderNo);
                orderDemandPO.setOrderType(OrderPlanTypeEnum.PROJECT_ORDER.getCode());
                orderDemandPO.setMaterialCode(originPO.getMaterialCode());
                orderDemandPO.setLogicalPlantNo(originPO.getLogicalPlantNo());
                orderDemandPO.setTenantId(reqBO.getTenantId());
                try {
                    orderDemandPO.setPlanDate(DateUtils.stringToDate(periodPO.getPlanPeriod(), DateUtils.YYYY_MM_DD_SLASH));
                } catch (Exception e) {
                    log.error("time parse error : {}", e.getMessage());
                }
                // 订单表数量设置为用户增加的物料数量
                Checker.checkState(periodPO.getPlanAmount() > originPO.getPlanAmount(), "修改数量大小不能少于物料原始数量!");
                orderDemandPO.setAmount(periodPO.getPlanAmount() - originPO.getPlanAmount());

                // 人工操作记录生成拓展信息 与系统订单结构一致
                orderDemandPO.setExtInfo(this.parseOrderDemandExtInfo(orderDemandPO, reqBO));

                orderPlanPOList.add(orderDemandPO);
            });
        }

        return demandPlanMaterialDao.modifyDemandPlanMaterialAmount(po, periodPOList, orderPlanPOList, reqBO.getTenantId());
    }

    private String parseOrderDemandExtInfo(OrderDemandPO po, ModifyDemandPlanMaterialAmountReqBO reqBO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderNo", po.getOrderNo());
        jsonObject.put("materialCode", po.getMaterialCode());
        jsonObject.put("logicalPlantNo", po.getLogicalPlantNo());
        jsonObject.put("tenantId", po.getTenantId());
        jsonObject.put("amount", po.getAmount());
        // 日期 行号
        jsonObject.put("planDate", DateUtils.dateToString(po.getPlanDate(), DateUtils.yyyy_MM_dd));
        // remark字段 补充用户修改信息
        String remark = "由" + reqBO.getUserName() + "于" + DateUtils.dateToString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss) + "修改";
        jsonObject.put("remark", remark);
        // 手工订单默认为合规
        jsonObject.put("planFlag", ProjectPlanFlagEnum.IN.getCode());
        return jsonObject.toString();
    }

    @Override
    public List<String> exportDemandPlanTemplate(Long demandPlanId,String tenantId) {
        Checker.notNull(demandPlanId);
        Checker.notNull(tenantId);
        List<String> titles = new ArrayList<>();
        DemandPlanPO planPO = demandPlanDao.selectById(demandPlanId);
        if (Objects.isNull(planPO)) {
            return titles;
        }
        if (!Objects.equals(tenantId,planPO.getTenantId())){
            throw Ex.of(ResponseCodeEnum.NO_AUTH);
        }
        titles.add("物料编码");
        List<String> dateTitles = this.getDateTitles(planPO);
        titles.addAll(dateTitles);
        return titles;
    }

    @Override
    public DemandPlanVersionSelectResBO selectByLogicalPlantNos(DemandPlanVersionSelectReqBO reqBO) {
        List<DemandPlanPO> poList = demandPlanDao.selectByLogicalPlantNos(reqBO.getLogicalPlantNos(), reqBO.getTenantId());
        List<DemandPlanVersionBO> boList = DemandPlanVersionConverter.INSTANCE.toBOList(poList);
        DemandPlanVersionSelectResBO resBO = new DemandPlanVersionSelectResBO();
        resBO.setDemandPlanVersionList(boList);
        return resBO;
    }

    @Override
    public DemandPlanDetailSelectResBO selectDemandPlanDetail(DemandPlanDetailSelectReqBO reqBO) {
        // 校验
        Checker.notNull(reqBO.getDemandPlanId());
        Checker.notNull(reqBO.getTenantId());

        // 获取计划
        DemandPlanPO demandPlanInfo = demandPlanDao.selectPlanInfo(reqBO.getDemandPlanId(), reqBO.getTenantId());
        if (demandPlanInfo == null) {
            return null;
        }

        // 获取物料及数量详情
        List<DemandPlanDetailResult> results = demandPlanDao.selectMaterialsInfo(reqBO.getDemandPlanId(), reqBO.getTenantId());

        List<DemandPlanDetailResultBO> bos = DemandPlanDetailConverter.INSTANCE.toResultBOList(results);

        // 预测需求 数据过滤
        if (demandPlanInfo.getDemandType().equals(DemandTypeEnum.PREDICT.getCode())) {
            // 展示开始时间
            // 用户选定开始时间-按选定时间 用户未选定开始时间-按本周期开始时间为准
            Date beginTime;
            if (reqBO.getBeginTime() != null) {
                beginTime = reqBO.getBeginTime();
            } else {
                beginTime = this.getBeginTimeByPeriodType(demandPlanInfo.getAggregationPeriod());
            }

            // 剔除展示时间范围外 展望期范围外的物料信息
            for (DemandPlanDetailResultBO bo : bos) {
                bo.getDemandList().removeIf(periodDemandResult -> {
                    // 计划时间
                    Date planPeriodStart = parsePlanPeriod(periodDemandResult.getPlanPeriod());
                    // 早于展望期下界
                    if (planPeriodStart.before(demandPlanInfo.getDemandHorizonBeginTime())) {
                        return true;
                    }
                    // 早于展示时间下界
                    if (planPeriodStart.before(beginTime)) {
                        return true;
                    }
                    // 晚于展望期结束时间
                    if (planPeriodStart.after(demandPlanInfo.getDemandHorizonEndTime())) {
                        return true;
                    }
                    // 晚于展示时间上界(计划周期为日 取30日以后)
                    if (demandPlanInfo.getAggregationPeriod() == DemandPlanAggregationPeriodEnum.DAY.getCode()) {
                        Date endTime = DateUtils.addDate(new Date(), 30);
                        return planPeriodStart.after(endTime);
                    }
                    return false;
                });
            }
        }

        // 返回
        return DemandPlanDetailSelectResBO.builder()
                .logicalPlantNo(demandPlanInfo.getLogicalPlantNo())
                .demandHorizonBeginTime(demandPlanInfo.getDemandHorizonBeginTime())
                .demandHorizonEndTime(demandPlanInfo.getDemandHorizonEndTime())
                .aggregationPeriod(demandPlanInfo.getAggregationPeriod())
                .materialList(bos)
                .build();
    }

    /**
     * 计划周期解析
     *
     * @param planPeriod 计划周期
     * @return planPeriodStart 计划周期起始时间
     */
    private Date parsePlanPeriod(String planPeriod) {
        Date planPeriodStart = null;
        try {
            planPeriodStart = DateUtils.stringToDate(planPeriod.split("-")[0], DateUtils.YYYY_MM_DD_SLASH);
        } catch (ParseException e) {
            // e.printStackTrace()改为日志打印 同时去掉assert语句
            log.error("analysis planDate error", e);
        }
        // 计划日期解析异常 将assert校验改为抛出异常
        if (null == planPeriodStart) {
            throw Ex.of(ResponseCodeEnum.SYSTEM_ERROR);
        }
        return planPeriodStart;
    }


    @Override
    public void generateData(MaterialBO bo) {
        if (Objects.isNull(bo)){
            //跑所有
            List<DemandPlanMaterialDetailPO> list =  demandPlanMaterialDetailDao.queryAllMaterial();
            if (CollectionUtils.isEmpty(list)){
                return;
            }
            for (DemandPlanMaterialDetailPO detailPO:list){
                ReverseCalculateRequestContext context = new ReverseCalculateRequestContext();
                ReverseCalculateMaterial calculateMaterial = new ReverseCalculateMaterial();
                calculateMaterial.setMaterialCode(detailPO.getMaterialCode());
                calculateMaterial.setLogicalPlantNo(detailPO.getLogicalPlantNo());
                calculateMaterial.setTenantId(detailPO.getTenantId());
                context.setMaterialList(Lists.newArrayList(calculateMaterial));
                executor.execute(()->{
                    demandPlanReverseCalculateHandler.calculate(context);
                });
            }

        }else {
            //跑单个
            ReverseCalculateRequestContext context = new ReverseCalculateRequestContext();
            ReverseCalculateMaterial calculateMaterial = new ReverseCalculateMaterial();
            calculateMaterial.setMaterialCode(bo.getMaterialCode());
            calculateMaterial.setLogicalPlantNo(bo.getLogicalPlantNo());
            calculateMaterial.setTenantId(bo.getTenantId());
            context.setMaterialList(Lists.newArrayList(calculateMaterial));
            demandPlanReverseCalculateHandler.calculate(context);
        }

    }

    /**
     * 根据计划周期类型给出本周期开始时间
     *
     * @param periodCode 周期代码
     * @return 周期开始时间(本日/本周第一天/本月第一天)
     */
    private Date getBeginTimeByPeriodType(Integer periodCode) {
        Calendar calendar = Calendar.getInstance();
        if (periodCode == DemandPlanAggregationPeriodEnum.DAY.getCode()) {
            //日
            calendar.setTime(new Date());
        } else if (periodCode == DemandPlanAggregationPeriodEnum.MONTH.getCode()) {
            //周
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            //月
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        //时分秒毫秒置0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 计算导出模板表头信息
     *
     * @param planPO
     * @return
     */
    private List<String> getDateTitles(DemandPlanPO planPO) {
        List<String> titles = new ArrayList<>();
        if (Objects.equals(planPO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.DAY.getCode())) {
            Date begin = DateUtils.getStartDateOfOneDay(planPO.getDemandHorizonBeginTime());
            Date end = DateUtils.getStartDateOfOneDay(planPO.getDemandHorizonEndTime());
            while (begin.getTime() <= end.getTime()) {
                titles.add(DateUtils.dateToString(begin, DateUtils.YYYY_MM_DD_SLASH));
                begin = DateUtils.addDate(begin, 1);
            }
        }
        if (Objects.equals(planPO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.WEEK.getCode())) {
            Date begin = DateUtils.getStartDateOfOneDay(planPO.getDemandHorizonBeginTime());
            Date end = DateUtils.getStartDateOfOneDay(planPO.getDemandHorizonEndTime());
            while (begin.getTime() <= end.getTime()) {
                titles.add(DateUtils.dateToString(begin, DateUtils.YYYY_MM_DD_SLASH) + "-" +
                        DateUtils.dateToString(DateUtils.addDate(begin, 6), DateUtils.YYYY_MM_DD_SLASH));
                begin = DateUtils.addDate(begin, 7);
            }
        }
        if (Objects.equals(planPO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.MONTH.getCode())) {
            Date begin = DateUtils.getStartDateOfOneDay(planPO.getDemandHorizonBeginTime());
            Date end = DateUtils.getStartDateOfOneDay(planPO.getDemandHorizonEndTime());
            while (begin.getTime() <= end.getTime()) {
                titles.add(DateUtils.dateToString(begin, DateUtils.YYYY_MM_DD_SLASH) + "-" +
                        DateUtils.dateToString(new Date(DateUtils.getEndOfMonth(begin)), DateUtils.YYYY_MM_DD_SLASH));
                begin = new Date(DateUtils.addMonth(begin.getTime(), 1));
            }
        }
        return titles;
    }


    private List<DemandPlanMaterialPO> convertDemandPlanMaterial(DemandPlanPO demandPlanPO, List<DemandPlanMaterialPeriodBO> periodBOList, DemandPlanMaterialBatchCreateReqBO createReqBO) {
        List<String> materialCodes = periodBOList.stream().map(DemandPlanMaterialPeriodBO::getMaterialCode).distinct().collect(Collectors.toList());
        Map<String, PlanProductBO> productMap = queryMaterial(materialCodes, createReqBO.getTenantId());
        List<DemandPlanMaterialPO> list = new ArrayList<>();
        for (String materialCode : materialCodes) {
            PlanProductBO product = productMap.get(materialCode);
            if (Objects.isNull(product)) {
                log.error("物料编码不存在:{}", materialCode);
                continue;
            }
            DemandPlanMaterialPO materialPO = new DemandPlanMaterialPO();
            materialPO.setDemandPlanId(demandPlanPO.getId());
            materialPO.setMaterialCode(materialCode);
            materialPO.setMaterialDesc(product.getName());
            materialPO.setLogicalPlantNo(demandPlanPO.getLogicalPlantNo());
            materialPO.setLogicalPlantName(demandPlanPO.getLogicalPlantName());
            materialPO.setMaterialUnit(product.getUnit());
            materialPO.setTenantId(createReqBO.getTenantId());
            materialPO.setCreatorId(createReqBO.getUserId());
            materialPO.setUpdatorId(createReqBO.getUserId());
            materialPO.setStatus(DemandStatusEnum.OFF.getCode());
            list.add(materialPO);
        }
        return list;
    }

    /**
     * 周期计划明细
     *
     * @param materialPOList
     * @param periodBOList
     * @return
     */
    private Map<String, List<DemandPlanMaterialPeriodPO>> convertDemandPlanMaterialPeriod(List<DemandPlanMaterialPO> materialPOList, List<DemandPlanMaterialPeriodBO> periodBOList) {
        Map<String, List<DemandPlanMaterialPeriodPO>> result = new HashMap<>();
        Map<String, DemandPlanMaterialPeriodBO> map = periodBOList.stream().collect
                (Collectors.toMap(DemandPlanMaterialPeriodBO::getMaterialCode, v -> v, (v1, v2) -> v2));
        periodBOList.stream().collect(Collectors.groupingBy(DemandPlanMaterialPeriodBO::getMaterialCode));
        for (DemandPlanMaterialPO po : materialPOList) {
            List<DemandPlanPeriodInfoBO> infoBOS = map.get(po.getMaterialCode()).getPlanPeriodInfoList();
            List<DemandPlanMaterialPeriodPO> poList = new ArrayList<>();
            infoBOS.forEach(infoBO -> {
                DemandPlanMaterialPeriodPO periodPO = new DemandPlanMaterialPeriodPO();
                periodPO.setPlanPeriod(infoBO.getPlanPeriod());
                periodPO.setPlanAmount(Long.parseLong(infoBO.getPlanAmount()));
                periodPO.setMaterialCode(po.getMaterialCode());
                periodPO.setLogicalPlantNo(po.getLogicalPlantNo());
                periodPO.setExtInfo(infoBO.getExtInfo());
                periodPO.setStatus(DemandStatusEnum.OFF.getCode());
                periodPO.setDemandType(Objects.isNull(infoBO.getDemandType())?DemandTypeEnum.PREDICT.getCode() : infoBO.getDemandType());
                poList.add(periodPO);
            });

            result.put(po.getMaterialCode(), poList);
        }
        return result;
    }


    /**
     * 校验物料编码
     *
     * @param demandPlanPO
     * @param periodBOList
     * @return
     */
    private List<DemandPlanMaterialBatchCreateDetailBO> validateImportInfo(DemandPlanPO demandPlanPO, List<DemandPlanMaterialPeriodBO> periodBOList, String tenantId) {

        List<String> materialCodes = periodBOList.stream().map(DemandPlanMaterialPeriodBO::getMaterialCode).collect(Collectors.toList());

        List<String> failMaterialCodeList = validateMaterialCodes(materialCodes, tenantId,demandPlanPO.getLogicalPlantNo());

        List<String> failStrategyCodeList = validateMaterialStrategy(demandPlanPO, materialCodes, tenantId);

        List<DemandPlanMaterialBatchCreateDetailBO> failDetails = new ArrayList<>();

        for (DemandPlanMaterialPeriodBO periodBO : periodBOList) {
            boolean checkDate = false;
            if (CollectionUtils.isNotEmpty(periodBO.getPlanPeriodInfoList())) {
                checkDate = checkPeriodDate(demandPlanPO, periodBO.getPlanPeriodInfoList());
            }
            if (checkDate) {
                //是否存在
                DemandPlanMaterialBatchCreateDetailBO bo = new DemandPlanMaterialBatchCreateDetailBO();
                bo.setMaterialCode(periodBO.getMaterialCode());
                bo.setMessage("该物料计划不在当前需求展望期内");
                failDetails.add(bo);
            } else if (failMaterialCodeList.contains(periodBO.getMaterialCode())) {
                //是否存在
                DemandPlanMaterialBatchCreateDetailBO bo = new DemandPlanMaterialBatchCreateDetailBO();
                bo.setMaterialCode(periodBO.getMaterialCode());
                bo.setMessage("该物料编码不存在");
                failDetails.add(bo);
            } else if (failStrategyCodeList.contains(periodBO.getMaterialCode())) {
                //是否MTS
                DemandPlanMaterialBatchCreateDetailBO bo = new DemandPlanMaterialBatchCreateDetailBO();
                bo.setMaterialCode(periodBO.getMaterialCode());
                bo.setMessage("该物料需求策略非MTS");
                failDetails.add(bo);
            } else {
                //数值是否正确
                List<DemandPlanPeriodInfoBO> periodInfoBOList = periodBO.getPlanPeriodInfoList();
                for (DemandPlanPeriodInfoBO periodInfoBO : periodInfoBOList) {
                    if (StringUtils.isEmpty(periodInfoBO.getPlanAmount()) || !StringUtils.isNumeric(periodInfoBO.getPlanAmount()) || Long.parseLong(periodInfoBO.getPlanAmount())<0L) {
                        DemandPlanMaterialBatchCreateDetailBO bo = new DemandPlanMaterialBatchCreateDetailBO();
                        bo.setMaterialCode(periodBO.getMaterialCode());
                        bo.setMessage("数量需为≥0的整数");
                        failDetails.add(bo);
                        break;
                    }
                }

            }
        }
        return failDetails;
    }


    /**
     * 批量查询物料信息
     *
     * @param materialCodes
     * @param tenant
     * @return
     */
    private Map<String, PlanProductBO> queryMaterial(List<String> materialCodes, String tenant) {

        Map<String, PlanProductBO> productMap = planProductStub.queryProductMap(materialCodes, tenant);

        return productMap;
    }


    /**
     * 校验物料
     *
     * @param materialCodes
     * @param tenantId
     * @param logicalPlantNo
     * @return
     */
    private List<String> validateMaterialCodes(List<String> materialCodes, String tenantId,String logicalPlantNo) {

        //这里设置一个临时变量，防止下面的removeAll把原值删除掉
        List<String> temp = Lists.newArrayList(materialCodes);
        Map<String, List<String>> map = inventoryMaterialApplicationService.listByLogicalPlantNos(Lists.newArrayList(logicalPlantNo), tenantId);
        if (Objects.nonNull(map) && CollectionUtils.isNotEmpty( map.get(logicalPlantNo))){
            temp.removeAll(map.get(logicalPlantNo));
            return temp;
        }
        /*List<SkuResponse> skuResponses = productCenterService.skuListByRequest(materialCodes, token);
        if (!CollectionUtils.isEmpty(skuResponses)) {
            List<String> codes = skuResponses.stream().filter(e -> Objects.nonNull(e) && StringUtils.isNotBlank(e.getCode()))
                    .map(SkuResponse::getCode).distinct().collect(Collectors.toList());
            temp.removeAll(codes);
            return temp;
        }*/
        return temp;
    }

    /**
     * 校验策略是否为MTS
     *
     * @param materialCodes
     * @return
     */
    private List<String> validateMaterialStrategy(DemandPlanPO demandPlanPO, List<String> materialCodes, String tenantId) {
        return Lists.newArrayList();
        //这里设置一个临时变量，防止下面的removeAll把原值删除掉
        /*List<String> temp = Lists.newArrayList(materialCodes);
        List<PlanMaterialParamQueryCondition> conditions = new ArrayList<>();
        for (String code : temp) {
            PlanMaterialParamQueryCondition condition = new PlanMaterialParamQueryCondition();
            condition.setMaterialCode(code);
            condition.setTenantId(tenantId);
            condition.setLogicalPlantNo(demandPlanPO.getLogicalPlantNo());
            conditions.add(condition);
        }
        List<PlanMaterialParameterPO> poList = planConfigDao.batchQueryPlanMaterialParam(conditions);
        List<String> validCodeList = poList.stream().filter(planMaterialParameterPO -> {
            return Objects.nonNull(planMaterialParameterPO.getDemandStrategyCode()) && planMaterialParameterPO.getDemandStrategyCode().startsWith("MTS");
        }).map(PlanMaterialParameterPO::getMaterialCode).collect(Collectors.toList());
        //去除所有符合条件的，看是否还有不符合条件的
        temp.removeAll(validCodeList);
        return temp;*/
    }

    /**
     * 校验导入的物料是否在需求展望期内
     *
     * @param planPO
     * @param planPeriodInfoList
     * @return
     */
    private boolean checkPeriodDate(DemandPlanPO planPO, List<DemandPlanPeriodInfoBO> planPeriodInfoList) {
        try {
            Date begin = DateUtils.getStartDateOfOneDay(planPO.getDemandHorizonBeginTime());
            Date end = DateUtils.getStartDateOfOneDay(planPO.getDemandHorizonEndTime());
            for (DemandPlanPeriodInfoBO periodInfoBO : planPeriodInfoList) {
                if (Objects.equals(planPO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.DAY.getCode())) {
                    Date date = DateUtils.stringToDate(periodInfoBO.getPlanPeriod(), DateUtils.YYYY_MM_DD_SLASH);
                    if (date.getTime() < begin.getTime() || date.getTime() > end.getTime()) {
                        return true;
                    }
                }
                if (Objects.equals(planPO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.WEEK.getCode())
                        || Objects.equals(planPO.getAggregationPeriod(), DemandPlanAggregationPeriodEnum.MONTH.getCode())) {
                    Date beginDate = DateUtils.stringToDate(periodInfoBO.getPlanPeriod().split("-")[0], DateUtils.YYYY_MM_DD_SLASH);
                    Date endDate = DateUtils.stringToDate(periodInfoBO.getPlanPeriod().split("-")[1], DateUtils.YYYY_MM_DD_SLASH);
                    if (beginDate.getTime() < begin.getTime() || endDate.getTime() > end.getTime()) {
                        return true;
                    }
                }
            }
            return false;
        } catch (ParseException e) {
            log.error("checkPeriodDate error", e);
        }
        return false;

    }
}
