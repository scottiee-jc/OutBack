package org.example.repository;

import org.example.model.Campaign;
import org.example.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
