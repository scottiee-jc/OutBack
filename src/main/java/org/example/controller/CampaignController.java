package org.example.controller;

import org.example.dto.CampaignDTO;
import org.example.dto.CreateCampaignRequest;
import org.example.model.Campaign;
import org.example.model.CampaignRecipient;
import org.example.service.CampaignService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody CreateCampaignRequest request,
                                                   @RequestAttribute Long tenantId) {
        Campaign campaign = new Campaign();
        campaign.setName(request.getName());
        campaign.setScheduledTime(request.getScheduledTime() != null ?
            java.time.LocalDateTime.parse(request.getScheduledTime()) : null);

        Campaign created = campaignService.createCampaign(campaign, tenantId, request.getContactIds());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Campaign>> getCampaigns(
            @RequestAttribute Long tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Campaign> campaigns = campaignService.getCampaignsByTenant(tenantId, pageable);
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable Long id,
                                                @RequestAttribute Long tenantId) {
        Campaign campaign = campaignService.findById(id, tenantId);
        return ResponseEntity.ok(campaign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id,
                                                   @Valid @RequestBody Campaign updatedCampaign,
                                                   @RequestAttribute Long tenantId) {
        Campaign campaign = campaignService.updateCampaign(id, updatedCampaign, tenantId);
        return ResponseEntity.ok(campaign);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id,
                                               @RequestAttribute Long tenantId) {
        campaignService.deleteCampaign(id, tenantId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Campaign> startCampaign(@PathVariable Long id,
                                                  @RequestAttribute Long tenantId) {
        Campaign campaign = campaignService.startCampaign(id, tenantId);
        return ResponseEntity.ok(campaign);
    }

    @GetMapping("/{id}/recipients")
    public ResponseEntity<List<CampaignRecipient>> getCampaignRecipients(@PathVariable Long id,
                                                                         @RequestAttribute Long tenantId) {
        List<CampaignRecipient> recipients = campaignService.getCampaignRecipients(id, tenantId);
        return ResponseEntity.ok(recipients);
    }
}

