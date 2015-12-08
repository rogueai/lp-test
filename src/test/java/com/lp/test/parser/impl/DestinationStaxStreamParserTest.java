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
    public final ExpectedException exception = ExpectedException.none();
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Test that parsing an empty xml throws a {@link ParseException}
     *
     * @throws Exception
     */
    @Test
    public void test_parse_empty() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-empty.xml");
        exception.expect(ParseException.class);
        parser.parse(is);
    }

    /**
     * Test that parsing a malformed xml throws a {@link ParseException}. The malformed xml used has a missing end tag.
     *
     * @throws Exception
     */
    @Test
    public void test_parse_malformed() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-malformed.xml");
        exception.expect(ParseException.class);
        parser.parse(is);
    }

    /**
     * Test parsing the provided xml
     *
     * @throws Exception
     */
    @Test
    public void test_parse_original() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-orig.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(24, result.size());
    }

    /**
     * Test that parsing a minimal destinations xml is successful
     *
     * @throws Exception
     */
    @Test
    public void test_parse_min() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-min.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(355064));
        Destination destination = result.get(355064);
        Assert.assertEquals(355064, destination.getId());
        Assert.assertEquals("Africa", destination.getTitle());
        Assert.assertNotNull(destination.getIntroductory());
        Assert.assertNotNull(destination.getIntroductory().getIntroduction());
        Assert.assertNotNull(destination.getIntroductory().getIntroduction().getOverview());
    }

    /**
     * Test parsing an inline xml with no spaces, e.g.:
     * <pre>
     *  {@code
     *  <destinations><destination>...</destination></destinations>
     *  }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void test_parse_inline() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-inline.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

    /**
     * Test parsing an inline xml with comments, e.g.:
     * <pre>
     *  {@code
     * <destinations>
     * ...
     * </destination>
     *
     * <!-- comment -->
     *
     * <destination atlas_id="2" asset_id="2" title="Asia" title-ascii="Asia">
     * ...
     * </destinations>
     *  }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void test_parse_comments() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-comments.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

    /**
     * Test parsing an uppercase xml, e.g.:
     * <pre>
     *  {@code
     * <DESTINATIONS><DESTINATION>...</DESTINATION></DESTINATIONS>
     *  }
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void test_parse_uppercase() throws Exception {
        DestinationStaxStreamParser parser = new DestinationStaxStreamParser();
        InputStream is = getClass().getResourceAsStream("/xml/destinations-uppercase.xml");
        Map<Integer, Destination> result = parser.parse(is);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

}
