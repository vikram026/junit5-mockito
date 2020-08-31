package com.ecom.service;

import com.ecom.model.Orders;

import java.util.List;

public interface OrderService {

    String processOrder(Orders orders);

    boolean isOutOfStock();

    List<Orders> getAll();

    List<Orders> findOrderDetailsByEmailId(String emailId);
}
