package com.inventory.middle.application.service.impl;

import com.inventory.middle.domain.model.entity.MdocSubFinance;
import com.inventory.middle.domain.model.types.MdocSubFinanceId;
import com.inventory.middle.domain.repository.MdocSubFinanceRepository;
import com.inventory.middle.domain.specification.MdocSubFinanceUpdateSpecification;
import com.inventory.middle.application.service.MdocSubFinanceApplicationService;
import com.inventory.middle.application.convertor.MdocSubFinanceDtoConvertor;
import com.inventory.middle.client.dto.command.MdocSubFinanceCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物料凭证-标签-财务ApplicationServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MdocSubFinanceApplicationServiceImpl implements MdocSubFinanceApplicationService {

	@Resource
	private MdocSubFinanceRepository mdocsubfinanceRepository;

	@Resource
	private MdocSubFinanceDtoConvertor dtoConvertor;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean add(MdocSubFinanceCommand mdocsubfinanceCommand) {
		MdocSubFinance mdocsubfinance = dtoConvertor.toMdocSubFinance(mdocsubfinanceCommand);
		return mdocsubfinanceRepository.store(mdocsubfinance);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(MdocSubFinanceCommand mdocsubfinanceCommand) {
		MdocSubFinance mdocsubfinance = dtoConvertor.toMdocSubFinance(mdocsubfinanceCommand);
		MdocSubFinanceUpdateSpecification mdocsubfinanceUpdateSpecification = new MdocSubFinanceUpdateSpecification();
		mdocsubfinanceUpdateSpecification.isSatisfiedBy(mdocsubfinance);
		return  mdocsubfinanceRepository.store(mdocsubfinance );
	}

	@Override
	public boolean deleteBatch(List<Long> ids) {
		List<MdocSubFinanceId> tempIds = new ArrayList<>();
		ids.forEach(id -> {
			tempIds.add(new MdocSubFinanceId(id));
		});
		return  mdocsubfinanceRepository.delete(tempIds);
	}
}

