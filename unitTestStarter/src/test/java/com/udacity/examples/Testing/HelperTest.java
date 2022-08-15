package com.udacity.examples.Testing;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelperTest {

    @Test
    public void test() {

    }

    @Test
    public void test1() {
        List<String> empNames = Arrays.asList("Sina", "Jorge");
        final long actual = Helper.getCount(empNames);
        assertEquals(2,actual);
    }

    @Test
    public void verify_getMergedList() {
        List<String> empNames = Arrays.asList("Sina", "Jorge");
        String mergedList = Helper.getMergedList(empNames);
        assertEquals("Sina, Jorge", mergedList);
    }
}
