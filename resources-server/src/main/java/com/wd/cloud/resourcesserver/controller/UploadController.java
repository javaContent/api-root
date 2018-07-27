package com.wd.cloud.resourcesserver.controller;

import cn.hutool.core.util.URLUtil;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.resourcesserver.config.GlobalConfig;
import com.wd.cloud.resourcesserver.enums.SaveDirEnum;
import com.wd.cloud.resourcesserver.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;


/**
 * @author He Zhigang
 * @date 2018/7/17
 * @Description:
 */
@Api(value = "文件上传", tags = {"文件上传处理接口"})
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    FileService fileService;

    @PostMapping("/doc")
    public ResponseModel uploadDocDeliveryFile(@NotNull MultipartFile file) {
        String msg = null;
        try {
            msg = fileService.save(file, SaveDirEnum.doc);
        } catch (IOException e) {
            return ResponseModel.serverErr("IO异常，请重试");
        }
        return ResponseModel.ok(msg);
    }

    @PostMapping("/image")
    public ResponseModel uploadImageFile(@NotNull MultipartFile file) {
        String msg = null;
        try {
            msg = fileService.save(file,SaveDirEnum.image);
        } catch (IOException e) {
            return ResponseModel.serverErr("IO异常，请重试");
        }
        return ResponseModel.ok(msg);
    }

    @PostMapping("/journal/{journalId}")
    public ResponseModel uploadImageFile(@NotNull MultipartFile file,@PathVariable String journalId) {
        String msg = null;
        try {
            msg = fileService.save(file,SaveDirEnum.journal,journalId);
        } catch (IOException e) {
            return ResponseModel.serverErr("IO异常，请重试");
        }
        return ResponseModel.ok(msg);
    }
}
