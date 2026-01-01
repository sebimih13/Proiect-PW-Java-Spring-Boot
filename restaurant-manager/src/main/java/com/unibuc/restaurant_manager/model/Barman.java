package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "barman")
public class Barman extends Angajat {

    private String specializare;

}
