package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignRecipientDTO {
    private Long id;
    private Long campaignId;
    private Long contactId;
    private String status;
    private String bounceReason;
}

