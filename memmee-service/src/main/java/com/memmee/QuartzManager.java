package com.memmee;

import com.yammer.dropwizard.lifecycle.Managed;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.EverythingMatcher;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 2/19/13
 * Time: 10:08 PM
 */
public class QuartzManager implements Managed {

    private Scheduler scheduler;
    private QuartzSchedulerMonitor schedulerMonitor;
    private QuartzJobMonitor jobMonitor;

    public  QuartzManager(SchedulerFactory sf) throws SchedulerException {
        scheduler = sf.getScheduler();
        schedulerMonitor = new QuartzSchedulerMonitor(); // Implements SchedulerListener
        scheduler.getListenerManager().addSchedulerListener(schedulerMonitor);
        jobMonitor = new QuartzJobMonitor(); // Implements JobListener
    }

    @Override
    public void start() throws Exception {
        scheduler.start();
        // Make our Job listener cover all scheduled jobs
        scheduler.getListenerManager().addJobListener(jobMonitor, EverythingMatcher.allJobs());
    }

    @Override
    public void stop() throws Exception {
        scheduler.getListenerManager().removeJobListener(jobMonitor.getName());
        scheduler.shutdown(true);
    }

    public boolean isHealthy(){
        return true;//return schedulerMonitor.isHealthy() && jobMonitor.isHealthy();
    }


}
