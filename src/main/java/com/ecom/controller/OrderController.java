package com.ecom.controller;

import com.ecom.model.Orders;
import com.ecom.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ORDER RELATED CRUD OPERATION AND ORDER PROCESS CONTROLLER
 */
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> orderItems(@RequestBody Orders orders){

        log.info("Into save method of Order");

        String response= orderService.processOrder(orders);

        log.info("response after process {} "+response);

        return !orderService.isOutOfStock() ?
         new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders(){

        log.info("into Order getAllItem method");
        List<Orders> orders= orderService.getAll();

        return orders!=null && !orders.isEmpty() ? new ResponseEntity<>(orders, HttpStatus.OK)
                :new ResponseEntity<>(orders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<List<Orders>> getOrderDetailsByCustomerEmailId(@PathVariable String emailId ){

        log.info("into find all order by emailid mehtod ");
        List<Orders> orders= orderService.findOrderDetailsByEmailId(emailId);
        log.info("orders details after the call"+orders);

        return orders!=null && !orders.isEmpty() ? new ResponseEntity<>(orders, HttpStatus.OK)
                :new ResponseEntity<>(orders, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
