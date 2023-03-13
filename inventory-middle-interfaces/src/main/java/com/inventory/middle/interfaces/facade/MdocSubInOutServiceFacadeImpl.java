package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.MdocSubInOutQueryService;
import com.inventory.middle.application.service.MdocSubInOutApplicationService;
import com.inventory.middle.client.dto.MdocSubInOutDto;
import com.inventory.middle.client.dto.command.MdocSubInOutCommand;
import com.inventory.middle.client.dto.query.MdocSubInOutPageQuery;
import com.inventory.middle.client.facade.MdocSubInOutServiceFacade;
import com.inventory.middle.application.convertor.MdocSubInOutDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物料凭证子表-出入库信息FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Component
@Slf4j
@CatchAndLog
public class MdocSubInOutServiceFacadeImpl implements MdocSubInOutServiceFacade {

	@Resource
	private MdocSubInOutApplicationService mdocsubinoutApplicationService;
	@Resource
	private MdocSubInOutQueryService mdocsubinoutQueryService;
	@Resource
	private MdocSubInOutDtoConvertor  mdocsubinoutDtoConvertor;


	/**
	 * 物料凭证子表-出入库信息分页查询
	 */
	@Override
	public PageResponse<MdocSubInOutDto> page(MdocSubInOutPageQuery mdocsubinoutPageQuery) {
		return mdocsubinoutQueryService.queryPage(mdocsubinoutPageQuery);
	}

	/**
	 * 物料凭证子表-出入库信息list查询
	 */
	@Override
	public MultiResponse<MdocSubInOutDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物料凭证子表-出入库信息信息
	 */
	@Override
	public SingleResponse<MdocSubInOutDto> findById(Long id) {
		return SingleResponse.buildSuccess(mdocsubinoutQueryService.findById(id));
	}

	/**
	 * 保存物料凭证子表-出入库信息
	 */
	@Override
	public SingleResponse<Boolean> save(MdocSubInOutCommand mdocsubinoutCommand) {
		return SingleResponse.buildSuccess(mdocsubinoutApplicationService.add(mdocsubinoutCommand));

	}

	/**
	 * 修改物料凭证子表-出入库信息
	 */
	@Override
	public SingleResponse<Boolean> update( MdocSubInOutCommand mdocsubinoutCommand) {
		return SingleResponse.buildSuccess(mdocsubinoutApplicationService.update(mdocsubinoutCommand));
	}

	/**
	 * 删除物料凭证子表-出入库信息
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(mdocsubinoutApplicationService.deleteBatch(ids));
	}

}

