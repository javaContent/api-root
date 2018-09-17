package com.wd.cloud.apifeign;

import cn.hutool.json.JSONObject;
import com.wd.cloud.commons.model.ResponseModel;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


/**
 * @author He Zhigang
 * @date 2018/7/24
 * @Description:
 */
@FeignClient(value = "resources-server",
        configuration = ResourcesServerApi.MultipartSupportConfig.class,
        fallback=ResourcesServerApi.HystrixCalculatorService.class)
public interface ResourcesServerApi {

    /**
     * 文件上传至Hbase
     *
     * @param tableName
     * @param fileName
     * @param rename
     * @param file
     * @return
     */
    @PostMapping(value = "/hf/{tableName}", consumes = "multipart/form-data")
    public ResponseModel<JSONObject> uploadFileToHf(@PathVariable("tableName") String tableName,
                                                    @RequestParam(value = "fileName", required = false) String fileName,
                                                    @RequestParam(value = "rename", required = false, defaultValue = "false") boolean rename,
                                                    @RequestPart(value = "file") MultipartFile file);

    /**
     * 从Hbase下载文件
     *
     * @param tableName
     * @param fileName
     * @return
     */
    @GetMapping("/hf/{tableName}")
    public ResponseEntity downloadFileToHf(@PathVariable("tableName") String tableName,
                                           @RequestParam("fileName") String fileName);

    /**
     * 上传文件到磁盘目录
     *
     * @param dir
     * @param fileName
     * @param rename
     * @param file
     * @return
     */
    @PostMapping(value = "/df/{dir}", consumes = "multipart/form-data")
    public ResponseModel<JSONObject> uploadFileToDf(@PathVariable("dir") String dir,
                                                    @RequestParam(value = "fileName", required = false) String fileName,
                                                    @RequestParam(value = "rename", required = false, defaultValue = "false") boolean rename,
                                                    @RequestPart(value = "file") MultipartFile file);

    /**
     * 从磁盘下载文件
     *
     * @param dir
     * @param fileName
     * @return
     */
    @GetMapping("/df/{dir}/{fileName}")
    public ResponseEntity downloadFileToDf(@PathVariable("dir") String dir,
                                           @RequestParam("fileName") String fileName);


    /**
     * 从Hbase获取文件byte流
     *
     * @param tableName
     * @param fileName
     * @return
     */
    @GetMapping("/bt/hf/{tableName}")
    public ResponseModel<byte[]> getFileByteToHf(@PathVariable("tableName") String tableName,
                                                 @RequestParam("fileName") String fileName);

    /**
     * 从磁盘目录获取文件byte流
     *
     * @param dir
     * @param fileName
     * @return
     */
    @GetMapping("/bt/df/{dir}")
    public ResponseModel<byte[]> getFileByteToDf(@PathVariable("dir") String dir,
                                                 @RequestParam("fileName") String fileName);


    class MultipartSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }


        @Bean
        public Decoder feignDecoder() {
            final List<HttpMessageConverter<?>> springConverters = messageConverters.getObject().getConverters();
            final List<HttpMessageConverter<?>> decoderConverters
                    = new ArrayList<HttpMessageConverter<?>>(springConverters.size() + 1);

            decoderConverters.addAll(springConverters);
            //decoderConverters.add(new ByteArrayHttpMessageConverter());
            final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(decoderConverters);

            return new SpringDecoder(new ObjectFactory<HttpMessageConverters>() {
                @Override
                public HttpMessageConverters getObject() {
                    return httpMessageConverters;
                }
            });
        }
    }

    class HystrixCalculatorService implements ResourcesServerApi{

        @Override
        public ResponseModel<JSONObject> uploadFileToHf(String tableName, String fileName, boolean rename, MultipartFile file) {
            return ResponseModel.serverErr("服务调用失败");
        }

        @Override
        public ResponseEntity downloadFileToHf(String tableName, String fileName) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        @Override
        public ResponseModel<JSONObject> uploadFileToDf(String dir, String fileName, boolean rename, MultipartFile file) {
            return ResponseModel.serverErr("服务调用失败");
        }

        @Override
        public ResponseEntity downloadFileToDf(String dir, String fileName) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        @Override
        public ResponseModel<byte[]> getFileByteToHf(String tableName, String fileName) {
            return ResponseModel.serverErr("服务调用失败");
        }

        @Override
        public ResponseModel<byte[]> getFileByteToDf(String dir, String fileName) {
            return ResponseModel.serverErr("服务调用失败");
        }
    }
}
