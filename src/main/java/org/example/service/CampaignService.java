package org.example.service;

import org.example.model.Template;
import org.example.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {
    private final TemplateRepository templateRepository;

    public CampaignService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    public Template updateTemplate(Long id, Template updatedTemplate) {
        Template template = templateRepository.findById(id).orElseThrow(() -> new RuntimeException("Template not found"));
        template.setName(updatedTemplate.getName());
        template.setSubject(updatedTemplate.getSubject());
        template.setBody(updatedTemplate.getBody());
        return templateRepository.save(template);
    }
}
