package com.wd.cloud.documentdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by DimonHo on 2018/4/9.
 */
@RestController
public class FrontendController {

    @GetMapping()
    public ResponseModel delivery(){
        return ResponseModel.success().setMsg("文献互助请求成功！");
    }


}
