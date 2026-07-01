package com.inventory.middle.application.service.impl;

import com.inventory.middle.application.service.FileApplicationService;
import com.inventory.middle.infra.integration.oss.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class FileApplicationServiceImpl implements FileApplicationService {

    @Resource
    private OssFileService ossFileService;

    @Override
    public byte[] getFile(String fileUrl) {
        return ossFileService.getFile(fileUrl);
    }
}
