package com.electronic.shoppingrestapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity{


    @Column(name = "quantity")
    private int quantity;

    @Column(name = "subtotal_price")
    private float subtotalPrice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;
}
