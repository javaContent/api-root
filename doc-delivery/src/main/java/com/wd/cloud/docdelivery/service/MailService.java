package com.wd.cloud.docdelivery.service;

import com.wd.cloud.docdelivery.enums.ChannelEnum;
import com.wd.cloud.docdelivery.enums.ProcessTypeEnum;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
public interface MailService {

    /**
     * 发送邮件
     * @param channelEnum
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param processTypeEnum
     */
    void sendMail(ChannelEnum channelEnum,String helpEmail, String docTitle, String url, ProcessTypeEnum processTypeEnum);
    /**
     * 发送邮件
     * @param channel
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param processTypeEnum
     */
    void sendMail(Integer channel,String helpEmail, String docTitle, String url, ProcessTypeEnum processTypeEnum);
    /**
     * 发送邮件
     * @param channel
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param processType
     */
    void sendMail(Integer channel,String helpEmail, String docTitle, String url, Integer processType);
    /**
     * 发送CRS邮件
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param processTypeEnum
     */
    void sendCrsMail(String helpEmail, String docTitle, String url, ProcessTypeEnum processTypeEnum);
    /**
     * 发送SPIS邮件
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param processTypeEnum
     */
    void sendSpisMail(String helpEmail, String docTitle,String url, ProcessTypeEnum processTypeEnum);
    /**
     * 发送智汇云邮件
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param processTypeEnum
     */
    void sendZhyMail(String helpEmail, String docTitle,String url, ProcessTypeEnum processTypeEnum);
}
