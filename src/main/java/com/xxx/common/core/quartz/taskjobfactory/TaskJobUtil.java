package com.xxx.common.core.quartz.taskjobfactory;

import com.xxx.common.core.quartz.BaseTaskJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xujingyang
 * @Description: task任务创建工具
 * @date 2018/5/30.
 */
@Component
public class TaskJobUtil {
    private static final Logger logger = LoggerFactory.getLogger(TaskJobUtil.class);

    private static TaskJobUtil jobUtil;

    @Autowired
    private Scheduler scheduler;

    public TaskJobUtil() {
        logger.info("init jobUtil");
        jobUtil = this;
    }

    public static TaskJobUtil getInstance() {
        logger.info("retun  JobCreateUtil");
        return TaskJobUtil.jobUtil;
    }

    /**
     * 创建job
     *
     * @param clazz          任务类
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     * @throws Exception
     */
    public void addJob(Class clazz, String jobGroupName, String cronExpression) throws Exception {

        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(((BaseTaskJob)clazz.newInstance()).getClass()).withIdentity(clazz.getSimpleName(), jobGroupName).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(clazz.getSimpleName(), jobGroupName)
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 暂停job
     *
     * @param jobClassName 任务类名称
     * @param jobGroupName 任务所在组名称
     * @throws SchedulerException
     */
    public void pauseJob(String jobClassName, String jobGroupName) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 恢复job
     *
     * @param jobClassName 任务类名称
     * @param jobGroupName 任务所在组名称
     * @throws SchedulerException
     */
    public void resumeJob(String jobClassName, String jobGroupName) throws SchedulerException {

        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    /**
     * job 更新
     *
     * @param jobClassName   任务类名称
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     * @throws Exception
     */
    public void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);

    }

    /**
     * job 删除
     *
     * @param jobClassName 任务类名称
     * @param jobGroupName 任务所在组名称
     * @throws Exception
     */
    public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

}
