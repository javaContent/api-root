package com.wd.cloud.docdelivery;

import cn.hutool.core.lang.Console;
import com.wd.cloud.docdelivery.service.FileService;
import com.wd.cloud.docdelivery.service.FrontService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DocDeliveryApplicationTests {

    @Autowired
    FileService fileService;

    @Autowired
    FrontService frontService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/backend/fiaied/118")
                .contentType(MediaType.APPLICATION_JSON)
                .param("giverId","1")
                .param("giverName","hgg"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("处理成功"));
    }

    @Test
    public void test(){
        AtomicInteger x = new AtomicInteger();
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(-1);
        ints.add(0);
        ints.add(23);
        ints.add(12);
        ints.add(8);
        ints.stream().filter(i -> i<10).forEach(j -> x.set(j));
        Console.log(x);

    }

}
