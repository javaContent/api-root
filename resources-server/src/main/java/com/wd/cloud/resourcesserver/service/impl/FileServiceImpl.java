package com.wd.cloud.resourcesserver.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.wd.cloud.resourcesserver.config.GlobalConfig;
import com.wd.cloud.resourcesserver.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author He Zhigang
 * @date 2018/7/20
 * @Description:
 */
@Service("fileService")
public class FileServiceImpl implements FileService{

    @Autowired
    GlobalConfig globalConfig;

    @Override
    public String save(MultipartFile file, String savePath) throws IOException {
        //文件MD5值
        String md5File = DigestUtil.md5Hex(file.getInputStream());
        //文件后缀
        String extName = StrUtil.subAfter(file.getOriginalFilename(), ".", true);
        String newFileName = md5File +"."+extName;
        //文件如果不存在，则保存，否则直接返回文件的MD5名
        newFileName = touch(file, savePath, newFileName);
        return newFileName;
    }

    @Override
    public String save(MultipartFile file, String savePath, String journalId) throws IOException {
        //文件后缀
        String extName = StrUtil.subAfter(file.getOriginalFilename(), ".", true);
        String newFileName = journalId +"."+extName;
        newFileName = touch(file, savePath, newFileName);
        return newFileName;
    }

    private String touch(MultipartFile file, String savePath, String newFileName) throws IOException {
        //文件如果不存在，则保存，否则直接返回文件的MD5名
        if (!FileUtil.exist(new File(savePath, newFileName))) {
            //创建一个新文件
            File newFile = FileUtil.touch(savePath, newFileName);
            //将文件流写入文件中
            FileUtil.writeFromStream(file.getInputStream(), newFile);
        }
        return newFileName;
    }


}
