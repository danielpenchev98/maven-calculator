package com.calculator.webapp.quartz;


import com.calculator.core.CalculatorApp;
import com.calculator.webapp.db.dao.CalculatorDaoImpl;
import com.calculator.webapp.restresources.CalculatorRestResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class QuartzJobListener implements ServletContextListener {

    private static final String JOB_NAME = "pendingCalculationJob";
    private static final String JOB_GROUP = "CalculatorJobs";
    private static final String TRIGGER_NAME = "pendingCalculationTrigger";
    private static final String TRIGGER_GROUP = "CalculatorTriggers";
    private static final int INTERVAL_DURATION=5;

    private static final Logger logger = LogManager.getLogger(QuartzJobListener.class);
    private Scheduler scheduler;

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            scheduler.shutdown();
        } catch (SchedulerException ex) {
            logger.error("Error stopping Quartz", ex);
        }

    }

    public void contextInitialized(ServletContextEvent event) {
        JobDetail job = getPendingCalculationJobInstance();
        Trigger trigger = getPendingCalculationTrigger();

        try {
            scheduler = getStandardScheduler();
            scheduler.scheduleJob(job,trigger);
            scheduler.start();
        } catch (SchedulerException ex) {
            logger.error("Quartz failed to initialize", ex);
        }
    }

    private JobDetail getPendingCalculationJobInstance(){
        JobDetail detail = JobBuilder.newJob(PendingCalculationJob.class)
                .withIdentity(JOB_NAME,JOB_GROUP)
                .build();

        return getJobWithInjectedDependencies(detail);

    }

    private JobDetail getJobWithInjectedDependencies(final JobDetail detail){
        JobDataMap map = detail.getJobDataMap();
        map.put("dao",new CalculatorDaoImpl());
        map.put("calculator",new CalculatorApp());
        return detail;
    }

    private Trigger getPendingCalculationTrigger() {
        return TriggerBuilder.newTrigger()
                .withIdentity(TRIGGER_NAME, TRIGGER_GROUP)
                .startNow()
                .withSchedule(getTriggerInterval())
                .build();
    }

    private SimpleScheduleBuilder getTriggerInterval(){
        return SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(INTERVAL_DURATION).repeatForever();
    }

    private Scheduler getStandardScheduler() throws SchedulerException{
        return new StdSchedulerFactory().getScheduler();
    }

}