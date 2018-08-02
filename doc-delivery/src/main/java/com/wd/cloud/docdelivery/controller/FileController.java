package com.wd.cloud.docdelivery.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.wd.cloud.apifeign.AuthServerApi;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.model.DownloadModel;
import com.wd.cloud.docdelivery.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author He Zhigang
 * @date 2018/6/12
 * @Description:
 */
@Api(value = "文件controller", tags = {"文件上传下载接口"})
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    GlobalConfig globalConfig;
    /**
     * 文献下载
     *
     * @return
     */
    @ApiOperation(value = "求助文件下载")
    @ApiImplicitParam(name = "helpRecodeId", value = "求助记录ID", dataType = "Long", paramType = "path")
    @GetMapping("/download/{helpRecodeId}")
    public void download(@PathVariable Long helpRecodeId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DownloadModel downloadModel = fileService.getDownloadFile(helpRecodeId);
        String filename = null;
        //判断是否是IE浏览器
        if (request.getHeader(Header.USER_AGENT.toString()).toLowerCase().contains("msie")) {
            filename = URLUtil.encode(downloadModel.getDownloadFileName(), CharsetUtil.UTF_8);
        }else {
            filename = new String(downloadModel.getDownloadFileName().getBytes(CharsetUtil.UTF_8),CharsetUtil.ISO_8859_1);
        }
        String disposition = StrUtil.format("attachment; filename=\"{}\"", filename);
        response.setHeader(Header.CACHE_CONTROL.toString(), "no-cache, no-store, must-revalidate");
        response.setHeader(Header.CONTENT_DISPOSITION.toString(), disposition);
        response.setHeader(Header.PRAGMA.toString(), "no-cache");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        OutputStream out = response.getOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        BufferedInputStream br = new BufferedInputStream(downloadModel.getInputStream());
        while ((len = br.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        br.close();
        out.close();
    }


    /**
     * 文献下载
     *
     * @return
     */
    @ApiOperation(value = "求助文件下载")
    @ApiImplicitParam(name = "helpRecodeId", value = "求助记录ID", dataType = "Long", paramType = "path")
    @GetMapping("/view/{helpRecodeId}")
    public void viewFile(@PathVariable Long helpRecodeId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DownloadModel downloadModel = fileService.getWaitAuditFile(helpRecodeId);
        String filename = null;
        //判断是否是IE浏览器
        if (request.getHeader(Header.USER_AGENT.toString()).toLowerCase().contains("msie")) {
            filename = URLUtil.encode(downloadModel.getDownloadFileName(), CharsetUtil.UTF_8);
        }else {
            filename = new String(downloadModel.getDownloadFileName().getBytes(CharsetUtil.UTF_8),CharsetUtil.ISO_8859_1);
        }
        String disposition = StrUtil.format("attachment; filename=\"{}\"", filename);
        response.setHeader(Header.CACHE_CONTROL.toString(), "no-cache, no-store, must-revalidate");
        response.setHeader(Header.CONTENT_DISPOSITION.toString(), disposition);
        response.setHeader(Header.PRAGMA.toString(), "no-cache");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        OutputStream out = response.getOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        BufferedInputStream br = new BufferedInputStream(downloadModel.getInputStream());
        while ((len = br.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        br.close();
        out.close();
    }
}
