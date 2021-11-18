package de.hey_car.repository;

import de.hey_car.entity.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaRepository<RecipientEntity, String> {
    RecipientEntity findByUserId(String userId);
}
