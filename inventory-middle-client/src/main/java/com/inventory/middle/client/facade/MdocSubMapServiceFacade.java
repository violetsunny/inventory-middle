package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.MdocSubMapDto;
import com.inventory.middle.client.dto.command.MdocSubMapCommand;
import com.inventory.middle.client.dto.query.MdocSubMapPageQuery;

import java.util.List;

/**
 * 物料凭证-标签-移动平均价Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubMapServiceFacade {

    /**
     * 分页查询
     *
     * @param mdocsubmapPageQuery
     * @return
     */
    PageResponse<MdocSubMapDto> page(MdocSubMapPageQuery mdocsubmapPageQuery);

    /**
     * 物料凭证-标签-移动平均价list查询
     */
    MultiResponse<MdocSubMapDto> list();

    /**
     * 通过ID获取物料凭证-标签-移动平均价
     *
     * @param id
     * @return
     */
    SingleResponse<MdocSubMapDto> findById(Long id);

    /**
     * 保存
     *
     * @param mdocsubmapCommand
     */
    SingleResponse<Boolean> save(MdocSubMapCommand mdocsubmapCommand);

    /**
     * 更新
     *
     * @param mdocsubmapCommand
     */
    SingleResponse<Boolean> update( MdocSubMapCommand mdocsubmapCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
