package com.lp.test.parser.impl;

import com.lp.test.model.Destination;
import com.lp.test.parser.exception.ParseException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class DestinationStaxStreamParserTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void test_parse_empty() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-empty.xml");
        exception.expect(ParseException.class);
        parser.parse(is);
    }

    @Test
    public void test_parse_malformed() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-malformed.xml");
        exception.expect(ParseException.class);
        parser.parse(is);
    }

    @Test
    public void test_parse_original() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-orig.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(24, result.size());
    }

    @Test
    public void test_parse_min() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-min.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(355064));
        Destination destination = result.get(355064);
        Assert.assertEquals(355064l, destination.getId());
        Assert.assertEquals("Africa", destination.getTitle());
        Assert.assertNotNull(destination.getIntroductory());
        Assert.assertNotNull(destination.getIntroductory().getIntroduction());
        Assert.assertNotNull(destination.getIntroductory().getIntroduction().getOverview());
    }

    @Test
    public void test_parse_inline() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-inline.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void test_parse_comments() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-comments.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

}
