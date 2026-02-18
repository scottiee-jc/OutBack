package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime scheduledTime;

    @ManyToOne
    private Template template;

    @ManyToOne
    private Tenant tenant;
}
