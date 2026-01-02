package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "manager")
@Inheritance(strategy = InheritanceType.JOINED)
public final class Manager extends Employee {

    public static Manager fromEmployee(Employee employee) {
        return Manager.builder()
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

    private String educationLevel;

}
