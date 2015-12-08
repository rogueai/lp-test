package com.lp.test.cli;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.TemporaryFolder;

import java.io.File;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class LpTestCliTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Test ensuring valid parameters are parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void test_parseValidArgs() throws Exception {
        LpTestCli cli = new LpTestCli();

        // create temp files
        File destinationsFile = tempFolder.newFile("destinations.xml");
        File taxonomyFile = tempFolder.newFile("taxonomy.xml");

        String destinationsPath = destinationsFile.getAbsolutePath();
        String taxonomyPath = taxonomyFile.getAbsolutePath();
        String outputPath = tempFolder.getRoot().getAbsolutePath();
        String[] args = new String[]{"--destinationsFile", destinationsPath, "--taxonomyFile", taxonomyPath, "--outputFolder", outputPath};

        Parameters params = LpTestCli.parseArgs(args);

        Assert.assertNotNull(params);
        Assert.assertEquals(destinationsPath, params.getDestinationsFile());
        Assert.assertEquals(taxonomyPath, params.getTaxonomyFile());
        Assert.assertEquals(outputPath, params.getOutputFolder());
    }

    /**
     * Test ensuring valid shortened form of parameters are parsed correctly.
     *
     * @throws Exception
     */
    @Test
    public void test_parseValidArgs_short() throws Exception {
        LpTestCli cli = new LpTestCli();

        // create temp files
        File destinationsFile = tempFolder.newFile("destinations.xml");
        File taxonomyFile = tempFolder.newFile("taxonomy.xml");

        String destinationsPath = destinationsFile.getAbsolutePath();
        String taxonomyPath = taxonomyFile.getAbsolutePath();
        String outputPath = tempFolder.getRoot().getAbsolutePath();
        String[] args = new String[]{"-d", destinationsPath, "-t", taxonomyPath, "-o", outputPath};

        Parameters params = LpTestCli.parseArgs(args);

        Assert.assertNotNull(params);
        Assert.assertEquals(destinationsPath, params.getDestinationsFile());
        Assert.assertEquals(taxonomyPath, params.getTaxonomyFile());
        Assert.assertEquals(outputPath, params.getOutputFolder());
    }

    /**
     * Test ensuring invalid parameters result in System.exit(1)
     *
     * @throws Exception
     */
    @Test
    public void test_parseValidArgs_missing() throws Exception {
        LpTestCli cli = new LpTestCli();

        String[] args = new String[]{};

        // expect a System.exit(1)
        exit.expectSystemExitWithStatus(1);
        Parameters params = LpTestCli.parseArgs(args);
    }
}
