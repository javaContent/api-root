package com.wd.cloud.docdelivery.service;

import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * 保存应助记录
     * @param giveRecord
     * @return
     */
    GiveRecord saveGiveRecord(GiveRecord giveRecord);

    /**
     * 保存文件
     * @param helpRecordId
     * @param fileName
     */
    void saveFilename(Long helpRecordId,Long giveUserId,String fileName,String giviIp);

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
    Page<HelpRecord> getHelpRecordsForUser(Integer helpUserId,Pageable pageable);

    /**
     * 获取用户的求助记录
     * @param helpEmail
     * @return
     */
    Page<HelpRecord> getHelpRecordsForEmail(String helpEmail,Pageable pageable);

    /**
     * 获取待应助的求助记录
     * @return
     */
    Page<HelpRecord> getWaitHelpRecords(Pageable pageable);

    Page<HelpRecord> getAllHelpRecord(Pageable pageable);

}
