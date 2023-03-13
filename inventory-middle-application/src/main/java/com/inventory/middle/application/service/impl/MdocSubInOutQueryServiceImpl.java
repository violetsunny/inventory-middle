package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.MdocSubInOutId;
import com.inventory.middle.domain.model.entity.MdocSubInOut;
import com.inventory.middle.domain.repository.MdocSubInOutRepository;
import com.inventory.middle.application.service.MdocSubInOutQueryService;
import com.inventory.middle.application.convertor.MdocSubInOutDtoConvertor;
import com.inventory.middle.client.dto.MdocSubInOutDto;
import com.inventory.middle.client.dto.query.MdocSubInOutPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证子表-出入库信息QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class MdocSubInOutQueryServiceImpl implements MdocSubInOutQueryService {

	@Resource
	private MdocSubInOutRepository mdocsubinoutRepository;
	@Resource
	private MdocSubInOutDtoConvertor dtoConvertor;


	@Override
	public PageResponse<MdocSubInOutDto> queryPage(MdocSubInOutPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<MdocSubInOut> page = mdocsubinoutRepository.queryPage(pageQuery, params);
		List<MdocSubInOutDto> dtoList = page.getData().stream().map(dtoConvertor::fromMdocSubInOut).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public MdocSubInOutDto findById(Long id) {
		return dtoConvertor.fromMdocSubInOut(mdocsubinoutRepository.findById(new MdocSubInOutId(id)));
	}

}

