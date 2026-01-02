package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "purchase_order")
public final class PurchaseOrder {

    public enum Status {
        PENDING,
        COMPLETED,
        CANCELED
    }

    @Id
    @Column(name = "id_purchase_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;
    private LocalDate date;
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<Contains> products;

}
