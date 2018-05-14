package com.wd.cloud.docdelivery.config;

import java.util.Arrays;
import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
public class DeliveryConfig {


    /**
     * 求助的来源渠道
     */
    public static final List<String> CHANNELS = Arrays.asList(new String[]{"QQ","SPIS","CRS","ZHY","XK"});

    public static final String SAVE_PATH = "F:/upload";

    public static final List<String> FILE_TYPES = Arrays.asList(new String[]{"pdf","doc","docx"});



}
