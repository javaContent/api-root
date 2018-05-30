package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.domain.DocFile;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "后台controller", tags = {"后台文献处理接口"})
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
    @ApiOperation(value = "文献互助列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "求助记录ID", dataType = "Short", paramType = "query")
    })
    @GetMapping("/help/list")
    public ResponseModel helpList(@RequestParam(required = false) Short status,@RequestParam(required = false) Short scid, 
    		@RequestParam(required = false) String keyword, @RequestParam(required = false) String beginTime,
            @RequestParam(required = false) String endTime,
            @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("scid", scid);
        param.put("status", status);
        param.put("keyword", keyword);
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);

        return ResponseModel.ok(backendService.getHelpList(pageable, param));
    }


    /**
     * 文档列表(复用)
     *
     * @return
     */
    @ApiOperation(value = "原数据列表")
    @GetMapping("/literature/list")
    public ResponseModel literatureList(@RequestParam(required = false) Boolean reusing, @RequestParam(required = false) String keyword,
                                        @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("reusing", reusing);
        param.put("keyword", keyword);
        return ResponseModel.ok(backendService.getLiteratureList(pageable, param));
    }

    /**
     * 文档列表(复用)
     *
     * @return
     */
    @ApiOperation(value = "应助文档列表")
    @GetMapping("/docFile/list")
    public ResponseModel getDocFileList(@RequestParam Long literatureId,
                                        @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseModel.ok(backendService.getDocFileList(pageable, literatureId));
    }


    /**
     * 直接处理，上传文件
     *
     * @return
     */
    @ApiOperation(value = "直接处理，上传文件")
    @PostMapping("/upload/{id}")
    public ResponseModel upload(@PathVariable Long id, @RequestParam Long giverId, @RequestParam String giverName, HttpServletRequest request, @NotNull MultipartFile file) {
        HelpRecord helpRecord = backendService.getHelpRecord(id);
        DocFile docFile = null;
        try {
            docFile = fileService.saveFile(helpRecord.getLiterature(), file);
        } catch (IOException e) {
            return ResponseModel.error("文件上传失败,请重新上传");
        }
        GiveRecord giveRecord = new GiveRecord();

        giveRecord.setHelpRecord(helpRecord);
        giveRecord.setDocFile(docFile);
        //设置应助类型为管理员应助
        giveRecord.setGiverType(GiveTypeEnum.MANAGER.getCode());
        giveRecord.setGiverId(giverId);
        giveRecord.setGiverName(giverName);
        //修改求助状态为应助成功
        helpRecord.setStatus(HelpStatusEnum.HELP_SUCCESSED.getCode());
        giveRecord.setHelpRecord(helpRecord);
        backendService.saveGiveRecord(giveRecord);
        String url = request.getRequestURL().toString().replace("/backend/upload", "/front/download") + "/"
                + helpRecord.getId();
        mailService.sendMail(helpRecord.getHelpChannel(), helpRecord.getHelperEmail(), helpRecord.getLiterature().getDocTitle(), url, HelpStatusEnum.HELP_SUCCESSED);
        return ResponseModel.ok("文件上传成功");
    }

    /**
     * 提交第三方处理
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "提交第三方处理")
    @PostMapping("/third/{id}")
    public ResponseModel helpThird(@PathVariable Long id, @RequestParam Long giverId, @RequestParam String giverName) {
        HelpRecord helpRecord = backendService.getHelpRecord(id);
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
        giveRecord.setHelpRecord(helpRecord);
        backendService.saveGiveRecord(giveRecord);
        return ResponseModel.ok("已提交第三方处理，请耐心等待第三方应助结果");
    }

    /**
     * 无结果，应助失败
     *
     * @param id
     * @param giverId
     * @return
     */
    @ApiOperation(value = "无结果处理")
    @PostMapping("/fiaied/{id}")
    public ResponseModel helpFail(@PathVariable Long id, @RequestParam Long giverId, @RequestParam String giverName) {
        HelpRecord helpRecord = backendService.getHelpRecord(id);
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
        giveRecord.setHelpRecord(helpRecord);
        backendService.saveGiveRecord(giveRecord);
        return ResponseModel.ok("处理成功");
    }

    /**
     * 审核通过
     *
     * @return
     */
    @ApiOperation(value = "审核通过")
    @PatchMapping("/audit/pass/{id}")
    public ResponseModel auditPass(@PathVariable Long id, @RequestParam(name = "auditorId") Long auditorId, @RequestParam(name = "auditorName") String auditorName, HttpServletRequest request) {
        HelpRecord helpRecord = backendService.getHelpRecord(id);
        GiveRecord giveRecord = backendService.getGiverRecord(helpRecord,0,2);
        if (giveRecord == null) {
            return ResponseModel.notFound();
        }
        giveRecord.setAuditStatus(AuditEnum.PASS.getCode());
        giveRecord.setAuditorId(auditorId);
        giveRecord.setAuditorName(auditorName);
        helpRecord.setStatus(HelpStatusEnum.HELP_SUCCESSED.getCode());
        String url = request.getRequestURL().toString().replace("/backend/upload", "/front/download") + "/"
                + helpRecord.getId();
        mailService.sendMail(helpRecord.getHelpChannel(), helpRecord.getHelperEmail(), helpRecord.getLiterature().getDocTitle(), url, HelpStatusEnum.HELP_SUCCESSED);
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.ok();
    }

    /**
     * 审核不通过
     *
     * @return
     */
    @ApiOperation(value = "审核不通过")
    @PatchMapping("/audit/nopass/{id}")
    public ResponseModel auditNoPass(@PathVariable Long id, @RequestParam(name = "auditorId") Long auditorId, @RequestParam(name = "auditorName") String auditorName) {
        HelpRecord helpRecord = backendService.getHelpRecord(id);
        GiveRecord giveRecord = backendService.getGiverRecord(helpRecord,0,2);
        if (giveRecord == null) {
            return ResponseModel.notFound();
        }
        giveRecord.setAuditStatus(AuditEnum.NO_PASS.getCode());
        giveRecord.setAuditorId(auditorId);
        giveRecord.setAuditorName(auditorName);
        helpRecord.setStatus(HelpStatusEnum.WAIT_HELP.getCode());
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.ok();
    }


    /**
     * 复用
     *
     * @return
     */
    @GetMapping("/reusing/pass/{docFileId}")
    public ResponseModel reusing(@PathVariable Long docFileId,@RequestParam(name = "literatureId") Long literatureId, @RequestParam(name = "reuseUserId") Long reuseUserId,@RequestParam(name = "reuseUserName") String reuseUserName) {
    	Map<String,Object> param = new HashMap<>();
    	param.put("docFileId", docFileId);
    	param.put("reusing", true);
    	param.put("literatureId", literatureId);
    	param.put("auditorId", reuseUserId);
    	param.put("auditorName", reuseUserName);
    	Boolean result = backendService.reusing(param);
    	if(result) {
    		return ResponseModel.ok();
    	} else {
    		return ResponseModel.error();
    	}
    }
    
    /**
     * 取消复用
     *
     * @return
     */
    @GetMapping("/reusing/nopass/{docFileId}")
    public ResponseModel notReusing(@PathVariable Long docFileId,@RequestParam(name = "literatureId") Long literatureId,@RequestParam(name = "reMark") String reMark, @RequestParam(name = "reuseUserId") Long reuseUserId,@RequestParam(name = "reuseUserName") String reuseUserName) {
    	Map<String,Object> param = new HashMap<>();
    	param.put("docFileId", docFileId);
    	param.put("reusing", false);
    	param.put("literatureId", literatureId);
    	param.put("auditorId", reuseUserId);
    	param.put("auditorName", reuseUserName);
    	param.put("reMark", reMark);
    	Boolean result = backendService.reusing(param);
    	if(result) {
    		return ResponseModel.ok();
    	} else {
    		return ResponseModel.error();
    	}
    }

}
