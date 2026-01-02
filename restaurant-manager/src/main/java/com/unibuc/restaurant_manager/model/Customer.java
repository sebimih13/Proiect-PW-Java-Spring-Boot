package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.JOINED)
public final class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<PurchaseOrder> orders;

    private String address;
    private Integer loyaltyPoints;

    // TODO: lastVisitDate
    // TODO: feedbackScore

}
