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
        MailUtil.send("hezhigang@hnwdkj.com","hello dimon","hello zhengwen",true);
        return ResponseModel.success("惊不惊喜？意不意外？");
    }

    /**
     * 文献求助请求
     *
     * @param email
     * @param literature
     * @return
     */
    @PostMapping("/help")
    public ResponseModel help(String email, @RequestBody Literature literature) {
        Literature literatureData = frontService.queryLiterature(literature);
        if (literatureData != null && StrUtil.isNotEmpty(literatureData.getDocFilename())){
            MailUtil.sendHtml(email,helpSuccessMailConfig.getSubject(),helpSuccessMailConfig.getContent());
            return ResponseModel.success("文献求助成功,请注意登陆邮箱" + email + "查收结果");
        }else{
            frontService.saveLiterature(literature);
            return ResponseModel.success("文献求助已发送，应助结果将会在24h内发送至您的邮箱，请注意查收");
        }
    }

    /**
     * 待应助
     *
     * @return
     */
    @GetMapping("/help/{pageNum}/{pageSize}")
    public ResponseModel help(@PathVariable int pageNum, @PathVariable int pageSize) {
        return ResponseModel.success();
    }

    /**
     * 我来应助，上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseModel upload(MultipartFile file,HttpServletResponse response) throws IOException {
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
     * @param email
     * @return
     */
    @GetMapping("/records/{userid}")
    public ResponseModel myRecords(String email) {
        List<HelpRecord> literatureList = frontService.getLiteratureForUser(email);
        return ResponseModel.success(literatureList);
    }

    /**
     * 指定邮箱的求助记录
     *
     * @param email
     * @return
     */
    @GetMapping("/records")
    public ResponseModel recordsByEmail(String email) {
        List<HelpRecord> literatureList = frontService.getLiteratureForUser(email);
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
