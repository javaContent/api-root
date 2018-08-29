package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.wd.cloud.docdelivery.config.HelpMailConfig;
import com.wd.cloud.docdelivery.enums.ChannelEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.model.HelpModel;
import com.wd.cloud.docdelivery.model.MailModel;
import com.wd.cloud.docdelivery.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
@Async
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    MailModel spis;

    @Autowired
    MailModel crs;


    @Autowired
    MailModel zhy;



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
    public void sendNotifyMail(Integer channel, String orgName, String helpEmail) {
        ChannelEnum channelEnum = getChannelEnum(channel);
        if (ChannelEnum.SPIS.equals(channelEnum)) {
            sendNotifyMail(spis,orgName,helpEmail);
        }
        if (ChannelEnum.CRS.equals(channelEnum)){
            sendNotifyMail(crs,orgName,helpEmail);
        }
        if (ChannelEnum.ZHY.equals(channelEnum)){
            sendNotifyMail(zhy,orgName,helpEmail);
        }
    }

    private void sendNotifyMail(MailModel mailModel,String orgName, String helpEmail){
        mailModel.setTitle(mailModel.getNotify().getTitle())
                .setContent(String.format(mailModel.getNotify().getContent(), orgName, helpEmail));
        mailModel.send();
    }

    @Override
    public void sendMail(Integer channel, String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum) {
        ChannelEnum channelEnum = getChannelEnum(channel);
        sendMail(channelEnum, helpEmail, docTitle, url, helpStatusEnum);
    }

    private ChannelEnum getChannelEnum(Integer channel) {
        ChannelEnum channelEnum = null;
        for (ChannelEnum channelInstance : ChannelEnum.values()) {
            if (channelInstance.getCode() == channel) {
                channelEnum = channelInstance;
            }
        }
        return channelEnum;
    }

    @Override
    public void sendMail(ChannelEnum channelEnum, String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum) {
        if (ChannelEnum.SPIS.equals(channelEnum)) {
            sendMail(spis, channelEnum,helpEmail, docTitle, url, helpStatusEnum);
        } else if (ChannelEnum.CRS.equals(channelEnum)) {
            sendMail(crs,channelEnum,helpEmail, docTitle, url, helpStatusEnum);
        } else if (ChannelEnum.ZHY.equals(channelEnum)) {
            sendMail(zhy,channelEnum,helpEmail, docTitle, url, helpStatusEnum);
        }
    }

    private void sendMail(MailModel mailModel,ChannelEnum channelEnum, String helpEmail, String docTitle, String url, HelpStatusEnum helpStatusEnum){
        String expTime = expStr(channelEnum);
        List<String> tos = splitAddress(helpEmail);
        mailModel.setTos(tos.toArray(new String[tos.size()]));
        if (HelpStatusEnum.HELP_SUCCESSED.equals(helpStatusEnum)) {
            mailModel.setTitle(String.format(mailModel.getSuccess().getTitle(), docTitle))
                    .setContent(String.format(mailModel.getSuccess().getContent(), docTitle, url, url, expTime));
        } else if (HelpStatusEnum.HELP_THIRD.equals(helpStatusEnum)) {
            mailModel.setTitle(String.format(mailModel.getOuther().getTitle(), docTitle))
                    .setContent(String.format(mailModel.getOuther().getContent(), docTitle));
        } else if (HelpStatusEnum.HELP_FAILED.equals(helpStatusEnum)) {
            mailModel.setTitle(String.format(mailModel.getFailed().getTitle(), docTitle))
                    .setContent(String.format(mailModel.getFailed().getContent(), docTitle));
        }
        mailModel.send();
    }


    /**
     * 计算过期时间
     *
     * @param channelEnum
     * @return
     */
    private String expStr(ChannelEnum channelEnum) {
        long expLong;
        if (ChannelEnum.SPIS.equals(channelEnum)) {
            expLong = System.currentTimeMillis() + spis.getExp();
        } else if (ChannelEnum.CRS.equals(channelEnum)) {
            expLong = System.currentTimeMillis() + crs.getExp();
        } else if (ChannelEnum.ZHY.equals(channelEnum)) {
            expLong = System.currentTimeMillis() + zhy.getExp();
        } else {
            expLong = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15;
        }
        return DateUtil.date(expLong).toString("yyyy-MM-dd HH:mm:ss");
    }


    private static List<String> splitAddress(String addresses){
        if(StrUtil.isBlank(addresses)) {
            return null;
        }

        List<String> result;
        if(StrUtil.contains(addresses, ',')) {
            result = StrUtil.splitTrim(addresses, ',');
        }else if(StrUtil.contains(addresses, ';')) {
            result = StrUtil.splitTrim(addresses, ';');
        }else {
            result = CollUtil.newArrayList(addresses);
        }
        return result;
    }
}
