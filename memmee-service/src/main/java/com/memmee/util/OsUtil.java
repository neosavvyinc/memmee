package com.memmee.util;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 6/21/12
 * Time: 9:16 PM
 */
public class OsUtil {

    private static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isUnix() {
        return getOsName().startsWith("Linux");
    }

    public static boolean isMac() {
        return getOsName().startsWith("Mac");
    }

}
