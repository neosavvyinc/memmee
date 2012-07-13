package com.memmee.util;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/13/12
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListUtil {

    public static Boolean nullOrEmpty(List<?> list) {
       return list == null || list.size() == 0;
    }

}
