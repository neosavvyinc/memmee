package com.memmee.util;

import com.memmee.MemmeeUrlConfiguration;
import com.memmee.domain.user.dto.User;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 7/7/12
 * Time: 12:14 AM
 */
public interface MemmeeMailSender {
    public void sendConfirmationEmail(User user);

    public void sendForgotPasswordEmail(User user, String temporaryPassword);

    public void setUrlConfiguration(MemmeeUrlConfiguration memmeeUrlConfiguration);

    public void sendInvalidEmailInvitation(String email);

    public void sendMemmeeReceivedConfirmationMail(User user);
}
