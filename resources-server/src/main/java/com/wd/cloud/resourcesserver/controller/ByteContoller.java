package com.wd.cloud.resourcesserver.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.resourcesserver.model.FileObjModel;
import com.wd.cloud.resourcesserver.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author He Zhigang
 * @date 2018/8/25
 * @Description:
 */

@Api(value = "文件byte流", tags = {"文件byte流接口"})
@RestController
@RequestMapping("/bt")
public class ByteContoller {

    @Autowired
    FileService fileService;


    @ApiOperation(value = "Hbase文件byte流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "tableName", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "String", paramType = "query")
    })
    @GetMapping("/hf/{tableName}")
    public ResponseModel<byte[]> getFileByteToHf(@PathVariable String tableName,
                                                 @RequestParam String fileName) {
        FileObjModel fileObjModel = fileService.getFileToHbase(tableName,fileName);
        if (fileObjModel.getFileByte() != null) {
            return ResponseModel.ok(fileObjModel.getFileByte());
        } else {
            return ResponseModel.notFound();
        }
    }

    @ApiOperation(value = "磁盘文件byte流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "文件目录", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "String", paramType = "query")
    })
    @GetMapping("/df/{dir}")
    public ResponseModel<byte[]> getFileByteToDf(@PathVariable String dir,
                                                 @RequestParam String fileName) {
        FileObjModel fileObjModel = fileService.getFileToDisk(dir,fileName);
        if (fileObjModel.getFileByte() != null) {
            return ResponseModel.ok(fileObjModel.getFileByte());
        } else {
            return ResponseModel.notFound();
        }
    }
}
