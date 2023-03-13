package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.MdocSubMaterialQueryService;
import com.inventory.middle.application.service.MdocSubMaterialApplicationService;
import com.inventory.middle.client.dto.MdocSubMaterialDto;
import com.inventory.middle.client.dto.command.MdocSubMaterialCommand;
import com.inventory.middle.client.dto.query.MdocSubMaterialPageQuery;
import com.inventory.middle.client.facade.MdocSubMaterialServiceFacade;
import com.inventory.middle.application.convertor.MdocSubMaterialDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物料凭证子表-物料信息FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Component
@Slf4j
@CatchAndLog
public class MdocSubMaterialServiceFacadeImpl implements MdocSubMaterialServiceFacade {

	@Resource
	private MdocSubMaterialApplicationService mdocsubmaterialApplicationService;
	@Resource
	private MdocSubMaterialQueryService mdocsubmaterialQueryService;
	@Resource
	private MdocSubMaterialDtoConvertor  mdocsubmaterialDtoConvertor;


	/**
	 * 物料凭证子表-物料信息分页查询
	 */
	@Override
	public PageResponse<MdocSubMaterialDto> page(MdocSubMaterialPageQuery mdocsubmaterialPageQuery) {
		return mdocsubmaterialQueryService.queryPage(mdocsubmaterialPageQuery);
	}

	/**
	 * 物料凭证子表-物料信息list查询
	 */
	@Override
	public MultiResponse<MdocSubMaterialDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物料凭证子表-物料信息信息
	 */
	@Override
	public SingleResponse<MdocSubMaterialDto> findById(Long id) {
		return SingleResponse.buildSuccess(mdocsubmaterialQueryService.findById(id));
	}

	/**
	 * 保存物料凭证子表-物料信息
	 */
	@Override
	public SingleResponse<Boolean> save(MdocSubMaterialCommand mdocsubmaterialCommand) {
		return SingleResponse.buildSuccess(mdocsubmaterialApplicationService.add(mdocsubmaterialCommand));

	}

	/**
	 * 修改物料凭证子表-物料信息
	 */
	@Override
	public SingleResponse<Boolean> update( MdocSubMaterialCommand mdocsubmaterialCommand) {
		return SingleResponse.buildSuccess(mdocsubmaterialApplicationService.update(mdocsubmaterialCommand));
	}

	/**
	 * 删除物料凭证子表-物料信息
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(mdocsubmaterialApplicationService.deleteBatch(ids));
	}

}

