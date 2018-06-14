package com.wd.cloud.authserver.service;

import com.wd.cloud.authserver.entity.User;

/**
 * @author He Zhigang
 * @date 2018/6/12
 * @Description:
 */
public interface UserInfoService {

    User getUserInfo(Long id);
}
