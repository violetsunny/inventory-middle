package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.MdocSubExtQueryService;
import com.inventory.middle.application.service.MdocSubExtApplicationService;
import com.inventory.middle.client.dto.MdocSubExtDto;
import com.inventory.middle.client.dto.command.MdocSubExtCommand;
import com.inventory.middle.client.dto.query.MdocSubExtPageQuery;
import com.inventory.middle.client.facade.MdocSubExtServiceFacade;
import com.inventory.middle.application.convertor.MdocSubExtDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物料凭证-标签-扩展FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Component
@Slf4j
@CatchAndLog
public class MdocSubExtServiceFacadeImpl implements MdocSubExtServiceFacade {

	@Resource
	private MdocSubExtApplicationService mdocsubextApplicationService;
	@Resource
	private MdocSubExtQueryService mdocsubextQueryService;
	@Resource
	private MdocSubExtDtoConvertor  mdocsubextDtoConvertor;


	/**
	 * 物料凭证-标签-扩展分页查询
	 */
	@Override
	public PageResponse<MdocSubExtDto> page(MdocSubExtPageQuery mdocsubextPageQuery) {
		return mdocsubextQueryService.queryPage(mdocsubextPageQuery);
	}

	/**
	 * 物料凭证-标签-扩展list查询
	 */
	@Override
	public MultiResponse<MdocSubExtDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物料凭证-标签-扩展信息
	 */
	@Override
	public SingleResponse<MdocSubExtDto> findById(Long id) {
		return SingleResponse.buildSuccess(mdocsubextQueryService.findById(id));
	}

	/**
	 * 保存物料凭证-标签-扩展
	 */
	@Override
	public SingleResponse<Boolean> save(MdocSubExtCommand mdocsubextCommand) {
		return SingleResponse.buildSuccess(mdocsubextApplicationService.add(mdocsubextCommand));

	}

	/**
	 * 修改物料凭证-标签-扩展
	 */
	@Override
	public SingleResponse<Boolean> update( MdocSubExtCommand mdocsubextCommand) {
		return SingleResponse.buildSuccess(mdocsubextApplicationService.update(mdocsubextCommand));
	}

	/**
	 * 删除物料凭证-标签-扩展
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(mdocsubextApplicationService.deleteBatch(ids));
	}

}

