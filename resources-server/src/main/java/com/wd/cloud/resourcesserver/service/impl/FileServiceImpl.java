package com.wd.cloud.resourcesserver.service.impl;

import com.wd.cloud.resourcesserver.config.GlobalConfig;
import com.wd.cloud.resourcesserver.model.FileObjModel;
import com.wd.cloud.resourcesserver.model.HbaseObjModel;
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
    public boolean saveToDisk(String dir, String fileName, MultipartFile file) throws IOException {
        //文件如果不存在，则保存
        if (!FileUtil.exist(new File(dir, fileName))) {
            try {
                //创建一个新文件
                File newFile = FileUtil.touch(dir, fileName);
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
    public FileObjModel getFileToDisk(String dir, String fileName) {
        FileObjModel fileObjModel = new FileObjModel();
        File file = new File(dir, fileName);
        if (FileUtil.exist(file)) {
            fileObjModel.setFile(file);
            fileObjModel.setFileByte(FileUtil.readBytes(file));
            fileObjModel.setFileName(fileName);
            return fileObjModel;
        }
        return fileObjModel;
    }

    @Override
    public boolean saveToHbase(String tableName, String fileName, MultipartFile file) throws IOException {
        HbaseObjModel hbaseObjModel = new HbaseObjModel(tableName,
                fileName.getBytes(),
                file.getBytes());
        log.info("上传文件：{}，size：{} byte", file.getOriginalFilename(), file.getBytes().length);
        return hbaseTemplate.execute(hbaseObjModel.getTableName(), (hTableInterface) -> {
            boolean flag = false;
            try {
                Put put = new Put(hbaseObjModel.getRowKey());
                put.addColumn(hbaseObjModel.getFamily(), hbaseObjModel.getQualifier(), hbaseObjModel.getValue());
                hTableInterface.put(put);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        });
    }

    @Override
    public FileObjModel getFileToHbase(String tableName, String fileName) {
        return hbaseTemplate.get(tableName, fileName, new RowMapper<FileObjModel>() {
            @Override
            public FileObjModel mapRow(Result result, int i) {
                FileObjModel fileObjModel = new FileObjModel();
                List<Cell> cells = result.listCells();
                if (cells != null) {
                    Cell cell = result.listCells().stream().findFirst().orElseGet(null);
                    byte[] hbaseFileByte = cell != null ? cell.getValueArray() : null;
                    byte[] fileByte = Arrays.copyOfRange(hbaseFileByte, cell.getValueOffset(), hbaseFileByte.length);
                    log.info("读取文件：{}，size：{} byte", fileName, fileByte.length);
                    fileObjModel.setFileByte(fileByte);
                }
                return fileObjModel;
            }
        }).setFileName(fileName);
    }


}
