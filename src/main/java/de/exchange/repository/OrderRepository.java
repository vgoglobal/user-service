package de.exchange.repository;

import de.exchange.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findByToCurrency(String code);
    List<OrderEntity> findByUserId(String userId);
}
