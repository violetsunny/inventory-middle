package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ywj
 * @Date: 2019-11-08
 * @Description:
 */
@Data
public class MapJournalDataBO implements Serializable {
    private static final long serialVersionUID = -5396029010493508271L;
    /**
     * map流水code
     */
    private String mapCode;

    /**
     * map子流水code
     */
    private String mapSubCode;

    /**
     * map流水状态
     */
    private String mapStatus;
}
