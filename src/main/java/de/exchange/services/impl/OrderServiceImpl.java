package de.exchange.services.impl;

import de.exchange.dto.Order;
import de.exchange.dto.OrderUpdate;
import de.exchange.dto.Transfer;
import de.exchange.dto.TransferStatusType;
import de.exchange.entity.*;
import de.exchange.repository.*;
import de.exchange.services.OrderService;
import de.exchange.services.StorageService;
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
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    MinerRepository minerRepository;
    @Autowired
    StorageService storageService;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    RecipientRepository recipientRepository;

    @Override
    public Transfer createExchangeOrder(Order order) {
        // TODO remove miners whose balance is less the transfer amount
        List<MinerEntity> minerDetails = minerRepository.findMiners(order.getFromCurrency(), order.getAmount());
        MinerEntity miner = selectMiner(minerDetails);
        OrderEntity orderEntity = orderRepository.save(inbound(order, miner));
        OrderDetailsEntity orderDetailsEntity = orderDetailsRepository.save(OrderDetailsEntity.builder().sourceMinerId(miner).orderId(orderEntity.getId()).status(TransferStatusType.CREATED).build());
        orderEntity.setOrderDetailsEntity(orderDetailsEntity);
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
            orderDetailsEntity.setTargetMinerId(minerRepository.findByUserId(userId));
            newOrderEntity.setOrderDetailsEntity(orderDetailsEntity);
            newOrderEntity.setStatus(TransferStatusType.IN_PROGRESS);
            orderRepository.save(newOrderEntity);
        }
    }

    @Override
    public void updateOrder(OrderUpdate orderUpdate, MultipartFile file) throws Exception {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderUpdate.getOrderId());
        if (orderEntity.isPresent()) {
            OrderEntity newOrderEntity = orderEntity.get();
            OrderDetailsEntity orderDetailsEntity = newOrderEntity.getOrderDetailsEntity();
            orderDetailsEntity.setTargetMinerId(minerRepository.findByUserId(orderUpdate.getUserId()));
            orderDetailsEntity.setSourceMinerId(minerRepository.findByUserId(orderUpdate.getUserId()));
            orderDetailsEntity.setRemarks(orderUpdate.getRemarks());
            if (file != null) {
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                String filename = orderUpdate.getUserId() + "-" + newOrderEntity.getId();
                storageService.store(file, filename);
                orderDetailsEntity.setRefFile(filename + "." + ext);
            }
            updateOrderStatusDate(orderDetailsEntity, orderUpdate.getStatus());
            newOrderEntity.setOrderDetailsEntity(orderDetailsEntity);
            newOrderEntity.setStatus(orderUpdate.getStatus());
            orderRepository.save(newOrderEntity);
        }
    }

    private void updateOrderStatusDate(OrderDetailsEntity orderDetailsEntity, TransferStatusType statusType) {
        switch (statusType) {
            case PICKED:
                orderDetailsEntity.setPickDate(Instant.now());
            case IN_PROGRESS:
                orderDetailsEntity.setUpdateDate(Instant.now());
            case DELIVERED:
                orderDetailsEntity.setDeliveryDate(Instant.now());
            case CANCELLED:
                orderDetailsEntity.setCancelledDate(Instant.now());
        }
        if (TransferStatusType.PICKED.equals(statusType)) {
            orderDetailsEntity.setPickDate(Instant.now());
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
                .recipientId(recipientRepository.findById(order.getRecipientId()).get())
                .fromCurrency(order.getFromCurrency())
                .toCurrency(order.getToCurrency()).recipientAmount(order.getRecipientAmount())
                .refId(generateRefId()).status(TransferStatusType.CREATED)
                .userId(order.getUserId())
                .transferFee(order.getTransferFee()).build();
    }

    private Transfer outbound(MinerEntity minerEntity, OrderEntity exchangeOrder) {
        Optional<WalletEntity> walletEntity = walletRepository.findByUserId(minerEntity.getUserId());
        if (walletEntity.isPresent()) {
            WalletEntity newWalletEntity = walletEntity.get();
            List<WalletDetailsEntity> walletDetailsList = newWalletEntity.getWalletDetailsEntity();
            Optional<WalletDetailsEntity> walletDetailsEntity = newWalletEntity.getWalletDetailsEntity().stream().filter(p -> exchangeOrder.getFromCurrency().equals(p.getBaseCurrency())).findAny();
            if (walletDetailsEntity.isPresent()) {
                WalletDetailsEntity newWalletDetailsEntity = walletDetailsEntity.get();
                newWalletDetailsEntity.setHoldAmount(exchangeOrder.getAmount());
                walletDetailsList.add(newWalletDetailsEntity);
                newWalletEntity.setWalletDetailsEntity(walletDetailsList);
                walletRepository.save(newWalletEntity);
            }
        }
        return Transfer.builder().orderId(exchangeOrder.getId()).resourceType(minerEntity.getResourceType()).refId(exchangeOrder.getRefId())
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
