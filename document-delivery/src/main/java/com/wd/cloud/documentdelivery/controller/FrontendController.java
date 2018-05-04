package com.wd.cloud.documentdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.documentdelivery.domain.Literature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@RestController
public class FrontendController {

    @GetMapping("/hello")
    public ResponseModel hello(){
        return ResponseModel.success("惊不惊喜？意不意外？");
    }

    @GetMapping("/dd/help")
    public ResponseModel help(String email, Literature literature){
        return ResponseModel.success("文献求助成功,请注意登陆邮箱"+email+"查收结果");
    }


}
