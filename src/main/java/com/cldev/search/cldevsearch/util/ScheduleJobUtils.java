package com.cldev.search.cldevsearch.util;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Copyright © 2018 eSunny Info. Developer Stu. All rights reserved.
 * <p>
 * code is far away from bug with the animal protecting
 * <p>
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author zpx
 * Build File @date: 2019/12/6 11:18
 * @version 1.0
 * @description
 */
@Component
public class ScheduleJobUtils extends QuartzJobBean {

//    private ExecutorService service = Executors.newSingleThreadExecutor();

//    @Autowired
//    private ScheduleJobLogMapper scheduleJobLogMapper;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob ) context.getMergedJobDataMap()
                .get(Constant.JOB_PARAM_KEY);
        //获取spring bean


//        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
//        ScheduleJobLog jobLog = new ScheduleJobLog();
//        jobLog.setJobId(scheduleJob.getJobId());
//        jobLog.setBeanName(scheduleJob.getBeanName());
//        jobLog.setMethodName(scheduleJob.getMethodName());
//        jobLog.setParams(scheduleJob.getParams());
//        jobLog.setCreateTime(new Date());

        //任务开始时间
//        long startTime = System.currentTimeMillis();
//        Byte zero = 0;
//
//        Byte one=1;
//        try {
//            //执行任务
////            log.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
//            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
//                    scheduleJob.getMethodName(), scheduleJob.getParams());
//            Future<?> future = service.submit(task);
//
//            future.get();
//
//            //任务执行总时长
//            long times = System.currentTimeMillis() - startTime;
//            jobLog.setTimes((int)times);
//            //任务状态    0：成功    1：失败
//            jobLog.setStatus(zero);
//
//            log.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
//        } catch (Exception e) {
//            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);
//
//            //任务执行总时长
//            long times = System.currentTimeMillis() - startTime;
//            jobLog.setTimes((int)times);
//
//            //任务状态    0：成功    1：失败
//            jobLog.setStatus(one);
//            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
//        }finally {
//            scheduleJobLogMapper.insertSelective(jobLog);
//        }
    }
}
