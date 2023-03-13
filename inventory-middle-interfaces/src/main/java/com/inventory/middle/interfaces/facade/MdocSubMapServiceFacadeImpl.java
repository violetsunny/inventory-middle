package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.MdocSubMapQueryService;
import com.inventory.middle.application.service.MdocSubMapApplicationService;
import com.inventory.middle.client.dto.MdocSubMapDto;
import com.inventory.middle.client.dto.command.MdocSubMapCommand;
import com.inventory.middle.client.dto.query.MdocSubMapPageQuery;
import com.inventory.middle.client.facade.MdocSubMapServiceFacade;
import com.inventory.middle.application.convertor.MdocSubMapDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物料凭证-标签-移动平均价FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Component
@Slf4j
@CatchAndLog
public class MdocSubMapServiceFacadeImpl implements MdocSubMapServiceFacade {

	@Resource
	private MdocSubMapApplicationService mdocsubmapApplicationService;
	@Resource
	private MdocSubMapQueryService mdocsubmapQueryService;
	@Resource
	private MdocSubMapDtoConvertor  mdocsubmapDtoConvertor;


	/**
	 * 物料凭证-标签-移动平均价分页查询
	 */
	@Override
	public PageResponse<MdocSubMapDto> page(MdocSubMapPageQuery mdocsubmapPageQuery) {
		return mdocsubmapQueryService.queryPage(mdocsubmapPageQuery);
	}

	/**
	 * 物料凭证-标签-移动平均价list查询
	 */
	@Override
	public MultiResponse<MdocSubMapDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物料凭证-标签-移动平均价信息
	 */
	@Override
	public SingleResponse<MdocSubMapDto> findById(Long id) {
		return SingleResponse.buildSuccess(mdocsubmapQueryService.findById(id));
	}

	/**
	 * 保存物料凭证-标签-移动平均价
	 */
	@Override
	public SingleResponse<Boolean> save(MdocSubMapCommand mdocsubmapCommand) {
		return SingleResponse.buildSuccess(mdocsubmapApplicationService.add(mdocsubmapCommand));

	}

	/**
	 * 修改物料凭证-标签-移动平均价
	 */
	@Override
	public SingleResponse<Boolean> update( MdocSubMapCommand mdocsubmapCommand) {
		return SingleResponse.buildSuccess(mdocsubmapApplicationService.update(mdocsubmapCommand));
	}

	/**
	 * 删除物料凭证-标签-移动平均价
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(mdocsubmapApplicationService.deleteBatch(ids));
	}

}

