package com.lp.test.cli;

import com.lp.test.cli.util.Util;
import com.lp.test.generator.Generator;
import com.lp.test.generator.impl.HtmlGenerator;
import com.lp.test.model.Destination;
import com.lp.test.model.Node;
import com.lp.test.parser.Parser;
import com.lp.test.parser.exception.ParseException;
import com.lp.test.parser.impl.DestinationStaxStreamParser;
import com.lp.test.parser.impl.TaxonomyStaxStreamParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The {@link BatchProcessor} performs the steps involved in the final generation:
 * <ul>
 * <li>Creates and initializes the output folder by copying the CSS templates</li>
 * <li>Parse the provided Taxonomy and Destinations XMLs</li>
 * <li>Generates the destinations' HTML files</li>
 * </ul>
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class BatchProcessor {

    public void run(Parameters parameters) {
        String taxonomyFile = parameters.getTaxonomyFile();
        String destinationsFile = parameters.getDestinationsFile();
        Util.printf("Running using Taxonomy file: %s, Destinations file: %s", taxonomyFile, destinationsFile);

        // initialize output folder
        String outputFolder = parameters.getOutputFolder();
        initOutputFolder(outputFolder);
        Util.printf("Created output folder: %s", outputFolder);

        // parse xml files
        Util.printf("Parsing node hierarchy...");
        Map<Integer, Node> nodes = parseNodes(taxonomyFile);
        Util.printf("Parsing destinations...");
        Map<Integer, Destination> destinations = parseDestinations(destinationsFile);
        Util.printf("Found %d destinations.", destinations.size());

        // html generation
        generate(nodes, destinations, outputFolder);

        Util.printf("Generation completed successfully.");

    }

    private Map<Integer, Node> parseNodes(String taxonomyFile) {
        Map<Integer, Node> nodes = null;
        try {
            InputStream isTaxonomy = new FileInputStream(Paths.get(taxonomyFile).toFile());
            Parser<Node> taxonomyParser = new TaxonomyStaxStreamParser();
            nodes = taxonomyParser.parse(isTaxonomy);
        } catch (FileNotFoundException | ParseException e) {
            Util.printf("Error parsing Taxonomy file: %s", e.getMessage());
            System.exit(0);
        }
        return nodes;
    }

    private Map<Integer, Destination> parseDestinations(String destinationsFile) {
        Map<Integer, Destination> destinations = null;
        try {
            InputStream isDestination = new FileInputStream(Paths.get(destinationsFile).toFile());
            Parser<Destination> destinationParser = new DestinationStaxStreamParser();
            destinations = destinationParser.parse(isDestination);
        } catch (FileNotFoundException | ParseException e) {
            Util.printf("Error parsing Destinations file: %s", e.getMessage());
            System.exit(0);
        }
        return destinations;
    }

    private void generate(Map<Integer, Node> nodes, Map<Integer, Destination> destinations, String outputFolder) {

        Util.printf("Generating files...");
        Generator generator = new HtmlGenerator(outputFolder);

        for (Integer id : destinations.keySet()) {
            if (nodes.containsKey(id)) {
                Node node = nodes.get(id);
                Destination destination = destinations.get(id);
                generator.generate(node, destination);
            } else {
                // a destination refers to an atlas_id not available in the taxonomy
                Util.printf("Cannot find node hierarchy for ID: %s", id);
            }

        }
    }

    private void initOutputFolder(String outputFolder) {
        // create the target folder structure and copy the css
        try {
            File outputFile = Paths.get(outputFolder).toFile();
            outputFile.mkdirs();
            if (outputFile.exists()) {
                URL url = LpTestCli.class.getResource("/templates/html/static/all.css");
                Path source = Paths.get(url.toURI());
                Path target = Paths.get(outputFolder + "/static/all.css");
                FileUtils.copyFile(source.toFile(), target.toFile());
            } else {
                throw new IOException("Cannot create output folder.");
            }
        } catch (Exception e) {
            Util.printf("Cannot initialize output folder: %s", e.getMessage());
            System.exit(0);
        }
    }
}
