package com.aliyun.schedulerx.example.springjob;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Spring Job Demo，通过注解定义任务表达式，可以自动同步到服务端
 * 
 * @author yaohui
 * @create 2022/9/19 下午5:30
 **/
@Component
public class SpringJob {

    /**
     * 每5秒跑一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void job1() {
        System.out.println("do job1...");
    }
    
    /**
     * 每分钟跑一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void job2() {
        System.out.println("do job2...");
    }

    /**
     * 上次跑完之后，隔2秒再跑
     */
    @Scheduled(fixedDelay = 2000)
    public void job3() {
        System.out.println("do job3...");
    }

    /**
     * 每2秒跑一次
     */
    @Scheduled(fixedRate = 2000)
    public void job4() {
        System.out.println("do job4...");
    }
    
    
}
