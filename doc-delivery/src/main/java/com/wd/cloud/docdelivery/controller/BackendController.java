package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.entity.DocFile;
import com.wd.cloud.docdelivery.entity.GiveRecord;
import com.wd.cloud.docdelivery.entity.HelpRecord;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.enums.GiveTypeEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.model.DownloadModel;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Short", paramType = "query"),
            @ApiImplicitParam(name = "helperScid", value = "学校id", dataType = "Short", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "搜索关键词", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "String", paramType = "query")
    })
    @GetMapping("/help/list")
    public ResponseModel helpList(@RequestParam(required = false) Short status, @RequestParam(required = false) Short helperScid,
                                  @RequestParam(required = false) String keyword, @RequestParam(required = false) String beginTime,
                                  @RequestParam(required = false) String endTime,
                                  @PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("helperScid", helperScid);
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reusing", value = "是否复用", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "搜索关键词", dataType = "String", paramType = "query")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "literatureId", value = "元数据id", dataType = "Long", paramType = "query")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "求助数据id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "giverId", value = "应助者(处理人)id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "giverName", value = "应助者(处理人)username", dataType = "String", paramType = "query")
    })
    @PostMapping("/upload/{helpRecordId}")
    public ResponseModel upload(@PathVariable Long helpRecordId,
                                @RequestParam Long giverId,
                                @RequestParam String giverName,
                                @NotNull MultipartFile file,
                                HttpServletRequest request) {
        HelpRecord helpRecord = backendService.getHelpRecord(helpRecordId);
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
        String url = fileService.getDownloadUrl(helpRecord.getId());
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "求助数据id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "giverId", value = "应助者(处理人)id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "giverName", value = "应助者(处理人)username", dataType = "String", paramType = "query")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "求助数据id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "giverId", value = "应助者(处理人)id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "giverName", value = "应助者(处理人)username", dataType = "String", paramType = "query")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "求助数据id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "auditorId", value = "审核者(处理人)id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "auditorName", value = "审核者(处理人)username", dataType = "String", paramType = "query")
    })
    @PatchMapping("/audit/pass/{id}")
    public ResponseModel auditPass(@PathVariable Long id, @RequestParam(name = "auditorId") Long auditorId, @RequestParam(name = "auditorName") String auditorName, HttpServletRequest request) {
        HelpRecord helpRecord = backendService.getHelpRecord(id);
        GiveRecord giveRecord = backendService.getGiverRecord(helpRecord, 0, 2);
        if (giveRecord == null) {
            return ResponseModel.notFound();
        }
        giveRecord.setAuditStatus(AuditEnum.PASS.getCode());
        giveRecord.setAuditorId(auditorId);
        giveRecord.setAuditorName(auditorName);
        helpRecord.setStatus(HelpStatusEnum.HELP_SUCCESSED.getCode());
        String downloadUrl = fileService.getDownloadUrl(helpRecord.getId());
        mailService.sendMail(helpRecord.getHelpChannel(),
                helpRecord.getHelperEmail(),
                helpRecord.getLiterature().getDocTitle(),
                downloadUrl,
                HelpStatusEnum.HELP_SUCCESSED);
        backendService.updateHelRecord(helpRecord);
        return ResponseModel.ok();
    }

    /**
     * 审核不通过
     *
     * @return
     */
    @ApiOperation(value = "审核不通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "求助数据id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "auditorId", value = "审核者(处理人)id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "auditorName", value = "审核者(处理人)username", dataType = "String", paramType = "query")
    })
    @PatchMapping("/audit/nopass/{id}")
    public ResponseModel auditNoPass(@PathVariable Long id, @RequestParam(name = "auditorId") Long auditorId, @RequestParam(name = "auditorName") String auditorName) {
        HelpRecord helpRecord = backendService.getHelpRecord(id);
        GiveRecord giveRecord = backendService.getGiverRecord(helpRecord, 0, 2);
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
    @ApiOperation(value = "复用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docFileId", value = "上传文档id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "literatureId", value = "元数据id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "reuseUserId", value = "复用人(处理人)id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "reuseUserName", value = "复用人(处理人)username", dataType = "String", paramType = "query")
    })
    @GetMapping("/reusing/pass/{docFileId}")
    public ResponseModel reusing(@PathVariable Long docFileId, @RequestParam(name = "literatureId") Long literatureId, @RequestParam(name = "reuseUserId") Long reuseUserId, @RequestParam(name = "reuseUserName") String reuseUserName) {
        Map<String, Object> param = new HashMap<>();
        param.put("docFileId", docFileId);
        param.put("reusing", true);
        param.put("literatureId", literatureId);
        param.put("auditorId", reuseUserId);
        param.put("auditorName", reuseUserName);
        Boolean result = backendService.reusing(param);
        if (result) {
            return ResponseModel.ok();
        } else {
            return ResponseModel.error("一篇文章不允许复用多个文档");
        }
    }

    /**
     * 取消复用
     *
     * @return
     */
    @ApiOperation(value = "取消复用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docFileId", value = "上传文档id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "literatureId", value = "元数据id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "reMark", value = "取消复用原因", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reuseUserId", value = "复用人(处理人)id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "reuseUserName", value = "复用人(处理人)username", dataType = "String", paramType = "query")
    })
    @GetMapping("/reusing/nopass/{docFileId}")
    public ResponseModel notReusing(@PathVariable Long docFileId, @RequestParam(name = "literatureId") Long literatureId, @RequestParam(name = "reMark") String reMark, @RequestParam(name = "reuseUserId") Long reuseUserId, @RequestParam(name = "reuseUserName") String reuseUserName) {
        Map<String, Object> param = new HashMap<>();
        param.put("docFileId", docFileId);
        param.put("reusing", false);
        param.put("literatureId", literatureId);
        param.put("auditorId", reuseUserId);
        param.put("auditorName", reuseUserName);
        param.put("reMark", reMark);
        Boolean result = backendService.reusing(param);
        if (result) {
            return ResponseModel.ok();
        } else {
            return ResponseModel.error();
        }
    }

    /**
     * 文献下载
     *
     * @return
     */
    @ApiOperation(value = "后台文件下载")
    @ApiImplicitParam(name = "docFileId", value = "文件ID", dataType = "Long", paramType = "path")
    @GetMapping("/download/{docFileId}")
    public ResponseEntity download(@PathVariable Long docFileId) {
        DownloadModel downloadModel;
        try {
            downloadModel = backendService.getDowloadFile(docFileId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + downloadModel.getDownloadFileName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(downloadModel.getDocFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(downloadModel.getDocFile()));
    }

}
