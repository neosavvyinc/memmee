package com.memmee.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/13/12
 * Time: 12:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListUtilTest {

    @Test
    public void testNullOrEmpty() {
        assertThat(ListUtil.nullOrEmpty(null), is(true));
        assertThat(ListUtil.nullOrEmpty(new ArrayList<Object>()), is(true));

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);

        assertThat(ListUtil.nullOrEmpty(list), is(false));
    }
}
