package org.example.service;

import com.github.mustachejavafork.Mustache;
import com.github.mustachejavafork.MustacheFactory;
import com.github.mustachejavafork.DefaultMustacheFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Service
public class TemplateRenderingService {

    private final MustacheFactory mf = new DefaultMustacheFactory();

    public String renderTemplate(String templateBody, Map<String, Object> variables) throws IOException {
        Mustache mustache = mf.compile(new StringReader(templateBody), "template");
        StringWriter writer = new StringWriter();
        mustache.execute(writer, variables).flush();
        return writer.toString();
    }
}

