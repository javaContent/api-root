package com.wd.cloud.docdelivery.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.wd.cloud.apifeign.ResourcesServerApi;
import com.wd.cloud.commons.model.HttpStatus;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.commons.model.SessionKey;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.entity.DocFile;
import com.wd.cloud.docdelivery.entity.GiveRecord;
import com.wd.cloud.docdelivery.entity.HelpRecord;
import com.wd.cloud.docdelivery.entity.Literature;
import com.wd.cloud.docdelivery.enums.GiveTypeEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.model.HelpModel;
import com.wd.cloud.docdelivery.service.FileService;
import com.wd.cloud.docdelivery.service.FrontService;
import com.wd.cloud.docdelivery.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@Api(value = "前台controller", tags = {"前台文献互助接口"})
@RestController
@RequestMapping("/front")
public class FrontendController {

    private static final Logger log = LoggerFactory.getLogger(FrontendController.class);
    @Autowired
    GlobalConfig globalConfig;

    @Autowired
    FileService fileService;

    @Autowired
    MailService mailService;

    @Autowired
    FrontService frontService;

    @Autowired
    ResourcesServerApi resourcesServerApi;

    /**
     * 1. 文献求助
     *
     * @return
     */
    @ApiOperation(value = "文献求助")
    @PostMapping(value = "/help/form")
    public ResponseModel<HelpRecord> helpFrom(@Valid HelpModel helpModel, HttpServletRequest request) {

        HelpRecord helpRecord = new HelpRecord();
        String helpEmail = helpModel.getHelperEmail();
        helpRecord.setHelpChannel(helpModel.getHelpChannel());
        helpRecord.setHelperScid(helpModel.getHelperScid());
        helpRecord.setHelperScname(helpModel.getHelperScname());
        helpRecord.setHelperId(helpModel.getHelperId());
        helpRecord.setHelperName(helpModel.getHelperName());
        helpRecord.setHelperIp(request.getLocalAddr());
        helpRecord.setHelperEmail(helpEmail);

        Literature literature = new Literature();
        // 防止调用者传过来的docTitle包含HTML标签，在这里将标签去掉
        literature.setDocTitle(frontService.clearHtml(helpModel.getDocTitle().trim()));
        literature.setDocHref(helpModel.getDocHref().trim());
        // 先查询元数据是否存在
        Literature literatureData = frontService.queryLiterature(literature);
        String msg = "waiting:文献求助已发送，应助结果将会在24h内发送至您的邮箱，请注意查收";
        if (null == literatureData) {
            // 如果不存在，则新增一条元数据
            literatureData = frontService.saveLiterature(literature);
        }
        if (frontService.checkExists(helpEmail,literatureData)){
            return ResponseModel.clientErr("error:您最近15天内已求助过这篇文献,请注意查收邮箱");
        }
        helpRecord.setLiterature(literatureData);
        DocFile docFile = frontService.getReusingFile(literatureData);
        // 如果文件已存在，自动应助成功
        if (null != docFile) {
            helpRecord.setStatus(HelpStatusEnum.HELP_SUCCESSED.getCode());
            GiveRecord giveRecord = new GiveRecord();
            giveRecord.setDocFile(docFile);
            giveRecord.setGiverType(GiveTypeEnum.AUTO.getCode());
            giveRecord.setGiverName("自动应助");
            //先保存求助记录，得到求助ID，再关联应助记录
            helpRecord = frontService.saveHelpRecord(helpRecord);
            giveRecord.setHelpRecord(helpRecord);
            frontService.saveGiveRecord(giveRecord);
            String downloadUrl = fileService.getDownloadUrl(helpRecord.getId());
            mailService.sendMail(helpRecord.getHelpChannel(),
                    helpEmail,
                    helpRecord.getLiterature().getDocTitle(),
                    downloadUrl,
                    HelpStatusEnum.HELP_SUCCESSED);
            msg = "success:文献求助成功,请登陆邮箱" + helpEmail + "查收结果";
        } else {
            try {
                // 发送通知邮件
                mailService.sendNotifyMail(helpRecord.getHelpChannel(),helpModel.getHelperScname(),helpModel.getHelperEmail());
                // 保存求助记录
                frontService.saveHelpRecord(helpRecord);
            } catch (Exception e) {
                return ResponseModel.clientErr("error:主键冲突!");
            }
        }
        return ResponseModel.ok(msg);
    }

