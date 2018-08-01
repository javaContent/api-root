package com.wd.cloud.resourcesserver.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.wd.cloud.resourcesserver.config.GlobalConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author He Zhigang
 * @date 2018/7/20
 * @Description:
 */
@Api(value = "文件下载", tags = {"文件下载处理接口"})
@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    GlobalConfig globalConfig;

    @ApiOperation(value = "文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "文件所在目录", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "filename", value = "文件名称", dataType = "String", paramType = "path")
    })
    @GetMapping("/{dir}/{filename}")
    public ResponseEntity downlowdFile(@PathVariable String dir,@PathVariable String filename, HttpServletRequest request) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        //判断是否是IE浏览器
        if (request.getHeader("user-agent").toLowerCase().contains("msie")) {
            filename = URLUtil.encode(filename, "UTF-8");
        }else {
            filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
        }
        File file = new File(globalConfig.getResources()+dir+"/"+filename);
        String disposition = StrUtil.format("attachment; filename=\"{}\"", filename);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", disposition);
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }
}
