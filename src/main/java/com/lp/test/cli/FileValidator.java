package com.lp.test.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
            try {
                Path path = Paths.get(value);
                File file = path.toFile();
                if (!file.exists() || !file.isFile()) {
                    throw new ParameterException("Parameter " + name + " must be an existing file.");
                }
            } catch (Exception e) {
                throw new ParameterException("Parameter " + name + " must be an existing file.");
            }
        }
    }
}