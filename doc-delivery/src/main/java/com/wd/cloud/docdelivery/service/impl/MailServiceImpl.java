package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.wd.cloud.docdelivery.config.HelpMailConfig;
import com.wd.cloud.docdelivery.enums.ChannelEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    HelpMailConfig helpMailConfig;

    @Override
    public void sendMail(Integer channel, String helpEmail, String docTitle, String url, Integer processType) {
        HelpStatusEnum helpStatusEnum = null;
        for (HelpStatusEnum helpStatusInstance : HelpStatusEnum.values()) {
            if (helpStatusInstance.getCode() == processType) {
                helpStatusEnum = helpStatusInstance;
            }
        }
        sendMail(channel, helpEmail, docTitle, url, helpStatusEnum);
    }

    @Override
    public void sendMail(Integer channel, String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum) {
        ChannelEnum channelEnum = null;
        for (ChannelEnum channelInstance : ChannelEnum.values()) {
            if (channelInstance.getCode() == channel) {
                channelEnum = channelInstance;
            }
        }
        sendMail(channelEnum, helpEmail, docTitle, url, helpStatusEnum);
    }

    @Override
    public void sendMail(ChannelEnum channelEnum, String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum) {
        if (ChannelEnum.SPIS.equals(channelEnum)) {
            sendSpisMail(helpEmail, docTitle, url, helpStatusEnum);
        } else if (ChannelEnum.CRS.equals(channelEnum)) {
            sendSpisMail(helpEmail, docTitle, url, helpStatusEnum);
        } else if (ChannelEnum.ZHY.equals(channelEnum)) {
            sendSpisMail(helpEmail, docTitle, url, helpStatusEnum);
        }
    }

    @Override
    public void sendCrsMail(String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum) {
        String subject = null;
        String content = null;
        String expTime = expStr(ChannelEnum.CRS);
        if (HelpStatusEnum.HELP_SUCCESSED.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getCrs().getSuccessSubject(), docTitle);
            content = String.format(helpMailConfig.getCrs().getSuccessContent(), docTitle, url, url, expTime);
        } else if (HelpStatusEnum.HELP_THIRD.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getCrs().getOutherSubject(), docTitle);
            content = String.format(helpMailConfig.getCrs().getOutherContent(), docTitle);
        } else if (HelpStatusEnum.HELP_FAILED.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getCrs().getFailSubject(), docTitle);
            content = String.format(helpMailConfig.getCrs().getFailContent(), docTitle);
        }

        MailUtil.send(helpMailConfig.getCrs().getAccount(), helpEmail, subject, content, true);
    }

    @Override
    public void sendSpisMail(String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum) {
        String subject = null;
        String content = null;
        String expTime = expStr(ChannelEnum.SPIS);
        if (HelpStatusEnum.HELP_SUCCESSED.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getSpis().getSuccessSubject(), docTitle);
            content = String.format(helpMailConfig.getSpis().getSuccessContent(), docTitle, url, url, expTime);
        } else if (HelpStatusEnum.HELP_THIRD.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getSpis().getOutherSubject(), docTitle);
            content = String.format(helpMailConfig.getSpis().getOutherContent(), docTitle);
        } else if (HelpStatusEnum.HELP_FAILED.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getSpis().getFailSubject(), docTitle);
            content = String.format(helpMailConfig.getSpis().getFailContent(), docTitle);
        }

        MailUtil.send(helpMailConfig.getSpis().getAccount(), helpEmail, subject, content, true);
    }

    @Override
    public void sendZhyMail(String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum) {
        String subject = null;
        String content = null;
        String expTime = expStr(ChannelEnum.ZHY);
        if (HelpStatusEnum.HELP_SUCCESSED.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getZhy().getSuccessSubject(), docTitle);
            content = String.format(helpMailConfig.getZhy().getSuccessContent(), docTitle, url, url, expTime);
        } else if (HelpStatusEnum.HELP_THIRD.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getZhy().getOutherSubject(), docTitle);
            content = String.format(helpMailConfig.getZhy().getOutherContent(), docTitle);
        } else if (HelpStatusEnum.HELP_FAILED.equals(helpStatusEnum)) {
            subject = String.format(helpMailConfig.getZhy().getFailSubject(), docTitle);
            content = String.format(helpMailConfig.getZhy().getFailContent(), docTitle);
        }

        MailUtil.send(helpMailConfig.getZhy().getAccount(), helpEmail, subject, content, true);
    }

    /**
     * 计算过期时间
     *
     * @param channelEnum
     * @return
     */
    public String expStr(ChannelEnum channelEnum) {
        long expLong;
        if (ChannelEnum.SPIS.equals(channelEnum)) {
            expLong = System.currentTimeMillis() + helpMailConfig.getSpis().getExp();
        } else if (ChannelEnum.CRS.equals(channelEnum)) {
            expLong = System.currentTimeMillis() + helpMailConfig.getCrs().getExp();
        } else if (ChannelEnum.ZHY.equals(channelEnum)) {
            expLong = System.currentTimeMillis() + helpMailConfig.getZhy().getExp();
        } else {
            expLong = System.currentTimeMillis() + helpMailConfig.getPub().getExp();
        }
        return DateUtil.date(expLong).toString("yyyy-MM-dd HH:mm:ss");
    }
}
