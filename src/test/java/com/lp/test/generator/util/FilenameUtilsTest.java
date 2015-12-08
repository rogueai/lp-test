package com.lp.test.generator.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class FilenameUtilsTest {

    /**
     * <p/>
     * Input: null
     * Expected: ""
     */
    @Test
    public void test_normalize_empty() throws Exception {
        String result = FilenameUtils.normalize(null);

        Assert.assertEquals("", result);
    }

    /**
     * <p/>
     * Input: ""
     * Expected: ""
     */
    @Test
    public void test_normalize_null() throws Exception {
        String result = FilenameUtils.normalize("");

        Assert.assertEquals("", result);
    }

    /**
     * <p/>
     * Input: "       "
     * Expected: ""
     */
    @Test
    public void test_normalize_blank() throws Exception {
        String result = FilenameUtils.normalize("       ");

        Assert.assertEquals("", result);
    }

    /**
     * <p/>
     * Input: "abc/def"
     * Expected: "abc_def"
     */
    @Test
    public void test_normalize_invalidCharacter() throws Exception {
        String result = FilenameUtils.normalize("abc/def");

        Assert.assertEquals("abc_def", result);
    }

    /**
     * <p/>
     * Input: "abc.def"
     * Expected: "abc.def"
     */
    @Test
    public void test_normalize_dot() throws Exception {
        String result = FilenameUtils.normalize("abc.def");

        Assert.assertEquals("abc.def", result);
    }

    /**
     * <p/>
     * Input: "  abcdef  "
     * Expected: "abcdef"
     */
    @Test
    public void test_normalize_trim() throws Exception {
        String result = FilenameUtils.normalize("  abcdef  ");

        Assert.assertEquals("abcdef", result);
    }

    /**
     * <p/>
     * Input: "123-456"
     * Expected: "123-456"
     */
    @Test
    public void test_normalize_numbersHyphen() throws Exception {
        String result = FilenameUtils.normalize("123-456");

        Assert.assertEquals("123-456", result);
    }
}
