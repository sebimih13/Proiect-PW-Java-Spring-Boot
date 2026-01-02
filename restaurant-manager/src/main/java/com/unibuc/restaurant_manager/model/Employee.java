package com.unibuc.restaurant_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends User {

    private Integer salary;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String CNP;

    @Column(nullable = false)
    private String IDSeries;

    @Column(nullable = false)
    private String IDNumber;

    private LocalDate employmentDate;
    private Integer workHoursPerDay;
    private Integer ptoDays;

    @ManyToOne
    @JoinColumn(name = "id_manager")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    @JsonBackReference
    private Restaurant restaurant;

}
