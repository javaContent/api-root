package com.wd.cloud.docdelivery.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.enums.ChannelEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.enums.ProcessTypeEnum;
import com.wd.cloud.docdelivery.model.HelpModel;
import com.wd.cloud.docdelivery.service.FileService;
import com.wd.cloud.docdelivery.service.FrontService;
import com.wd.cloud.docdelivery.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
        //frontService.save();
        mailService.sendMail(ChannelEnum.CRS, "hezhigang@hnwdkj.com", "测试标题", "http://www.baidu.com", ProcessTypeEnum.PASS);
        return ResponseModel.success("惊不惊喜？意不意外？");
    }

    /**
     * 文献求助请求
     * @return
     */
    @PostMapping(value = "/help")
    public ResponseModel help(@Validated HelpModel helpModel, HttpServletRequest request) {
        HelpRecord helpRecord = new HelpRecord();
        String helpEmail = helpModel.getHelpEmail();
        helpRecord.setHelpChannel(helpModel.getHelpChannel());
        helpRecord.setHelpUserScid(helpModel.getHelpUserScid());
        helpRecord.setHelpUserId(helpModel.getHelpUserId());
        helpRecord.setHelpIp(request.getLocalAddr());
        helpRecord.setHelpEmail(helpModel.getHelpEmail());

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
        helpRecord.setLiteratureId(literatureData.getId());
        // 如果文件已存在，自动应助成功
        if (StrUtil.isNotEmpty(literatureData.getDocFilename())) {
            helpRecord.setStatus(HelpStatusEnum.PASS.getCode());
            helpRecord.setDocFilename(literatureData.getDocFilename());
            mailService.sendMail(helpRecord.getHelpChannel(),helpEmail,helpRecord.getDocTitle(),"",ProcessTypeEnum.PASS);
            msg = "文献求助成功,请登陆邮箱" + helpEmail + "查收结果";
        }
        // 插入求助记录
        frontService.saveHelpRecord(helpRecord);
        return ResponseModel.success(msg);
    }

    /**
     * 待应助
     *
     * @return
     */
    @GetMapping("/help/{pageNum}/{pageSize}")
    public ResponseModel help(@PathVariable int pageNum, @PathVariable int pageSize) {
        List<HelpRecord> waitHelpRecords = frontService.getWaitHelpRecords(pageNum, pageSize);
        return ResponseModel.success(waitHelpRecords);
    }

    /**
     * 我来应助，上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseModel upload(MultipartFile file) throws IOException {
        FileTypeUtil.putFileType("255044462D312E", "pdf");
        if (file == null) {
            return ResponseModel.fail();
        } else if (!globalConfig.getFileTypes().contains(FileTypeUtil.getType(file.getInputStream()))) {
            return ResponseModel.fail("不支持的文件类型");
        }
        String filename = fileService.saveFile(file);

        return ResponseModel.success(filename);
    }


    /**
     * 我的求助记录
     *
     * @param helpUserId
     * @return
     */
    @GetMapping("/records/{helpUserId}")
    public ResponseModel myRecords(Integer helpUserId) {
        List<HelpRecord> myHelpRecords = frontService.getHelpRecordsForUser(helpUserId);
        return ResponseModel.success(myHelpRecords);
    }

    /**
     * 指定邮箱的求助记录
     *
     * @param email
     * @return
     */
    @GetMapping("/records")
    public ResponseModel recordsByEmail(String email) {
        List<HelpRecord> literatureList = frontService.getHelpRecordsForEmail(email);
        return ResponseModel.success(literatureList);
    }

    /**
     * 文献求助记录
     *
     * @return
     */
    @GetMapping("/records/all")
    public ResponseModel allRecords() {
        return ResponseModel.success("所有记录");
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
