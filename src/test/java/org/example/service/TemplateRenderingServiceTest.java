package org.example.service;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemplateRenderingServiceTest {

    private TemplateRenderingService templateRenderingService = new TemplateRenderingService();

    @Test
    void testRenderSimpleTemplate() throws IOException {
        String template = "Hello {{firstName}} {{lastName}}!";
        Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", "John");
        variables.put("lastName", "Doe");

        String result = templateRenderingService.renderTemplate(template, variables);

        assertEquals("Hello John Doe!", result);
    }

    @Test
    void testRenderTemplateWithConditionals() throws IOException {
        String template = "{{#showPrice}}Price: ${{price}}{{/showPrice}}";
        Map<String, Object> variables = new HashMap<>();
        variables.put("showPrice", true);
        variables.put("price", "99.99");

        String result = templateRenderingService.renderTemplate(template, variables);

        assertEquals("Price: $99.99", result);
    }

    @Test
    void testRenderComplexTemplate() throws IOException {
        String template = "Dear {{name}},\n\nWe have identified {{count}} opportunities for you.";
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "Alice");
        variables.put("count", 5);

        String result = templateRenderingService.renderTemplate(template, variables);

        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("5"));
    }
}

