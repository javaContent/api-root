package com.wd.cloud.authserver.service.impl;

import com.wd.cloud.authserver.entity.User;
import com.wd.cloud.authserver.repository.UserRepository;
import com.wd.cloud.authserver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User loing(String username, String pwd) {

        return userRepository.findByUsernameAndPwd(username, pwd);
    }
}
