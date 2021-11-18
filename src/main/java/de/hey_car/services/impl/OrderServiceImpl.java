package de.hey_car.services.impl;

import de.hey_car.dto.Order;
import de.hey_car.dto.Transfer;
import de.hey_car.dto.TransferStatusType;
import de.hey_car.repository.OrderRepository;
import de.hey_car.repository.MinerRepository;
import de.hey_car.repository.OrderStatusRepository;
import de.hey_car.repository.entity.OrderDetailsEntity;
import de.hey_car.repository.entity.OrderEntity;
import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.repository.entity.OrderStatusEntity;
import de.hey_car.services.OrderService;
import de.hey_car.services.StorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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
    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Override
    public Transfer createExchangeOrder(Order order) {
        // TODO remove miners whose balance is less the transfer amount
        List<MinerEntity> minerDetails = minerRepository.findMiners(order.getFromCurrency(), order.getAmount());
        MinerEntity miner = selectMiner(minerDetails);
        OrderEntity orderEntity = orderRepository.save(inbound(order, miner));
        return outbound(selectMiner(minerDetails), orderEntity);
    }

    private MinerEntity selectMiner(List<MinerEntity> minerDetails) {
        // TODO need to use selection of miner using some logic
        Random randomizer = new Random();
        return minerDetails.get(randomizer.nextInt(minerDetails.size()));
    }

    @Override
    public List<OrderEntity> getOrdersByCurrency(String currency) {
        return orderRepository.findByToCurrency(currency);
    }

    @Override
    public void pickOrder(String userId, String orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        if (orderEntity.isPresent()) {
            OrderEntity newOrderEntity = orderEntity.get();
            OrderDetailsEntity orderDetailsEntity = newOrderEntity.getOrderDetailsEntity();
            orderDetailsEntity.setOffshoreMinerId(minerRepository.findByUserId(userId));
            newOrderEntity.setOrderDetailsEntity(orderDetailsEntity);
            newOrderEntity.setPickedBy(userId);
            newOrderEntity.setStatus(TransferStatusType.IN_PROGRESS);
            orderRepository.save(newOrderEntity);
        }
    }

    @Override
    public void updateOrder(String userId, String id, MultipartFile file, String statusBy) throws Exception {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            OrderEntity newOrderEntity = orderEntity.get();
            OrderStatusEntity orderStatusEntity = OrderStatusEntity.builder().orderId(newOrderEntity.getId())
                    .status(statusBy).updatedBy(userId).build();
            if (file != null) {
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                String filename = userId+"-"+newOrderEntity.getId();
                storageService.store(file, filename);
                orderStatusEntity.setRefFile(filename+"."+ext);
            }

            newOrderEntity.setStatus(TransferStatusType.RECEIVED);
            orderStatusRepository.save(orderStatusEntity);
            orderRepository.save(newOrderEntity);
        }
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderEntity> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    private OrderEntity inbound(Order order, MinerEntity minerEntity) {
        return OrderEntity.builder().amount(order.getAmount())
                //.recipientId(order.getRecipientId())
                .fromCurrency(order.getFromCurrency())
                .toCurrency(order.getToCurrency()).recipientAmount(order.getRecipientAmount())
                .refId(generateRefId()).status(TransferStatusType.CREATED)
                .orderDetailsEntity(OrderDetailsEntity.builder().onshoreMinerId(minerEntity).build())
                .userId(order.getUserId())
                .transferFee(order.getTransferFee()).build();
    }

    private Transfer outbound(MinerEntity minerEntity, OrderEntity exchangeOrder) {

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
