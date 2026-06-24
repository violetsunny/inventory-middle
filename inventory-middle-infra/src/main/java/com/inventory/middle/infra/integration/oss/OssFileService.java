package com.inventory.middle.infra.integration.oss;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@Component
public class OssFileService {

    @Resource
    private RestTemplate restTemplate;

    public byte[] getFile(String fileUrl) {
        try {
            return restTemplate.getForObject(fileUrl, byte[].class);
        } catch (Exception e) {
            log.warn("OssFileService.getFile failed, url={}", fileUrl, e);
            return new byte[0];
        }
    }
}
