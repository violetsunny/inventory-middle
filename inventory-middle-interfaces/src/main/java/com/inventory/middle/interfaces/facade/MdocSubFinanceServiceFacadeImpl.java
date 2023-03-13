package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.MdocSubFinanceQueryService;
import com.inventory.middle.application.service.MdocSubFinanceApplicationService;
import com.inventory.middle.client.dto.MdocSubFinanceDto;
import com.inventory.middle.client.dto.command.MdocSubFinanceCommand;
import com.inventory.middle.client.dto.query.MdocSubFinancePageQuery;
import com.inventory.middle.client.facade.MdocSubFinanceServiceFacade;
import com.inventory.middle.application.convertor.MdocSubFinanceDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物料凭证-标签-财务FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Component
@Slf4j
@CatchAndLog
public class MdocSubFinanceServiceFacadeImpl implements MdocSubFinanceServiceFacade {

	@Resource
	private MdocSubFinanceApplicationService mdocsubfinanceApplicationService;
	@Resource
	private MdocSubFinanceQueryService mdocsubfinanceQueryService;
	@Resource
	private MdocSubFinanceDtoConvertor  mdocsubfinanceDtoConvertor;


	/**
	 * 物料凭证-标签-财务分页查询
	 */
	@Override
	public PageResponse<MdocSubFinanceDto> page(MdocSubFinancePageQuery mdocsubfinancePageQuery) {
		return mdocsubfinanceQueryService.queryPage(mdocsubfinancePageQuery);
	}

	/**
	 * 物料凭证-标签-财务list查询
	 */
	@Override
	public MultiResponse<MdocSubFinanceDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物料凭证-标签-财务信息
	 */
	@Override
	public SingleResponse<MdocSubFinanceDto> findById(Long id) {
		return SingleResponse.buildSuccess(mdocsubfinanceQueryService.findById(id));
	}

	/**
	 * 保存物料凭证-标签-财务
	 */
	@Override
	public SingleResponse<Boolean> save(MdocSubFinanceCommand mdocsubfinanceCommand) {
		return SingleResponse.buildSuccess(mdocsubfinanceApplicationService.add(mdocsubfinanceCommand));

	}

	/**
	 * 修改物料凭证-标签-财务
	 */
	@Override
	public SingleResponse<Boolean> update( MdocSubFinanceCommand mdocsubfinanceCommand) {
		return SingleResponse.buildSuccess(mdocsubfinanceApplicationService.update(mdocsubfinanceCommand));
	}

	/**
	 * 删除物料凭证-标签-财务
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(mdocsubfinanceApplicationService.deleteBatch(ids));
	}

}

