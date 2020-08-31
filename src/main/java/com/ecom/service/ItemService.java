package com.ecom.service;

import com.ecom.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item save(Item item);

    Optional<Item> findById(int id);
    String deleteById(int id);

    List<Item> findAll();
}
