package com.unibuc.restaurant_manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "restaurant")
public final class Restaurant {

    @Id
    @Column(name = "id_restaurant")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer stars;
    private String city;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private List<Employee> employees;

    @OneToMany(mappedBy = "restaurant")
    private List<PurchaseOrder> orders;

}
