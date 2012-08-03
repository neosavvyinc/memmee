package com.memmee.error;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 8/3/12
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordGeneratorException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "There has been a problem with the password encryption";

    public PasswordGeneratorException() {
        super(DEFAULT_MESSAGE);
    }

    public PasswordGeneratorException(String message) {
        super(message);
    }

}
