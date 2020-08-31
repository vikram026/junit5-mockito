package com.ecom.controller;

import com.ecom.config.TestConfig;
import com.ecom.model.Item;
import com.ecom.repository.ItemRepository;
import com.ecom.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ItemController.class, ItemServiceImpl.class})
@ContextConfiguration(classes = TestConfig.class)
public class ItemControllerComponentTest {

    @Autowired
    private ItemController itemController;

    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("This method will test the save method  it will mock db ")
    @ParameterizedTest
    @MethodSource("getItemSource")
    public void saveTest(Item item){
        when(itemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(item);
        ResponseEntity<Item> responseEntity =  itemController.save(item);
        assertAll( "savetest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED,()->"status should be 201")
                ,()->assertEquals(responseEntity.getBody().getId(),1,()->"id should be mocked id only")
        );
    }

    @DisplayName("This method will test the save method  it will mock db ")
    @ParameterizedTest
    @MethodSource("getItemSource")
    public void saveNegativeTest(Item item){
        when(itemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(null);
        ResponseEntity<Item> responseEntity =  itemController.save(item);
        assertAll( "savetestnegative",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR,()->"status should be 500")
                ,()->assertNull(responseEntity.getBody(),()->"body is null")
        );
    }

    @DisplayName("This method will test the get method   it will mock db ")
    @ParameterizedTest
    @ValueSource(ints={1})
    public void getByIdTest(int id){
        Item response=mockItem();
        when(itemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(response));
        ResponseEntity<Optional<Item>> responseEntity = itemController.getById(id);
        assertAll( "gettest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.OK,()->"status should be 200")
                ,()->assertNotNull(responseEntity.getBody(),()->"body is not null")
        );
    }


    @DisplayName("This method will test the get method for negative scenario it will mock db ")
    @ParameterizedTest
    @ValueSource(ints={1})
    public void getByIdTestNegative(int id){
        Item response=mockItem();
        when(itemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.ofNullable(null));
        ResponseEntity<Optional<Item>> responseEntity = itemController.getById(id);
        assertAll( "gettestnegative",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND,()->"status should be NotFound")
                ,()->assertEquals(responseEntity.getBody(),Optional.empty(),()->"body is  null")
        );
    }


    @DisplayName("This method will test the getAll method   it will mock db ")
    @Test
    public void getAllTest(){
        List<Item> response=Arrays.asList(mockItem());
        when(itemRepository.findAll()).thenReturn(response);
        ResponseEntity<List<Item>> responseEntity = itemController.getAll();
        assertAll( "getAllTest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.OK,()->"status should be 200")
                ,()->assertEquals(responseEntity.getBody(),response,()->"body is  List")
        );
    }


    @DisplayName("This method will test the getAll method for Negeate test it will mock db ")
    @Test
    public void getAllTestNegative(){
        List<Item> response=Arrays.asList(mockItem());
        when(itemRepository.findAll()).thenReturn(null);
        ResponseEntity<List<Item>> responseEntity = itemController.getAll();
        assertAll( "getAllTest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND,()->"status should be Not found")
                ,()->assertNull(responseEntity.getBody(),()->"body is  null")
        );
    }

    @DisplayName("This method will test the deletebyId method  it will mock db ")
    @ParameterizedTest
    @ValueSource(ints={1})
    public void deleteByIdTest(int id){
        Item response=mockItem();
        doNothing().when(itemRepository).deleteById(ArgumentMatchers.anyInt());
        ResponseEntity<String> responseEntity = itemController.deleteById(id);
        assertAll( "deleteByIdTest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.OK,()->"status should be OK")
                ,()->assertEquals(responseEntity.getBody(),"Item with id "+1+" is deleted",()->"body is  null")

        );
    }



    private static Stream<Arguments> getItemSource(){
        return Stream.of(Arguments.of(mockItem()));
    }

    public static Item mockItem() {
        Item item=new Item();
        item.setQty(1);
        item.setId(1);
        item.setName("test");
        item.setPrice(1);
        return item;
    }


}
