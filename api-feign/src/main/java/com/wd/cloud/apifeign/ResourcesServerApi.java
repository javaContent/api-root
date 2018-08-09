package com.wd.cloud.apifeign;

import cn.hutool.json.JSONObject;
import com.wd.cloud.commons.model.ResponseModel;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;



/**
 * @author He Zhigang
 * @date 2018/7/24
 * @Description:
 */
@FeignClient(value = "resources-server",configuration = ResourcesServerApi.MultipartSupportConfig.class)
public interface ResourcesServerApi {

    @PostMapping(value = "/upload/{dir}",consumes = "multipart/form-data")
    public ResponseModel<JSONObject> uploadCustomer(@RequestPart(value = "file") MultipartFile file,
                                                    @RequestParam("dir") String dir);

    @PostMapping(value = "/upload/doc",consumes = "multipart/form-data")
    public ResponseModel<JSONObject> uploadDocDeliveryFile(@RequestPart(value = "file") MultipartFile file);

    @PostMapping(value = "/upload/image",consumes = "multipart/form-data")
    public ResponseModel<JSONObject> uploadImageFile(@RequestPart(value = "file") MultipartFile file);

    @PostMapping(value = "/upload/journal/{journalId}",consumes = "multipart/form-data")
    public ResponseModel<JSONObject> uploadJournalImageFile(@RequestPart(value = "file") MultipartFile file,
                                                            @RequestParam("journalId") String journalId);




    class MultipartSupportConfig {
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }
}
