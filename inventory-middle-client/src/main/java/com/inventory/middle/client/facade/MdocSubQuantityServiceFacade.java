package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.MdocSubQuantityDto;
import com.inventory.middle.client.dto.command.MdocSubQuantityCommand;
import com.inventory.middle.client.dto.query.MdocSubQuantityPageQuery;

import java.util.List;

/**
 * 物料凭证子表-数量Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubQuantityServiceFacade {

    /**
     * 分页查询
     *
     * @param mdocsubquantityPageQuery
     * @return
     */
    PageResponse<MdocSubQuantityDto> page(MdocSubQuantityPageQuery mdocsubquantityPageQuery);

    /**
     * 物料凭证子表-数量list查询
     */
    MultiResponse<MdocSubQuantityDto> list();

    /**
     * 通过ID获取物料凭证子表-数量
     *
     * @param id
     * @return
     */
    SingleResponse<MdocSubQuantityDto> findById(Long id);

    /**
     * 保存
     *
     * @param mdocsubquantityCommand
     */
    SingleResponse<Boolean> save(MdocSubQuantityCommand mdocsubquantityCommand);

    /**
     * 更新
     *
     * @param mdocsubquantityCommand
     */
    SingleResponse<Boolean> update( MdocSubQuantityCommand mdocsubquantityCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
