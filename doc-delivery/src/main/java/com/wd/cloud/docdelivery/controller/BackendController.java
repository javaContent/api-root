package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.enums.GiveTypeEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.model.Md5FileModel;
import com.wd.cloud.docdelivery.service.BackendService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import com.wd.cloud.docdelivery.service.FileService;
import com.wd.cloud.docdelivery.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping("/help/list")
    public ResponseModel helpList(@RequestParam(required=false) Short type,@RequestParam(required=false) Short processType,
                                  @RequestParam(required=false) Short school, @RequestParam(required=false) String keyword, @RequestParam(required=false) String beginTime,
                                  @RequestParam(required=false) String endTime,

                                  @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

    	Map<String,Object> param = new HashMap<String, Object>();
    	param.put("type", type);
    	param.put("scid", school);
    	param.put("processType", processType);
    	param.put("keyword", keyword);
    	param.put("beginTime", beginTime);
    	param.put("endTime", endTime);

        return ResponseModel.success(backendService.getHelpList(pageable, param));
    }

    /**
     * 直接处理，上传文件
     *
     * @return
     */
    @PostMapping("/upload/{id}")
    public ResponseModel upload(@PathVariable Long id, @RequestParam Long giverId,@RequestParam String giverName, HttpServletRequest request, @NotNull MultipartFile file) {
        HelpRecord helpRecord = backendService.get(id);
        Md5FileModel md5FileModel = null;
        try {
            md5FileModel = fileService.saveFile(file);
        } catch (IOException e) {
            return ResponseModel.fail("文件上传失败,请重新上传");
        }
        String url = request.getRequestURL().toString().replace("/backend/upload", "/front/download") + "/"
                + md5FileModel.getName();

        mailService.sendMail(helpRecord.getHelpChannel(), helpRecord.getHelperEmail(), helpRecord.getLiterature().getDocTitle(), url, HelpStatusEnum.HELP_SUCCESSED);
        GiveRecord giveRecord = new GiveRecord();
        giveRecord.setHelpRecord(helpRecord);
        giveRecord.setDocFileName(md5FileModel.getName());
        giveRecord.setDocFileType(md5FileModel.getType());
        giveRecord.setGiverType(GiveTypeEnum.MANAGER.getCode());
        giveRecord.setGiverId(giverId);
        giveRecord.setGiverName(giverName);
        helpRecord.setStatus(HelpStatusEnum.HELP_SUCCESSED.getCode());
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success("文件上传成功");
    }

    /**
     * 提交第三方处理
     *
     * @param id
     * @return
     */
    @PostMapping("/third/{id}")
    public ResponseModel helpThird(@PathVariable Long id, @RequestParam Long giverId,@RequestParam String giverName) {
        HelpRecord helpRecord = backendService.get(id);
        helpRecord.setStatus(HelpStatusEnum.HELP_THIRD.getCode());
        GiveRecord giveRecord = new GiveRecord();
        giveRecord.setGiverId(giverId);
        giveRecord.setGiverType(GiveTypeEnum.MANAGER.getCode());
        giveRecord.setGiverName(giverName);
        mailService.sendMail(helpRecord.getHelpChannel(),
                helpRecord.getHelperEmail(),
                helpRecord.getLiterature().getDocTitle(),
                null,
                HelpStatusEnum.HELP_THIRD);
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success("已提交第三方处理，请耐心等待第三方应助结果");
    }

    /**
     * 无结果，应助失败
     *
     * @param id
     * @param giverId
     * @return
     */
    @PostMapping("/fiaied/{id}")
    public ResponseModel helpFail(@PathVariable Long id, @RequestParam Long giverId,@RequestParam String giverName) {
        HelpRecord helpRecord = backendService.get(id);
        helpRecord.setStatus(HelpStatusEnum.HELP_FAILED.getCode());
        GiveRecord giveRecord = new GiveRecord();
        giveRecord.setGiverId(giverId);
        giveRecord.setGiverType(GiveTypeEnum.MANAGER.getCode());
        giveRecord.setGiverName(giverName);
        mailService.sendMail(helpRecord.getHelpChannel(),
                helpRecord.getHelperEmail(),
                helpRecord.getLiterature().getDocTitle(),
                null,
                HelpStatusEnum.HELP_FAILED);
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success("处理成功");
    }

    /**
     * 审核通过
     *
     * @return
     */
    @PatchMapping("/audit/pass/{id}")
    public ResponseModel auditPass(@PathVariable Long id, @RequestParam(name = "auditorId") Long auditorId,@RequestParam(name = "auditorName") String auditorName, HttpServletRequest request) {
//        GiveRecord giveRecord = backendService.getWaitAudit(id);
    	HelpRecord helpRecord = backendService.get(id);
    	GiveRecord giveRecord = backendService.getGiverRecord(helpRecord);
        if (giveRecord == null) {
            return ResponseModel.fail();
        }
        giveRecord.setAuditStatus(AuditEnum.PASS.getCode());
        giveRecord.setAuditorId(auditorId);
        giveRecord.setAuditorName(auditorName);
//        HelpRecord helpRecord = giveRecord.getHelpRecord();
        helpRecord.setStatus(HelpStatusEnum.HELP_SUCCESSED.getCode());
        String url = request.getRequestURL().toString().replace("/backend/upload", "/front/download") + "/"
                + giveRecord.getDocFileName();
        mailService.sendMail(helpRecord.getHelpChannel(), helpRecord.getHelperEmail(), helpRecord.getLiterature().getDocTitle(), url, HelpStatusEnum.HELP_SUCCESSED);
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success();
    }

    /**
     * 审核不通过
     *
     * @return
     */
    @PatchMapping("/audit/nopass/{id}")
    public ResponseModel auditNoPass(@PathVariable Long id, @RequestParam(name = "auditorId") Long auditorId,@RequestParam(name = "auditorName") String auditorName) {
    	HelpRecord helpRecord = backendService.get(id);
    	GiveRecord giveRecord = backendService.getGiverRecord(helpRecord);
//    	GiveRecord giveRecord = backendService.getWaitAudit(giveRecordId);
        if (giveRecord == null) {
            return ResponseModel.fail();
        }
        giveRecord.setAuditStatus(AuditEnum.NO_PASS.getCode());
        giveRecord.setAuditorId(auditorId);
        giveRecord.setAuditorName(auditorName);
//        HelpRecord helpRecord = giveRecord.getHelpRecord();
        helpRecord.setStatus(HelpStatusEnum.WAIT_HELP.getCode());
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success();
    }
    
    
    /**
     * 复用
     *
     * @return
     */
    @PatchMapping("/reusing/{id}")
    public ResponseModel reusing(@PathVariable Long id, @RequestParam(name = "auditorId") Long auditorId,@RequestParam(name = "auditorName") String auditorName) {
    	HelpRecord helpRecord = backendService.get(id);
    	helpRecord.getLiterature().setReusing(true);
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.success();
    }

}
