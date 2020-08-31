package com.ecom.repository;

import com.ecom.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * saving order data to db
 */
@Repository
public interface OrderRepository  extends JpaRepository<Orders, Integer> {
    List<Orders> findByEmailId(String emailId);
}
