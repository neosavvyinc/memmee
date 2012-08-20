package com.memmee.util;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/14/12
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    public static Boolean nullOrEmpty(String val) {
        return val == null || val == "";
    }

    //@TODO, this is temporary till I get a jar for random password generation
    public static String generateRandom() {
        return "r@ndom67";
    }

}
