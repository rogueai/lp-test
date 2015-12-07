package com.lp.test.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.lp.test.cli.util.Util;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class LpTestCli {

    public static void main(String[] args) {

        Parameters parameters = parseArgs(args);

        Generator generator = new Generator();
        try {
            generator.generate(parameters);
        } catch (Exception e) {
            Util.printf("Error generating output: %s", e.getMessage());
        }
    }

    private static Parameters parseArgs(String[] args) {
        Parameters parameters = new Parameters();
        JCommander commander = new JCommander(parameters);
        commander.setProgramName("java -jar lp-test.jar");
        commander.setCaseSensitiveOptions(true);
        try {
            commander.parse(args);

            if (parameters.isHelp()) {
                commander.usage();
                System.exit(0);
            }
        } catch (ParameterException ex) {
            Util.printf("%s", ex.getMessage());
            commander.usage();
            System.exit(0);
        }
        return parameters;
    }
}
