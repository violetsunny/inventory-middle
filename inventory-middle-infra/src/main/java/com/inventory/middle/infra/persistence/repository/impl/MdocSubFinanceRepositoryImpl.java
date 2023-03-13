package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.MdocSubFinance;
import com.inventory.middle.domain.model.types.MdocSubFinanceId;
import com.inventory.middle.domain.repository.MdocSubFinanceRepository;
import com.inventory.middle.infra.persistence.convertor.MdocSubFinanceConvertor;
import com.inventory.middle.infra.persistence.entity.MdocSubFinanceDo;
import com.inventory.middle.infra.persistence.mapper.MdocSubFinanceMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.infra.dal.mybatis.util.PlusPageQuery;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证-标签-财务Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Repository
public class MdocSubFinanceRepositoryImpl extends ServiceImpl<MdocSubFinanceMapper, MdocSubFinanceDo> implements MdocSubFinanceRepository, IService<MdocSubFinanceDo> {

	@Resource
	private MdocSubFinanceConvertor convertor;

	@Override
	public PageResponse<MdocSubFinance> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<MdocSubFinanceDo> page = baseMapper.queryPage(new PlusPageQuery<MdocSubFinanceDo>(pageQuery).getPage(params),params);
		List<MdocSubFinanceDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<MdocSubFinance> dtoList = records.stream().map(convertor::toMdocSubFinance).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public MdocSubFinance findById(MdocSubFinanceId id) {
		MdocSubFinanceDo mdocsubfinanceDo = baseMapper.findById(id.get());
		return convertor.toMdocSubFinance(mdocsubfinanceDo);
	}

	@Override
	public boolean store(MdocSubFinance mdocsubfinance) {
		MdocSubFinanceDo mdocsubfinanceDo = convertor.fromMdocSubFinance(mdocsubfinance);
		return this.saveOrUpdate(mdocsubfinanceDo);
	}

	@Override
	public boolean update(MdocSubFinance mdocsubfinance) {
		MdocSubFinanceDo mdocsubfinanceDo = convertor.fromMdocSubFinance(mdocsubfinance);
		return this.saveOrUpdate(mdocsubfinanceDo);
	}

	@Override
	public boolean delete(List<MdocSubFinanceId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
