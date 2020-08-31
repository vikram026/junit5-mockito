package com.ecom.repository;

import com.ecom.model.Item;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SAVING DATA TO DB
 */
@Repository
public interface ItemRepository  extends JpaRepository<Item,Integer> {
}