    /**
     * 待应助列表
     *
     * @return
     */
    @ApiOperation(value = "待应助列表")
    @ApiImplicitParam(name = "helpChannel", value = "求助渠道", dataType = "Integer", paramType = "path")
    @GetMapping("/help/wait/{helpChannel}")
    public ResponseModel helpWaitList(@PathVariable int helpChannel,
                                       @PageableDefault(sort = {"gmtCreate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> waitHelpRecords = frontService.getWaitHelpRecords(helpChannel, pageable);
        return ResponseModel.ok(waitHelpRecords);
    }

    /**
     * 应助完成列表，包含成功和失败的
     *
     * @param helpChannel
     * @param pageable
     * @return
     */
    @ApiOperation(value = "求助完成列表")
    @ApiImplicitParam(name = "helpChannel", value = "求助渠道", dataType = "Integer", paramType = "path")
    @GetMapping("/help/finish/{helpChannel}")
    public ResponseModel helpSuccessList(@PathVariable Integer helpChannel,
                                         @PageableDefault(sort = {"gmtCreate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> finishHelpRecords = frontService.getFinishHelpRecords(helpChannel, pageable);
        return ResponseModel.ok(finishHelpRecords);
    }

    /**
     * 4. 我的求助
     *
     * @param helperId
     * @return
     */
    @ApiOperation(value = "我的求助记录")
    @ApiImplicitParam(name = "helperId", value = "用户ID", dataType = "Long", paramType = "path")
    @GetMapping("/help/records/{helperId}")
    public ResponseModel myRecords(@PathVariable Long helperId,
                                   @PageableDefault(sort = {"gmtCreate"},
                                           direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> myHelpRecords = frontService.getHelpRecordsForUser(helperId, pageable);
        return ResponseModel.ok(myHelpRecords);
    }

    @ApiOperation(value = "获取用户当天已求助记录的数量")
    @ApiImplicitParam(name = "email", value = "用户邮箱", dataType = "String", paramType = "query")
    @GetMapping("/help/count")
    public int getHelpCountToDay(@RequestParam String email){
        return frontService.getCountHelpRecordToDay(email);
    }

    /**
     * 应助认领
     *
     * @param helpRecordId
     * @param giverId
     * @return
     */
    @ApiOperation(value = "我要应助")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helpRecordId", value = "求助记录ID", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "giverId", value = "应助者用户ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "giverName", value = "应助者用户名称", dataType = "String", paramType = "query")
    })
    @PatchMapping("/give/{helpRecordId}")
    public ResponseModel giving(@PathVariable Long helpRecordId,
                                @RequestParam Long giverId,
                                @RequestParam String giverName,
                                HttpServletRequest request) {
        HelpRecord helpRecord = frontService.getWaitOrThirdHelpRecord(helpRecordId);
        // 该求助记录状态为非待应助，那么可能已经被其他人应助过或已应助完成
        if (helpRecord == null) {
            return ResponseModel.clientErr("该求助已经被其它人应助", helpRecord);
        }
        //检查用户是否已经认领了应助
        String docTitle = frontService.checkExistsGiveing(giverId);
        if (docTitle!=null) {
            return ResponseModel.error("请先完成您正在应助的文献："+ docTitle);
        }
        helpRecord = frontService.givingHelp(helpRecordId, giverId, giverName, HttpUtil.getClientIP(request));
        return ResponseModel.ok(helpRecord);
    }

    /**
     * 取消应助
     *
     * @param helpRecordId
     * @return
     */
    @ApiOperation(value = "取消应助")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helpRecordId", value = "求助记录ID", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "giverId", value = "应助者用户ID", dataType = "Long", paramType = "query")
    })
    @PatchMapping("/give/cancle/{helpRecordId}")
    public ResponseModel cancelGiving(@PathVariable Long helpRecordId,
                                      @RequestParam Long giverId) {
        //检查用户是否已经认领了应助
        String docTtitle = frontService.checkExistsGiveing(giverId);
        if (docTtitle != null) {
            //有认领记录，可以取消
            frontService.cancelGivingHelp(helpRecordId, giverId);
            return ResponseModel.ok();
        }
        return ResponseModel.notFound();
    }

