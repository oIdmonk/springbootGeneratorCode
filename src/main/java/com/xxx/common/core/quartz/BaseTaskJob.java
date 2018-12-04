package com.xxx.common.core.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author xujingyang
 * @Description: 基础任务调度taskJob接口，新建任务请继承此接口
 * @date 2018/5/30
 */
public interface BaseTaskJob extends Job {
    void execute(JobExecutionContext context)
            throws JobExecutionException;
}
