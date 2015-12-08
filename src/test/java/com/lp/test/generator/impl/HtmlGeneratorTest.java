package com.lp.test.generator.impl;

import com.lp.test.model.Destination;
import com.lp.test.model.Introduction;
import com.lp.test.model.Introductory;
import com.lp.test.model.Node;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class HtmlGeneratorTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Test the nothing is generated when null output folder is provided
     *
     * @throws Exception
     */
    @Test
    public void test_generate_nullOutputFolder() throws Exception {
        HtmlGenerator generator = new HtmlGenerator(null);
        HtmlGenerator mockGenerator = Mockito.spy(generator);
        StringWriter writer = new StringWriter();
        Mockito.doReturn(writer).when(mockGenerator).getWriter(Mockito.any(File.class));
        mockGenerator.generate(null, null);
        Assert.assertTrue(writer.toString().isEmpty());
    }


    /**
     * Test the nothing is generated when null Node and Destination are provided
     *
     * @throws Exception
     */
    @Test
    public void test_generate_nullNodeDestination() throws Exception {
        HtmlGenerator generator = new HtmlGenerator(tempFolder.getRoot().getAbsolutePath());
        HtmlGenerator mockGenerator = Mockito.spy(generator);
        StringWriter writer = new StringWriter();
        Mockito.doReturn(writer).when(mockGenerator).getWriter(Mockito.any(File.class));
        mockGenerator.generate(null, null);
        Assert.assertTrue(writer.toString().isEmpty());
    }

    /**
     * Test the nothing is generated when empty Node name is provided, since it's required data to generate the target
     * fileName
     *
     * @throws Exception
     */
    @Test
    public void test_generate_emptyNodeName() throws Exception {
        HtmlGenerator generator = new HtmlGenerator(tempFolder.getRoot().getAbsolutePath());
        HtmlGenerator mockGenerator = Mockito.spy(generator);
        StringWriter writer = new StringWriter();
        Mockito.doReturn(writer).when(mockGenerator).getWriter(Mockito.any(File.class));
        mockGenerator.generate(new Node(), new Destination());
        Assert.assertTrue(writer.toString().isEmpty());
    }

    /**
     * Test minimal generation is successful
     *
     * @throws Exception
     */
    @Test
    public void test_generate_min() throws Exception {
        HtmlGenerator generator = new HtmlGenerator(tempFolder.getRoot().getAbsolutePath());
        Node node = new Node();
        node.setId(1);
        node.setName("TestNode");
        Destination destination = new Destination();
        destination.setId(1);
        destination.setTitle("TestDestination");
        HtmlGenerator mockGenerator = Mockito.spy(generator);
        StringWriter writer = new StringWriter();
        Mockito.doReturn(writer).when(mockGenerator).getWriter(Mockito.any(File.class));
        mockGenerator.generate(node, destination);
        String actual = writer.toString();
        Assert.assertTrue(!actual.isEmpty());
        String expected = getHtml("/html/html-min.txt");
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test minimal complete is successful.
     * A complete generation scenario includes Destination overview and a Node parent-child hierarchy
     *
     * @throws Exception
     */
    @Test
    public void test_generate_complete() throws Exception {
        // create test fixtures
        Node node = new Node();
        node.setId(1);
        node.setName("TestNode");
        Node parent = new Node();
        parent.setName("TestNodeParent");
        parent.setId(1);
        node.setParent(parent);
        Node child = new Node();
        child.setName("TestNodeChild");
        node.getChildren().add(child);

        Destination destination = new Destination();
        Introduction introduction = new Introduction();
        introduction.setOverview("TestOverview");
        Introductory introductory = new Introductory();
        introductory.setIntroduction(introduction);
        destination.setIntroductory(introductory);
        destination.setId(1);
        destination.setTitle("TestDestination");

        HtmlGenerator generator = new HtmlGenerator(tempFolder.getRoot().getAbsolutePath());

        HtmlGenerator mockGenerator = Mockito.spy(generator);
        StringWriter writer = new StringWriter();
        Mockito.doReturn(writer).when(mockGenerator).getWriter(Mockito.any(File.class));
        mockGenerator.generate(node, destination);
        String actual = writer.toString();
        Assert.assertTrue(!actual.isEmpty());
        String expected = getHtml("/html/html-complete.txt");
        Assert.assertEquals(expected, actual);
    }

    private String getHtml(String fileName) throws IOException {
        InputStream is = getClass().getResourceAsStream(fileName);
        return IOUtils.toString(is);
    }

}