    /**
     * 我来应助，上传文件
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "用户上传应助文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helpRecordId", value = "求助记录ID", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "giverId", value = "应助者用户ID", dataType = "Long", paramType = "query")
    })
    @PostMapping("/give/upload/{helpRecordId}")
    public ResponseModel upload(@PathVariable Long helpRecordId,
                                @RequestParam Long giverId,
                                @NotNull MultipartFile file,
                                HttpServletRequest request) {
        if (file == null) {
            return ResponseModel.error("请选择上传文件");
        } else if (!globalConfig.getFileTypes().contains(StrUtil.subAfter(file.getOriginalFilename(), ".", true))) {
            return ResponseModel.error("不支持的文件类型");
        }
        // 检查求助记录状态是否为HelpStatusEnum.HELPING
        HelpRecord helpRecord = frontService.getHelpingRecord(helpRecordId);
        if (helpRecord == null) {
            return ResponseModel.notFound("没有这个求助或求助已完成");
        }
        //保存文件
        DocFile docFile = null;
        ResponseModel<JSONObject> fileModel = resourcesServerApi.uploadFileToHf(globalConfig.getHbaseTableName(),null,true,file);
        log.info("code={}:msg={}:body={}",fileModel.getCode(),fileModel.getMsg(),fileModel.getBody().toString());
        if (fileModel.getCode() != HttpStatus.HTTP_OK){
            return ResponseModel.serverErr("文件上传失败，请重试");
        }
        String filename = fileModel.getBody().getStr("file");
        docFile = frontService.saveDocFile(helpRecord.getLiterature(),filename);

        //更新记录
        frontService.createGiveRecord(helpRecord, giverId, docFile, HttpUtil.getClientIP(request));
        return ResponseModel.ok("应助成功，感谢您的帮助");
    }


    /**
     * 指定邮箱的求助记录
     *
     * @param email
     * @return
     */
    @ApiOperation(value = "根据邮箱查询求助记录")
    @ApiImplicitParam(name = "email", value = "条件email", dataType = "String", paramType = "query")
    @GetMapping("/help/records")
    public ResponseModel recordsByEmail(@RequestParam String email,
                                        @PageableDefault(sort = {"gmtCreate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> helpRecords = frontService.getHelpRecordsForEmail(email, pageable);
        return ResponseModel.ok(helpRecords);
    }

    @ApiOperation(value = "邮箱或标题查询记录")
    @ApiImplicitParam(name = "keyword", value = "条件keyword", dataType = "String", paramType = "query")
    @GetMapping("/help/search")
    public ResponseModel recordsBySearch(@RequestParam String keyword, @PageableDefault(sort = {"gmtCreate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> helpRecords = frontService.search(keyword, pageable);

        return ResponseModel.ok(helpRecords);
    }
    /**
     * 文献求助记录
     *
     * @return
     */

    @GetMapping("/help/records/all")
    public ResponseModel allRecords(@PageableDefault(sort = {"gmtCreate"}, direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request) {
        return ResponseModel.ok(request.getSession().getAttribute(SessionKey.LOGIN_USER));
    }

}
