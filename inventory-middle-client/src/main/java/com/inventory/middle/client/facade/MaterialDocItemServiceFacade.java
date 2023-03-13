package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.MaterialDocItemDto;
import com.inventory.middle.client.dto.command.MaterialDocItemCommand;
import com.inventory.middle.client.dto.query.MaterialDocItemPageQuery;

import java.util.List;

/**
 * 物料凭证-itemFacade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocItemServiceFacade {

    /**
     * 分页查询
     *
     * @param materialdocitemPageQuery
     * @return
     */
    PageResponse<MaterialDocItemDto> page(MaterialDocItemPageQuery materialdocitemPageQuery);

    /**
     * 物料凭证-itemlist查询
     */
    MultiResponse<MaterialDocItemDto> list();

    /**
     * 通过ID获取物料凭证-item
     *
     * @param id
     * @return
     */
    SingleResponse<MaterialDocItemDto> findById(Long id);

    /**
     * 保存
     *
     * @param materialdocitemCommand
     */
    SingleResponse<Boolean> save(MaterialDocItemCommand materialdocitemCommand);

    /**
     * 更新
     *
     * @param materialdocitemCommand
     */
    SingleResponse<Boolean> update( MaterialDocItemCommand materialdocitemCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
