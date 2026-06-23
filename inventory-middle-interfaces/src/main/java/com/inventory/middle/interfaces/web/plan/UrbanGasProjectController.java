package com.inventory.middle.interfaces.web.plan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * 城燃项目服务。
 */
@Tag(name = "城燃项目工程相关服务")
@CatchAndLog
@RestController
@RequestMapping("/urbanGasProject")
@Slf4j
public class UrbanGasProjectController {

    @Value("${urbanGasProject.standardLeadTime:25}")
    private Integer standardLeadTime;

    @Value("${urbanGasProject.changeBufferTime:5}")
    private Integer changeBufferTime;

    @Value("${urbanGasProject.updateTime:1970-01-01 00:00:00}")
    private String updateTime;

    @Operation(summary = "获取物料备货提前期")
    @GetMapping("/queryMaterialLeadTime")
    public SingleResponse<MaterialLeadTime> queryMaterialLeadTime() throws ParseException {
        Date parsedTime = DateUtils.parseDate(updateTime, "yyyy-MM-dd HH:mm:ss");
        return SingleResponse.buildSuccess(MaterialLeadTime.of(standardLeadTime, changeBufferTime, parsedTime));
    }

    @Getter
    private static class MaterialLeadTime implements Serializable {

        private static final long serialVersionUID = 1L;

        private final Integer standardLeadTime;
        private final Integer changeBufferTime;
        private final Date updateTime;

        private MaterialLeadTime(Integer standardLeadTime, Integer changeBufferTime, Date updateTime) {
            this.standardLeadTime = standardLeadTime;
            this.changeBufferTime = changeBufferTime;
            this.updateTime = updateTime;
        }

        public static MaterialLeadTime of(Integer standardLeadTime, Integer changeBufferTime, Date updateTime) {
            return new MaterialLeadTime(standardLeadTime, changeBufferTime, updateTime);
        }
    }
}
