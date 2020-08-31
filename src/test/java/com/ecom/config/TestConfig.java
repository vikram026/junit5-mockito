package com.ecom.config;

import com.ecom.repository.ItemRepository;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.OrderedItemsRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {

    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    OrderedItemsRepository orderedItemsRepository;
}