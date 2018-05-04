package com.wd.cloud.documentdelivery.domain;

/**
 * @author He Zhigang
 * @date 2018/5/3
 * @Description: 文献
 */
public class Literature {

    private boolean favorite = false;

    /**
     * 文章的链接地址
     */
    private String href;
    /**
     * 标题
     */
    private String title;
    /**
     * 来源
     */
    private String source;
    /**
     * 摘要
     */
    private String summary;

    /**
     * 被引信息
     */
    private String quoteText;
    private String quoteLink;

    /**
     * 相关文章连接
     */
    private String relatedLink;

    /**
     * 版本信息
     */
    private String versionText;
    private String versionLink;

    /**
     * webScience信息
     */
    private String webScienceText;
    private String webScienceLink;

    //===================新版添加属性=================//

    /**文档类型*/
    private String docType;

    /**开发获取来源*/
    private String openSource;

    /**
     * 开放获取来源的文档类型
     */
    private String openSourceDocType;

    /**开发获取地址*/
    private String openUri;

    /**是否是开发获取的，默认为否*/
    private boolean isOpen=false;

    /**本地是否有*/
    private boolean hasLocal=false;

    /**
     * 是有来自谷歌图书馆
     */
    private boolean isGoogleBook=false;

    private String id;
}
