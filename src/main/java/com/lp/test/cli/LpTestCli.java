package com.lp.test.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Command Line Interface functioning as an entry point for the application.
 * <ul>
 * <li>Options marked with "{@literal *}" are required.</li>
 * <li>The CLI will accept both full ({@code --help}) and abbreviated ({@code -h}) options.</li>
 * <li>Options are case sensitive</li>
 * </ul>
 * Example of usage:
 * <pre>
 * Usage: java -jar lp-test.jar [options]
 *   Options:
 *   {@literal *} --destinationsFile, -d
 *        Destinations input file path
 *     --help, -h
 *
 *        Default: false
 *   {@literal *} --outputFolder, -o
 *        Output folder path
 *   {@literal *} --taxonomyFile, -t
 *        Taxonomy input file path
 * </pre>
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class LpTestCli {

    private static final Logger LOG = LoggerFactory.getLogger(LpTestCli.class);

    private static final String PROGRAM_NAME = "java -jar lp-test.jar";

    public static void main(String[] args) {
        Parameters parameters = parseArgs(args);

        BatchProcessor processor = new BatchProcessor();
        try {
            processor.run(parameters);
        } catch (Exception e) {
            LOG.error("Error processing output", e);
        }
    }

    protected static Parameters parseArgs(String[] args) {
        Parameters parameters = new Parameters();
        JCommander commander = new JCommander(parameters);
        commander.setProgramName(PROGRAM_NAME);
        commander.setCaseSensitiveOptions(true);
        try {
            commander.parse(args);

            if (parameters.isHelp()) {
                commander.usage();
                System.exit(0);
            }
        } catch (ParameterException e) {
            LOG.error(e.getMessage());
            commander.usage();
            System.exit(1);
        }
        return parameters;
    }
}
