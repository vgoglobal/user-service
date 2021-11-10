package de.hey_car.services.impl;

import de.hey_car.dto.Order;
import de.hey_car.dto.Transfer;
import de.hey_car.dto.TransferStatusType;
import de.hey_car.repository.OrderRepository;
import de.hey_car.repository.MinerRepository;
import de.hey_car.repository.entity.OrderEntity;
import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.services.OrderService;
import de.hey_car.services.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MinerRepository minerRepository;
    @Autowired
    StorageService storageService;

    @Override
    public Transfer createExchangeOrder(Order order) {
        // TODO remove miners whose balance is less the transfer amount
        List<MinerEntity> minerDetails = minerRepository.findByResourceCurrency(order.getFromCurrency());
        OrderEntity orderEntity = orderRepository.save(inbound(order));
        return outbound(minerDetails, orderEntity);
    }

    @Override
    public List<OrderEntity> getOrdersByCurrency(String currency) {
        List<OrderEntity> orders = orderRepository.findByFromCurrency(currency);
        return orders;
    }

    @Override
    public void pickOrder(String userId, String orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        if (orderEntity.isPresent()) {
            OrderEntity newOrderEntity = orderEntity.get();
            newOrderEntity.setPickedBy(userId);
            newOrderEntity.setStatus(TransferStatusType.IN_PROGRESS);
            orderRepository.save(newOrderEntity);
        }
    }

    @Override
    public void updateOrder(String userId, String id, MultipartFile file) throws Exception {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            OrderEntity newOrderEntity = orderEntity.get();
            newOrderEntity.setStatus(TransferStatusType.RECEIVED);
            orderRepository.save(newOrderEntity);
            if (file != null)
                storageService.store(file);
        }
    }

    private OrderEntity inbound(Order order) {
        return OrderEntity.builder().amount(order.getAmount())
                .recipientId(order.getRecipientId()).fromCurrency(order.getFromCurrency())
                .toCurrency(order.getToCurrency()).recipientAmount(order.getRecipientAmount())
                .refId(generateRefId()).status(TransferStatusType.CREATED)
                .userId(order.getUserId())
                .transferFee(order.getTransferFee()).build();
    }

    private Transfer outbound(List<MinerEntity> minerEntities, OrderEntity exchangeOrder) {
        MinerEntity minerEntity = minerEntities.get(0);
        return Transfer.builder().orderId(exchangeOrder.getId()).minerResourceType(minerEntity.getResourceType()).refId(exchangeOrder.getRefId())
                .resourceAddress(minerEntity.getResourceAddress()).resourceCode(minerEntity.getResourceCode())
                .resourceCurrency(minerEntity.getResourceCurrency()).resourceName(minerEntity.getResourceName())
                .resourceNumber(minerEntity.getResourceNumber()).transferAmount(exchangeOrder.getAmount()).build();
    }

    private String generateRefId() {
        Random rand = new Random();
        int number = rand.nextInt(99999999);
        return Instant.now().toEpochMilli() + "" + number;
    }
}
