package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.MdocSubExtDto;
import com.inventory.middle.client.dto.command.MdocSubExtCommand;
import com.inventory.middle.client.dto.query.MdocSubExtPageQuery;

import java.util.List;

/**
 * 物料凭证-标签-扩展Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MdocSubExtServiceFacade {

    /**
     * 分页查询
     *
     * @param mdocsubextPageQuery
     * @return
     */
    PageResponse<MdocSubExtDto> page(MdocSubExtPageQuery mdocsubextPageQuery);

    /**
     * 物料凭证-标签-扩展list查询
     */
    MultiResponse<MdocSubExtDto> list();

    /**
     * 通过ID获取物料凭证-标签-扩展
     *
     * @param id
     * @return
     */
    SingleResponse<MdocSubExtDto> findById(Long id);

    /**
     * 保存
     *
     * @param mdocsubextCommand
     */
    SingleResponse<Boolean> save(MdocSubExtCommand mdocsubextCommand);

    /**
     * 更新
     *
     * @param mdocsubextCommand
     */
    SingleResponse<Boolean> update( MdocSubExtCommand mdocsubextCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
