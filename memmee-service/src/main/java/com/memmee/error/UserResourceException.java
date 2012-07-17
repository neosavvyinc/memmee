package com.memmee.error;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/16/12
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserResourceException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "There has been a problem with a user operation";
    public static final String INVALID_EMAIL = "The email you have provided an invalid email address.";
    public static final String IN_USE_EMAIL = "The email you have provided is already in use.";

    public UserResourceException() {
        super(DEFAULT_MESSAGE);
    }

    public UserResourceException(String message) {
        super(message);
    }

}
