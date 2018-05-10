package com.wd.cloud.docdelivery.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description: 互助记录
 */
@Entity
@Table(name="help_record")
public class HelpRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    /**
     * 互助请求的email地址
     */
    private String email;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 互助状态
     */
    private int status;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章地址
     */
    private String url;

    /**
     * 文章全文的文件名称
     */
    private String filename;


}
