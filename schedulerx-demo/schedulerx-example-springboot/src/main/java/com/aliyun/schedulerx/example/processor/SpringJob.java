package com.aliyun.schedulerx.example.processor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Spring Job Demo
 * @author yaohui
 * @create 2022/9/19 下午5:30
 **/
@Component
public class SpringJob {

    /**
     * 通过Cron表达式指定频率或指定时间
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void doSomethingByCron() {
        System.out.println("do something");
    }

    /**
     * 固定执行间隔时间
     */
    @Scheduled(fixedDelay = 2000)
    public void doSomethingByFixedDelay() {
        System.out.println("do something");
    }

    /**
     * 固定执行触发频率
     */
    @Scheduled(fixedRate = 2000)
    public void doSomethingByFixedRate() {
        System.out.println("do something");
    }
}
