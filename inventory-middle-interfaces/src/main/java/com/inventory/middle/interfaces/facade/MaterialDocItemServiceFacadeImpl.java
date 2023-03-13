package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.MaterialDocItemQueryService;
import com.inventory.middle.application.service.MaterialDocItemApplicationService;
import com.inventory.middle.client.dto.MaterialDocItemDto;
import com.inventory.middle.client.dto.command.MaterialDocItemCommand;
import com.inventory.middle.client.dto.query.MaterialDocItemPageQuery;
import com.inventory.middle.client.facade.MaterialDocItemServiceFacade;
import com.inventory.middle.application.convertor.MaterialDocItemDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物料凭证-itemFacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Component
@Slf4j
@CatchAndLog
public class MaterialDocItemServiceFacadeImpl implements MaterialDocItemServiceFacade {

	@Resource
	private MaterialDocItemApplicationService materialdocitemApplicationService;
	@Resource
	private MaterialDocItemQueryService materialdocitemQueryService;
	@Resource
	private MaterialDocItemDtoConvertor  materialdocitemDtoConvertor;


	/**
	 * 物料凭证-item分页查询
	 */
	@Override
	public PageResponse<MaterialDocItemDto> page(MaterialDocItemPageQuery materialdocitemPageQuery) {
		return materialdocitemQueryService.queryPage(materialdocitemPageQuery);
	}

	/**
	 * 物料凭证-itemlist查询
	 */
	@Override
	public MultiResponse<MaterialDocItemDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物料凭证-item信息
	 */
	@Override
	public SingleResponse<MaterialDocItemDto> findById(Long id) {
		return SingleResponse.buildSuccess(materialdocitemQueryService.findById(id));
	}

	/**
	 * 保存物料凭证-item
	 */
	@Override
	public SingleResponse<Boolean> save(MaterialDocItemCommand materialdocitemCommand) {
		return SingleResponse.buildSuccess(materialdocitemApplicationService.add(materialdocitemCommand));

	}

	/**
	 * 修改物料凭证-item
	 */
	@Override
	public SingleResponse<Boolean> update( MaterialDocItemCommand materialdocitemCommand) {
		return SingleResponse.buildSuccess(materialdocitemApplicationService.update(materialdocitemCommand));
	}

	/**
	 * 删除物料凭证-item
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(materialdocitemApplicationService.deleteBatch(ids));
	}

}

