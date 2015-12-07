package com.lp.test.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class Parameters {

    @Parameter(names = {"--taxonomyFile", "-t"}, description = "Taxonomy input file path", required = true, validateWith = FileValidator.class)
    private String taxonomyFile;
    @Parameter(names = {"--destinationsFile", "-d"}, description = "Destinations input file path", required = true, validateWith = FileValidator.class)
    private String destinationsFile;
    @Parameter(names = {"--outputFolder", "-o"}, description = "Output folder path", required = true)
    private String outputFolder;
    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help;

    public String getDestinationsFile() {
        return destinationsFile;
    }

    public void setDestinationsFile(String destinationsFile) {
        this.destinationsFile = destinationsFile;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getTaxonomyFile() {
        return taxonomyFile;
    }

    public void setTaxonomyFile(String taxonomyFile) {
        this.taxonomyFile = taxonomyFile;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

}
