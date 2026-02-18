package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}