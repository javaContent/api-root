package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.model.Md5FileModel;
import com.wd.cloud.docdelivery.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public Md5FileModel saveFile(MultipartFile file) throws IOException {
        Md5FileModel md5FileModel = new Md5FileModel();
        //文件MD5值
        String md5File = DigestUtil.md5Hex(file.getInputStream());
        //文件后缀
        String extName = StrUtil.subAfter(file.getName(), ".", true);
        md5FileModel.setName(md5File);
        md5FileModel.setType(extName);
        //String extName = FileTypeUtil.getType(file.getInputStream());
        //组装成新的文件名
        //String md5Filename = md5File+"."+extName;
        //文件如果不存在，则保存，否则直接返回文件的MD5名
        if (!FileUtil.exist(new File(globalConfig.getSavePath(), md5File))) {
            //创建一个新文件
            File attachFile = FileUtil.touch(globalConfig.getSavePath(), md5File);
            //将文件流写入文件中
            FileUtil.writeFromStream(file.getInputStream(), attachFile);
        }
        return md5FileModel;
    }
}
