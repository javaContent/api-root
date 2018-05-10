package com.wd.cloud.commons.util;

import java.io.File;

/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
public class FileUtil {

    public static void createDir(File path){
        if(path.exists()){//如果路径已经存在
            return ;
        }
        File parent=path.getParentFile();
        if(parent.exists()){//如果上级路径是存在的，直接创建
            path.mkdir();//创建目录
        }else{
            createDir(parent);
        }
    }

    public static File createFile(String path, String name){
        File file=new File(path,name);
        if(!file.exists()){
            return file;
        }
        return file;
    };

    public static File createFile(File path, String name){
        File file=new File(path,name);
        if(!file.exists()){
            return file;
        }
        return file;
    };
}
