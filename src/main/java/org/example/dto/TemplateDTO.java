package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDTO {
    private Long id;
    private String name;
    private String subject;
    private String body;
    private Integer version;
}

