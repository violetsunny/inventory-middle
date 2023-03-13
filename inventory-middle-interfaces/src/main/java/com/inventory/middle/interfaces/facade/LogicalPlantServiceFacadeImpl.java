package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.application.service.LogicalPlantApplicationService;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.command.LogicalPlantCommand;
import com.inventory.middle.client.dto.query.LogicalPlantPageQuery;
import com.inventory.middle.client.facade.LogicalPlantServiceFacade;
import com.inventory.middle.application.convertor.LogicalPlantDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 逻辑仓库表FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class LogicalPlantServiceFacadeImpl implements LogicalPlantServiceFacade {

	@Resource
	private LogicalPlantApplicationService logicalplantApplicationService;
	@Resource
	private LogicalPlantQueryService logicalplantQueryService;
	@Resource
	private LogicalPlantDtoConvertor  logicalplantDtoConvertor;


	/**
	 * 逻辑仓库表分页查询
	 */
	@Override
	public PageResponse<LogicalPlantDto> page(LogicalPlantPageQuery logicalplantPageQuery) {
		return logicalplantQueryService.queryPage(logicalplantPageQuery);
	}

	/**
	 * 逻辑仓库表list查询
	 */
	@Override
	public MultiResponse<LogicalPlantDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 逻辑仓库表信息
	 */
	@Override
	public SingleResponse<LogicalPlantDto> findById(Long id) {
		return SingleResponse.buildSuccess(logicalplantQueryService.findById(id));
	}

	/**
	 * 保存逻辑仓库表
	 */
	@Override
	public SingleResponse<Boolean> save(LogicalPlantCommand logicalplantCommand) {
		return SingleResponse.buildSuccess(logicalplantApplicationService.add(logicalplantCommand));

	}

	/**
	 * 修改逻辑仓库表
	 */
	@Override
	public SingleResponse<Boolean> update( LogicalPlantCommand logicalplantCommand) {
		return SingleResponse.buildSuccess(logicalplantApplicationService.update(logicalplantCommand));
	}

	/**
	 * 删除逻辑仓库表
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(logicalplantApplicationService.deleteBatch(ids));
	}

}

