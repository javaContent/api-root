package com.wd.cloud.resourcesserver.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wd.cloud.resourcesserver.config.GlobalConfig;
import com.wd.cloud.resourcesserver.entity.UploadRecord;
import com.wd.cloud.resourcesserver.enums.TargetEnum;
import com.wd.cloud.resourcesserver.model.FileObjModel;
import com.wd.cloud.resourcesserver.model.HbaseObjModel;
import com.wd.cloud.resourcesserver.repository.UploadRecordRepository;
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
import java.util.Optional;

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

    @Autowired
    UploadRecordRepository uploadRecordRepository;

    @Override
    public boolean saveToDisk(String dir, String fileName, MultipartFile file) throws IOException {
        boolean flag = true;
        //文件如果不存在，则保存
        if (!FileUtil.exist(new File(dir, fileName))) {
            try {
                //创建一个新文件
                File newFile = FileUtil.touch(dir, fileName);
                //将文件流写入文件中
                FileUtil.writeFromStream(file.getInputStream(), newFile);
                flag = saveUploadRecord(TargetEnum.DISK,dir,fileName,file);
            } catch (IOException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
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
                flag = saveUploadRecord(TargetEnum.HBASE,tableName,fileName,file);;
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
                    Cell cell = cells.stream().findFirst().get();
                    byte[] fileByte = Arrays.copyOfRange(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    log.info("读取文件：{}，size：{} byte", fileName, fileByte.length);
                    File file = FileUtil.writeByteToDisk(globalConfig.getResources()+"/temp/",fileName,fileByte);
                    fileObjModel.setFileByte(fileByte);
                    fileObjModel.setFile(file);
                }
                return fileObjModel;
            }
        }).setFileName(fileName);
    }


    private boolean saveUploadRecord(TargetEnum target,String path,String fileName,MultipartFile file){
        UploadRecord uploadRecord = uploadRecordRepository
                .findByTargetAndPathAndFileNameAndFileSizeAndMissed(target.name(),path,fileName,file.getSize(),false)
                .orElse(new UploadRecord());
        uploadRecord.setTarget(target.name());
        uploadRecord.setPath(path);
        uploadRecord.setFileName(fileName);
        uploadRecord.setFileType(StrUtil.subAfter(file.getOriginalFilename(), ".", true));
        uploadRecord.setFileSize(file.getSize());
        return !uploadRecordRepository.save(uploadRecord).isMissed();
    }

}
