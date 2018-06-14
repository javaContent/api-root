package com.wd.cloud.authserver.service;

import com.wd.cloud.authserver.entity.User;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
public interface AuthService {

    User loing(String username, String pwd);
}
