package com.ecom.repository;

import com.ecom.model.OrderedItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SAVING DATA TO DB
 */
@Repository
public interface OrderedItemsRepository extends JpaRepository<OrderedItems,Integer> {

}
