package com.wd.cloud.userserver.controller;

import cn.hutool.json.JSONUtil;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.commons.model.SessionKey;
import com.wd.cloud.userserver.domain.User;
import com.wd.cloud.userserver.service.AuthService;
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
    public ResponseModel login(@RequestParam String username, @RequestParam String pwd, HttpServletRequest request) {
        User user = authService.loing(username, pwd);
        request.getSession().setAttribute(SessionKey.LOGGER, JSONUtil.parseObj(user));
        return ResponseModel.ok(user);
    }

    @GetMapping("/index")
    public ResponseModel index(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return ResponseModel.ok("sessionId: " + sessionId);
    }
}
