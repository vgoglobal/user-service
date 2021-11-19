package de.exchange.repository;

import de.exchange.entity.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaRepository<RecipientEntity, String> {
    RecipientEntity findByUserId(String userId);
}
