package com.wd.cloud.docdelivery.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.enums.GiveTypeEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.model.HelpModel;
import com.wd.cloud.docdelivery.service.FileService;
import com.wd.cloud.docdelivery.service.FrontService;
import com.wd.cloud.docdelivery.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
        //frontService.saveHelpRecord();
        //mailService.sendMail(ChannelEnum.CRS, "hezhigang@hnwdkj.com", "测试标题", "http://www.baidu.com", ProcessTypeEnum.PASS);
        return ResponseModel.success("惊不惊喜？意不意外？");
    }

    /**
     * 文献求助请求
     *
     * @return
     */
    @PostMapping(value = "/help")
    public ResponseModel help(@Validated HelpModel helpModel, HttpServletRequest request) {
        HelpRecord helpRecord = new HelpRecord();
        String helpEmail = helpModel.getHelperEmail();
        helpRecord.setHelpChannel(helpModel.getHelpChannel());
        helpRecord.setHelperScid(helpModel.getHelperScid());
        helpRecord.setHelperId(helpModel.getHelperId());
        helpRecord.setHelperIp(request.getLocalAddr());
        helpRecord.setHelperEmail(helpEmail);

        Literature literature = new Literature();
        literature.setDocTitle(helpModel.getDocTitle());
        literature.setDocHref(helpModel.getDocHref());
        // 先查询元数据是否存在
        Literature literatureData = frontService.queryLiterature(literature);
        String msg = "文献求助已发送，应助结果将会在24h内发送至您的邮箱，请注意查收";
        if (null == literatureData) {
            // 如果不存在，则新增一条元数据
            literatureData = frontService.saveLiterature(literature);
        }
        helpRecord.setLiterature(literatureData);
        // 如果文件已存在，自动应助成功
        if (StrUtil.isNotEmpty(literatureData.getDocFilename())) {
            helpRecord.setStatus(HelpStatusEnum.HELP_SUCCESSED.getCode());
            GiveRecord giveRecord = new GiveRecord();
            giveRecord.setDocFilename(literatureData.getDocFilename());
            giveRecord.setFileMd5(literatureData.getFileMd5());
            giveRecord.setGiverType(GiveTypeEnum.AUTO.getCode());
            //先保存求助记录，得到求助ID，再关联应助记录
            helpRecord = frontService.saveHelpRecord(helpRecord);
            giveRecord.setHelpRecord(helpRecord);
            frontService.saveGiveRecord(giveRecord);
            mailService.sendMail(helpRecord.getHelpChannel(), helpEmail, helpRecord.getLiterature().getDocTitle(), "", HelpStatusEnum.HELP_SUCCESSED);
            msg = "文献求助成功,请登陆邮箱" + helpEmail + "查收结果";
        } else{
            // 保存求助记录
            frontService.saveHelpRecord(helpRecord);
        }
        return ResponseModel.success(msg);
    }

    /**
     * 待应助
     *
     * @return
     */
    @GetMapping("/help/wait")
    public ResponseModel help(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> waitHelpRecords = frontService.getWaitHelpRecords(pageable);
        return ResponseModel.success(waitHelpRecords);
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
        FileTypeUtil.putFileType("255044462D312E", "pdf");
        if (file == null) {
            return ResponseModel.fail();
        } else if (!globalConfig.getFileTypes().contains(FileTypeUtil.getType(file.getInputStream()))) {
            return ResponseModel.fail("不支持的文件类型");
        }
        //保存文件
        String filename = fileService.saveFile(file);
        //更新记录
        frontService.saveFilename(helpRecordId, giveUserId, filename, request.getLocalAddr());
        return ResponseModel.success(filename);
    }


    /**
     * 我的求助记录
     *
     * @param helpUserId
     * @return
     */
    @GetMapping("/records/{helpUserId}")
    public ResponseModel myRecords(Integer helpUserId, @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HelpRecord> myHelpRecords = frontService.getHelpRecordsForUser(helpUserId, pageable);
        return ResponseModel.success(myHelpRecords);
    }

    /**
     * 指定邮箱的求助记录
     *
     * @param email
     * @return
     */
    @GetMapping("/records")
    public ResponseModel recordsByEmail(String email, @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
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
     * @param filename
     * @return
     */
    @GetMapping("/download/{filename}")
    public ResponseModel download(@PathVariable String filename) {

        return ResponseModel.success();
    }
}
