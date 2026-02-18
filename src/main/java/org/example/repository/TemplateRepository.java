package org.example.repository;

import org.example.model.Campaign;
import org.example.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
