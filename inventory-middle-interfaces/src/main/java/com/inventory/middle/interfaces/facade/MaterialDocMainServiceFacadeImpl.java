package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.MaterialDocMainQueryService;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.command.MaterialDocMainCommand;
import com.inventory.middle.client.dto.query.MaterialDocMainPageQuery;
import com.inventory.middle.client.facade.MaterialDocMainServiceFacade;
import com.inventory.middle.application.convertor.MaterialDocMainDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物料凭证主表FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Component
@Slf4j
@CatchAndLog
public class MaterialDocMainServiceFacadeImpl implements MaterialDocMainServiceFacade {

	@Resource
	private MaterialDocMainApplicationService materialdocmainApplicationService;
	@Resource
	private MaterialDocMainQueryService materialdocmainQueryService;
	@Resource
	private MaterialDocMainDtoConvertor  materialdocmainDtoConvertor;


	/**
	 * 物料凭证主表分页查询
	 */
	@Override
	public PageResponse<MaterialDocMainDto> page(MaterialDocMainPageQuery materialdocmainPageQuery) {
		return materialdocmainQueryService.queryPage(materialdocmainPageQuery);
	}

	/**
	 * 物料凭证主表list查询
	 */
	@Override
	public MultiResponse<MaterialDocMainDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物料凭证主表信息
	 */
	@Override
	public SingleResponse<MaterialDocMainDto> findById(Long id) {
		return SingleResponse.buildSuccess(materialdocmainQueryService.findById(id));
	}

	/**
	 * 保存物料凭证主表
	 */
	@Override
	public SingleResponse<Boolean> save(MaterialDocMainCommand materialdocmainCommand) {
		return SingleResponse.buildSuccess(materialdocmainApplicationService.add(materialdocmainCommand));

	}

	/**
	 * 修改物料凭证主表
	 */
	@Override
	public SingleResponse<Boolean> update( MaterialDocMainCommand materialdocmainCommand) {
		return SingleResponse.buildSuccess(materialdocmainApplicationService.update(materialdocmainCommand));
	}

	/**
	 * 删除物料凭证主表
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(materialdocmainApplicationService.deleteBatch(ids));
	}

}

