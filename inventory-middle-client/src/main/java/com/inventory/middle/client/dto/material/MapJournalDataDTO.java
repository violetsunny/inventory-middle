package com.inventory.middle.client.dto.material;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:kll
 */
@Data
public class MapJournalDataDTO implements Serializable {
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
