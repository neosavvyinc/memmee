package com.memmee;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 2/19/13
 * Time: 11:06 PM
 */
public class QuartzJobMonitor implements JobListener {
    @Override
    public String getName() {
        return "MailListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
