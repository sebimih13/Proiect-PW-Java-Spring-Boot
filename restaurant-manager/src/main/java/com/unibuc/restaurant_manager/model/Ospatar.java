package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ospatar")
public class Ospatar extends Angajat {

    @Column(name = "nivel_engleza")
    private String nivelEngleza;

}
