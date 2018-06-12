package com.wd.cloud.userserver.service.impl;

import com.wd.cloud.userserver.domain.User;
import com.wd.cloud.userserver.repository.UserRepository;
import com.wd.cloud.userserver.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author He Zhigang
 * @date 2018/6/12
 * @Description:
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserInfo(Long id) {
        return userRepository.getOne(id);
    }
}
