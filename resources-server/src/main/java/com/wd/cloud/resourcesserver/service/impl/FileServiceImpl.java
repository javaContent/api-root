package com.wd.cloud.resourcesserver.service.impl;

import com.wd.cloud.resourcesserver.config.GlobalConfig;
import com.wd.cloud.resourcesserver.model.FileModel;
import com.wd.cloud.resourcesserver.model.HbaseModel;
import com.wd.cloud.resourcesserver.service.FileService;
import com.wd.cloud.resourcesserver.util.FileUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/7/20
 * @Description:
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    GlobalConfig globalConfig;

    @Autowired
    HbaseTemplate hbaseTemplate;

    @Override
    public boolean saveToDisk(String savePath, String fileName, MultipartFile file) throws IOException {
        //文件如果不存在，则保存
        if (!FileUtil.exist(new File(savePath, fileName))) {
            try {
                //创建一个新文件
                File newFile = FileUtil.touch(savePath, fileName);
                //将文件流写入文件中
                FileUtil.writeFromStream(file.getInputStream(), newFile);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveToHbase(String tableName, String fileName, MultipartFile file) throws IOException {
        HbaseModel hbaseModel = new HbaseModel(tableName,
                fileName.getBytes(),
                file.getBytes());
        return saveToHbase(hbaseModel);
    }

    @Override
    public FileModel getFileToHbase(String rowKey, String tableName) {
        return hbaseTemplate.get(tableName, rowKey, new RowMapper<FileModel>() {
            @Override
            public FileModel mapRow(Result result, int i) {
                FileModel fileModel = new FileModel();
                List<Cell> cells = result.listCells();
                if (cells != null) {
                    Cell cell = result.listCells().stream().findFirst().orElseGet(null);
                    byte[] hbaseFileByte = cell != null ? cell.getValueArray() : null;
                    //hbase读取的byte在原文件byte数组前多了38个byte字节，这里去掉前面这38个字节，否则读取的文件有错误
                    byte[] fileByte = Arrays.copyOfRange(hbaseFileByte, 38, hbaseFileByte.length);
                    fileModel.setFile(fileByte);
                }
                return fileModel;
            }
        }).setRowKey(rowKey);
    }

    /**
     * 保存文件到hbase
     *
     * @param hbaseModel
     * @return
     */
    private boolean saveToHbase(HbaseModel hbaseModel) {
        return hbaseTemplate.execute(hbaseModel.getTableName(), (hTableInterface) -> {
            boolean flag = false;
            try {
                Put put = new Put(hbaseModel.getRowKey());
                put.addColumn(hbaseModel.getFamily(), hbaseModel.getQualifier(), hbaseModel.getValue());
                hTableInterface.put(put);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        });
    }

}
