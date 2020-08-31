package com.ecom.service.impl;

import com.ecom.common.EcomUtils;
import com.ecom.model.Item;
import com.ecom.model.OrderedItems;
import com.ecom.model.Orders;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.OrderedItemsRepository;
import com.ecom.service.ItemService;
import com.ecom.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Actual Business logic for Processing the order and fetching the order details
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderedItemsRepository orderedItemsRepository;

    @Autowired
    private ItemService itemService;

    private String response;
    private boolean outOfStock;
    int totalAmount;

    @Override
    public boolean isOutOfStock(){
        return outOfStock;
    }

    @Override
    public String processOrder(Orders orders) {
        response="";
        outOfStock=false;
        totalAmount=0;
        try {
            log.info("Into Process Order method");
            process(orders.getOrderedItems());

            if(!outOfStock) {
                log.info("Total purchase amount : " + totalAmount);
                orders.setAmount(totalAmount);
                Orders ordersSaved = orderRepository.save(orders);
                log.info("order after saved into database is " + ordersSaved);
                response=EcomUtils.SUCCESS_MESSAGE+totalAmount+EcomUtils.RUPEES
                        +" Please visite the "+EcomUtils.GET_ORDER_URL+orders.getEmailId();
                return response;
            }
        }catch (Exception e){
            log.error("could not saved into db");
            e.printStackTrace();
            response= EcomUtils.ERROR_IN_DB;

        }
        return response;

    }



    @Override
    public List<Orders> getAll() {
        log.info("Before calling getAll method to Db");
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> findOrderDetailsByEmailId(String emailId) {
        log.info("before Db call ");
        return orderRepository.findByEmailId(emailId);
    }

    private void process(List<OrderedItems> orderedItemsList) {
        log.info("started processing/calculating the order ");

        for(OrderedItems e:orderedItemsList){
            Optional<Item> itemOptional=itemService.findById(e.getItemId());

            if(itemOptional.isPresent()){
                Item item=itemOptional.get();
                log.info("item available in the cart is "+item);
                if( e.getQty()!=0 && e.getQty()<=item.getQty()){
                    totalAmount+=e.getQty()*item.getPrice();
                    item.setQty(item.getQty()-e.getQty());
                    itemService.save(item);
                }
                else if(e.getQty()==0){
                    response=EcomUtils.ITEMS_QUANTITY_SHOULD_NOT_BE_ZERO+" for item id "+e.getItemId()+" or remove that item from the cart/json";
                    outOfStock=true;
                    break;
                }
                else{

                     response=item.getQty()>0?"item "+item.getName()+" with id="+e.getItemId()+" is the first item we found out of stock for your purchase You can purchase only "+item.getQty()+" "+item.getName()
                             :"Item " +item.getName()+" with id="+e.getItemId()+" is out of Stock please visit after some time.";
                     outOfStock=true;
                     break;
                }
            }else {
                response="Item with id "+e.getItemId()+" not available please visit after some time.";
                outOfStock=true;
                break;
            }
        }

    }
}
