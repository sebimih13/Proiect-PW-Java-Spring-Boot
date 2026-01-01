package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produs")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Produs {

    @Id
    @Column(name = "id_produs")
    private Integer id;

    private String nume;
    private String descriere;
    private Integer pret;

}
