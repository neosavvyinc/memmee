package com.memmee.util;

import com.memmee.MemmeeUrlConfiguration;
import com.memmee.domain.user.dto.User;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class MemmeeMailSenderTest {

    private MemmeeMailSender mailSender = new MemmeeMailSenderImpl();

    @Test
    public void testSendConfirmationEmail()
    {
        User user = new User();
        user.setEmail("aparrish@neosavvy.com");

        MemmeeUrlConfiguration config = new MemmeeUrlConfiguration();
        config.setActiveEmailAddress("aparrish@neosavvy.com");
        config.setActiveUrl("test-cases.memmee.com");


        mailSender.setUrlConfiguration( config );
        mailSender.sendConfirmationEmail(user);
    }

}
