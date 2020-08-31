package com.ecom.service.impl;

import com.ecom.model.Item;
import com.ecom.repository.ItemRepository;
import com.ecom.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Item related service Basic CRUD OPERATION
 */
@Service
@Slf4j
public class ItemServiceImpl  implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> findById(int id) {
        return itemRepository.findById(id);
    }

    @Override
    public String deleteById(int id) {
        try{
            itemRepository.deleteById(id);
            return "Item with id "+id+" is deleted";
        }catch (Exception e){
            log.error("Item with id is not available");
            e.getMessage();
            return "Item with id is not available or db is down ";
        }
    }

    @Override
    public List<Item> findAll() {
        log.info("into findAll service ");
        return itemRepository.findAll();
    }
}
