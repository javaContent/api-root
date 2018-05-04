package com.wd.cloud.documentdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@RestController
public class BackendController {

    @PostMapping("/file/upload")
    public ResponseModel upload(){
        return ResponseModel.success("文件上传成功");
    }
}
