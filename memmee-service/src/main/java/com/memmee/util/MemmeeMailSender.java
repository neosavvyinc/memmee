package com.memmee.util;

import com.memmee.domain.user.dto.User;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 7/7/12
 * Time: 12:14 AM
 */
public interface MemmeeMailSender {
    public void sendConfirmationEmail(User user);
}
