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

    /**
     * 保存元数据
     * @param literature
     * @return
     */
    Literature saveLiterature(Literature literature);

    /**
     * 保存求助记录
     * @param helpRecord
     * @return
     */
    HelpRecord saveHelpRecord(HelpRecord helpRecord);

    /**
     * 查询元数据
     * @param literature
     * @return
     */
    Literature queryLiterature(Literature literature);

    /**
     * 获取用户的求助记录
     * @param helpUserId
     * @return
     */
    List<HelpRecord> getHelpRecordsForUser(Integer helpUserId);

    /**
     * 获取用户的求助记录
     * @param helpEmail
     * @return
     */
    List<HelpRecord> getHelpRecordsForEmail(String helpEmail);

    /**
     * 获取待应助的求助记录
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<HelpRecord> getWaitHelpRecords(int pageNum,int pageSize);
}
