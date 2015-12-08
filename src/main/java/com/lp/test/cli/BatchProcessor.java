package com.lp.test.cli;

import com.lp.test.generator.Generator;
import com.lp.test.generator.impl.HtmlGenerator;
import com.lp.test.model.Destination;
import com.lp.test.model.Node;
import com.lp.test.parser.Parser;
import com.lp.test.parser.exception.ParseException;
import com.lp.test.parser.impl.DestinationStaxStreamParser;
import com.lp.test.parser.impl.TaxonomyStaxStreamParser;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The {@link BatchProcessor} performs the steps involved in the final generation:
 * <ul>
 * <li>Creates and initializes the output folder by copying the CSS templates</li>
 * <li>Parses the provided Taxonomy and Destinations XMLs</li>
 * <li>Generates the destinations' HTML files</li>
 * </ul>
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class BatchProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BatchProcessor.class);

    public void run(Parameters parameters) {
        if (parameters == null) {
            LOG.error("Cannot process files: no parameters provided");
            System.exit(1);
        }

        String taxonomyFile = parameters.getTaxonomyFile();
        String destinationsFile = parameters.getDestinationsFile();
        LOG.info("Running using Taxonomy file: {}, Destinations file: {}", taxonomyFile, destinationsFile);

        // initialize output folder
        String outputFolder = parameters.getOutputFolder();
        initOutputFolder(outputFolder);

        // parse xml files
        Map<Integer, Node> nodes = parseNodes(taxonomyFile);
        Map<Integer, Destination> destinations = parseDestinations(destinationsFile);
        int size = destinations == null ? 0 : destinations.size();
        LOG.info("Done parsing. Found {} destinations.", size);

        // generation
        generate(nodes, destinations, outputFolder);

    }

    /**
     * Parse {@link Node} elements from the Taxonomy XML file
     *
     * @param taxonomyFile the taxonomy file to parse
     * @return the parsed nodes
     */
    private Map<Integer, Node> parseNodes(String taxonomyFile) {
        LOG.info("Parsing Taxonomy file: {}", taxonomyFile);
        Map<Integer, Node> nodes = null;
        try {
            InputStream isTaxonomy = new FileInputStream(Paths.get(taxonomyFile).toFile());
            Parser<Node> taxonomyParser = new TaxonomyStaxStreamParser();
            nodes = taxonomyParser.parse(isTaxonomy);
        } catch (FileNotFoundException | ParseException e) {
            LOG.error("Error parsing Taxonomy file: {}", taxonomyFile, e);
            System.exit(1);
        }
        return nodes;
    }

    /**
     * Parse {@link Destination} elements from the Destinations XML file
     *
     * @param destinationsFile the destinations file to parse
     * @return the parsed destinations
     */
    private Map<Integer, Destination> parseDestinations(String destinationsFile) {
        LOG.info("Parsing Destinations: {}", destinationsFile);
        Map<Integer, Destination> destinations = null;
        try {
            InputStream isDestination = new FileInputStream(Paths.get(destinationsFile).toFile());
            Parser<Destination> destinationParser = new DestinationStaxStreamParser();
            destinations = destinationParser.parse(isDestination);
        } catch (FileNotFoundException | ParseException e) {
            LOG.error("Error parsing Destinations file: {}", destinationsFile, e);
            System.exit(1);
        }
        return destinations;
    }

    /**
     * Invoke the generation for each element discovered during parsing.
     * The generation involves correlating
     * a {@link Destination} to the corresponding taxonomy {@link Node}. Such correlation is performed by mapping a
     * destination's {@code atlas_id} to a matching taxonomy node {@code atlas_node_id}.
     *
     * @param nodes        the taxonomy nodes lookup table
     * @param destinations the destinations lookup table
     * @param outputFolder the output folder that will contain the generated files
     */
    private void generate(Map<Integer, Node> nodes, Map<Integer, Destination> destinations, String outputFolder) {
        LOG.info("Generating files to output directory: {}", outputFolder);
        Generator generator = null;
        try {
            generator = new HtmlGenerator(outputFolder);
        } catch (Exception e) {
            LOG.error("Failed to initialize generator engine", e);
            System.exit(1);
        }
        for (Integer id : destinations.keySet()) {
            if (nodes.containsKey(id)) {
                Node node = nodes.get(id);
                Destination destination = destinations.get(id);
                generator.generate(node, destination);
            } else {
                // a destination refers to an atlas_id not available in the taxonomy
                LOG.warn("Cannot find Taxonomy node hierarchy for Destination ID: {}.", id);
            }
        }
        LOG.info("Generation completed.");
    }

    /**
     * Create the output folder (if not already existent) and initializes its content with static resources, such as CSS file.
     * If the folder cannot be created or the static files cannot be copied, the application will terminate.
     *
     * @param outputFolder the folder path to initialize
     */
    private void initOutputFolder(String outputFolder) {
        try {
            // create the target folder structure and copy the static css
            File outputFile = Paths.get(outputFolder).toFile();
            // create the directory tree
            outputFile.mkdirs();
            if (outputFile.exists()) {
                LOG.info("Created output folder: {}", outputFolder);
                URL url = LpTestCli.class.getResource("/templates/html/static/all.css");
                Path source = Paths.get(url.toURI());
                Path target = Paths.get(outputFolder + "/static/all.css");
                FileUtils.copyFile(source.toFile(), target.toFile());
                LOG.info("Copied static resources to output folder");
            } else {
                LOG.error("Cannot initialize output folder: {}", outputFolder);
                System.exit(1);
            }
        } catch (Exception e) {
            LOG.error("Cannot initialize output folder: {}", outputFolder, e);
            System.exit(1);
        }
    }
}
