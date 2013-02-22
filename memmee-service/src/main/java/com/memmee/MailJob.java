package com.memmee;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 2/19/13
 * Time: 11:20 PM
 */
public class MailJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        int returnCode = 0;
        try {
            returnCode = EmailResource.checkForEmail();
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (returnCode >= 0) {
            System.out.println("processed " + returnCode + " emails.");
        }
        else {
            System.out.println("There's been an error... better call somebody");
        }

    }
}
