package de.hey_car.repository;

import de.hey_car.repository.entity.OrderEntity;
import de.hey_car.repository.entity.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, String> {

}
