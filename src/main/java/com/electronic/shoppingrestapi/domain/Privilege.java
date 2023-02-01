package com.electronic.shoppingrestapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "privilege")
public class Privilege extends BaseEntity {

    private String privilege;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "privileges")
    @JsonIgnoreProperties("privileges")
    private List<Role> roles = new ArrayList<>();
}
