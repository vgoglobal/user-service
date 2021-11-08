package de.hey_car.repository;

import de.hey_car.repository.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {
    List<OrderEntity> findByFromCurrency(String code);
}
