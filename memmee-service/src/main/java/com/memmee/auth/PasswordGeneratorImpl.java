package com.memmee.auth;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.memmee.error.PasswordGeneratorException;
import sun.misc.BASE64Encoder;

public class PasswordGeneratorImpl implements PasswordGenerator
{
    public synchronized String encrypt(String plaintext) throws PasswordGeneratorException
    {
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("SHA");
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new PasswordGeneratorException(e.getMessage());
        }
        try
        {
            md.update(plaintext.getBytes("UTF-8"));
        }
        catch(UnsupportedEncodingException e)
        {
            throw new PasswordGeneratorException(e.getMessage());
        }

        byte raw[] = md.digest();
        String hash = (new BASE64Encoder()).encode(raw);
        return hash;
    }
}
