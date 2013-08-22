package com.grexdev.pimabank.menuprovider.parser.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

import lombok.extern.slf4j.Slf4j;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

@Slf4j
public class VelocityXmlTemplator {

    private VelocityEngine velocityEngine;

    public VelocityXmlTemplator() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }

    public String createDocumentUsingTemplate(String templateName, Object groupRoot) {
        Template template = velocityEngine.getTemplate(templateName);
        VelocityContext context = new VelocityContext();
        context.put("root", groupRoot);

        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        log.info("Document generated from template = >>>{}<<<", stringWriter.toString());

        return stringWriter.toString();
    }

}
