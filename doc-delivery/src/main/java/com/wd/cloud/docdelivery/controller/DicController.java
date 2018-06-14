package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.entity.AuditMsg;
import com.wd.cloud.docdelivery.repository.AuditMsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/27
 * @Description:
 */
@RestController
@RequestMapping("/dic")
public class DicController {

    @Autowired
    AuditMsgRepository auditMsgRepository;

    @GetMapping("/audit/msg")
    public ResponseModel auditMsgs() {
        List<AuditMsg> auditMsgs = auditMsgRepository.findAll();
        return ResponseModel.ok(auditMsgs);
    }


    public ResponseModel channel() {
        return ResponseModel.ok();
    }
}
