package com.wd.cloud.docdelivery.monitor;

import com.wd.cloud.docdelivery.entity.HelpRecord;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Aspect
@Component
public class ServiceMonitor {

    @Autowired
    private HelpRecordRepository recordRepository;

    /**
     * 定义一个切入点
     * 匹配controller1包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(* com.wd.cloud.docdelivery.*controller.*(..))")
    public void executePackage() {
    }

    /**
     * 前置通知，目标方法调用前被调用
     */
    @Before("executePackage()")
    public void beforeAdvice() {
        HelpRecord helpModel = new HelpRecord();
        if (helpModel.getMonitor()==2){

        }else {
            helpModel.setMonitor(1);
            recordRepository.save(helpModel);
        }


    }

    /**
     * 后置通知，目标方法调用后被调用
     */

    @After("executePackage()")
    public void afterAdvice() {
        recordRepository.findAll();
        HelpRecord helpModel = new HelpRecord();
        helpModel.setMonitor(2);
        recordRepository.save(helpModel);

    }

}
