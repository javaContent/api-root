package com.wd.cloud.apifeign;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.commons.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author He Zhigang
 * @date 2018/6/12
 * @Description:
 */
@FeignClient(value = "auth-server", fallback=AuthServerApi.HystrixCalculatorService.class)
public interface AuthServerApi {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/user/info/{userId}")
    public ResponseModel<UserVo> getUserInfo(@PathVariable(value="userId") Long userId);

    @Component
    class HystrixCalculatorService implements AuthServerApi {
        @Override
        public ResponseModel<UserVo> getUserInfo(@PathVariable(value="userId") Long userId) {
            return ResponseModel.serverErr("服务不可用");
        }
    }
}
