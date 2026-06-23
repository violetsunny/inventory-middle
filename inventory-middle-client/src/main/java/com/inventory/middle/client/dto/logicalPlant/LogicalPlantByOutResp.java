package com.inventory.middle.client.dto.logicalPlant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据外部编码查询响应
 *
 * @author vincent.li
 * @date 2022/5/12 11:06
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogicalPlantByOutResp implements Serializable {

    private List<LogicalPlantResponse> result;
}
