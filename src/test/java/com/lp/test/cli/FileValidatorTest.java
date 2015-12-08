package com.lp.test.cli;

import com.beust.jcommander.ParameterException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class FileValidatorTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void test_validate_null() throws Exception {
        FileValidator validator = new FileValidator();
        try {
            validator.validate(null, null);
        } catch (ParameterException e) {
            Assert.fail("Unexpected ParameterException thrown");
        }
    }

    @Test
    public void test_validate_invalidFile() throws Exception {
        FileValidator validator = new FileValidator();
        File file = tempFolder.newFile();
        String path = file.getAbsolutePath();
        // remove the temp folder so that w ensure the file will be unexistent
        tempFolder.delete();
        exception.expect(ParameterException.class);
        validator.validate("fileName", path);
    }

    @Test
    public void test_validate_validFile() throws Exception {
        FileValidator validator = new FileValidator();
        File file = tempFolder.newFile();
        String path = file.getAbsolutePath();
        try {
            validator.validate("fileName", path);
        } catch (ParameterException e) {
            Assert.fail("Unexpected ParameterException thrown");
        }
    }

}
