package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "bartender")
public final class Bartender extends Employee {

    public static Bartender fromEmployee(Employee employee) {
        return Bartender.builder()
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

}
