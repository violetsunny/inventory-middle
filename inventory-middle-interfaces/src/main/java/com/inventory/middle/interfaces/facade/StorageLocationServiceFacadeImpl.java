package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.StorageLocationQueryService;
import com.inventory.middle.application.service.StorageLocationApplicationService;
import com.inventory.middle.client.dto.StorageLocationDto;
import com.inventory.middle.client.dto.command.StorageLocationCommand;
import com.inventory.middle.client.dto.query.StorageLocationPageQuery;
import com.inventory.middle.client.facade.StorageLocationServiceFacade;
import com.inventory.middle.application.convertor.StorageLocationDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 存储地点表FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class StorageLocationServiceFacadeImpl implements StorageLocationServiceFacade {

	@Resource
	private StorageLocationApplicationService storagelocationApplicationService;
	@Resource
	private StorageLocationQueryService storagelocationQueryService;
	@Resource
	private StorageLocationDtoConvertor  storagelocationDtoConvertor;


	/**
	 * 存储地点表分页查询
	 */
	@Override
	public PageResponse<StorageLocationDto> page(StorageLocationPageQuery storagelocationPageQuery) {
		return storagelocationQueryService.queryPage(storagelocationPageQuery);
	}

	/**
	 * 存储地点表list查询
	 */
	@Override
	public MultiResponse<StorageLocationDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 存储地点表信息
	 */
	@Override
	public SingleResponse<StorageLocationDto> findById(Long id) {
		return SingleResponse.buildSuccess(storagelocationQueryService.findById(id));
	}

	/**
	 * 保存存储地点表
	 */
	@Override
	public SingleResponse<Boolean> save(StorageLocationCommand storagelocationCommand) {
		return SingleResponse.buildSuccess(storagelocationApplicationService.add(storagelocationCommand));

	}

	/**
	 * 修改存储地点表
	 */
	@Override
	public SingleResponse<Boolean> update( StorageLocationCommand storagelocationCommand) {
		return SingleResponse.buildSuccess(storagelocationApplicationService.update(storagelocationCommand));
	}

	/**
	 * 删除存储地点表
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(storagelocationApplicationService.deleteBatch(ids));
	}

}

