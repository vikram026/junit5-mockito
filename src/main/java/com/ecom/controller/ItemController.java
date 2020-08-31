package com.ecom.controller;

import com.ecom.model.Item;
import com.ecom.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Item related CRUD OPERATION
 */
@RestController
@RequestMapping("item")
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> save(@RequestBody Item item){
        log.info("into controller save method");
        Item savedItem= itemService.save(item);
        if(savedItem!=null)
            return new ResponseEntity<>(savedItem,HttpStatus.CREATED);
        else
            return new ResponseEntity<>(savedItem,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Item>> getById(@PathVariable int id){
        log.info("into controller getById method");
        Optional<Item> items= itemService.findById(id);
        if(items.isPresent())
            return new ResponseEntity<>(items, HttpStatus.OK);
        else
            return new ResponseEntity<>(items, HttpStatus.NOT_FOUND);

    }
    @GetMapping
    public ResponseEntity<List<Item>> getAll(){

        log.info("into getAll method " );
        List<Item> items= itemService.findAll();
        if(items!=null && !items.isEmpty())
            return new ResponseEntity<>(items, HttpStatus.OK);
        else
            return new ResponseEntity<>(items, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam int id){
        log.info("into controllers  Delete method for item");
        String response= itemService.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
