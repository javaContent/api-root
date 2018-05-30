package com.wd.cloud.docdelivery.service;

import com.wd.cloud.docdelivery.domain.DocFile;
import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.model.DownloadModel;
import com.wd.cloud.docdelivery.model.Md5FileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
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
    HelpRecord givingHelp(long helpRecordId, long giverId, String giverName,String giverIp);

    boolean cancelGivingHelp(long helpRecordId, long giverId);

    /**
     * 得到应种中状态的应助记录
     * @param helpRecordId
     * @return
     */
    HelpRecord getHelpingRecord(long helpRecordId);
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
    void createGiveRecord(HelpRecord helpRecord, long giveUserId, DocFile docFile, String giviIp);

    /**
     * 保存求助记录
     *
     * @param helpRecord
     * @return
     */
    HelpRecord saveHelpRecord(HelpRecord helpRecord);

    HelpRecord getHelpRecord(long helpRecordId);

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
    Page<HelpRecord> getHelpRecordsForUser(long helpUserId, Pageable pageable);

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
    Page<HelpRecord> getWaitHelpRecords(int helpChannel,Pageable pageable);

    /**
     * 求助完成列表
     * @param helpChannel
     * @param pageable
     * @return
     */
    Page<HelpRecord> getFinishHelpRecords(int helpChannel,Pageable pageable);

    Page<HelpRecord> getAllHelpRecord(Pageable pageable);

    DocFile getReusingFile(Literature literature);

    boolean checkExistsGiveing(long giverId);
}
