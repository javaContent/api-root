package com.wd.cloud.resourcesserver.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

/**
 * @author He Zhigang
 * @date 2018/8/23
 * @Description:
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    public static String buildFileName(String fileName,boolean rename,MultipartFile file) throws IOException {
        if(rename){
            String extName = StrUtil.subAfter(file.getOriginalFilename(), ".", true);
            if (fileName != null) {
                fileName = fileName + "." + extName;
            } else {
                //文件MD5值
                String md5File = DigestUtil.md5Hex(file.getInputStream());
                fileName = md5File +"."+extName;
            }
        }else{
            fileName = file.getOriginalFilename();
        }
        return fileName;
    }


    public static File writeByteToDisk(String path, String fileName, byte[] hbaseFileByte){
        File file  = new File(path,fileName);
        if (!FileUtil.exist(file)){
            file = FileUtil.writeBytes(hbaseFileByte,file);
        }
        return file;
    }
}
