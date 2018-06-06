package com.wd.cloud.userserver.service;

import com.wd.cloud.userserver.domain.User;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
public interface AuthService {

    User loing(String username, String pwd);
}
