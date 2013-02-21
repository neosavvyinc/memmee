package com.memmee;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 2/19/13
 * Time: 11:20 PM
 */
public class MailJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.print("yay job running");
    }
}
