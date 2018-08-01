package com.wd.cloud.resourcesserver.controller;

import cn.hutool.json.JSONObject;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.resourcesserver.config.GlobalConfig;
import com.wd.cloud.resourcesserver.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    GlobalConfig globalConfig;

    /**
     * 自定义上传
     * @param file
     * @param dir
     * @return
     */
    @ApiOperation(value = "自定义文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "文件上传目录", dataType = "String", paramType = "path")
    })
    @PostMapping("/{dir}")
    public ResponseModel<JSONObject> uploadCustomer(@NotNull MultipartFile file, @PathVariable String dir) {
        String fileName = null;
        JSONObject jsonObject = new JSONObject();
        try {
            fileName = fileService.save(file, globalConfig.getResources()+dir);
            jsonObject.append("file",fileName);
        } catch (IOException e) {
            return ResponseModel.serverErr("IO异常，请重试");
        }
        return ResponseModel.ok(jsonObject);
    }

    /**
     * 文献传递上传
     * @param file
     * @return
     */
    @ApiOperation(value = "文献上传")
    @PostMapping("/doc")
    public ResponseModel<JSONObject> uploadDocDeliveryFile(@NotNull MultipartFile file) {
        String fileName = null;
        JSONObject jsonObject = new JSONObject();
        try {
            fileName = fileService.save(file, globalConfig.getDocPath());
            jsonObject.append("file",fileName);
        } catch (IOException e) {
            return ResponseModel.serverErr("IO异常，请重试");
        }
        return ResponseModel.ok(jsonObject);
    }

    /**
     * 图片上传
     * @param file
     * @return
     */
    @ApiOperation(value = "图片上传")
    @PostMapping("/image")
    public ResponseModel<JSONObject> uploadImageFile(@NotNull MultipartFile file) {
        String fileName = null;
        JSONObject jsonObject = new JSONObject();
        try {
            fileName = fileService.save(file,globalConfig.getImagePath());
            jsonObject.append("file",fileName);
        } catch (IOException e) {
            return ResponseModel.serverErr("IO异常，请重试");
        }
        return ResponseModel.ok(jsonObject);
    }

    /**
     * 期刊封面上传
     * @param file
     * @param journalId
     * @return
     */
    @ApiOperation(value = "期刊封面上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "journalId", value = "期刊ID,作为文件名称", dataType = "String", paramType = "path")
    })
    @PostMapping("/journal/{journalId}")
    public ResponseModel<JSONObject> uploadJournalImageFile(@NotNull MultipartFile file,@PathVariable String journalId) {
        String fileName = null;
        JSONObject jsonObject = new JSONObject();
        try {
            fileName = fileService.save(file,globalConfig.getJournalPath(),journalId);
            jsonObject.append("file",fileName);
        } catch (IOException e) {
            return ResponseModel.serverErr("IO异常，请重试");
        }
        return ResponseModel.ok(jsonObject);
    }
}
