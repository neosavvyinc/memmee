package com.memmee.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     *
     * Valid values that appear in gmail.com
     * Adam Parrish <aparrish@neosavvy.com>
     * 4142480161@pm.sprint.com
     *
     * @param emailAddress
     * @return
     */
    public static String findUserNameFromEmail( String emailAddress ) {

        Matcher emailOnlyMatcher = emailPattern.matcher(emailAddress);
        Matcher firstNameEmailEmbeddedMatcher = nameWithEmailPattern.matcher(emailAddress);
        if( emailOnlyMatcher.matches() ) {
            return emailOnlyMatcher.group(1);
        }
        else if( firstNameEmailEmbeddedMatcher.matches() ) {

            System.out.println(firstNameEmailEmbeddedMatcher.group(0));
            System.out.println(firstNameEmailEmbeddedMatcher.group(1));
            System.out.println(firstNameEmailEmbeddedMatcher.group(2));
            System.out.println(firstNameEmailEmbeddedMatcher.group(3));
            System.out.println(firstNameEmailEmbeddedMatcher.group(4));
            System.out.println(firstNameEmailEmbeddedMatcher.group(5));
            System.out.println(firstNameEmailEmbeddedMatcher.group(6));

            return firstNameEmailEmbeddedMatcher.group(3);
        }

        return "";
    }

    private static final String EMAIL_PATTERN =
            "(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*)@"
                    + "([A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)";

    private static final String NAME_WITH_EMAIL_EMBEDDED =
            "((\\w*)\\s*)*<(\\w*)@((\\w*)(\\.\\w*))>";
    private static Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static Pattern nameWithEmailPattern = Pattern.compile(NAME_WITH_EMAIL_EMBEDDED);


}
