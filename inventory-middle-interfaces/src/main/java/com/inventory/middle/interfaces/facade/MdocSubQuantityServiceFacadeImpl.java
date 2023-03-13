package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.MdocSubQuantityQueryService;
import com.inventory.middle.application.service.MdocSubQuantityApplicationService;
import com.inventory.middle.client.dto.MdocSubQuantityDto;
import com.inventory.middle.client.dto.command.MdocSubQuantityCommand;
import com.inventory.middle.client.dto.query.MdocSubQuantityPageQuery;
import com.inventory.middle.client.facade.MdocSubQuantityServiceFacade;
import com.inventory.middle.application.convertor.MdocSubQuantityDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物料凭证子表-数量FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Component
@Slf4j
@CatchAndLog
public class MdocSubQuantityServiceFacadeImpl implements MdocSubQuantityServiceFacade {

	@Resource
	private MdocSubQuantityApplicationService mdocsubquantityApplicationService;
	@Resource
	private MdocSubQuantityQueryService mdocsubquantityQueryService;
	@Resource
	private MdocSubQuantityDtoConvertor  mdocsubquantityDtoConvertor;


	/**
	 * 物料凭证子表-数量分页查询
	 */
	@Override
	public PageResponse<MdocSubQuantityDto> page(MdocSubQuantityPageQuery mdocsubquantityPageQuery) {
		return mdocsubquantityQueryService.queryPage(mdocsubquantityPageQuery);
	}

	/**
	 * 物料凭证子表-数量list查询
	 */
	@Override
	public MultiResponse<MdocSubQuantityDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物料凭证子表-数量信息
	 */
	@Override
	public SingleResponse<MdocSubQuantityDto> findById(Long id) {
		return SingleResponse.buildSuccess(mdocsubquantityQueryService.findById(id));
	}

	/**
	 * 保存物料凭证子表-数量
	 */
	@Override
	public SingleResponse<Boolean> save(MdocSubQuantityCommand mdocsubquantityCommand) {
		return SingleResponse.buildSuccess(mdocsubquantityApplicationService.add(mdocsubquantityCommand));

	}

	/**
	 * 修改物料凭证子表-数量
	 */
	@Override
	public SingleResponse<Boolean> update( MdocSubQuantityCommand mdocsubquantityCommand) {
		return SingleResponse.buildSuccess(mdocsubquantityApplicationService.update(mdocsubquantityCommand));
	}

	/**
	 * 删除物料凭证子表-数量
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(mdocsubquantityApplicationService.deleteBatch(ids));
	}

}

