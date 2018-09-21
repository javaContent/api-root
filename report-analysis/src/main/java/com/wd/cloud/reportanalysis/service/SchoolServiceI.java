package com.wd.cloud.reportanalysis.service;

import com.wd.cloud.reportanalysis.entity.School;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
public interface SchoolServiceI {

	School findByScid(int scid);

}
