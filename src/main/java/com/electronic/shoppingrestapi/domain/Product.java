package com.electronic.shoppingrestapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    @Lob
    private String description;

    private String brand;
    private float price;
    private int inStock;

    //@Lob
    //private Byte[] image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnoreProperties("product")
    private List<Image> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    private Category category;
}
