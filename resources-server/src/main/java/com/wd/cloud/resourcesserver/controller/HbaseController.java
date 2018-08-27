package com.wd.cloud.resourcesserver.controller;

import cn.hutool.json.JSONObject;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.resourcesserver.model.FileObjModel;
import com.wd.cloud.resourcesserver.service.FileService;
import com.wd.cloud.resourcesserver.util.FileUtil;
import com.wd.cloud.resourcesserver.util.HttpHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author He Zhigang
 * @date 2018/8/23
 * @Description:
 */
@Api(value = "文件上传", tags = {"文件上传至Hbase接口"})
@RestController
@RequestMapping("/hf")
public class HbaseController {

    @Autowired
    FileService fileService;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传，返回自定义文件名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "hbaseTableName", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fileName", value = "文件名称（非必传）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rename", value = "是否重命名(默认为false)", dataType = "boolean", paramType = "query")
    })
    @PostMapping("/{tableName}")
    public ResponseModel<JSONObject> uploadFile(@PathVariable String tableName,
                                                @RequestParam(required = false) String fileName,
                                                @RequestParam(required = false, defaultValue = "false") boolean rename,
                                                @NotNull MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        try {
            fileName = FileUtil.buildFileName(fileName, rename, file);
            boolean flag = fileService.saveToHbase(tableName, fileName, file);
            if (!flag) {
                return ResponseModel.serverErr("上传失败，请重试...");
            }
            jsonObject.put("file", fileName);
        } catch (IOException e) {
            return ResponseModel.serverErr("上传失败，请重试...");
        }
        return ResponseModel.ok("上传成功", jsonObject);
    }

    @ApiOperation(value = "下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "hbaseTableName", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fileName", value = "文件名", dataType = "String", paramType = "query")
    })
    @GetMapping("/{tableName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String tableName,
                                       @RequestParam String fileName,
                                       HttpServletRequest request) throws UnsupportedEncodingException {
        FileObjModel fileObjModel = fileService.getFileToHbase(tableName, fileName);
        if (fileObjModel.getFileByte() != null) {
            return ResponseEntity
                    .ok()
                    .headers(HttpHeaderUtil.buildHttpHeaders(fileName, request))
                    .contentLength(fileObjModel.getFileByte().length)
                    .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .body(fileObjModel.getFileByte());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
