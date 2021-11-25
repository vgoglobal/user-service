package de.exchange.repository;

import de.exchange.entity.ComplaintEntity;
import de.exchange.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<ComplaintEntity, String> {

}
