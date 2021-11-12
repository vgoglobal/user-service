package de.hey_car.controllers;

import de.hey_car.dto.Order;
import de.hey_car.dto.Transfer;
import de.hey_car.repository.entity.OrderEntity;
import de.hey_car.services.OrderService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * Method to
     *
     * @return
     */
    @PostMapping(value = "/exchange/create")
    public ResponseEntity<Transfer> createExchangeOrder(@RequestBody @Valid Order order) {
        LOGGER.info("Processing createExchangeOrder ");
        return ResponseEntity.ok().body(orderService.createExchangeOrder(order));
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/{currency}")
    public ResponseEntity<List<OrderEntity>> getOrders(@PathVariable String currency) {
        LOGGER.info("Processing getOrders ");
        return ResponseEntity.ok().body(orderService.getOrdersByCurrency(currency));
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        LOGGER.info("Processing getOrders ");
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    /**
     * Method to
     */
    @PostMapping(value = "/pick/{userId}/{id}")
    public ResponseEntity<String> pickOrder(@PathVariable String userId, @PathVariable String id) {
        LOGGER.info("Processing pickOrder ");
        orderService.pickOrder(userId, id);
        return ResponseEntity.ok().body("Orders picked");
    }

    /**
     * Method to
     */
    @PostMapping(value = "/update/{userId}/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String userId, @PathVariable String orderId) throws Exception {
        LOGGER.info("Processing confirmOrder ");
        orderService.updateOrder(userId, orderId, null);
        return ResponseEntity.ok().body("Orders picked");
    }

    /**
     * Method to
     */
    @PostMapping(value = "/confirm/{userId}/{orderId}")
    public ResponseEntity<String> confirmOrder(@PathVariable String userId, @PathVariable String orderId, @RequestParam("file") MultipartFile file) throws Exception {
        LOGGER.info("Processing updateOrderStatus ");
        orderService.updateOrder(userId, orderId, file);
        return ResponseEntity.ok().body("Orders status updated");
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<List<OrderEntity>> getOrdersByUserId(@PathVariable String userId) {
        LOGGER.info("Processing getOrdersByUserId ");
        return ResponseEntity.ok().body(orderService.getOrdersByUser(userId));
    }
}
