package com.inventory.middle.client.service;

import top.kdla.framework.dto.SingleResponse;

public interface SayHiFeignService {

    SingleResponse<Long> hiClient(String text);

}
