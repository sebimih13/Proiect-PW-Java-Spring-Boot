package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {

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
    private List<Employee> employees;

    @OneToMany(mappedBy = "restaurant")
    private List<PurchaseOrder> orders;

}
