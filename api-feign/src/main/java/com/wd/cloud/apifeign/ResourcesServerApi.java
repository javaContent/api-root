package com.wd.cloud.apifeign;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.commons.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author He Zhigang
 * @date 2018/7/24
 * @Description:
 */
@FeignClient(value = "resources-server", fallback=AuthServerApi.HystrixCalculatorService.class)
public interface ResourcesServerApi {

    @PostMapping("/upload/doc")
    public ResponseModel uploadDocDeliveryFile(MultipartFile file);
}
