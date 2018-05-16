package com.wd.cloud.docdelivery.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.config.DeliveryConfig;
import com.wd.cloud.docdelivery.config.HelpSuccessMailConfig;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.enumeration.HelpStatus;
import com.wd.cloud.docdelivery.service.FrontService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Checksum;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@RestController
@RequestMapping("/front")
public class FrontendController {

    @Autowired
    DeliveryConfig deliveryConfig;

    @Autowired
    HelpSuccessMailConfig helpSuccessMailConfig;

    @Autowired
    FrontService frontService;

    @GetMapping("/hello")
    public ResponseModel hello() {
        //frontService.save();
        //MailUtil.send("hezhigang@hnwdkj.com","hello dimon","hello zhengwen",true);
        return ResponseModel.success("惊不惊喜？意不意外？");
    }

    /**
     * 文献求助请求
     *
     * @param helpUserId
     * @return
     */
    @PostMapping(value = "/help/{helpUserId}")
    public ResponseModel help(@PathVariable Integer helpUserId,
                              @RequestParam String helpEmail,
                              @RequestParam Integer helpChannel,
                              @RequestParam Integer helpUserScid,
                              @RequestParam String docTitle,
                              @RequestParam String docHref,
                              HttpServletRequest request) {

        HelpRecord helpRecord = new HelpRecord();
        helpRecord.setHelpChannel(helpChannel);
        helpRecord.setHelpUserScid(helpUserScid);
        helpRecord.setHelpUserId(helpUserId);
        helpRecord.setHelpIp(request.getLocalAddr());
        helpRecord.setHelpEmail(helpEmail);

        Literature literature = new Literature();
        literature.setDocTitle(docTitle);
        literature.setDocTitle(docHref);
        // 先查询元数据是否存在
        Literature literatureData = frontService.queryLiterature(literature);
        String msg = "文献求助已发送，应助结果将会在24h内发送至您的邮箱，请注意查收";
        if (null == literatureData){
            // 如果不存在，则新增一条元数据
            literatureData = frontService.saveLiterature(literature);
        }
        helpRecord.setLiteratureId(literatureData.getId());
        // 如果文件已存在，自动应助成功
        if (StrUtil.isNotEmpty(literatureData.getDocFilename())) {
                helpRecord.setStatus(HelpStatus.FINISHED.value());
                helpRecord.setDocFilename(literatureData.getDocFilename());
                MailUtil.sendHtml(helpEmail, helpSuccessMailConfig.getSubject(), helpSuccessMailConfig.getContent());
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
        List<HelpRecord> waitHelpRecords = frontService.getWaitHelpRecords(pageNum,pageSize);
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
        if (file == null){
            return ResponseModel.fail();
        }else if (!deliveryConfig.getFileTypes().contains(FileTypeUtil.getType(file.getInputStream()))){
            return ResponseModel.fail("不支持的文件类型");
        }
        //创建文件目录
        File dir = FileUtil.mkdir(deliveryConfig.getSavePath());
        //文件MD5值
        String md5File = DigestUtil.md5Hex(file.getInputStream());
        //文件后缀
        String extName = FileTypeUtil.getType(file.getInputStream());
        //组装成新的文件名
        String md5Filename = md5File+"."+extName;
        if (FileUtil.exist(new File(dir,md5Filename))){
            return ResponseModel.fail("请不要重复上传");
        }
        //创建一个新文件
        File attachFile = FileUtil.touch(dir, md5Filename);
        //将文件流写入文件中
        FileUtil.writeFromStream(file.getInputStream(), attachFile);

        return ResponseModel.success(attachFile.getAbsolutePath());
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
