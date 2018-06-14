package com.wd.cloud.authserver.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.commons.model.SessionKey;
import com.wd.cloud.commons.vo.UserVo;
import com.wd.cloud.authserver.entity.User;
import com.wd.cloud.authserver.service.AuthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author He Zhigang
 * @date 2018/6/4
 * @Description:
 */
@Api(value = "用户认证controller", tags = {"用户认证接口"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @GetMapping("/login")
    public ResponseModel<UserVo> login(@RequestParam String username, @RequestParam String pwd, HttpServletRequest request) {
        User user = authService.loing(username, pwd);
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user,userVo);
        request.getSession().setAttribute(SessionKey.LOGIN_USER, userVo);
        return ResponseModel.ok(userVo);
    }

    @GetMapping("/index")
    public ResponseModel index(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return ResponseModel.ok("sessionId: " + sessionId);
    }
}
