package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {
    private Long id;
    private String name;
    private String status;
    private Long templateId;
    private String scheduledTime;
    private Integer totalRecipients;
    private Integer sentCount;
    private Integer failedCount;
}
