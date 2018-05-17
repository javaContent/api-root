package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author He Zhigang
 * @date 2018/5/16
 * @Description:
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    GlobalConfig globalConfig;

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        //文件MD5值
        String md5File = DigestUtil.md5Hex(file.getInputStream());
        //文件后缀
        String extName = FileTypeUtil.getType(file.getInputStream());
        //组装成新的文件名
        String md5Filename = md5File+"."+extName;
        //文件如果不存在，则保存，否则直接返回文件的MD5名
        if (!FileUtil.exist(new File(globalConfig.getSavePath(),md5Filename))){
            //创建一个新文件
            File attachFile = FileUtil.touch(globalConfig.getSavePath(), md5Filename);
            //将文件流写入文件中
            FileUtil.writeFromStream(file.getInputStream(), attachFile);
        }
        return md5Filename;
    }
}
