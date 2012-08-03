package com.memmee.auth;

import com.memmee.error.PasswordGeneratorException;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 8/3/12
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PasswordGenerator {

    public String encrypt(String plaintext) throws PasswordGeneratorException;

}
