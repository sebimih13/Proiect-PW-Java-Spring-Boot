package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cook")
public final class Cook extends Employee {

    public static Cook fromEmployee(Employee employee) {
        return Cook.builder()
                .username(employee.getUsername())
                .password(employee.getPassword())
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .birthDate(employee.getBirthDate())
                .CNP(employee.getCNP())
                .IDSeries(employee.getIDSeries())
                .IDNumber(employee.getIDNumber())
                .employmentDate(employee.getEmploymentDate())
                .workHoursPerDay(employee.getWorkHoursPerDay())
                .ptoDays(employee.getPtoDays())
                .manager(employee.getManager())
                .restaurant(employee.getRestaurant())
                .build();
    }

    private String specialization;

    @ManyToMany
    @JoinTable(
            name = "cook_dish",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_product")
    )
    private List<Dish> dishes;

}
