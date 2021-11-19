package de.exchange.services;

import de.exchange.dto.Order;
import de.exchange.dto.Transfer;
import de.exchange.entity.OrderEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderService {
    Transfer createExchangeOrder(Order order);
    List<OrderEntity> getOrdersByCurrency(String currency);
    void pickOrder(String userId, String orderId);
    void updateOrder(String userId, String orderId, MultipartFile file, String statusBy) throws Exception;
    List<OrderEntity> getAllOrders();
    List<OrderEntity> getOrdersByUser(String userId);
}
