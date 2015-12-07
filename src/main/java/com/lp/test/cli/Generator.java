package com.lp.test.cli;

import com.lp.test.cli.util.Util;
import com.lp.test.model.Destination;
import com.lp.test.model.Node;
import com.lp.test.parser.Parser;
import com.lp.test.parser.impl.DestinationStaxStreamParser;
import com.lp.test.parser.impl.TaxonomyStaxStreamParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The Generator performs the steps involved in the final generation:
 * <ul>
 * <li>Creates and initializes the output folder by copying the CSS templates</li>
 * <li>Parse the provided Taxonomy and Destinations XMLs</li>
 * <li>Generates the destinations' HTML files</li>
 * </ul>
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class Generator {

    public void generate(Parameters parameters) {
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
        generateHtmlFiles(nodes, destinations, outputFolder);

        Util.printf("Generation completed successfully.");

    }


    private Map<Integer, Node> parseNodes(String taxonomyFile) {
        Map<Integer, Node> nodes = null;
        try {
            InputStream isTaxonomy = new FileInputStream(Paths.get(taxonomyFile).toFile());
            Parser<Node> taxonomyParser = new TaxonomyStaxStreamParser();
            nodes = taxonomyParser.parse(isTaxonomy);
        } catch (FileNotFoundException | XMLStreamException e) {
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
        } catch (FileNotFoundException | XMLStreamException e) {
            Util.printf("Error parsing Destinations file: %s", e.getMessage());
            System.exit(0);
        }
        return destinations;
    }

    private void generateHtmlFiles(Map<Integer, Node> nodes, Map<Integer, Destination> destinations, String outputFolder) {
        Util.printf("Generating HTML files...");
        VelocityEngine engine = getTemplateEngine();
        // retrieve the template
        Template template = engine.getTemplate("/templates/html/template.html.vm");
        for (Integer id : destinations.keySet()) {
            if (nodes.containsKey(id)) {
                Node node = nodes.get(id);
                Destination destination = destinations.get(id);

                VelocityContext context = getTemplateContext(outputFolder, node, destination);

                String normalize = FilenameUtils.normalize(node.getName());
                String fileName = outputFolder + normalize + ".html";
                try {
                    FileUtils.touch(new File(fileName));
                    FileWriter writer = new FileWriter(fileName);
                    template.merge(context, writer);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // a destination refers to an atlas_id not available in the taxonomy
                Util.printf("Cannot find node hierarchy for ID: %s", id);
            }

        }
    }

    private VelocityContext getTemplateContext(String outputFolder, Node node, Destination destination) {
        VelocityContext context = new VelocityContext();
        context.put("output", outputFolder);
        context.put("node", node);
        context.put("destination", destination);
        context.put("FilenameUtils", FilenameUtils.class);
        return context;
    }

    private VelocityEngine getTemplateEngine() {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
        return engine;
    }

    private void initOutputFolder(String outputFolder) {
        // create the target folder structure and copy the css
        Paths.get(outputFolder).toFile().mkdirs();
        try {
            URL url = LpTestCli.class.getResource("/templates/html/static/all.css");
            Path source = Paths.get(url.toURI());
            Path target = Paths.get(outputFolder + "/static/all.css");
            FileUtils.copyFile(source.toFile(), target.toFile());
        } catch (IOException | URISyntaxException e) {
            Util.printf("Cannot initialize output folder: %s", e.getMessage());
            System.exit(0);
        }
    }
}
