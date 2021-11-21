package de.exchange.repository;

import de.exchange.entity.OrderDetailsEntity;
import de.exchange.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, String> {

}
