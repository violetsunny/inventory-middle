package com.inventory.middle.interfaces.web.file;

import com.inventory.middle.application.service.FileApplicationService;
import com.inventory.middle.domain.common.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;

@Tag(name = "OSS文件操作")
@CatchAndLog
@Slf4j
@RestController
@RequestMapping("/files")
public class FileController {

    @Resource
    private FileApplicationService fileApplicationService;

    @Operation(summary = "下载文件")
    @PostMapping("/download")
    public void downloadFile(HttpServletResponse response, @RequestBody DownloadFileRequest request) throws IOException {
        if (request == null || StringUtils.isBlank(request.getFileUrl())) {
            throw new BusinessException("文件URL不能为空");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("file", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        byte[] file = fileApplicationService.getFile(request.getFileUrl());
        new BufferedInputStream(new ByteArrayInputStream(file));
        response.getOutputStream().write(file);
    }

    @Data
    public static class DownloadFileRequest implements Serializable {
        private String fileUrl;
    }
}
