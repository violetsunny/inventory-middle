/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kll
 * @version $Id: MaterialExtDataBO, v 0.1 2021/6/17 10:48 Exp $
 */
@Data
public class MaterialExtDataDTO implements Serializable {

    /**
     * 生产日期yyyy-MM-dd HH:mm:ss
     */
    private String batchProduceDate;

    /**
     * 海关编码
     */
    private String hsCode;

    /**
     * 批次有效天数
     */
    private Integer validDays;

    /**
     * 下次年检日期 yyyy-mm-dd
     */
    private Date annualDate;

    /**
     * 年检周期天数
     */
    private Integer annualCycleDays;


}
