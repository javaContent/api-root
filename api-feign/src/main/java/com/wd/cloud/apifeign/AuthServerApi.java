package com.wd.cloud.apifeign;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.commons.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author He Zhigang
 * @date 2018/6/12
 * @Description:
 */
@FeignClient(value = "auth-server")
public interface AuthServerApi {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/user/info/{userId}")
    public ResponseModel<UserVo> getUserInfo(@PathVariable(value="userId") Long userId);
}
