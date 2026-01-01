package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "employee")
public abstract class Employee extends User {

    private Integer salary;

    private LocalDate birthDate;
    private LocalDate employmentDate;
    private Integer workHoursPerDay;
    private Integer ptoDays;

    @ManyToOne
    @JoinColumn(name = "id_manager")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

}
