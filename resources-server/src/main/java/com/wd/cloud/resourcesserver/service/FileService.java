package com.wd.cloud.resourcesserver.service;

import com.wd.cloud.resourcesserver.enums.SaveDirEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author He Zhigang
 * @date 2018/7/20
 * @Description:
 */
public interface FileService {

    String save(MultipartFile file, SaveDirEnum saveDirEnum) throws IOException;
}
