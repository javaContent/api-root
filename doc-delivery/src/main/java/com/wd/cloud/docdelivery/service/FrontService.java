package com.wd.cloud.docdelivery.service;

import com.wd.cloud.docdelivery.domain.DocFile;
import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.model.DownloadModel;
import com.wd.cloud.docdelivery.model.Md5FileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface FrontService {

    /**
     * 我要应助
     * @param helpRecordId
     * @param giverId
     * @param giverName
     */
    boolean givingHelp(Long helpRecordId, Long giverId, String giverName);
    /**
     * 去除字符串中的HTML标签
     * @param docTitle
     * @return
     */
    String clearHtml(String docTitle);

    /**
     * 获取下载文件
     * @param helpRecordId
     * @return
     */
    DownloadModel getDowloadFile(long helpRecordId);
    /**
     * 保存元数据
     *
     * @param literature
     * @return
     */
    Literature saveLiterature(Literature literature);

    /**
     * 保存应助记录
     *
     * @param giveRecord
     * @return
     */
    GiveRecord saveGiveRecord(GiveRecord giveRecord);

    /**
     * 创建应助记录
     */
    void createGiveRecord(HelpRecord helpRecord, Long giveUserId, DocFile docFile, String giviIp);

    /**
     * 保存求助记录
     *
     * @param helpRecord
     * @return
     */
    HelpRecord saveHelpRecord(HelpRecord helpRecord);

    HelpRecord getHelpRecord(Long helpRecordId);

    /**
     * 查询元数据
     *
     * @param literature
     * @return
     */
    Literature queryLiterature(Literature literature);

    /**
     * 获取用户的求助记录
     *
     * @param helpUserId
     * @return
     */
    Page<HelpRecord> getHelpRecordsForUser(Integer helpUserId, Pageable pageable);

    /**
     * 获取用户的求助记录
     *
     * @param helpEmail
     * @return
     */
    Page<HelpRecord> getHelpRecordsForEmail(String helpEmail, Pageable pageable);

    /**
     * 获取待应助的求助记录
     *
     * @return
     */
    Page<HelpRecord> getWaitHelpRecords(Pageable pageable);

    Page<HelpRecord> getAllHelpRecord(Pageable pageable);

    DocFile getReusingFile(Literature literature);
}
