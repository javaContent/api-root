package com.wd.cloud.docdelivery.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.domain.DocFile;
import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.enums.GiveTypeEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.model.DownloadModel;
import com.wd.cloud.docdelivery.model.HelpModel;
import com.wd.cloud.docdelivery.model.Md5FileModel;
import com.wd.cloud.docdelivery.service.FileService;
import com.wd.cloud.docdelivery.service.FrontService;
import com.wd.cloud.docdelivery.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@RestController
@RequestMapping("/front")
public class FrontendController {
    @Autowired
    GlobalConfig globalConfig;

    @Autowired
    FileService fileService;

    @Autowired
    MailService mailService;

    @Autowired
    FrontService frontService;

    @GetMapping("/hello")
    public ResponseModel hello() throws InterruptedException {
        return ResponseModel.success("惊不惊喜？意不意外？");
    }

    /**
     * 文献求助请求
     *
     * @return
     */
    @PostMapping(value = "/help/form")
    public ResponseModel helpFrom(@Valid HelpModel helpModel, HttpServletRequest request) {
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
        literature.setDocTitle(frontService.clearHtml(helpModel.getDocTitle()));
        literature.setDocHref(helpModel.getDocHref());
        // 先查询元数据是否存在
        Literature literatureData = frontService.queryLiterature(literature);
        String msg = "文献求助已发送，应助结果将会在24h内发送至您的邮箱，请注意查收";
        if (null == literatureData) {
            // 如果不存在，则新增一条元数据
            literatureData = frontService.saveLiterature(literature);
        }
        helpRecord.setLiterature(literatureData);
        DocFile docFile = frontService.getReusingFile(literature);
        // 如果文件已存在，自动应助成功
        if (null != docFile) {
            helpRecord.setStatus(HelpStatusEnum.HELP_SUCCESSED.getCode());
            GiveRecord giveRecord = new GiveRecord();
            giveRecord.setDocFile(docFile);
            giveRecord.setGiverType(GiveTypeEnum.AUTO.getCode());
            //先保存求助记录，得到求助ID，再关联应助记录
            helpRecord = frontService.saveHelpRecord(helpRecord);
            giveRecord.setHelpRecord(helpRecord);
            frontService.saveGiveRecord(giveRecord);
            mailService.sendMail(helpRecord.getHelpChannel(), helpEmail, helpRecord.getLiterature().getDocTitle(), "", HelpStatusEnum.HELP_SUCCESSED);
            msg = "文献求助成功,请登陆邮箱" + helpEmail + "查收结果";
        } else {
            // 保存求助记录
            frontService.saveHelpRecord(helpRecord);
        }
        return ResponseModel.success(msg);
    }

    /**
     * 待应助列表
     *
     * @return
     */
    @GetMapping("/help/wait/{helpChannel}")
    public ResponseModel help(@RequestBody @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> waitHelpRecords = frontService.getWaitHelpRecords(pageable);
        return ResponseModel.success(waitHelpRecords);
    }

    /**
     * 我要应助
     * @param helpRecordId
     * @param giverId
     * @return
     */
    @PatchMapping("/help/givering/{helpRecordId}")
    public ResponseModel helping(@PathVariable Long helpRecordId,
                                 @RequestParam Long giverId,
                                 @RequestParam String giverName){

        if (frontService.givingHelp(helpRecordId,giverId,giverName)){
            return ResponseModel.success("请于15分钟内上传文件");
        }
        return ResponseModel.fail("未找到待应助记录");
    }

    /**
     * 我来应助，上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/{helpRecordId}")
    public ResponseModel upload(@PathVariable Long helpRecordId,
                                @RequestParam Long giveUserId,
                                MultipartFile file,
                                HttpServletRequest request) throws IOException {
        if (file == null) {
            return ResponseModel.fail("请选择上传文件");
        } else if (!globalConfig.getFileTypes().contains(StrUtil.subAfter(file.getOriginalFilename(),".",true))) {
            return ResponseModel.fail("不支持的文件类型");
        }
        HelpRecord helpRecord = frontService.getHelpRecord(helpRecordId);
        //保存文件
        DocFile docFile = fileService.saveFile(helpRecord.getLiterature(),file);
        //更新记录
        frontService.createGiveRecord(helpRecord, giveUserId, docFile, request.getLocalAddr());
        return ResponseModel.success("应助成功，感谢您的帮助");
    }


    /**
     * 我的求助记录
     *
     * @param helperId
     * @return
     */
    @GetMapping("/help/records/{helperId}")
    public ResponseModel myRecords(Integer helperId, @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> myHelpRecords = frontService.getHelpRecordsForUser(helperId, pageable);
        return ResponseModel.success(myHelpRecords);
    }

    /**
     * 指定邮箱的求助记录
     *
     * @param email
     * @return
     */
    @GetMapping("/help/records")
    public ResponseModel recordsByEmail( String email, @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> literatureList = frontService.getHelpRecordsForEmail(email, pageable);
        return ResponseModel.success(literatureList);
    }

    /**
     * 文献求助记录
     *
     * @return
     */
    @GetMapping("/records/all")
    public ResponseModel allRecords(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseModel.success(frontService.getAllHelpRecord(pageable));
    }

    /**
     * 文献下载
     *
     * @return
     */
    @GetMapping("/download/{helpRecodeId}")
    public ResponseEntity download(@PathVariable Long helpRecodeId) {

        DownloadModel downloadModel = frontService.getDowloadFile(helpRecodeId);

        if (downloadModel.getDocFile() == null) {
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
