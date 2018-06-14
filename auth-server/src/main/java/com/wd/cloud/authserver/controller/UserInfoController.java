package com.wd.cloud.authserver.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.commons.vo.UserVo;
import com.wd.cloud.authserver.entity.User;
import com.wd.cloud.authserver.service.UserInfoService;
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
    public ResponseModel user(@PathVariable Long id) {
        User user = userInfoService.getUserInfo(id);
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user,userVo);
        return ResponseModel.ok(userVo);
    }


}
