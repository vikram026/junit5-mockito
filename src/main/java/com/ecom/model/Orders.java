package com.ecom.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * ACTUAL ORDER PLACED PER UER
 * also  //used hibernate one-to-many mapping
 */
@Data
@Entity
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    //using hibernate one-to-many mapping
    @OneToMany(targetEntity = OrderedItems.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId",referencedColumnName = "orderId")
    private List<OrderedItems> orderedItems;

    private String emailId;
    private int amount;
}
