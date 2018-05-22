package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author He Zhigang
 * @date 2018/5/22
 * @Description: 文献传递相关报表
 */
@RestController
@RequestMapping("/record")
public class RecordController {


    /**
     * 审核不通过记录报表
     * @return
     */
    @GetMapping("/process/audit/fail/list")
    public ResponseModel auditFails(){
        return ResponseModel.success();
    }

    /**
     * 应助失败记录报表
     * @return
     */
    @GetMapping("/give/fail/list")
    public ResponseModel giveFails(){
        return ResponseModel.success();
    }

    /**
     * 应助成功记录报表
     * @return
     */
    @GetMapping("/give/success/list")
    public ResponseModel giveSuccess(){
        return ResponseModel.success();
    }

    /**
     * 自动应助的报表
     * @return
     */
    @GetMapping("/give/auto/list")
    public ResponseModel autoGives(){
        return ResponseModel.success();
    }


}
