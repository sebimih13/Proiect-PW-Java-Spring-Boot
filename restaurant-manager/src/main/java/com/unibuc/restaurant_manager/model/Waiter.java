package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "waiter")
public final class Waiter extends Employee {

    private String englishLevel;

}
