package com.wd.cloud.resourcesserver.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.resourcesserver.model.FileModel;
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
     * 文献传递上传
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传，返回MD5文件名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "hbaseTableName", dataType = "String", paramType = "path")
    })
    @PostMapping("/{tableName}/")
    public ResponseModel<JSONObject> uploadMd5File(@PathVariable String tableName,
                                                   @NotNull MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        try {
            String fileName = FileUtil.fileMd5(file);
            boolean flag = fileService.saveToHbase(tableName, fileName, file);
            if (!flag){
                return ResponseModel.serverErr("上传失败，请重试...");
            }
            jsonObject.put("file",fileName);
        } catch (IOException e) {
            return ResponseModel.serverErr("上传失败，请重试...");
        }
        return ResponseModel.ok("上传成功",jsonObject);
    }


    /**
     * 期刊封面上传
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传，返回自定义文件名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "hbaseTableName", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "String", paramType = "path")
    })
    @PostMapping("/{tableName}/{fileName}")
    public ResponseModel<JSONObject> uploadCustomFile(@PathVariable String tableName,
                                                      @PathVariable String fileName,
                                                      @NotNull MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        String extName = StrUtil.subAfter(file.getOriginalFilename(), ".", true);
        String newFileName = fileName +"."+extName;
        try {
            boolean flag = fileService.saveToHbase(tableName,newFileName,file);
            if (!flag){
                return ResponseModel.serverErr("上传失败，请重试...");
            }
            jsonObject.put("file",newFileName);
        } catch (IOException e) {
            return ResponseModel.serverErr("上传失败，请重试...");
        }
        return ResponseModel.ok("上传成功",jsonObject);
    }

    @ApiOperation(value = "下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "hbaseTableName", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fileName", value = "文件名", dataType = "String", paramType = "path")
    })
    @GetMapping("/{tableName}/{fileName}")
    public ResponseEntity downloadJournalImageFile(@PathVariable String tableName,
                                                   @PathVariable String fileName,
                                                   HttpServletRequest request) throws UnsupportedEncodingException {
        FileModel fileModel = fileService.getFileToHbase(fileName,tableName);
        if (fileModel.getFile() != null){
            return ResponseEntity
                    .ok()
                    .headers(HttpHeaderUtil.buildHttpHeaders(fileName, request))
                    .contentLength(fileModel.getFile().length)
                    .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .body(fileModel.getFile());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
