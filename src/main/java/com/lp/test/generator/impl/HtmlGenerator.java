package com.lp.test.generator.impl;

import com.lp.test.generator.Generator;
import com.lp.test.model.Destination;
import com.lp.test.model.Node;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class HtmlGenerator implements Generator {

    private final String outputFolder;

    private Template template;

    public HtmlGenerator(String outputFolder) {
        this.outputFolder = outputFolder;
        initEngine();
    }

    private void initEngine() {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
        // retrieve the template
        this.template = engine.getTemplate("/templates/html/template.html.vm");
    }

    @Override
    public void generate(Node node, Destination destination) {

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
    }

    private VelocityContext getTemplateContext(String outputFolder, Node node, Destination destination) {
        VelocityContext context = new VelocityContext();
        context.put("output", outputFolder);
        context.put("node", node);
        context.put("destination", destination);
        context.put("FilenameUtils", FilenameUtils.class);
        return context;
    }

}
