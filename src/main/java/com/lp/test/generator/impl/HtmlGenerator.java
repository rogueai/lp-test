package com.lp.test.generator.impl;

import com.lp.test.generator.Generator;
import com.lp.test.generator.util.FilenameUtils;
import com.lp.test.model.Destination;
import com.lp.test.model.Node;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * {@link Generator} implementation that generates HTML files based on a template.
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class HtmlGenerator implements Generator {
    public static final String TEMPLATE_PATH = "/templates/html/template.html.vm";

    private static final Logger LOG = LoggerFactory.getLogger(HtmlGenerator.class);

    private final String outputFolder;
    private Template template;

    public HtmlGenerator(String outputFolder) {
        this.outputFolder = outputFolder;
        initEngine();
    }

    /**
     * Initializes the template engine
     */
    private void initEngine() {
        LOG.debug("Initializing template engine");
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
        // retrieve the template
        this.template = engine.getTemplate(TEMPLATE_PATH);
        LOG.debug("Using template: {}", TEMPLATE_PATH);
    }

    @Override
    public void generate(Node node, Destination destination) {
        if (StringUtils.isBlank(outputFolder)) {
            LOG.error("Cannot perform generation: missing output folder");
            return;
        }
        if (node == null || destination == null) {
            LOG.error("Cannot perform generation: Node and Destination are required");
            return;
        }
        // retrieve template context
        VelocityContext context = getTemplateContext(outputFolder, node, destination);

        String fileName = FilenameUtils.normalize(node.getName());
        if (StringUtils.isBlank(fileName)) {
            LOG.error("Cannot generate a valid file name from Node name: {}", node.getName());
            return;
        }
        fileName = fileName + ".html";
        try {
            File outputFile = new File(outputFolder, fileName);
            LOG.debug("Generating HTML file: {}", outputFile.getAbsolutePath());
            Writer writer = getWriter(outputFile);
            getTemplate().merge(context, writer);
            writer.close();
        } catch (IOException e) {
            LOG.error("Error generating HTML file: {}", fileName, e);
        }
    }

    private Template getTemplate() {
        return template;
    }

    Writer getWriter(File outputFile) throws IOException {
        return new FileWriter(outputFile);
    }

    /**
     * Creates a {@link VelocityContext}, holding variables to be reusable in the template using the Velocity
     * expression language.
     */
    private VelocityContext getTemplateContext(String outputFolder, Node node, Destination destination) {
        VelocityContext context = new VelocityContext();
        context.put("output", outputFolder);
        context.put("node", node);
        context.put("destination", destination);
        context.put("FilenameUtils", FilenameUtils.class);
        return context;
    }

}
