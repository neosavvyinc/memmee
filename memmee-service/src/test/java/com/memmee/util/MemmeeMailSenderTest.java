package com.memmee.util;

import com.memmee.user.dto.User;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 7/6/12
 * Time: 6:58 PM
 */
public class MemmeeMailSenderTest {

    private MemmeeMailSender mailSender = new MemmeeMailSenderImpl();

    @Test
    public void testSendConfirmationEmail()
    {
        User user = new User();
        user.setEmail("aparrish@neosavvy.com");

        mailSender.sendConfirmationEmail(user);
    }

}
