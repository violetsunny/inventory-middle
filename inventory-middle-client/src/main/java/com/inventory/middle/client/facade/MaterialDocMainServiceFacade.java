package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.command.MaterialDocMainCommand;
import com.inventory.middle.client.dto.query.MaterialDocMainPageQuery;

import java.util.List;

/**
 * 物料凭证主表Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocMainServiceFacade {

    /**
     * 分页查询
     *
     * @param materialdocmainPageQuery
     * @return
     */
    PageResponse<MaterialDocMainDto> page(MaterialDocMainPageQuery materialdocmainPageQuery);

    /**
     * 物料凭证主表list查询
     */
    MultiResponse<MaterialDocMainDto> list();

    /**
     * 通过ID获取物料凭证主表
     *
     * @param id
     * @return
     */
    SingleResponse<MaterialDocMainDto> findById(Long id);

    /**
     * 保存
     *
     * @param materialdocmainCommand
     */
    SingleResponse<Boolean> save(MaterialDocMainCommand materialdocmainCommand);

    /**
     * 更新
     *
     * @param materialdocmainCommand
     */
    SingleResponse<Boolean> update( MaterialDocMainCommand materialdocmainCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
