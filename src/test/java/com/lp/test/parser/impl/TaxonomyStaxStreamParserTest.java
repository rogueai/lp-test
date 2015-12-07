package com.lp.test.parser.impl;

import com.lp.test.model.Node;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class TaxonomyStaxStreamParserTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void test_parse_original() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-orig.xml");
        Assert.assertEquals(24, result.size());
    }

    @Test
    public void test_parse_min() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-min.xml");

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(355064));
        Node node = result.get(355064);
        Assert.assertEquals(355064, node.getId());
        Assert.assertEquals("Africa", node.getName());
    }

    @Test
    public void test_parse_multiline() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-multiline.xml");

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(1));
        Node node = result.get(1);
        Assert.assertEquals("Africa", node.getName());
    }

    @Test
    public void test_parse_comments_1() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-comments.xml");

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(1));
        Node node = result.get(1);
        Assert.assertEquals("Africa", node.getName());
    }

    @Test
    public void test_parse_comments_2() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-comments-2.xml");

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(1));
        Node node = result.get(1);
        Assert.assertEquals("South Africa", node.getName());
    }

    @Test
    public void test_parse_inline() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-inline.xml");

        Assert.assertEquals(2, result.size());
    }

    private Map<Integer, Node> parse(String filePath) throws Exception {
        TaxonomyStaxStreamParser parser = new TaxonomyStaxStreamParser();
        InputStream is = getClass().getResourceAsStream(filePath);
        return parser.parse(is);
    }

}
