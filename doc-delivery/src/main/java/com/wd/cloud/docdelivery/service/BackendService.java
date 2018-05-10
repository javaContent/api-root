package com.wd.cloud.docdelivery.service;

import org.springframework.data.domain.Page;

/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
public interface BackendService {

    Page getHelpList(int pageNum, int pageSize);
}
