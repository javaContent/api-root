package com.wd.cloud.docdelivery.service;

import com.wd.cloud.docdelivery.enums.ChannelEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;

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
     * @param helpStatusEnum
     */
    void sendMail(ChannelEnum channelEnum,String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum);
    /**
     * 发送邮件
     * @param channel
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param helpStatusEnum
     */
    void sendMail(Integer channel,String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum);
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
     * @param helpStatusEnum
     */
    void sendCrsMail(String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum);
    /**
     * 发送SPIS邮件
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param helpStatusEnum
     */
    void sendSpisMail(String helpEmail, String docTitle,String url, HelpStatusEnum helpStatusEnum);
    /**
     * 发送智汇云邮件
     * @param helpEmail
     * @param docTitle
     * @param url
     * @param helpStatusEnum
     */
    void sendZhyMail(String helpEmail, String docTitle,String url, HelpStatusEnum helpStatusEnum);
}
