package com.inventory.middle.application.service.impl;

import com.alibaba.excel.EasyExcel;
import com.inventory.middle.application.bo.monitor.ImportMonitorRuleLineExcelBO;
import com.inventory.middle.application.service.monitor.MonitorRuleLineExcelReadListener;
import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleLineId;
import com.inventory.middle.domain.repository.InventoryMonitorRuleLineRepository;
import com.inventory.middle.domain.specification.InventoryMonitorRuleLineUpdateSpecification;
import com.inventory.middle.application.service.InventoryMonitorRuleLineApplicationService;
import com.inventory.middle.application.convertor.InventoryMonitorRuleLineDtoConvertor;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 库存预警规则明细ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class InventoryMonitorRuleLineApplicationServiceImpl implements InventoryMonitorRuleLineApplicationService {

	@Resource
	private InventoryMonitorRuleLineRepository inventorymonitorrulelineRepository;

	@Resource
	private InventoryMonitorRuleLineDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand) {
		InventoryMonitorRuleLine inventorymonitorruleline = dtoConvertor.toInventoryMonitorRuleLine(inventorymonitorrulelineCommand);
		return inventorymonitorrulelineRepository.store(inventorymonitorruleline);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand) {
		InventoryMonitorRuleLine inventorymonitorruleline = dtoConvertor.toInventoryMonitorRuleLine(inventorymonitorrulelineCommand);
		InventoryMonitorRuleLineUpdateSpecification inventorymonitorrulelineUpdateSpecification = new InventoryMonitorRuleLineUpdateSpecification();
		inventorymonitorrulelineUpdateSpecification.isSatisfiedBy(inventorymonitorruleline);
		return  inventorymonitorrulelineRepository.store(inventorymonitorruleline );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<InventoryMonitorRuleLineId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new InventoryMonitorRuleLineId(id));
		});
		return  inventorymonitorrulelineRepository.delete(tempIds);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String importByExcel(Long monitorRuleId, String monitorType, MultipartFile file, String tenantId) {
		String taskKey = UUID.randomUUID().toString().replace("-", "");
		log.info("importByExcel start: monitorRuleId={}, monitorType={}, tenantId={}, taskKey={}",
				monitorRuleId, monitorType, tenantId, taskKey);
		try {
			MonitorRuleLineExcelReadListener listener = new MonitorRuleLineExcelReadListener(rows -> {
				List<InventoryMonitorRuleLine> lines = rows.stream().map(row -> {
					InventoryMonitorRuleLine line = new InventoryMonitorRuleLine();
					line.setMonitorRuleId(monitorRuleId != null ? monitorRuleId : row.getMonitorRuleId());
					line.setMonitorDimension(row.getMonitorDimension());
					line.setMonitorObject(row.getMonitorObject());
					line.setMonitorCeil(row.getMonitorCeil() != null ? row.getMonitorCeil() : BigDecimal.ZERO);
					line.setMonitorFloor(row.getMonitorFloor() != null ? row.getMonitorFloor() : BigDecimal.ZERO);
					line.setTenantId(tenantId != null ? tenantId : row.getTenantId());
					return line;
				}).collect(Collectors.toList());
				if (!lines.isEmpty()) {
					inventorymonitorrulelineRepository.batchStore(lines);
					log.info("importByExcel done: count={}, taskKey={}", lines.size(), taskKey);
				}
				return true;
			});
			EasyExcel.read(file.getInputStream(), ImportMonitorRuleLineExcelBO.class, listener).sheet().doRead();
			if (!listener.isSuccess()) {
				throw new RuntimeException("Excel 行数据校验失败");
			}
		} catch (Exception e) {
			log.error("importByExcel failed, taskKey={}", taskKey, e);
			throw new RuntimeException("Excel 导入失败: " + e.getMessage(), e);
		}
		return taskKey;
	}
}

