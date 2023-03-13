package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.MdocSubFinanceDto;
import com.inventory.middle.client.dto.command.MdocSubFinanceCommand;
import com.inventory.middle.client.dto.query.MdocSubFinancePageQuery;

import java.util.List;

/**
 * 物料凭证-标签-财务Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MdocSubFinanceServiceFacade {

    /**
     * 分页查询
     *
     * @param mdocsubfinancePageQuery
     * @return
     */
    PageResponse<MdocSubFinanceDto> page(MdocSubFinancePageQuery mdocsubfinancePageQuery);

    /**
     * 物料凭证-标签-财务list查询
     */
    MultiResponse<MdocSubFinanceDto> list();

    /**
     * 通过ID获取物料凭证-标签-财务
     *
     * @param id
     * @return
     */
    SingleResponse<MdocSubFinanceDto> findById(Long id);

    /**
     * 保存
     *
     * @param mdocsubfinanceCommand
     */
    SingleResponse<Boolean> save(MdocSubFinanceCommand mdocsubfinanceCommand);

    /**
     * 更新
     *
     * @param mdocsubfinanceCommand
     */
    SingleResponse<Boolean> update( MdocSubFinanceCommand mdocsubfinanceCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
