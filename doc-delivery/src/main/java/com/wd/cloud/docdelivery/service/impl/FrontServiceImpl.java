package com.wd.cloud.docdelivery.service.impl;

import com.wd.cloud.docdelivery.domain.HelpLierature;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.repository.HelpLieratureRepository;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.repository.LiteratureRepository;
import com.wd.cloud.docdelivery.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
@Service("frontService")
public class FrontServiceImpl implements FrontService{

    @Autowired
    LiteratureRepository literatureRepository;

    @Autowired
    HelpRecordRepository helpRecordRepository;

    @Autowired
    HelpLieratureRepository helpLieratureRepository;

    @Override
    public List<HelpRecord> getLiteratureForUser(String email) {
        return helpRecordRepository.findByEmail(email);
    }

    @Override
    public void save(){
        HelpLierature helpLierature = new HelpLierature();
        helpLierature.setDocTitle("test");
        helpLierature.setDoi("123");
        List<HelpRecord> helpRecords = new ArrayList<>();
        HelpRecord helpRecord = new HelpRecord();
        helpRecord.setDocFilename("test.pdf");
        helpRecord.setEmail("vampirehgg@qq.com");
        helpRecords.add(helpRecord);
        helpLierature.setHelpRecords(helpRecords);
        helpLieratureRepository.save(helpLierature);
    }
}
