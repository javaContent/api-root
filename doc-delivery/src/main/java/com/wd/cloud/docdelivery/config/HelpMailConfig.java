package com.wd.cloud.docdelivery.config;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.setting.Setting;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@Component
@ConfigurationProperties(value = "mail.success")
public class HelpSuccessMailConfig {

    private static final Setting setting = new Setting("mail.setting");
    private String subject;
    private String content;

    public String getSubject() {
        return subject;
    }


    @Bean("spisMailAccount")
    public MailAccount spisMailAccount(){
        MailAccount spisMailAcount = new MailAccount();
        spisMailAcount.setAuth(setting.getBool("spis","spisAuth"));
        spisMailAcount.setFrom(setting.get("spis","spisFrom"));
        spisMailAcount.setHost(setting.get("spis","spisHost"));
        spisMailAcount.setPort(setting.getInt("spis","spisPort"));
        spisMailAcount.setStartttlsEnable(setting.getBool("spis","spisStartttlsEnable"));
        spisMailAcount.setUser(setting.get("spis","spisUser"));
        spisMailAcount.setPass(setting.get("spis","spisPass"));
        return spisMailAcount;
    }
    @Bean("crsMailAccount")
    public MailAccount crsMailAccount(){
        MailAccount crsMailAccount = new MailAccount();
        crsMailAccount.setAuth(setting.getBool("crs","crsAuth"));
        crsMailAccount.setFrom(setting.get("crs","crsFrom"));
        crsMailAccount.setHost(setting.get("crs","crsHost"));
        crsMailAccount.setPort(setting.getInt("crs","crsPort"));
        crsMailAccount.setStartttlsEnable(setting.getBool("crs","crsStartttlsEnable"));
        crsMailAccount.setUser(setting.get("crs","crsUser"));
        crsMailAccount.setPass(setting.get("crs","crsPass"));
        return crsMailAccount;
    }
    @Bean("zhyMailAccount")
    public MailAccount zhyMailAccount(){
        MailAccount zhyMailAccount = new MailAccount();
        zhyMailAccount.setAuth(true);
        return zhyMailAccount;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}