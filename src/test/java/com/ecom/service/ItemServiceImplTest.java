package com.ecom.service;

import com.ecom.model.Item;
import com.ecom.repository.ItemRepository;
import com.ecom.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)  //takes care of  all the spring  annotation test
@ExtendWith(MockitoExtension.class)  // checks redundent mocking // also no need to add Mockito.initMock(this);
public class ItemServiceImplTest {

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @MockBean
    private ItemRepository itemRepository;

    @DisplayName("This method will test the save method  it will mock db ")
    @ParameterizedTest
    @MethodSource("getItemSource")
    public void saveTest(Item item){
        when(itemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(item);
        Item itemResponse =  itemServiceImpl.save(item);
        assertAll( "savetest",
                ()-> assertEquals(itemResponse.getId(), item.getId(),()->"item id should be matched"),
                ()-> assertEquals(itemResponse.getName(), item.getName(),()->"item name should be matched")

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
