package com.lp.test.parser.impl;

import com.lp.test.model.Destination;
import com.lp.test.model.Node;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class TaxonomyStaxStreamParserTest {
    @Test
    public void test_parse_original() throws Exception {
        TaxonomyStaxStreamParser parser = new TaxonomyStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/taxonomy-orig.xml");
        Map<Integer, Node> result = parser.parse(is);
        Assert.assertEquals(24, result.size());
    }

    @Test
    public void test_parse_min() throws Exception {
        TaxonomyStaxStreamParser parser = new TaxonomyStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/taxonomy-min.xml");
        Map<Integer, Node> result = parser.parse(is);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(355064));
        Node node = result.get(355064);
        Assert.assertEquals(355064l, node.getId());
        Assert.assertEquals("Africa", node.getName());
    }
}
