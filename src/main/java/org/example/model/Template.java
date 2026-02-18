package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subject;
    @Lob
    private String body;

    @ManyToOne
    private Tenant tenant;
}
