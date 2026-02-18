package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCampaignRequest {
    private String name;
    private Long templateId;
    private List<Long> contactIds;
    private String scheduledTime;
}

