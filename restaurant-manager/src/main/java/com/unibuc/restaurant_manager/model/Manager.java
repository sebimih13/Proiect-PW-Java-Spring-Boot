package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manager")
public class Manager extends Angajat {

    @Column(name = "nivel_educatie")
    private String nivelEducatie;

}
