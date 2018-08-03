package com.wd.cloud.docdelivery;

import com.wd.cloud.docdelivery.service.FileService;
import com.wd.cloud.docdelivery.service.FrontService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocDeliveryApplicationTests {

    @Autowired
    FileService fileService;

    @Autowired
    FrontService frontService;
    @Test
    public void contextLoads() {
        frontService.getCountHelpRecordToDay("hezhigang@hnwdkj.com");
    }

}
