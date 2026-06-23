package com.inventory.middle.client.dto.sku;

import lombok.Data;
import java.io.Serializable;

/** 分页参数（迁移自 com.enn.biz.dto.Pager） */
@Data
public class Pager implements Serializable {
    private int page = 1;
    private int size = 20;
}
