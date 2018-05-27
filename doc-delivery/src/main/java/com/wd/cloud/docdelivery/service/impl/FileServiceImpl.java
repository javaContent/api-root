package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.domain.DocFile;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.model.Md5FileModel;
import com.wd.cloud.docdelivery.repository.DocFileRepostitory;
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

    @Autowired
    DocFileRepostitory docFileRepostitory;

    @Override
    public DocFile saveFile(Literature literature,MultipartFile file) throws IOException {

        //文件MD5值
        String md5File = DigestUtil.md5Hex(file.getInputStream());
        //文件后缀
        String extName = StrUtil.subAfter(file.getName(), ".", true);
        DocFile docFile = docFileRepostitory.findByLiteratureAndFileNameAndFileType(literature,md5File,extName);
        if (docFile == null){
            docFile.setFileName(md5File);
            docFile.setFileType(extName);
            docFile.setLiterature(literature);
            docFile = docFileRepostitory.save(docFile);
        }
        //文件如果不存在，则保存，否则直接返回文件的MD5名
        if (!FileUtil.exist(new File(globalConfig.getSavePath(), md5File))) {
            //创建一个新文件
            File attachFile = FileUtil.touch(globalConfig.getSavePath(), md5File);
            //将文件流写入文件中
            FileUtil.writeFromStream(file.getInputStream(), attachFile);
        }

        return docFile;
    }
}
