package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import org.springframework.web.bind.annotation.*;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@RestController
@RequestMapping("/backend")
public class BackendController {

    /**
     * 文献互助列表
     * @return
     */
    @GetMapping("/help/list/{pageNum}/{pageSize}")
    public ResponseModel helpList(@PathVariable int pageNum, @PathVariable int pageSize){
        return ResponseModel.success();
    }

    /**
     * 直接处理，上传文件
     * @return
     */
    @PostMapping("/upload")
    public ResponseModel upload(){
        return ResponseModel.success("文件上传成功");
    }
}
