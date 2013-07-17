package com.memmee.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 8/20/12
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtilTest {

    @Test
    public void testNullOrEmpty() {
        assertThat(StringUtil.nullOrEmpty(null), is(true));
        assertThat(StringUtil.nullOrEmpty(""), is(true));
        assertThat(StringUtil.nullOrEmpty(" "), is(false));
        assertThat(StringUtil.nullOrEmpty("San Francisco"), is(false));
        assertThat(StringUtil.nullOrEmpty("0"), is(false));
    }

    @Test
    public void testPlainOldEmailAddress() {
        String normal = "aparrish@neosavvy.com";
        String user = StringUtil.findUserNameFromEmail(normal);
        assertThat(user, is("aparrish@neosavvy.com"));
    }

    @Test
    public void testEmailAndNameEmbedded() {
        String input = "William Adam Parrish <aparrish@neosavvy.com>";
        String returnVal = StringUtil.findUserNameFromEmail(input);
        assertThat(returnVal, is("aparrish@neosavvy.com"));
    }

    @Test
    public void testEmailAndSingleNameEmbedded() {
        String input = "Adam <aparrish@neosavvy.com>";
        String returnVal = StringUtil.findUserNameFromEmail(input);
        assertThat(returnVal, is("aparrish@neosavvy.com"));
    }

    @Test
    public void testEmailAndTwoNameEmbedded() {
        String input = "Adam Parrish <aparrish@neosavvy.com>";
        String returnVal = StringUtil.findUserNameFromEmail(input);
        assertThat(returnVal, is("aparrish@neosavvy.com"));
    }

    public void testPhoneNumber() {
        String input = "4142480161@pm.sprint.com";
        String returnVal = StringUtil.findUserNameFromEmail(input);
        assertThat(returnVal, is("4142480161"));
    }

}
