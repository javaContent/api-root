package com.wd.cloud.docdelivery.service;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface FrontService {

    Literature saveLiterature(Literature literature);

    Literature queryLiterature(Literature literature);

    List<HelpRecord> getLiteratureForUser(String email);

}
