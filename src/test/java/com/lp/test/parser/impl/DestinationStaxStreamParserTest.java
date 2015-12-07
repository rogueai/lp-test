package com.lp.test.parser.impl;

import com.lp.test.model.Destination;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class DestinationStaxStreamParserTest {

    @Test
    public void test_parse_original() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-orig.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertEquals(24, result.size());
    }

    @Test
    public void test_parse_min() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-min.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(355064));
        Destination destination = result.get(355064);
        Assert.assertEquals(355064l, destination.getId());
        Assert.assertEquals("Africa", destination.getTitle());
        Assert.assertNotNull(destination.getIntroductory());
        Assert.assertNotNull(destination.getIntroductory().getIntroduction());
        Assert.assertNotNull(destination.getIntroductory().getIntroduction().getOverview());
    }

}
