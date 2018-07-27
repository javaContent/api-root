package com.wd.cloud.docdelivery.task;

import cn.hutool.core.lang.Console;
import com.wd.cloud.docdelivery.entity.GiveRecord;
import com.wd.cloud.docdelivery.entity.HelpRecord;
import com.wd.cloud.docdelivery.repository.GiveRecordRepository;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/7/17
 * @Description:
 */
@Component
public class GiveRecordTask {

    @Autowired
    GiveRecordRepository giveRecordRepository;
    @Autowired
    HelpRecordRepository helpRecordRepository;

    @Scheduled(fixedRate=1000 * 30)
    public void deleteGiveRecord(){
        List<GiveRecord> giveRecords = giveRecordRepository.findTimeOutRecord();
        giveRecords.stream().forEach(g -> updateHelpStatus(g));
    }

    public void updateHelpStatus(GiveRecord giveRecord){
        giveRecordRepository.delete(giveRecord);
        HelpRecord helpRecord = giveRecord.getHelpRecord();
        helpRecord.setStatus(0);
        helpRecordRepository.save(helpRecord);
    }
}
