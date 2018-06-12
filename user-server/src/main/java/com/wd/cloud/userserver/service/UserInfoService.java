package com.wd.cloud.userserver.service;

import com.wd.cloud.userserver.domain.User;

/**
 * @author He Zhigang
 * @date 2018/6/12
 * @Description:
 */
public interface UserInfoService {

    User getUserInfo(Long id);
}
