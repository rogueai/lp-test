package com.lp.test.cli;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class BatchProcessorTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Test that passing null parameters will result in System.exit(1)
     *
     * @throws Exception
     */
    @Test
    public void test_run_nullParameters() throws Exception {
        BatchProcessor processor = new BatchProcessor();
        exit.expectSystemExitWithStatus(1);
        processor.run(null);
    }

    /**
     * Test that passing empty parameters will result in System.exit(1)
     *
     * @throws Exception
     */
    @Test
    public void test_run_emptyParameters() throws Exception {
        BatchProcessor processor = new BatchProcessor();
        Parameters params = new Parameters();
        exit.expectSystemExitWithStatus(1);
        processor.run(params);
    }

    /**
     * Test that passing valid parameters is successful
     *
     * @throws Exception
     */
    @Test
    public void test_run_validParameters() throws Exception {
        BatchProcessor processor = new BatchProcessor();
        Parameters params = new Parameters();
        params.setOutputFolder(tempFolder.getRoot().getAbsolutePath());

        String destinationsPath = Paths.get(getClass().getResource("/xml/destinations-inline.xml").toURI()).toFile().getAbsolutePath();
        String taxonomyPath = Paths.get(getClass().getResource("/xml/taxonomy-inline.xml").toURI()).toFile().getAbsolutePath();

        params.setDestinationsFile(destinationsPath);
        params.setTaxonomyFile(taxonomyPath);

        processor.run(params);

        File[] files = tempFolder.getRoot().listFiles();
        Assert.assertNotNull(files);
        Assert.assertTrue(files.length > 0);
    }


}
