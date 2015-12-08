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

    /**
     * Test parsing the provided xml
     *
     * @throws Exception
     */
    @Test
    public void test_parse_original() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-orig.xml");
        Assert.assertEquals(24, result.size());
    }

    /**
     * Test with a minimal xml
     *
     * @throws Exception
     */
    @Test
    public void test_parse_min() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-min.xml");

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(355064));
        Node node = result.get(355064);
        Assert.assertEquals(355064, node.getId());
        Assert.assertEquals("Africa", node.getName());
    }

    /**
     * Test parsing multiline text block containing spaces, e.g.:
     * <pre>
     * {@code
     * <node_name>
     *
     * Africa
     *
     * </node_name>
     * }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void test_parse_multiline() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-multiline.xml");

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(1));
        Node node = result.get(1);
        Assert.assertEquals("Africa", node.getName());
    }

    /**
     * Test parsing multiline text block containing comments, e.g.:
     * <pre>
     * {@code
     * <node_name>
     * <!-- comment -->
     * Africa
     * <!-- comment -->
     * </node_name>
     * }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void test_parse_comments_1() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-comments.xml");

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(1));
        Node node = result.get(1);
        Assert.assertEquals("Africa", node.getName());
    }

    /**
     * Test parsing text block containing inline comments, e.g.:
     * <pre>
     * {@code
     * <node_name>
     * South <!-- comment -->Africa
     * </node_name>
     * }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void test_parse_comments_2() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-comments-2.xml");

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(1));
        Node node = result.get(1);
        Assert.assertEquals("South Africa", node.getName());
    }

    /**
     * Test parsing an inline xml with no spaces, e.g.:
     * <pre>
     *  {@code
     *  <taxonomies><taxonomy><taxonomy_name>World</taxonomy_name>...
     *  }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void test_parse_inline() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-inline.xml");

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

    /**
     * Test parsing an uppercase xml, e.g.:
     * <pre>
     *  {@code
     * <TAXONOMIES><TAXONOMY><NODE>...</NODE></TAXONOMY></TAXONOMIES>
     *  }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void test_parse_uppercase() throws Exception {
        Map<Integer, Node> result = parse("/xml/taxonomy-uppercase.xml");

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

    private Map<Integer, Node> parse(String filePath) throws Exception {
        TaxonomyStaxStreamParser parser = new TaxonomyStaxStreamParser();
        InputStream is = getClass().getResourceAsStream(filePath);
        return parser.parse(is);
    }

}
