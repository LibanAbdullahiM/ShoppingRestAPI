package com.electronic.shoppingrestapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image extends BaseEntity{

    @Lob
    private Byte[] image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("images")
    private Product product;
}
