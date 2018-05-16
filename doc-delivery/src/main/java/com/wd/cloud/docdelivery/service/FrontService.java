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

    HelpRecord saveHelpRecord(HelpRecord helpRecord);

    Literature queryLiterature(Literature literature);

    List<HelpRecord> getHelpRecordsForUser(Integer helpUserId);

    List<HelpRecord> getHelpRecordsForEmail(String helpEmail);

    List<HelpRecord> getWaitHelpRecords(int pageNum,int pageSize);
}
