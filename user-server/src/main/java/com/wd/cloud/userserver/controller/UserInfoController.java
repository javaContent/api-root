package com.wd.cloud.userserver.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.userserver.domain.User;
import com.wd.cloud.userserver.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author He Zhigang
 * @date 2018/6/12
 * @Description:
 */
@Api(value = "用户信息controller", tags = {"用户信息获取接口"})
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/info/{id}")
    public ResponseModel user(@PathVariable Long id){
        User user =  userInfoService.getUserInfo(id);
        return ResponseModel.ok(user);
    }


}
