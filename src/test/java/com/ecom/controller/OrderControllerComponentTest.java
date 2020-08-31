package com.ecom.controller;


import com.ecom.config.TestConfig;
import com.ecom.model.Item;
import com.ecom.model.OrderedItems;
import com.ecom.model.Orders;
import com.ecom.repository.ItemRepository;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.OrderedItemsRepository;
import com.ecom.service.ItemService;
import com.ecom.service.impl.ItemServiceImpl;
import com.ecom.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderController.class, OrderServiceImpl.class, ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class OrderControllerComponentTest {

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderedItemsRepository orderedItemsRepository;

    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("This method will test the orderItems method  it will mock db ")
    @ParameterizedTest
    @MethodSource("getOrdersSource")
    public void orderItemsTest(Orders orders){

        String actualRespone="Order Placed...!  you have spent :"+1+" Rupees"
                +" Please visite the http://localhost:8991/order/"+orders.getEmailId();

        when(orderRepository.save(ArgumentMatchers.any(Orders.class))).thenReturn(orders);
        when(itemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(ItemControllerComponentTest.mockItem()));

        when(itemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(null);

        ResponseEntity<String> responseEntity =  orderController.orderItems(orders);
        assertAll( "orderItemsTest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.OK,()->"status should be 200")
                ,()->assertEquals(responseEntity.getBody(),actualRespone,()->" messages should match")
        );
    }

    @DisplayName("This method will test the orderItems method for Out of Stock it will mock db ")
    @ParameterizedTest
    @MethodSource("getOrdersOutOfStockSource")
    public void orderItemsOutOfStockTest(Orders orders){

        String outOfStockRespone="item "+"test"+" with id="+1+" is the first item we found out of stock for your purchase You can purchase only "+1+" "+"test";

        when(orderRepository.save(ArgumentMatchers.any(Orders.class))).thenReturn(orders);
        when(itemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(ItemControllerComponentTest.mockItem()));

        when(itemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(null);

        ResponseEntity<String> responseEntity =  orderController.orderItems(orders);
        assertAll( "orderItemsTest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE,()->"status should be 200")
                ,()->assertEquals(responseEntity.getBody(),outOfStockRespone,()->" messages should match")
        );
    }

    @DisplayName("This method will test the orderItems method for Out of Stock If Item is Not Available, it will mock db ")
    @ParameterizedTest
    @MethodSource("getOrdersOutOfStockAsItemNotFoundSource")
    public void orderItemsOutOfStockIfItemNotAvailableTest(Orders orders){

        String outOfStockRespone="Item with id "+orders.getOrderedItems().get(0).getItemId()+" not available please visit after some time.";
        when(orderRepository.save(ArgumentMatchers.any(Orders.class))).thenReturn(orders);
        when(itemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.ofNullable(null));

        when(itemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(null);

        ResponseEntity<String> responseEntity =  orderController.orderItems(orders);
        assertAll( "orderItemsTest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE,()->"status should be 200")
                ,()->assertEquals(responseEntity.getBody(),outOfStockRespone,()->" messages should match")
        );
    }


    @DisplayName("This method will test the getOrderDetailsByCustomerEmailId method   it will mock db ")
    @ParameterizedTest
    @ValueSource(strings={"someemail"})
    public void getOrderDetailsByCustomerEmailIdTest(String email){
        List<Orders> response=Arrays.asList(mockOrders());
        when(orderRepository.findByEmailId(ArgumentMatchers.anyString())).thenReturn(response);
        ResponseEntity<List<Orders>> responseEntity = orderController.getOrderDetailsByCustomerEmailId(email);
        assertAll( "gettest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.OK,()->"status should be 200")
                ,()->assertNotNull(responseEntity.getBody(),()->"body is not null")
        );
    }


    @DisplayName("This method will test the getOrderDetailsByCustomerEmailId  method  for negative scenario  it will mock db ")
    @ParameterizedTest
    @ValueSource(strings={"someemail"})
    public void getOrderDetailsByCustomerEmailIdTestNegative(String email){
        when(orderRepository.findByEmailId(ArgumentMatchers.anyString())).thenReturn(null);
        ResponseEntity<List<Orders>> responseEntity = orderController.getOrderDetailsByCustomerEmailId(email);
        assertAll( "gettest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR,()->"status should be 200")
                ,()->assertNull(responseEntity.getBody(),()->"body is  null")
        );
    }


    @DisplayName("This method will test the getAllOrder method   it will mock db ")
    @Test
    public void getAllOrderTest(){
        List<Orders> response=Arrays.asList(mockOrders());
        when(orderRepository.findAll()).thenReturn(response);
        ResponseEntity<List<Orders>> responseEntity = orderController.getAllOrders();
        assertAll( "getAllOrdersTest",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.OK,()->"status should be 200")
                ,()->assertEquals(responseEntity.getBody(),response,()->"body is  List")
        );
    }


    @DisplayName("This method will test the getAllOrders method for Negeate test it will mock db ")
    @Test
    public void getAllTestNegative(){
        List<Orders> response=Arrays.asList(mockOrders());
        when(orderRepository.findAll()).thenReturn(null);
        ResponseEntity<List<Orders>> responseEntity = orderController.getAllOrders();
        assertAll( "getAllTestNegative",
                ()-> assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR,()->"status should be Not found")
                ,()->assertNull(responseEntity.getBody(),()->"body is  null")
        );
    }



    private static Stream<Arguments> getOrdersOutOfStockSource(){

        return Stream.of(Arguments.of(mockOutOfStockOrders()));
    }
    private static Stream<Arguments> getOrdersOutOfStockAsItemNotFoundSource(){

        return Stream.of(Arguments.of(mockOutOfStockOrdersAsItemNotFound()));
    }

    private static Stream<Arguments> getOrdersSource(){
        return Stream.of(Arguments.of(mockOrders()));
    }

    public static Orders mockOrders() {
        Orders orders=new Orders();
        orders.setOrderId(1);
        orders.setEmailId("someemail");
        OrderedItems orderedItems=new OrderedItems();
        orderedItems.setItemId(1);
        orderedItems.setQty(1);
        orders.setOrderedItems(Arrays.asList(orderedItems));
        return orders;
    }
    public static Orders mockOutOfStockOrders() {
        Orders orders=new Orders();
        orders.setOrderId(1);
        orders.setEmailId("someemail");
        OrderedItems orderedItems=new OrderedItems();
        orderedItems.setItemId(1);
        orderedItems.setQty(2);
        orders.setOrderedItems(Arrays.asList(orderedItems));
        return orders;
    }
    public static Orders mockOutOfStockOrdersAsItemNotFound() {
        Orders orders=new Orders();
        orders.setOrderId(1);
        orders.setEmailId("someemail");
        OrderedItems orderedItems=new OrderedItems();
        orderedItems.setItemId(2);
        orderedItems.setQty(2);
        orders.setOrderedItems(Arrays.asList(orderedItems));
        return orders;
    }


}
