package com.wd.cloud.docdelivery.config;

import cn.hutool.system.SystemUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@Component
@ConfigurationProperties(value = "help.global")
public class GlobalConfig {

    private String cloudDomain;
    /**
     * 求助的来源渠道
     */
    private List<String> channels;

    /**
     * 文件保存路径
     */
    private String savePath;

    /**
     * 文件在hbase的位置
     */
    private String hbaseTableName = "journalImage";

    /**
     * 上传文件类型
     */
    private List<String> fileTypes;

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public String getSavePath() {
        return SystemUtil.get(SystemUtil.USER_HOME) + savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getHbaseTableName() {
        return hbaseTableName;
    }

    public GlobalConfig setHbaseTableName(String hbaseTableName) {
        this.hbaseTableName = hbaseTableName;
        return this;
    }

    public List<String> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(List<String> fileTypes) {
        this.fileTypes = fileTypes;
    }

    public String getCloudDomain() {
        return cloudDomain;
    }

    public void setCloudDomain(String cloudDomain) {
        this.cloudDomain = cloudDomain;
    }
}
