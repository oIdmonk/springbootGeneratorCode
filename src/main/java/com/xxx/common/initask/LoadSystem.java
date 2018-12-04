package com.xxx.common.initask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动后执行，初始化定时任务
 * author xujingyang
 * date 2018/07/06
 */
@Component
public class LoadSystem implements ApplicationRunner {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
    }
}
