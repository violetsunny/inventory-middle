package com.inventory.middle.application.service.impl;

import com.inventory.middle.application.service.InventoryMonitorRuleLineApplicationService;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleId;
import com.inventory.middle.domain.repository.InventoryMonitorRuleRepository;
import com.inventory.middle.domain.specification.InventoryMonitorRuleUpdateSpecification;
import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.application.convertor.InventoryMonitorRuleDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;
import com.inventory.middle.client.enums.monitor.MonitorRuleTypeEnum;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRulePageQuery;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;
import com.inventory.middle.domain.service.InventoryMonitorRuleDomainService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import top.kdla.framework.domain.ApplicationContextHelp;
import top.kdla.framework.dto.PageResponse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import com.inventory.middle.application.service.InventoryMonitorRuleLineQueryService;
import com.inventory.middle.client.dto.query.InventoryMonitorRuleLinePageQuery;
import java.util.stream.Collectors;


/**
 * 库存预警规则ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class InventoryMonitorRuleApplicationServiceImpl implements InventoryMonitorRuleApplicationService {

	@Resource
	private InventoryMonitorRuleRepository inventorymonitorruleRepository;

	@Resource
	private InventoryMonitorRuleDtoConvertor dtoConvertor;

	@Resource
	private InventoryMonitorRuleDomainService inventoryMonitorRuleDomainService;

        @Resource
        private InventoryMonitorRuleLineApplicationService monitorRuleLineApplicationService;

        @Resource
        private InventoryMonitorRuleLineQueryService monitorRuleLineQueryService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryMonitorRuleCommand inventorymonitorruleCommand) {
		InventoryMonitorRule inventorymonitorrule = dtoConvertor.toInventoryMonitorRule(inventorymonitorruleCommand);
		return inventorymonitorruleRepository.store(inventorymonitorrule);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryMonitorRuleCommand inventorymonitorruleCommand) {
		InventoryMonitorRule inventorymonitorrule = dtoConvertor.toInventoryMonitorRule(inventorymonitorruleCommand);
		InventoryMonitorRuleUpdateSpecification inventorymonitorruleUpdateSpecification = new InventoryMonitorRuleUpdateSpecification();
		inventorymonitorruleUpdateSpecification.isSatisfiedBy(inventorymonitorrule);
		return  inventorymonitorruleRepository.store(inventorymonitorrule );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryMonitorRuleId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryMonitorRuleId(id));
		});
		return inventorymonitorruleRepository.delete(tempIds);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void processMonitorMessage(MonitorMessageBO monitorMessageBO) {
		if (monitorMessageBO == null || StringUtils.isBlank(monitorMessageBO.getBeanName())) {
			log.warn("InventoryMonitorRuleApplicationServiceImpl.processMonitorMessage: invalid message, skip");
			return;
		}
		log.info("InventoryMonitorRuleApplicationServiceImpl.processMonitorMessage beanName={} monitorRuleId={}",
				monitorMessageBO.getBeanName(), monitorMessageBO.getMonitorRuleId());
		IHandleChain<MonitorMessageBO, Boolean> handleChain =
				ApplicationContextHelp.getBean(monitorMessageBO.getBeanName(), IHandleChain.class);
		HandleMessage<MonitorMessageBO, Boolean> handleMessage = new HandleMessage<>();
		handleMessage.setT(monitorMessageBO);
		handleChain.doProcess(handleMessage);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void processInOutBoundMessage(com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage changeMessage) {
		if (changeMessage == null) {
			log.warn("InventoryMonitorRuleApplicationServiceImpl.processInOutBoundMessage: null message, skip");
			return;
		}
		log.info("InventoryMonitorRuleApplicationServiceImpl.processInOutBoundMessage materialCode={} logicalPlantNo={}",
				changeMessage.getMaterialCode(), changeMessage.getLogicalPlantNo());
		IHandleChain<com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage, Boolean> handleChain =
				ApplicationContextHelp.getBean("monitorRuleInventorySnapshotHandleChain", IHandleChain.class);
		HandleMessage<com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage, Boolean> handleMessage = new HandleMessage<>();
		handleMessage.setT(changeMessage);
		handleChain.doProcess(handleMessage);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void processAnnualInspection() {
		log.info("InventoryMonitorRuleApplicationServiceImpl.processAnnualInspection start");
		IHandleChain<InventoryMonitorRuleBO, Boolean> handleChain =
				ApplicationContextHelp.getBean("monitorAnnualInspectionHandleChain", IHandleChain.class);
		int pageNum = 1;
		int pageSize = 100;
		Long lastRuleId = 0L;
		while (true) {
			InventoryMonitorRulePageQuery pageQuery = new InventoryMonitorRulePageQuery();
			pageQuery.setMonitorType(MonitorRuleTypeEnum.ANNUAL_INSPECTION.name());
			pageQuery.setMonitorEnableStatus("ENABLE");
			pageQuery.setPageNum(pageNum);
			pageQuery.setPageSize(pageSize);
			PageResponse<InventoryMonitorRuleBO> page = inventoryMonitorRuleDomainService.pageListFromMaxId(pageQuery, lastRuleId);
			if (page == null || page.getData() == null || page.getData().isEmpty()) {
				break;
			}
			for (InventoryMonitorRuleBO ruleBO : page.getData()) {
				try {
					HandleMessage<InventoryMonitorRuleBO, Boolean> handleMessage = new HandleMessage<>();
					handleMessage.setT(ruleBO);
					handleChain.doProcess(handleMessage);
					lastRuleId = ruleBO.getId();
				} catch (Exception e) {
					log.error("InventoryMonitorRuleApplicationServiceImpl.processAnnualInspection rule={} error", ruleBO.getId(), e);
				}
			}
			if (page.getData().size() < pageSize) {
				break;
			}
		}
		log.info("InventoryMonitorRuleApplicationServiceImpl.processAnnualInspection end");
	}

        @Override
        @Transactional(rollbackFor = Exception.class)
        public boolean updateWithLines(InventoryMonitorRuleCommand ruleCommand,
                                       List<InventoryMonitorRuleLineCommand> lineCommands) {
                // 1. 更新规则主体
                update(ruleCommand);
                // 2. 按 ruleId 查全量旧明细，删除
                if (ruleCommand.getId() != null) {
                        InventoryMonitorRuleLinePageQuery query = new InventoryMonitorRuleLinePageQuery();
                        query.setMonitorRuleId(ruleCommand.getId());
                        query.setPageSize(Integer.MAX_VALUE);
                        query.setPageNum(1);
                        top.kdla.framework.dto.PageResponse<com.inventory.middle.client.dto.InventoryMonitorRuleLineDto> oldPage =
                                monitorRuleLineQueryService.queryPage(query);
                        if (oldPage != null && oldPage.getData() != null && !oldPage.getData().isEmpty()) {
                                List<Long> oldIds = oldPage.getData().stream()
                                        .map(com.inventory.middle.client.dto.InventoryMonitorRuleLineDto::getId)
                                        .filter(id -> id != null)
                                        .collect(Collectors.toList());
                                if (!oldIds.isEmpty()) {
                                        monitorRuleLineApplicationService.deleteBatch(oldIds);
                                }
                        }
                }
                // 3. 批量新增明细
                if (lineCommands != null) {
                        lineCommands.forEach(c -> {
                                c.setMonitorRuleId(ruleCommand.getId());
                                monitorRuleLineApplicationService.add(c);
                        });
                }
                return true;
        }

}
