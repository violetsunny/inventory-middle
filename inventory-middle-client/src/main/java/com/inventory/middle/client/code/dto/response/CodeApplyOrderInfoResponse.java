package com.inventory.middle.client.code.dto.response;


import com.inventory.middle.client.code.dto.CodeApplyOrderDTO;
import com.inventory.middle.client.code.dto.CodeApplyOrderDetailDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderInfoResponse implements Serializable {

    private static final long serialVersionUID = 8059882543528529594L;

    /**
     * 申请单头
     */
    private CodeApplyOrderDTO orderDTO;

    /**
     * 申请单行
     */
    private List<CodeApplyOrderDetailDTO> orderDetailDTOList;
}
