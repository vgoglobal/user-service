package de.exchange.repository;

import de.exchange.entity.LoyaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyRepository extends JpaRepository<LoyaltyEntity, String> {

}
