package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.enums.ProcessTypeEnum;
import com.wd.cloud.docdelivery.service.BackendService;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import com.wd.cloud.docdelivery.service.FileService;
import com.wd.cloud.docdelivery.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */

@RestController
@RequestMapping("/backend")
public class BackendController {

    @Autowired
    BackendService backendService;

    @Autowired
    FileService fileService;

    @Autowired
    MailService mailService;

    /**
     * 文献互助列表
     *
     * @return
     */
    @GetMapping("/help/list/{pageNum}/{pageSize}")
    public ResponseModel helpList(@PathVariable int pageNum, @PathVariable int pageSize, @RequestParam short type,
                                  @RequestParam String shool, @RequestParam String keyword, @RequestParam String beginTime,
                                  @RequestParam String endTime) {


        backendService.getHelpList(pageNum, pageSize);
        return ResponseModel.success();
    }

    /**
     * 直接处理，上传文件
     *
     * @return
     */
    @PostMapping("/upload/{id}")
    public ResponseModel upload(@PathVariable Long id, @RequestParam Long processUserId, HttpServletRequest request,@NotNull MultipartFile file) {

        HelpRecord helpRecord = backendService.get(id);
        String md5Filename = null;
        try {
            md5Filename = fileService.saveFile(file);
        } catch (IOException e) {
            return ResponseModel.fail("文件上传失败,请重新上传");
        }
        String url = request.getRequestURL().toString().replace("/backend/upload", "/front/download") + "/"
                + md5Filename;

        mailService.sendMail(helpRecord.getHelpChannel(),helpRecord.getHelpEmail(), helpRecord.getDocTitle(), url, ProcessTypeEnum.PASS);

        helpRecord.setDocFilename(md5Filename);
        helpRecord.setProcessType(ProcessTypeEnum.PASS.getCode());
        helpRecord.setGmtModified(new Date());
        // 当后台管理员直接处理时，GiveUserId和processUserId一致
        helpRecord.setGiveUserId(processUserId);
        helpRecord.setProcessUserId(processUserId);
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success("文件上传成功");
    }

    /**
     * 其他处理方式（第三方、无结果、无结果图书）
     *
     * @param id
     * @return
     */
    @PostMapping("/process/{id}")
    public ResponseModel process(@PathVariable Long id, @RequestParam Long processUserId,
                                 @RequestParam Integer processType) {
        HelpRecord helpRecord = backendService.get(id);
        helpRecord.setProcessType(processType);
        helpRecord.setGmtModified(new Date());
        // 当后台管理员直接处理时，GiveUserId和processUserId一致
        helpRecord.setGiveUserId(processUserId);
        helpRecord.setProcessUserId(processUserId);
        String subject = "", content = "";
        mailService.sendMail(helpRecord.getHelpChannel(),helpRecord.getHelpEmail(),helpRecord.getDocTitle(),null,processType);

        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success("处理成功");
    }

    /**
     * 审核通过
     *
     * @return
     */
    @PatchMapping("/audit/pass/{id}")
    public ResponseModel auditPass(@PathVariable Long id, @RequestParam(name = "processUserId", required = true) Long processUserId, HttpServletRequest request) {
        HelpRecord helpRecord = backendService.getWaitAudit(id);
        if (helpRecord == null) {
            return ResponseModel.fail();
        }
        helpRecord.setProcessType(ProcessTypeEnum.PASS.getCode());
        helpRecord.setGmtModified(new Date());
        helpRecord.setProcessUserId(processUserId);
        helpRecord.setStatus(HelpStatusEnum.PASS.getCode());
        String url = request.getRequestURL().toString().replace("/backend/upload", "/front/download") + "/"
                + helpRecord.getDocFilename();
        mailService.sendMail(helpRecord.getHelpChannel(),helpRecord.getHelpEmail(),helpRecord.getDocTitle(),url,ProcessTypeEnum.PASS);
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success();
    }

    /**
     * 审核不通过
     *
     * @return
     */
    @PatchMapping("/audit/nopass/{id}")
    public ResponseModel auditNoPass(@PathVariable Long id, @RequestParam(name = "processUserId", required = true) Long processUserId) {
        HelpRecord helpRecord = backendService.getWaitAudit(id);
        if (helpRecord == null) {
            return ResponseModel.fail();
        }
        helpRecord.setGmtModified(new Date());
        helpRecord.setProcessUserId(processUserId);
        helpRecord.setStatus(HelpStatusEnum.NOPASS.getCode());
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success();
    }

}
