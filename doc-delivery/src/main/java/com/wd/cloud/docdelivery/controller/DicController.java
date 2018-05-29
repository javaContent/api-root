package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author He Zhigang
 * @date 2018/5/27
 * @Description:
 */
@RestController
@RequestMapping("/dic")
public class DicController {

    public ResponseModel auditFileMsg(){

        return ResponseModel.ok();
    }


    public ResponseModel channel(){
        return ResponseModel.ok();
    }
}
