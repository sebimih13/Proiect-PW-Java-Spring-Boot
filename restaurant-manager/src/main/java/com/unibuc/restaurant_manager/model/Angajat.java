package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "angajat")
public abstract class Angajat extends Utilizator {

    private Integer salariu;

    @ManyToOne
    @JoinColumn(name = "id_manager")
    private Angajat manager;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

}
